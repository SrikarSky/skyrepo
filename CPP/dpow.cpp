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

using namespace std;

/* Dont use lists if you can help it */
/* 
 
 using std::list;
 using std::string;
 list<string> myList;
 myList.push_back("Hello");
 */

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

double dpow(int base, int ex)
{
    int exp = abs(ex);
    double result = 1;
    while (exp)
    {
        if (exp & 1)
            result *= base;
        exp >>= 1;
        base *= base;
    }
    
    return (ex > 0) ? result : 1/result;
}


int main(int argc, const char * argv[])
{

    // insert code here...
    std::cout << "Hello, World!\n";
    //if exp is greater than -1
    int res = ipow(5,4);
    std::cout<<res<<std::endl;
    
    double n = dpow(5, -4);
    std::cout<<n<<std::endl;
    
    vector<string> data;
    data.push_back("my name");
    
    
    return 0;
}



