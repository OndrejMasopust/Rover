#!/usr/bin/env python3.6
'''
Created on Sep 4, 2017

This module contains only the Sensors class.

@author: Ondrej Masopust
'''

import threading
import time
import math
import RPi.GPIO as gpio
from smbus2 import SMBusWrapper

from com.masopust.ondra.python.motors.Motors import Motors

class Sensors (threading.Thread):
    '''
    This class is used to control the distance sensor of the Rover.
    '''

    def __init__(self, halt, tcpCommunication):
        '''
        This constructor assigns given arguments to instance variables, sets the thread as daemon and sets up an interrupt
        that is executed when the sensor finishes one rotation.

        :param halt: A threading.Event object, that is used to stop this thread.
            It contains, whether the client disconnected.
        :type halt: threading.Event
        :param tcpCommunication: Instance of the TCPCommunication class that is used to send data to the client.
        :type tcpCommunication: TCPCommunication
        '''
        threading.Thread.__init__(self)
        self.daemon = True
        
        # This Event contains True when the Raspberry Pi should start to sense another rotation
        self.sensorState = threading.Event()
        
        self.rotationTime = 0
        
        self.halt = halt
        self.tcpCommunication = tcpCommunication
        
        self.sensorMotor = Motors(25, [9, 11], self.tcpCommunication)
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
        self.DEFAULTROTATIONTIME = 1.35
        # this variable counts the initial free rotations when the sensors don't measure yet
        self.rotationCounter = 0
        # this is the variable that tells how many dots will be displayed on the screen
        self.resolution = 0
    
    def run(self):
        '''This method is called when this thread is started by the start() method in the Main class.'''
        self.initSens()
        while not self.halt.is_set() and self.running:
            # wait() blocks the flow of the program, if necessary, until the Event is set
            if self.sensorState.wait():
                self.sensorState.clear()
                self.sensOneRotation()
        # stop sensor motor
        self.sensorMotor.stop()

        # this only needs to be done, when the sensor is stopped because the client disconnected
        # not only when the sensor was stopped temporarily using the 'stopMeasure' command
        if self.running:
            self.sensorMotor.clean()
            # send ACK back to the main thread
            self.halt.clear()

    def sensOneRotation(self):
        '''This method receives information form the sensor for one rotation'''
        for index in range(0, self.resolution):
                startTime = time.time()
                print("startTime = " + str(startTime))
                # check if there is not stop from the main thread
                if not self.halt.is_set():
                    print(time.time())
                    # if there is not a temporary stop
                    if self.running:
                        print(time.time())
                        # if the sensor didn't finish the rotation earlier
                        if not self.sensorState.is_set():
                            print(time.time())
                            self.conversionStartClock = time.time()
                            message = "dt"
                            print("index = " + str(index))
                            if index < 10:
                                message += "0"
                            message += str(index)
                            message += str(self.measure())
                            t = threading.Thread(target=self.tcpCommunication.sendToHostWrapper, args=(message,))
                            t.start()
                            endTime = time.time()
                            print("endTime = " + str(endTime))
                            # wait for next conversion
                            sleepTime = self.CONVERSIONTIME - (endTime - startTime)
                            if sleepTime > 0:
                                time.sleep(sleepTime)
                                print("sleepTime = " + str(sleepTime))
                            else:
                                print("not slept, sleepTime = " + str(sleepTime))
                            print("\n")
                        else:
                            break
                    else:
                        break
                else:
                    break
    
    def initSens(self):
        '''
        This method starts the sensor motor and based on how fast it rotates,
        it counts how many dots will be displayed on the client's computer screen
        and sends this information to the client.
        '''
        if not self.running:
            # reset the rotationCounter
            self.rotationCounter = 0
            self.sensorMotor.run(100)
            self.running = True
            # wait() blocks the flow of the program, if Event is not set
            self.sensorState.wait()
            print("broke")
            print("initSens() -> self.rotationTime = " + str(self.rotationTime))
            self.resolution = round(self.rotationTime / self.CONVERSIONTIME)
            self.tcpCommunication.sendToHostWrapper("rd")
            self.tcpCommunication.sendToHostWrapper("ro" + str(self.resolution))

    def stop(self):
        '''This method stops the direction sensing'''
        self.running = False
        
    def kill(self):
        self.running = False
        self.sensorMotor.stop()
        self.sensorMotor.clean()
    
    def measure(self):
        '''
        This method reads the data from the AVR and returns the measured distance
        
        :return: Measured distance
        :rtype: float
        '''
        with SMBusWrapper(1) as bus:
            val = bus.read_i2c_block_data(0x0A, 0, 4)
            longSensor = int((val[2] << 8) | val[3])
            longVoltage = longSensor * (5 / 1024)
            if longVoltage == 0:
                # so that we don't get the math domain error
                longVoltage = 1
            longDist = round(916.64 * math.pow(longVoltage, -2.236))
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
        self.rotationTime = time.time() - self.lastInterruptClock
        if self.rotationTime > 0.8:
            print("\nnow")
            print("self.rotationTime = " + str(self.rotationTime))
            '''
            FIXME
            tohle přece nedává smysl - vždy zrychluju
            if abs(self.rotationTime - self.DEFAULTROTATIONTIME) > 0.2:
                self.sensorMotor.speedUp(3)
            '''
            self.lastInterruptClock = time.time()
            # this is needed when initializing the sensor. The sensor motor makes some free
            # rotations to examine how fast it rotates.
            if self.rotationCounter < 3:
                self.rotationCounter += 1
            else:
                # tell, that the sensor thread should start another sensing rotation
                self.sensorState.set()
            