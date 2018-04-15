'''
Created on Dec 23, 2017

Used just to test, that the I2C communication works.

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
from smbus2 import SMBusWrapper
import math

def Main():
    # the 'with' block with SMBusWrapper closes the bus automatically
    with SMBusWrapper(1) as bus:
        val = bus.read_i2c_block_data(0x0A, 0, 4)
        shortSensor = (val[0] << 8) | val[1]
        longSensor = (val[2] << 8) | val[3]
        shortVoltage = shortSensor * (5 / 1024)
        #shortDist =  math.pow(13.401 / shortVoltage, 1 / 0.631)
        longVoltage = longSensor * (5 / 1024)
        longDist = 916.64 * math.pow(longVoltage, -2.236)
        print(val)
        print("\nshortSensor: %d\n" % (shortSensor))
        print("shortSensor: {0:b}\n".format(shortSensor))
        print("val[0] << 8: {0:b}\n".format(val[0] << 8))
        print("val[1]: {0:b}\n".format(val[1]))
        #print("shortDist: %d\n\n" % (shortDist))
        
        print("longSensor: %d\n" % (longSensor))
        print("longSensor: {0:b}\n".format(longSensor))
        print("val[2] << 8: {0:b}\n".format(val[2] << 8))
        print("val[3]: {0:b}\n".format(val[3]))
        print("longtDist: %d\n" % (longDist))

if __name__ == '__main__':
    Main()