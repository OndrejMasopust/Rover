'''
Created on Jan 6, 2018

This module is just to test the servo.

@author: Ondrej Masopust
'''

import pigpio
import time

def Main():
    pi = pigpio.pi()
    
    for x in range(0,4):
        pi.set_servo_pulsewidth(18, 1000)
        time.sleep(0.5)
        pi.set_servo_pulsewidth(18, 1500)
        time.sleep(0.5)
        pi.set_servo_pulsewidth(18, 2000)
        time.sleep(0.5)
        pi.set_servo_pulsewidth(18, 1500)
        time.sleep(0.5)
    
    pi.stop

if __name__ == '__main__':
    Main()