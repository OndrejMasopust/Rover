'''
Created on Jan 6, 2018

This module is just to test the gpio and pwm.

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
import pigpio

def Main():
    pi = pigpio.pi()
    pi.set_servo_pulsewidth(18, 1000)
    gpio.setmode(gpio.BCM)
    gpio.setup([14, 15, 17, 27, 22, 23], gpio.OUT)
    gpio.output(17, gpio.LOW)
    gpio.output(27, gpio.HIGH)
    pwm1 = gpio.PWM(14, 100)
    pwm2 = gpio.PWM(15, 100)
    pwm1.start(100)
    time.sleep(1)
    pi.set_servo_pulsewidth(18, 1500)
    gpio.output([27, 23], gpio.LOW)
    gpio.output([17, 22], gpio.HIGH)
    pwm1.ChangeDutyCycle(75)
    pwm2.start(100)
    time.sleep(1)
    pi.set_servo_pulsewidth(18, 2000)
    gpio.output(22, gpio.LOW)
    gpio.output(23, gpio.HIGH)
    pwm2.ChangeDutyCycle(75)
    pwm1.stop()
    time.sleep(1)
    pi.set_servo_pulsewidth(18, 1500)
    pwm2.stop()
    time.sleep(1)
    gpio.cleanup()


if __name__ == '__main__':
    Main()