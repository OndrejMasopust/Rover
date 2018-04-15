#!/usr/bin/env python3.6
'''
This module is just to test the speed of reading over I2C.

MIT License

Copyright (c) 2018 Ondřej Masopust; Gymnázium Praha 6, Nad Alejí 1952

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


@author: Ondrej Masopust
'''

import time
from smbus2 import SMBusWrapper
import math

def Main():
    for i in range(0, 100):
        print(time.time())
        with SMBusWrapper(1) as bus:
            val = bus.read_i2c_block_data(0x0A, 0, 4)
            longSensor = int((val[2] << 8) | val[3])
            longVoltage = longSensor * (5 / 1024)
            if longVoltage == 0:
                # so that we don't get the math domain error
                longVoltage = 1
            longDist = round(916.64 * math.pow(longVoltage, -2.236))
        print(time.time())
        print("\n")

if __name__ == '__main__':
    Main()