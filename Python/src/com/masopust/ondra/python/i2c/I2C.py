'''
Created on Dec 23, 2017

@author: Ondrej Masopust
'''

from smbus2 import SMBus

class I2C:
    '''
    classdocs
    '''
    
    global bus

    def __init__(self):
        '''
        Constructor
        '''
        self.bus = SMBus(1)
    
    def write(self, deviceAddress = 0x00, register = 0, data = 0):
        if (data > 256):
            print("The value needs to be less than 256.\n")
        else:
            self.bus.write_byte_data(deviceAddress, register, data)
    
    def close(self):
        bus.close()