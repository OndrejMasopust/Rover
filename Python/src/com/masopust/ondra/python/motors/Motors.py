'''
Created on Jan 27, 2018

@author: Ondrej Masopust
'''

import RPi.GPIO as gpio

class Motors(object):
    '''
    classdocs
    '''


    def __init__(self, pwmOut, directionOut):
        '''
        This constructor takes as arguments numbers of pins that should be used to control the desired motor.
        
        :param pwmOut: The number of the pin that should be used as the output of the PWM
        :type pwmOut: int
        :param directionOut: A list that contains two numbers representing the pins that should be used to control the direction of the rotation.
        :type directionOut: list of ints 
        '''
        gpio.setmode(gpio.BCM)
        self.pwmOut = pwmOut
        self.directionOut = directionOut
        #set the defined pins to be output
        gpio.setup(pwmOut, gpio.OUT)
        gpio.setup(directionOut, gpio.OUT)
        #define a variable that is used to to control the PWM
        self.pwm = gpio.PWM(pwmOut, 100)
        self.pwmRunning = False
        
    def run(self):
        #TODO check if the pwm is running. Thn just adjust the duty cycle.
        pass
    
    def stop(self):
        '''
        This function stops the PWM.
        '''
        self.pwm.stop()
        self.pwmRunning = False
    
    def setSpeed(self, dutyCycle):
        '''
        This function sets the duty cycle of the PWM and thus changes the speed of the motor.
        
        :param dutyCycle: The duty cycle to be applied. 0 =< dutyCycle =< 100
        :type dutyCycle: int
        '''
        pass
    
    def setDirection(self, direction):
        '''
        This function sets the rotating direction of the motor.
        
        :param direction: Direction to be applied. If true - clockwise, if false - counterclockwise. 
        :type direction: boolean
        '''
        pass
    
    def clean(self):
        gpio.cleanup()
        self.stop()
        