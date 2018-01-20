'''
Created on Jan 6, 2018

@author: Ondra
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