#!/usr/bin/env python3.6

import time
from smbus2 import SMBusWrapper
import math

def Main():
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