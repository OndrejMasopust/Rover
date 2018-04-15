#!/usr/bin/env python3.6
'''
Created on Feb 2, 2018

This module contains only the Servos class.

MIT License

Copyright (c) 2018 Ondřej Masopust; Gymnázium Praha 6, Nad Alejí 1952

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

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
            if 1000 < position and position < 2000:
                self.myPigpio.set_servo_pulsewidth(self.pwmOut, position)
            else:
                raise ValueError("Servos.setPostition(): Argument out of bounds. Needs to be 1000 < argument < 2000.")
        except ValueError as err:
            print(err)
            self.tcpCommunication.sendToHost('er' + err)
    
    def clean(self):
        '''This method stops the PWM going to the servo and stops the connection to the pigpio server.'''
        self.myPigpio.set_servo_pulsewidth(self.pwmOut, 0)
        self.myPigpio.stop()
        