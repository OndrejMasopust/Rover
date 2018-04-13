'''
Created on Jan 29, 2018

This module is just to test the interrupts.

@author: Ondrej Masopust
'''

import RPi.GPIO as gpio
import time
import sys

sys.path.append('/home/pi/Documents/Ondra/Rover/src/src')

from com.masopust.ondra.python.motors.Motors import Motors

tick = 0

def ISR(channel):
    print(str(time.time()) + "\n") 

def Main():
    gpio.setmode(gpio.BCM)
    gpio.setup(22, gpio.IN)
    gpio.add_event_detect(22, gpio.FALLING, callback=ISR)
    sensorMotor = Motors(25, [9, 11], None)
    sensorMotor.setDirection(True)
    sensorMotor.run(100)
    try:
        while True:
            pass
    except KeyboardInterrupt:
        sensorMotor.stop()
        gpio.cleanup()

if __name__ == '__main__':
    Main()