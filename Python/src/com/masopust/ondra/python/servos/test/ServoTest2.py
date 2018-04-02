'''
Created on Jan 6, 2018

This module is just to test the servo.

@author: Ondrej Masopust
'''

import pigpio
import time

def Main():
    pi = pigpio.pi()
    
    pi.set_servo_pulsewidth(18, 1400)
    time.sleep(2)
    
    pi.stop

if __name__ == '__main__':
    Main()