'''
Created on Dec 23, 2017

@author: Ondrej Masopust
'''

from smbus2 import SMBusWrapper

class I2C:
    '''
    This class is used to communicate via I2C with other devices.
    '''

    def __init__(self):
        '''
        Constructor
        '''
    
    def readSensors(self):
        with SMBusWrapper(1) as bus:
            data = bus.read_i2c_block_data(0x0A, 0, 4) 
        return data