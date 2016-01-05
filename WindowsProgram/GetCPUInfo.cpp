// GetCPUInfo.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <windows.h>
#include <iostream>

void displayCPUInfo()
{
	/*
	typedef struct _SYSTEM_INFO {
		  union {
			DWORD  dwOemId;
			struct {
			  WORD wProcessorArchitecture;
			  WORD wReserved;
			};
		  };
		  DWORD     dwPageSize;
		  LPVOID    lpMinimumApplicationAddress;
		  LPVOID    lpMaximumApplicationAddress;
		  DWORD_PTR dwActiveProcessorMask;
		  DWORD     dwNumberOfProcessors;
		  DWORD     dwProcessorType;
		  DWORD     dwAllocationGranularity;
		  WORD      wProcessorLevel;
		  WORD      wProcessorRevision;
		} SYSTEM_INFO;
	*/

	SYSTEM_INFO siSysInfo; 
	GetSystemInfo(&siSysInfo); 
	
	std::cout<<"Number of processors : "<< siSysInfo.dwNumberOfProcessors <<std::endl;
	std::cout<<"Processor type: "<< siSysInfo.dwProcessorType <<std::endl;
	std::cout<<"Active processor mask: "<< siSysInfo.dwActiveProcessorMask <<std::endl;
	std::cout<<"Processor Architecture: "<< siSysInfo.wProcessorArchitecture <<std::endl;
	std::cout<<"Processor Level: "<< siSysInfo.wProcessorLevel <<std::endl;
	std::cout<<"Processor Revision: "<< siSysInfo.wProcessorRevision <<std::endl;

	std::cout<< std::endl<<"Page Size: "<< siSysInfo.dwPageSize <<std::endl;
	std::cout<<"Minimum Application Address: "<< siSysInfo.lpMinimumApplicationAddress <<std::endl;
	std::cout<<"Maximum Application Address: "<< siSysInfo.lpMaximumApplicationAddress <<std::endl;
	std::cout<<"Allocation Granularity: "<< siSysInfo.dwAllocationGranularity <<std::endl;
}

 void debug4Process()
{
	DWORD  dwSystemAffinity;
	DWORD  dwProcessAffinity;
	::GetProcessAffinityMask(GetCurrentProcess(), (PDWORD_PTR)&dwProcessAffinity, (PDWORD_PTR)&dwSystemAffinity);
	std::cout<< dwProcessAffinity << std::endl;
	std::cout<< dwSystemAffinity << std::endl;

	::SetProcessAffinityMask(GetCurrentProcess(), 4L);	//bind crnt process to CPU #2

	::GetProcessAffinityMask(GetCurrentProcess(), (PDWORD_PTR)&dwProcessAffinity, (PDWORD_PTR)&dwSystemAffinity);
	std::cout<< dwProcessAffinity << std::endl;
	std::cout<< dwSystemAffinity << std::endl;
	while(true)
	{
	}
}

DWORD WINAPI threadworker(LPVOID n)
 {
	while(true)
	{
		//std::cout<<".";
	}
 }

  void debug4Thread()
{
	DWORD  dwSystemAffinity;
	DWORD  dwProcessAffinity;

	//::SetProcessAffinityMask(GetCurrentProcess(), 4L); //bind crnt process to CPU #2
	::GetProcessAffinityMask(GetCurrentProcess(), (PDWORD_PTR)&dwProcessAffinity, (PDWORD_PTR)&dwSystemAffinity);
	std::cout<< dwProcessAffinity << std::endl;
	
	HANDLE aThreadhandle ;
	aThreadhandle = CreateThread(NULL,0,threadworker,LPVOID(1),0,NULL);
	if (aThreadhandle == NULL)
	{
		std::cout<<"Create Thread error!" << std::endl;
	 }

	::SetThreadAffinityMask(aThreadhandle,16L);//bind crnt thread to CPU #4	
	::GetProcessAffinityMask(GetCurrentProcess(), (PDWORD_PTR)&dwProcessAffinity, (PDWORD_PTR)&dwSystemAffinity);
	std::cout<< dwProcessAffinity << std::endl;

	WaitForSingleObject( aThreadhandle, INFINITE );
	CloseHandle(aThreadhandle);
}

int _tmain(int argc, _TCHAR* argv[])
{
	//displayCPUInfo();
	//debug4Process();
	debug4Thread();
	return 0;
}

