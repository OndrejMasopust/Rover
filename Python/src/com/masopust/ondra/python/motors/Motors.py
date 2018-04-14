#!/usr/bin/env python3.6
'''
Created on Jan 27, 2018

This module contains only the Motor class.

@author: Ondrej Masopust
'''

import RPi.GPIO as gpio


class Motors(object):
    '''
    This class is used to control the motors of the Rover.
    '''

    def __init__(self, pwmOut, directionOut, tcpCommunication):
        '''
        This constructor initializes a new Motor object.
        
        :param pwmOut: The number of the pin that should be used as the output of the PWM
        :type pwmOut: int
        :param directionOut: A list that contains two numbers representing the pins that should be used to control the direction of the rotation.
        :type directionOut: list of ints 
        '''
        gpio.setmode(gpio.BCM)
        self.pwmOut = pwmOut
        self.directionOut = directionOut
        self.tcpCommunication = tcpCommunication
        # set the defined pins to be output
        gpio.setup(self.pwmOut, gpio.OUT)
        gpio.setup(directionOut, gpio.OUT)
        # define a variable that is used to to control the PWM
        # set PWM on the pwmOut pin, 100Hz
        self.pwm = gpio.PWM(self.pwmOut, 100)
        self.pwmRunning = False
        self.currentDutyCycle = 0;
        
    def run(self, dutyCycle):
        '''
        This method starts the PWM. It is pretty similar to the setSpeed() method.
        It either starts the PWM, if stopped, or only changes the duty cycle.
        
        :param dutyCycle: The duty cycle to be applied. 0 <= dutyCycle <= 100
        :type dutyCycle: int
        :raises ValueError: A ValueError is raised when the argument <= 0 or 100 <= argument
        '''
        try:
            if (dutyCycle >= 0 and 100 >= dutyCycle):
                if self.pwmRunning:
                    self.pmw.ChangeDutyCycle(dutyCycle)
                else:
                    self.pwm.start(dutyCycle)
                self.currentDutyCycle = dutyCycle
            else:
                raise ValueError("Motors.run(): Argument out of bounds. Must be: argument <= 0 or 100 <= argument, was: " + dutyCycle)
        except ValueError as err:
            print(err)
            self.tcpCommunication.sendToHost('er' + err)
            
    
    def setSpeed(self, dutyCycle):
        '''
        This method sets the duty cycle of the PWM and thus changes the speed of the motor.
        It is pretty similar to the run() method but if the PWM is stopped,this method does not start it.
        
        :param dutyCycle: The duty cycle to be applied. 0 =< dutyCycle =< 100
        :type dutyCycle: int
        :raises ValueError: A ValueError is raised when the argument <= 0 or 100 <= argument
        '''
        
        try:
            if (dutyCycle >= 0 and 100 >= dutyCycle):
                if self.pwmRunning:
                    self.pmw.ChangeDutyCycle(dutyCycle)
                    self.currentDutyCycle = dutyCycle
            else:
                raise ValueError("Motors.setSpeed(): Argument out of bounds. Must be: argument <= 0 or 100 <= argument, was: " + dutyCycle)
        except ValueError as err:
            print(err)
            self.tcpCommunication.sendToHost('er' + err)

    def speedUp(self, increment):
        '''
        This methods sets a new speed by incrementing the current duty cycle by the given argument if possible.
        
        :param increment: The number that should be added to the current duty cycle
        :type increment: int
        :raises ValueError: A ValueError is raised when the (self.currentDutyCycle + increment) <= 0 or 100 <= (self.currentDutyCycle + increment)
        '''
        try:
            if (self.currentDutyCycle + increment) >= 0 or 100 >= (self.currentDutyCycle + increment):
                if self.pwmRunning:
                    self.pwm.ChangeDutyCycle(self.currentDutyCycle + increment)
            else:
                raise ValueError("Motors.speedUp(): Argument not legal. Must be: (self.currentDutyCycle + increment) >= 0\
                    or 100 >= (self.currentDutyCycle + increment), was: " + self.currentDutyCycle + " + " + increment)
        except ValueError as err:
            print(err)
            self.tcpCommunication.sendToHost('er' + err)

    def setDirection(self, direction):
        '''
        This method sets the rotation direction of the motor.
        
        :param direction: Direction to be applied. An enum *Direction* should be used. 
        :type direction: boolean
        '''
        # FIXME check if this setup works
        if direction:
            gpio.output(self.directionOut[0], gpio.HIGH)
            gpio.output(self.directionOut[1], gpio.LOW)
        else:
            gpio.output(self.directionOut[0], gpio.LOW)
            gpio.output(self.directionOut[1], gpio.HIGH)
    
    def getDutyCycle(self):
        '''
        :return: Value of current duty cycle
        :rtype: int
        '''
        return self.currentDutyCycle

    def stop(self):
        '''This method stops the PWM.'''
        self.pwm.stop()
        self.pwmRunning = False
        self.currentDutyCycle = 0;
    
    def clean(self):
        '''This method is called when the client disconnects. It stops the motors and cleans up the GPIO.'''
        self.stop()
        gpio.cleanup()
        
