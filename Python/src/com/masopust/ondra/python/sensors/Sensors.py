'''
Created on Sep 4, 2017

@author: Ondrej Masopust
'''

import threading
import time
import math
import RPi.GPIO as gpio
from smbus2 import SMBusWrapper
from com.masopust.ondra.python.motors.Motors import Motors
from com.masopust.ondra.python.motors.Direction import Direction

class Sensors (threading.Thread):
    '''
    This class is used to control the distance sensor of the Rover.
    '''

    def __init__(self, queue, tcpCommunication):
        '''
        This constructor assigns given arguments to instance variables, sets the thread as daemon, sets up an interrupt
        that is executed when the sensor finishes one rotation and calls initSens method in order to get the resolution.

        :param queue: The LifoQueue instance that is used to exchange information between this thread and the main thread.
            It contains, whether the client disconnected and if the measuring was stopped.
        :type queue: LifoQueue
        :param tcpCommunication: Instance of the TCPCommunication class that is used to send data to the client.
        :type tcpCommunication: TCPCommunication
        '''
        self.daemon = True
        self.q = queue
        self.tcpCommunication = tcpCommunication
        
        self.sensorMotor = Motors(22, [21, 23])
        self.sensorMotor.setDirection(Direction.RIGHT)
        
        # TODO comment on this as it is not clear what it is
        self.waitTime = 0.025

        gpio.setmode(gpio.BCM)
        # set the pin 22, which is connected to the output of the optolatch, as input
        gpio.setup(22, gpio.IN)
        # set up an interrupt
        gpio.add_event_detect(22, gpio.FALLING, callback=self.__isr)
        self.myTime = time.time()

        # time in seconds that it takes for the sensor to take one measurement
        self.CONVERSIONTIME = 0.02
        self.rotationCounter = 0
        # this is the variable that tells how many dots will be displayed on the screen
        self.resolution = self.initSens()
    
    def run(self):
        '''
        This method is called when this thread is started by the start() method in the Main class.
        '''
        while self.q.empty():
            for index in range(0, self.resolution):
				if self.q.empty():
	                message = "dt" + index
    	            distance = self.measure()
        	        message += str(distance)
            	    self.tcpCommunication.sendToHostWrapper(message)
                	time.sleep(self.waitTime)
                else:
                	break
        # stop sensor motor
        self.sensorMotor.clean()
        # send ACK back to the queue
        self.q.put(True)
    
    def initSens(self):
        '''
        This method starts the sensor motor and based on how fast it makes one rotation,
        it counts how many dots will be displayed on the client's computer screen.
        '''
        self.sensorMotor.run(xx)    # FIXME set the duty cycle
        while self.rotationCounter < 3:
            pass
        self.resolution = round(self.rotationTime / self.CONVERSIONTIME)
    
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
    
    def __isr(self, channel):
        '''
        This interrupt service routine is called when the optolatch changes its state.
        This function counts the time that it takes for the sensor to make one turn. This function is private.
        
        :param channel: Number of the pin that caused the interrupt
        :type channel: int
        '''
        currentTime = time.time()
        if (currentTime - self.myTime) > 0.8:
            self.rotationTime = currentTime - self.myTime
        self.myTime = currentTime
        if self.rotationCounter < 3:
            self.rotationCounter += 1
            