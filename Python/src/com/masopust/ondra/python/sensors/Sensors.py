'''
Created on Sep 4, 2017

@author: Ondra
'''

import threading
from com.masopust.ondra.python.tcpCommunication.TCPCommunication import TCPCommunication
import time
from com.masopust.ondra.python.motors.Motors import Motors
from smbus2 import SMBusWrapper
import math


class Sensors (threading.Thread):
    
    def __init__(self, queue):
        self.daemon = True
        self.waitTime = 0.025
        self.sensorMotor = Motors(22, [21, 23])
        self.q = queue
        #this is the variable that tells how many dots will be displayed on the screen
        self.resolution = self.initSens()
    
    def run(self):
        '''
        This function is called when this thread is started by the start() function in the Main class.
        '''
        while self.q.empty():
            for index in range(0, self.resolution):
                message = "dt" + index
                distance = self.measure()
                message += str(distance)
                TCPCommunication.sendToHost(message)
                time.sleep(self.waitTime)
            #wait for the loop of the sensor to be completed and handle some not propre behaviour
        #stop sensor motor
        self.sensorMotor.clean()
        #send ACK back to the queue
        self.q.put(True)
    
    def initSens(self):
        pass
    
    def measure(self):
        '''
        This function reads the data from the AVR and returns the measured distance
        
        :return
        '''
        with SMBusWrapper(1) as bus:
            val = bus.read_i2c_block_data(0x0A, 0, 4)
            longSensor = (val[2] << 8) | val[3]
            longVoltage = longSensor * (5 / 1024)
            longDist = 916.64 * math.pow(longVoltage, -2.236)
        return longDist