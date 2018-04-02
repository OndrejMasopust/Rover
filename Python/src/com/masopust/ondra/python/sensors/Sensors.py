#!/usr/bin/env python3.6
'''
Created on Sep 4, 2017

This module contains only the Sensors class.

@author: Ondrej Masopust
'''

import threading
import time
import math
from queue import Queue
import RPi.GPIO as gpio
from smbus2 import SMBusWrapper

from com.masopust.ondra.python.motors.Motors import Motors

class Sensors (threading.Thread):
    '''
    This class is used to control the distance sensor of the Rover.
    '''

    def __init__(self, queue, tcpCommunication):
        '''
        This constructor assigns given arguments to instance variables, sets the thread as daemon and sets up an interrupt
        that is executed when the sensor finishes one rotation.

        :param queue: The LifoQueue instance that is used to exchange information between this thread and the main thread.
            It contains, whether the client disconnected and if the measuring was stopped.
        :type queue: LifoQueue
        :param tcpCommunication: Instance of the TCPCommunication class that is used to send data to the client.
        :type tcpCommunication: TCPCommunication
        '''
        threading.Thread.__init__(self)
        self.daemon = True
        
        # this queue contains True if the Raspberry Pi should start to sense another rotation
        # it needs to be a queue, because it is shared between this thread and the interrupt thread
        self.sensorQueue = Queue(1)
        
        self.mainQueue = queue
        self.tcpCommunication = tcpCommunication
        
        self.sensorMotor = Motors(22, [21, 23], self.tcpCommunication)
        self.sensorMotor.setDirection(True)
        
        # this variable tells if the sensor motor is running
        self.running = False

        gpio.setmode(gpio.BCM)
        # set the pin 22, which is connected to the output of the optolatch, as input
        gpio.setup(22, gpio.IN)
        # set up an interrupt for the optolatch
        gpio.add_event_detect(22, gpio.FALLING, callback=self.__isr)
        
        self.lastInterruptClock = time.time()

        # time in seconds that it takes for the sensor to take one measurement
        self.CONVERSIONTIME = 0.02
        # this constant holds the ideal time of one rotation in seconds
        # TODO check if this number works
        self.DEFAULTROTATIONTIME = 1.2
        # this variable counts the initial free rotations when the sensors don't measure yet
        self.rotationCounter = 0
        # this is the variable that tells how many dots will be displayed on the screen
        self.resolution = 0
    
    def run(self):
        '''This method is called when this thread is started by the start() method in the Main class.'''
        while self.mainQueue.empty() and self.running:
            # get() blocks the flow of the program if necessary until an item in the queue is available 
            doRotation = self.sensorQueue.get()
            if doRotation:
                self.sensOneRotation()
        # stop sensor motor
        self.sensorMotor.clean()

        # this only needs to be done, when th sensor is stopped because the client disconnected
        # not only when the sensor was stopped temporarily using the 'stopMeasure' command
        if self.running:
            # send ACK back to the queue
            self.mainQueue.put(True)

    def sensOneRotation(self):
        '''This method senses one rotation'''
        for index in range(0, self.resolution):
                # check if there is not stop from the main thread or if the sensor didn't finish the rotation earlier
                if self.mainQueue.empty() and self.sensorQueue.empty():
                    self.conversionStartClock = time.time()
                    message = "dt"
                    if index < 10:
                        message += "0"
                    message += index
                    distance = self.measure()
                    message += str(distance)
                    self.tcpCommunication.sendToHostWrapper(message)
                    # wait for next conversion
                    time.sleep(self.CONVERSIONTIME)
                else:
                    # the rotation was not finished in time
                    # speed up the sensor motor
                    self.sensorMotor.speedUp(3)
                    break
    
    def initSens(self):
        '''
        This method starts the sensor motor and based on how fast it rotates,
        it counts how many dots will be displayed on the client's computer screen.
        '''
        if not self.running:
            # reset the rotationCounter
            self.rotationCounter = 0
            self.sensorMotor.run(100) # FIXME set the duty cycle
            self.running = True
            while self.rotationCounter < 4:
                pass
            self.resolution = round(self.lastRotationTime / self.CONVERSIONTIME)
            self.tcpCommunication.sendToHostWrapper("rd")
            self.tcpCommunication.sendToHostWrapper("ro" + self.resolution)

    def stop(self):
        '''This method stops the direction sensing'''
        self.running = False
    
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
        
    def getRunning(self):
        '''
        :return: The state of sensing
        :rtype: boolean
        '''
        return self.running
    
    def __isr(self, channel):
        '''
        This interrupt service routine is called automatically when the optolatch changes its state.
        This function counts the time that it takes for the sensor to make one turn. This function is private.
        
        :param channel: Number of the pin that caused the interrupt
        :type channel: int
        '''
        # The signal edges form the optolatch are noisy so it produces more than one
        # interrupt when there should be only one. That is why there is this
        # condition - to clean the undesired interrupts
        prevRotationTime = time.time() - self.lastInterruptClock
        if prevRotationTime > 0.8:
            currentTime = time.time()
            if abs(prevRotationTime - self.DEFAULTROTATIONTIME) > 0.2:
                sensorMotor.speedUp(3)
            self.lastInterruptClock = currentTime
            # this is needed when initializing the sensor. The sensor motor makes some free
            # rotations to examine how fast it rotates.
            if self.rotationCounter < 3:
                self.rotationCounter += 1
            else:
                if self.rotationCounter == 3:
                    self.lastRotationTime = prevRotationTime
                    self.rotationCounter += 1
                # tell, that the sensor thread should start another sensing rotation
                self.sensorQueue.put(True)
            