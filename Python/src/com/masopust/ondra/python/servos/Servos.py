'''
Created on Feb 2, 2018

@author: Ondrej Masopust
'''

import pigpio

class Servos(object):
    '''
    This class is used to control servos on the Rover. It is necessary to start the pigpiod server for this class to work.
    '''


    def __init__(self, pwmOut):
        '''
        This constructor assigns given arguments to instance variables.
        
        :param pwmOut: BCM number of the pin that should be used to control the servo.
        :type pwmOut: int
        '''
        self.pwmOut = pwmOut
        self.myPigpio = pigpio.pi()
 
    def setPosition(self, position):
        '''
        This method sets the position of the servo horn.
        
        :param position: Position that the horn should be put into. 1500 = center
        :type position: int
        '''
        try:
            if 1000 < position and position < 2000: # TODO check how much it is needed for the servo to rotate
                self.myPigpio.set_servo_pulsewidth(self.pwmOut, position)
            else:
                raise ValueError("Argument out of bounds. Needs to be 1000 < argument < 2000.")
        except ValueError as err:
            print(err)
    
    def clean(self):
        '''
        This method stops the PWM going to the servo and stops the connection to the pigpio server.
        '''
        self.myPigpio.set_servo_pulsewidth(self.pwmOut, 0)
        self.myPigpio.stop()
        