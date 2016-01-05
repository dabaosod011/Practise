#include <iostream>
#include <string>

long long int karatsubaMul(std::string num1, std::string num2)
{
	if (num1.size() == 1 || num2.size() ==1)
		return std::stoi(num1) * std::stoi(num2);
	else 
	{
		std::string num1_high = num1.substr(0, num1.size()/2) ; 
		std::string num1_low = num1.substr(num1.size()/2) ; 
		std::string num2_high = num2.substr(0, num2.size()/2) ; 
		std::string num2_low = num2.substr(num2.size()/2) ;

		long long int part1 = karatsubaMul(num1_high,num2_high) * pow(10, (num1.size() + num2.size()) / 2); // a*c
		long long int part2 = karatsubaMul(num1_low,num2_low);   // b*d
		long long int part3 = karatsubaMul(num1_high,num2_low) * pow(10, num1.size()/2) ;   // a*d
		long long int part4 = karatsubaMul(num1_low,num2_high ) * pow(10, num2.size()/2);   // b*c

		return  part1 + part2 + part3 + part4;		
	}
		
}

void main()
{
	std::string num1 = "1234";
	std::string num2 = "5678";

	long long int result = karatsubaMul(num1,num2);
	std::cout << "the result is " << result <<std::endl;
}