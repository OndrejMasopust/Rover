'''
Created on Dec 23, 2017

@author: Ondrej Masopust
'''

from smbus2 import SMBusWrapper

class I2C:
    '''
    classdocs
    '''

    def __init__(self):
        '''
        Constructor
        '''
    
    def readSensors(self):
        with SMBusWrapper(1) as bus:
            data = bus.read_i2c_block_data(0x0A, 0, 4) 
        return data