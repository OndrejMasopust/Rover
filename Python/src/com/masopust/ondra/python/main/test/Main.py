#!/usr/bin/env python3.6
'''
Created on Aug 3, 2017

This module is just to test the communication over TCP.

@author: Ondrej Masopust
'''
import sys
sys.path.append('/home/pi/Documents/Ondra/Rover/src/src')
#sys.path.append('/Users/Ondra/Documents/Programming/Maturita/Project/Python/src/')
import timeit

from com.masopust.ondra.python.tcpCommunication.TCPCommunication import TCPCommunication

global tcpCommunication

def test():
    tcpCommunication.sendToHost('a')

def Main():
    #create server and wait for connection
    tcpCommunication = TCPCommunication()
    tcpCommunication.establishTCPConnection()
    
    #initialize sensors
    #sensors = Sensors
    #sensors.initSens()
    #tcpCommunication.sendToHost('Sensors initialized. Starting measuring.')
    #start measuring
    #sensors.start()
    
    print(timeit.timeit("test()", setup="from __main__ import test", number=1))
    
    print('Client disconnected - terminating program')
    #terminate all threads

if __name__ == '__main__':
    Main()