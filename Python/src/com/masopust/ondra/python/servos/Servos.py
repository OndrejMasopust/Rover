#!/usr/bin/env python3.6
'''
Created on Feb 2, 2018

This module contains only the Servos class.

@author: Ondrej Masopust
'''

import pigpio

class Servos(object):
    '''This class is used to control servos on the Rover. It is necessary to start the pigpiod server for this class to work.'''

    def __init__(self, pwmOut, tcpCommunication):
        '''
        This constructor assigns given arguments to instance variables.
        
        :param pwmOut: BCM number of the pin that should be used to control the servo.
        :type pwmOut: int
        '''
        self.pwmOut = pwmOut
        self.myPigpio = pigpio.pi()
        self.tcpCommunication = tcpCommunication
        self.myPigpio.set_servo_pulsewidth(self.pwmOut, 1500)
 
    def setPosition(self, position):
        '''
        This method sets the position of the servo horn.
        
        :param position: Position that the horn should be put into. 1500 = center
        :type position: int
        :raises ValueError: A ValueError is raised when the argument =< 1000 or 2000 =< argument
        '''
        try:
            if 1000 < position and position < 2000: # TODO check how much it is needed for the servo to rotate
                self.myPigpio.set_servo_pulsewidth(self.pwmOut, position)
            else:
                raise ValueError("Servos.setPostition(): Argument out of bounds. Needs to be 1000 < argument < 2000.")
        except ValueError as err:
            print(err)
            self.tcpCommunication.sendToHostWrapper('er' + err)
    
    def clean(self):
        '''This method stops the PWM going to the servo and stops the connection to the pigpio server.'''
        self.myPigpio.set_servo_pulsewidth(self.pwmOut, 0)
        self.myPigpio.stop()
        