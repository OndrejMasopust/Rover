'''
Created on Jan 6, 2018

This module is just to test the servo.

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