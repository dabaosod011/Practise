#include <iostream>
#include <thread>
#include <future> 

auto promise = std::promise<std::string>();
auto producer = std::thread([&]{
    promise.set_value("Hello World\n");
    // can not set again.
    //promise.set_value("Again!");     
});

auto future = promise.get_future();
auto consumer = std::thread([&]{
    std::cout << future.get();
});

int main()
{
    producer.join();
    consumer.join();

    return 0;
}
