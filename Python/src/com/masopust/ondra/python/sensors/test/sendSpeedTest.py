'''
This module is just to test the speed of sending a message over TCP.

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
import timeit

global connectionSocket

def Main(connectionSocke):
    '''
    The Main function opens server socket and listens for incoming connections.
    If connection is established, both server and host can communicate.
    It is here only for testing purposes.
    '''
    port = 5321
    
    serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    serverSocket.bind(('', port))
    serverSocket.listen(1)
    connectionSocke, clientAddress = serverSocket.accept()
    
    print("Connection from " + str(clientAddress) + " was successful")
    
    print(timeit.timeit("connectionSocket.send('a')", setup="from __main__ import connectionSocket", number=1))


if __name__ == "__main__":
    global connectionSocket
    connectionSocket = None
    Main(connectionSocket)