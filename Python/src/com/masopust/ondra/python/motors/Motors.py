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
        # set the defined pins to be output
        gpio.setup(pwmOut, gpio.OUT)
        gpio.setup(directionOut, gpio.OUT)
        # define a variable that is used to to control the PWM
        # set PWM on the pwmOut pin, 100Hz
        self.pwm = gpio.PWM(pwmOut, 100)
        self.pwmRunning = False
        
    def run(self, dutyCycle):
        '''
        This function starts the PWM. It is pretty similar to the setSpeed() function. It either starts the PWM, if stopped, or only changes the duty cycle.
        
        :param dutyCycle: The duty cycle to be applied. 0 =< dutyCycle =< 100
        :type dutyCycle: int
        '''
        if self.pwmRunning:
            self.pmw.ChangeDutyCycle(dutyCycle)
        else:
            self.pwm.start(dutyCycle)
    
    def stop(self):
        '''
        This function stops the PWM.
        '''
        self.pwm.stop()
        self.pwmRunning = False
    
    def setSpeed(self, dutyCycle):
        '''
        This function sets the duty cycle of the PWM and thus changes the speed of the motor. It is pretty similar to the run() function but if the PWM is stopped, this function does not start it.
        
        :param dutyCycle: The duty cycle to be applied. 0 =< dutyCycle =< 100
        :type dutyCycle: int
        '''
        if self.pwmRunning:
            self.pmw.ChangeDutyCycle(dutyCycle)
        else:
            pass
    
    def setDirection(self, direction):
        '''
        This function sets the rotating direction of the motor.
        
        :param direction: Direction to be applied. An enum Direction should be used. 
        :type direction: boolean
        '''
        if direction:
            pass
        else:
            pass
    
    def clean(self):
        self.stop()
        gpio.cleanup()
        
