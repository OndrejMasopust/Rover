'''
Created on Sep 4, 2017

@author: Ondrej Masopust
'''

import threading
import time
from com.masopust.ondra.python.motors.Motors import Motors
from smbus2 import SMBusWrapper
import math


class Sensors (threading.Thread):
    '''
    This class is used to control the distance sensor of the Rover.
    '''

    def __init__(self, queue, tcpCommunication):
        '''
        This constructor assigns given arguments to instace variables, sets the thread as daemon and calls initSens method in order to get the resolution.
        
        :param queue: The LifoQueue instance that is used to exchange information between this thread and the main thread.
            It contains, whether the client disconnected and if the measuring was stopped.
        :type queue: LifoQueue
        :param tcpCommunication: Instance of the TCPCommunication class that is used to send data to the client.
        :type tcpCommunication: TCPCommunication
        '''
        self.daemon = True
        self.waitTime = 0.025
        self.sensorMotor = Motors(22, [21, 23])
        self.q = queue
        self.tcpCommunication = tcpCommunication
        # this is the variable that tells how many dots will be displayed on the screen
        self.resolution = self.initSens()
    
    def run(self):
        '''
        This method is called when this thread is started by the start() method in the Main class.
        '''
        while self.q.empty():
            for index in range(0, self.resolution):
                message = "dt" + index
                distance = self.measure()
                message += str(distance)
                self.tcpCommunication.sendToHostWrapper(message)
                time.sleep(self.waitTime)
            # wait for the loop of the sensor to be completed and handle some not propre behaviour
        # stop sensor motor
        self.sensorMotor.clean()
        # send ACK back to the queue
        self.q.put(True)
    
    def initSens(self):
        '''
        This method starts the sensor motor and counts how fast it makes one rotation and based on that,
        it counts how many dots will be displayed on the client's computer screen.
        '''
        pass
    
    def measure(self):
        '''
        This method reads the data from the AVR and returns the measured distance
        
        :return: Measured distance
        :rtype: float
        '''
        with SMBusWrapper(1) as bus:
            val = bus.read_i2c_block_data(0x0A, 0, 4)
            longSensor = (val[2] << 8) | val[3]
            longVoltage = longSensor * (5 / 1024)
            longDist = 916.64 * math.pow(longVoltage, -2.236)
        return longDist
