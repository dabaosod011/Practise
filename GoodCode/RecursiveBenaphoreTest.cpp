#include <windows.h>
#include <stdio.h>
#include <intrin.h>


#define LIGHT_ASSERT(x) { if (!(x)) DebugBreak(); }
 
//-------------------------------------
//  RecursiveBenaphore
//-------------------------------------
class RecursiveBenaphore
{
private:
    LONG m_counter;
    DWORD m_owner;
    DWORD m_recursion;
    HANDLE m_semaphore;
 
public:
    RecursiveBenaphore::RecursiveBenaphore()
    {
        m_counter = 0;
        m_owner = 0;            // an invalid thread ID
        m_recursion = 0;
        m_semaphore = CreateSemaphore(NULL, 0, 1, NULL);
    }
 
    RecursiveBenaphore::~RecursiveBenaphore()
    {
        CloseHandle(m_semaphore);
    }
 
    void Lock()
    {
        DWORD tid = GetCurrentThreadId();
        if (_InterlockedIncrement(&m_counter) > 1)
        {
            if (tid != m_owner)
                WaitForSingleObject(m_semaphore, INFINITE);
        }
        //--- We are now inside the Lock ---
        m_owner = tid;
        m_recursion++;
    }
 
    void Unlock()
    {
        DWORD tid = GetCurrentThreadId();
        LIGHT_ASSERT(tid == m_owner);
        DWORD recur = --m_recursion;
        if (recur == 0)
            m_owner = 0;
        DWORD result = _InterlockedDecrement(&m_counter);
        if (result > 0)
        {
            if (recur == 0)
                ReleaseSemaphore(m_semaphore, 1, NULL);
        }
        //--- We are now outside the Lock ---
    }
 
    bool TryLock()
    {
        DWORD tid = GetCurrentThreadId();
        if (m_owner == tid)
        {
            // Already inside the lock
            _InterlockedIncrement(&m_counter);
        }
        else
        {
            LONG result = _InterlockedCompareExchange(&m_counter, 1, 0);
            if (result != 0)
                return false;
            //--- We are now inside the Lock ---
            m_owner = tid;
        }
        m_recursion++;
        return true;
    }
};


//-------------------------------------
//  MersenneTwister
//-------------------------------------
#define MT_IA  397
#define MT_LEN 624

class MersenneTwister
{
    unsigned long m_buffer[MT_LEN];
    int m_index;

public:
    MersenneTwister();
    unsigned long integer();
    float fraction() { return integer() * 2.3283e-10f; }
};

MersenneTwister::MersenneTwister()
{
    for (int i = 0; i < MT_LEN; i++)
        m_buffer[i] = rand();
    m_index = 0;
    for (int i = 0; i < MT_LEN * 100; i++)
        integer();
}

unsigned long MersenneTwister::integer()
{
    // Indices
    int i = m_index;
    int i2 = m_index + 1; if (i2 >= MT_LEN) i2 = 0; // wrap-around
    int j = m_index + MT_IA; if (j >= MT_LEN) j -= MT_LEN; // wrap-around

    // Twist
    unsigned long s = (m_buffer[i] & 0x80000000) | (m_buffer[i2] & 0x7fffffff);
    unsigned long r = m_buffer[j] ^ (s >> 1) ^ ((s & 1) * 0x9908B0DF);
    m_buffer[m_index] = r;
    m_index = i2;

    // Swizzle
    r ^= (r >> 11);
    r ^= (r << 7) & 0x9d2c5680UL;
    r ^= (r << 15) & 0xefc60000UL;
    r ^= (r >> 18);
    return r;
}


//-------------------------------------
//  ThreadStats
//-------------------------------------
struct ThreadStats
{
    int iterations;
    int workUnitsComplete;
    int amountIncremented;

    ThreadStats()
    {
        iterations = 0;
        workUnitsComplete = 0;
        amountIncremented = 0;
    }

    void Accumulate(const ThreadStats &other)
    {
        iterations += other.iterations;
        workUnitsComplete += other.workUnitsComplete;
        amountIncremented += other.amountIncremented;
    }
};


//-------------------------------------
//  Global state
//-------------------------------------
static const int kMaxThreads = 4;       // This test is hardcoded for Quad-Core Xeon
ThreadStats g_threadStats[kMaxThreads];

HANDLE g_initSemaphore = INVALID_HANDLE_VALUE;
HANDLE g_startEvent = INVALID_HANDLE_VALUE;
RecursiveBenaphore g_lock;

// No need for volatile. The compiler understands that they are protected by g_lock.
int g_counter = 0;
bool g_done = false;


