#!/usr/bin/env python3.6

import timeit

def test():
    message = "dt"
    message += str(0)
    message += str(1)
    message += str(340)

def Main():
    print(timeit.timeit("test()", setup="from __main__ import test", number=1))

if __name__ == '__main__':
    Main()