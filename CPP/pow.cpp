//
//  main.cpp
//  CPPProgs
//
//  Created by Srikar Surendra on 10/6/14.
//  Copyright (c) 2014 CMagic. All rights reserved.
//

#include <iostream>
#include <vector>  //for std::vector
#include <string>  //for std::string
using std::vector;
using std::string;

int ipow(int base, int exp)
{
    int result = 1;
    while (exp)
    {
        if (exp & 1)
            result *= base;
        exp >>= 1;
        base *= base;
    }
    
    return result;
}


int main(int argc, const char * argv[])
{

    std::cout << "Hello, World!\n";
    //if exp is greater than -1
    int res = ipow(5,4);
    std::cout<<res;
    /*
    vector<string> data;
    data.push_back("my name");
    */
    
    return 0;
}



