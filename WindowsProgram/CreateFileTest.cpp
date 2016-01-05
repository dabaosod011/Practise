#include <iostream>
#include <string>
#include <vector>
#include <Windows.h>

#define BUFFERSIZE 100

struct Test {
  int data;
  Test() { std::cout << "Test::Test()" << std::endl; }
  ~Test() { std::cout << "Test::~Test()" << std::endl; }
};
 
int main()
{
	// Must allocate our own memory
	Test *ptr = (Test *)malloc(sizeof(Test));
 
	 // Use placement new
	new (ptr) Test;
 
	// Must call the destructor ourselves
	ptr->~Test();
 
	 // Must release the memory ourselves
	free(ptr);
	
  /*
	std::wstring filename = L"testread.txt";
	HANDLE hFile = CreateFile(filename.c_str(), GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_ALWAYS, 0, NULL);

	if (hFile == INVALID_HANDLE_VALUE)
	{
		std::wcout<< "Open file: " << filename << " failed!" << std::endl;
	} else
	{
		//	part 1: file size
		LARGE_INTEGER filesize;
		if (GetFileSizeEx(hFile, &filesize))
		{
			std::wcout <<"The size of " << filename << " is " << filesize.QuadPart << std::endl; 		
		}

		DWORD filesize2 = GetFileSize(hFile, NULL);
		std::wcout <<"The size of " << filename << " is " << filesize2 << std::endl; 

		// read file content
		char  pbuffer[BUFFERSIZE + 1] = {0};
		DWORD dwNumBytes; 
		ReadFile(hFile, pbuffer, BUFFERSIZE, &dwNumBytes, NULL);
		std::cout <<"The content is: " << pbuffer << std::endl; 
		ReadFile(hFile, pbuffer, BUFFERSIZE, &dwNumBytes, NULL);
		std::cout <<"The content is: " << pbuffer << std::endl; 
	}

	CloseHandle(hFile);	
	

	std::vector<int>  container;
	container.push_back(1);
	container.push_back(18);
	std::cout<<*(container.end()) <<std::endl;

	std::vector<std::string>  container2;
	std::vector<std::string>::iterator it = container2.begin();
	std::cout<< it->size() <<std::endl;
	*/
	return 0;
}