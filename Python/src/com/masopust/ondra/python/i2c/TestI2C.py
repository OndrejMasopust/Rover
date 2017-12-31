'''
Created on Dec 23, 2017

@author: Ondra
'''
from smbus2 import SMBusWrapper

def Main():
    # the 'with' block with SMBusWrapper closes the bus automatically
    with SMBusWrapper(1) as bus:
        val = bus.read_i2c_block_data(0x0A, 0, 4)
        print(val)

if __name__ == '__main__':
    Main()