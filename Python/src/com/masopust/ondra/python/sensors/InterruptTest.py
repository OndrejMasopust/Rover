'''
Created on Jan 29, 2018

@author: Ondrej Masopust
'''

import RPi.GPIO as gpio
import time

tick = 0

def ISR(channel):
    print("\nnow\n")
    myTime = time.time()
    global tick
    if (myTime - tick) > 1:
        print(myTime - tick)
    tick = time.time()

def Main():
    global tick
    tick = time.time()
    gpio.setmode(gpio.BCM)
    gpio.setup(22, gpio.IN)
    gpio.add_event_detect(22, gpio.FALLING, callback=ISR)
    try:
        while True:
            pass
    except KeyboardInterrupt:
        gpio.cleanup()

if __name__ == '__main__':
    Main()