'''
Created on Dec 23, 2017

Used just to test, that the I2C communication works.

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