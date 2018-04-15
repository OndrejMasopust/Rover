'''
Created on Jan 29, 2018

This module is just to test the interrupts.

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