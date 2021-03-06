#!/usr/bin/env python3.6
'''
Created on Aug 3, 2017

This module starts the whole Rover application.

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
import subprocess
import sys
import time
from threading import Event

sys.path.append('/home/pi/Documents/Ondra/Rover/src')

from com.masopust.ondra.python.motors.Motors import Motors
from com.masopust.ondra.python.tcpCommunication.TCPCommunication import TCPCommunication
from com.masopust.ondra.python.sensors.Sensors import Sensors
from com.masopust.ondra.python.servos import Servos

def Main():
    '''The main method'''
    try:
        # create server and wait for connection
        tcpCommunication = TCPCommunication()
        tcpCommunication.establishTCPConnection()
        
        halt = Event()
        
        # initialize sensors
        sensors = Sensors(halt, tcpCommunication)
        
        # initialize an instance that controls the main motor that rotates the wheels 
        mainMotor = Motors(5, [0, 1], tcpCommunication)
        
        # start the pigpiod server
        subprocess.run(["sudo", "pigpiod"])
        # initialze an instance that controls the servo that turns the wheels
        servo = Servos.Servos(18, tcpCommunication)
        
        # listen to commands
        while True:
            data = tcpCommunication.handleRecvAndSend()
            if data != '':
                # 'stop' returned from the tcpCommunication.handleRecvAndSend() function
                if 'halt'.encode(encoding='utf_8', errors='strict') in data:
                    halt.set()
                    # wait for the ACK from the sensors thread
                    if sensors.getRunning():
                        while halt.is_set():
                            pass
                    servo.clean()
                    mainMotor.clean()
                    # wait for one second
                    time.sleep(1)
                    tcpCommunication.closeSocket()
                    break
                # just checking, if the socket was not broken
                elif 'check'.encode(encoding='utf_8', errors='strict') in data:
                    tcpCommunication.sendToHost('check')
                # 'mr' motor run command
                elif 'mr'.encode(encoding='utf_8', errors='strict') in data:
                    # 100 is taken as 'do not move'
                    # numbers above 100 as 'move forward'
                    speed = int(data[2:])
                    if speed > 100:
                        mainMotor.setDirection(True)
                    # numbers less than 100 as 'move backward'
                    else:
                        mainMotor.setDirection(False)
                    mainMotor.run( abs(speed - 100) )
                # 'ms' motor stop command
                elif 'ms'.encode(encoding='utf_8', errors='strict') in data:
                    mainMotor.stop()
                # 'tr' turn command
                elif 'tr'.encode(encoding='utf_8', errors='strict') in data:
                    servo.setPosition( int(data[2:]) )
                elif 'startMeasure'.encode(encoding='utf_8', errors='strict') in data:
                    sensors.start()
                elif 'stopMeasure'.encode(encoding='utf_8', errors='strict') in data:
                    sensors.stop()
                    sensors = Sensors(halt, tcpCommunication)
                else:
                    print(data.decode())
    
        # stop the pigpiod server
        subprocess.run(["sudo", "killall", "pigpiod"])
        
        print('Client disconnected - terminating program')
    except KeyboardInterrupt:
        sensors.kill()
        servo.clean()
        mainMotor.clean()
        tcpCommunication.sendToHost('ACK')
        time.sleep(1)
        tcpCommunication.closeSocket()
        subprocess.run(["sudo", "killall", "pigpiod"])
        print("KeyboardInterrupt: exiting program")


if __name__ == '__main__':
    Main()
