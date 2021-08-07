#include <iostream>
#include <vector>
#include <numeric>
#include <string>

int abs_sum(int sum, int a)
{
    return sum + (a >=0 ? a: -a);
}

int main()
{
    std::vector<int> data;
    data.push_back(1);
    data.push_back(2);
    data.push_back(3);
    data.push_back(4);
    data.push_back(5);
    data.push_back(-6);
    data.push_back(-7);
    data.push_back(-8);
    data.push_back(-9);
    data.push_back(-10);

    int sum = std::accumulate(data.begin(), data.end(), 0, abs_sum);

    std::cout << "sum = " << sum << std::endl;

    return 0;
}
