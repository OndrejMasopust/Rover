#!/usr/bin/env python3.6
'''
Created on Aug 3, 2017

This module starts the whole Rover application.

@author: Ondrej Masopust
'''
import subprocess
import sys
import time
from queue import LifoQueue

sys.path.append('/home/pi/Documents/Ondra/Rover/src/src')

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
        
        # create a queue, that will share, if host sent 'stop'
        # the queue has maxsize = 2
        q = LifoQueue(2)
        # initialize sensors
        sensors = Sensors(q, tcpCommunication)
        
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
                    q.put(True)
                    # wait for the ACK from the sensors thread
                    if sensors.getRunning():
                        while not q.full():
                            pass
                    servo.clean()
                    mainMotor.clean()
                    tcpCommunication.sendToHostWrapper('ACK')
                    # wait for one second
                    time.sleep(1)
                    tcpCommunication.closeSocket()
                    break
                # just checking, if the socket was not broken
                elif 'check'.encode(encoding='utf_8', errors='strict') in data:
                    tcpCommunication.sendToHostWrapper('check')
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
                else:
                    print(data.decode())
    
        # stop the pigpiod server
        subprocess.run(["sudo", "killall", "pigpiod"])
        
        print('Client disconnected - terminating program')
    except KeyboardInterrupt:
        sensors.kill()
        servo.clean()
        mainMotor.clean()
        tcpCommunication.sendToHostWrapper('ACK')
        time.sleep(1)
        tcpCommunication.closeSocket()
        subprocess.run(["sudo", "killall", "pigpiod"])
        print("KeyboardInterrupt: exiting program")


if __name__ == '__main__':
    Main()
