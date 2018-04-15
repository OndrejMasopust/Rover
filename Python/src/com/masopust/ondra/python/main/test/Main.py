'''
Created on Aug 3, 2017

This module is just to test the communication over TCP.

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
import sys
sys.path.append('/Users/Ondra/Documents/Programming/Maturita/Project/Python/src/')

from com.masopust.ondra.python.tcpCommunication.TCPCommunication import TCPCommunication

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
   
    #listen to commands
    while True:
        data = tcpCommunication.handleRecvAndSend()
        if data != '':
            if data == 'stop': #'stop' returned from the tcpCommunication.handleRecvAndSend() function
                break
            else:
                print(data)
                #check for commands
    print('Client disconnected - terminating program')
    #terminate all threads

if __name__ == '__main__':
    Main()