//-------------------------------------
//  ThreadProc
//-------------------------------------
DWORD WINAPI ThreadProc(LPVOID lpParameter)
{
    // Initialize
    ThreadStats localStats;
    MersenneTwister random;
    int lockCount = 0;
    int lastCounter = 0;
    int threadNumber = (int) lpParameter;
    LIGHT_ASSERT(threadNumber < kMaxThreads);

    // Indicate ready, wait for start signal
    ReleaseSemaphore(g_initSemaphore, 1, NULL);
    WaitForSingleObject(g_startEvent, INFINITE);

    // Go
    for (;;)
    {
        localStats.iterations++;

        // Do a random amount of work in the range [0, 10) units, biased towards low numbers.
        float f = random.fraction();
        int workUnits = (int) (f * f * 10);
        for (int i = 1; i < workUnits; i++)
            random.integer();       // Do one work unit
        localStats.workUnitsComplete += workUnits;

        // Consistency check
        if (lockCount > 0)
        {
            LIGHT_ASSERT(g_counter == lastCounter);
        }

        // Decide what the new Lock count should be in the range [0, 4), biased towards low numbers.
        f = random.fraction();
        int desiredLockCount = (int) (f * f * 4);

        // Perform unlocks, if any
        while (lockCount > desiredLockCount)
        {
            g_lock.Unlock();
            lockCount--;
        }

        // Perform locks, if any
        bool useTryLock = (random.integer() & 1) == 0;
        while (lockCount < desiredLockCount)
        {
            if (useTryLock)
            {
                if (!g_lock.TryLock())
                    break;
            }
            else
            {
                g_lock.Lock();
            }
            lockCount++;
        }

        // If locked, increment counter
        if (lockCount > 0)
        {
            LIGHT_ASSERT((g_counter - lastCounter) >= 0);
            g_counter += threadNumber + 1;
            lastCounter = g_counter;
            localStats.amountIncremented += threadNumber + 1;
        }

        // Check for break out of loop
        if (g_done)
            break;
    }

    // Release Lock if still holding it
    while (lockCount > 0)
    {
        g_lock.Unlock();
        lockCount--;
    }

    // Copy statistics
    g_threadStats[threadNumber] = localStats;
    return 0;
}

//-------------------------------------
//  PerformStressTest
//-------------------------------------
void PerformStressTest(int threadCount, bool useAffinities, int milliseconds)
{
    LIGHT_ASSERT(threadCount <= kMaxThreads);
    g_counter = 0;
    g_done = false;
    printf("Spawning %d threads %s affinities for %d milliseconds...\n", threadCount, useAffinities ? "with" : "without", milliseconds);

    // Spawn threads, wait for all ready
    ResetEvent(g_startEvent);
    HANDLE threads[kMaxThreads];
    for (int t = 0; t < threadCount; t++)
    {
        threads[t] = CreateThread(NULL, 0, ThreadProc, (LPVOID) t, 0, NULL);
        if (useAffinities)
            SetThreadAffinityMask(threads[t], 1 << (t * 2));    // Hardcoded for Quad-Core Xeon
    }
    for (int t = 0; t < threadCount; t++)
        WaitForSingleObject(g_initSemaphore, INFINITE);

    // Start threads
    SetEvent(g_startEvent);

    // Let them run
    Sleep(milliseconds);

    // Stop them
    g_done = true;
    WaitForMultipleObjects(threadCount, threads, TRUE, INFINITE);

    // Close handles
    for (int t = 0; t < threadCount; t++)
        CloseHandle(threads[t]);

    // Gather stats
    ThreadStats totalStats;
    for (int t = 0; t < threadCount; t++)
        totalStats.Accumulate(g_threadStats[t]);
    LIGHT_ASSERT(totalStats.amountIncremented == g_counter);
    printf("%d total iterations, %d workUnits, g_counter=%d\n", totalStats.iterations, totalStats.workUnitsComplete, g_counter);
}


//-------------------------------------
//  main
//-------------------------------------
int main()
{
    // Initialize
    SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_ABOVE_NORMAL);    // This is the watchdog thread
    g_initSemaphore = CreateSemaphore(NULL, 0, kMaxThreads, NULL);
    g_startEvent = CreateEvent(NULL, TRUE, FALSE, NULL);

    // Test
    for (int iterations = 0; iterations < 10; iterations++)
        for (int affinities = 0; affinities < 2; affinities++)
            for (int threadCount = 2; threadCount <= kMaxThreads; threadCount++)
                PerformStressTest(threadCount, affinities == 0, 2000);

    // Shutdown
    CloseHandle(g_startEvent);
    CloseHandle(g_initSemaphore);
	return 0;
}

