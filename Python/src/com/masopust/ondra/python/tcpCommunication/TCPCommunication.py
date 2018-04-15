#!/usr/bin/env python3.6
'''
Created on Jul 12, 2017

This module contains the TCPCommunication class. The Main() function is used just for testing purposes.
It is not the main function of the Rover program.

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
import socket
import select
import sys
import time

class TCPCommunication:
    '''
    This class is used to communicate with the client over TCP.
    '''

    def __init__(self, port = 5321):
        '''
        This constructor assigns the given argument to instace variable.
        
        :param port: The number of the port that the program should listen to.
        :type port: int
        '''
        self.port = port
    
    def establishTCPConnection(self):
        '''
        This method establishes the TCP communication. It waits, until someone connects,
        prints it's information and sends him acknowledging message. 
        '''
        serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        serverSocket.bind(('', self.port)) # FIXME on RPi, maybe the '' would need to change to socket.gethostname()
        serverSocket.listen(1)
        self.clientSocket, clientAddress = serverSocket.accept()
        self.clientSocket.setblocking(0)
        
        print("Connection from " + str(clientAddress) + " was successful")
        
        time.sleep(1)
        
        self.sendToHost("Raspberry Pi is connected.")
    
    def sendToHost(self, message = "null"):
        '''
        This method sends the given message to the host.
        
        :param message: The message that is to be sent.
        :type message: string
        '''
        # check if the message ends with '\r\n' and if not, add it
        if not("\r\n" in message):
            message += "\r\n"
        self.clientSocket.sendall(message.encode(encoding='utf_8', errors='strict'))
        
    def closeSocket(self):
        '''This method closes the client socket.'''
        self.clientSocket.close()

    def handleRecvAndSend(self):
        '''
        This method still waits and listens to the socket and the stdin. It interrupts the flow of the code.
        This method needs to have its own thread.
        '''
        while True:
            potential_readers = [self.clientSocket, sys.stdin]
            potential_writers = [self.clientSocket]
        
            readyToRead, readyToWrite, error = select.select(potential_readers, potential_writers, [])
        
            for reader in readyToRead:
                if reader == self.clientSocket:
                    # buffer size 4096
                    data = self.clientSocket.recv(4096)
                    if not data:
                        self.clientSocket.close()
                        print("connectionSocket closed successfully (hopefully)")
                        sys.exit()
                    else: return data
                if reader == sys.stdin:
                    if self.clientSocket in readyToWrite:
                        message = sys.stdin.readline()
                        self.sendToHost(message)
                    else:
                        print("ERROR: Message could not be sent because the client socket is not ready to write. Try sending it again later.")

def Main():
    '''
    The Main function opens server socket and listens for incoming connections.
    If connection is established, both server and host can communicate.
    It is here only for testing purposes.
    '''
    port = 5321
    
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.bind(('', port))
    serverSocket.listen(1)
    connectionSocket, clientAddress = serverSocket.accept()
    
    print("Connection from " + str(clientAddress) + " was successful")
    
    while True:
        inputList = [sys.stdin, connectionSocket]
        
        readyToRead, readyToWrite, error = select.select(inputList, [], [])
        
        for sock in readyToRead:
            if sock == connectionSocket:
                data = connectionSocket.recv(1024)
                if not data:
                    connectionSocket.close()
                    print("connectionSocket closed successfully (hopefully)")
                    sys.exit()
                print("Mac: " + data)
            else:
                message = sys.stdin.readline()
                connectionSocket.send(message)


if __name__ == "__main__":
    Main()