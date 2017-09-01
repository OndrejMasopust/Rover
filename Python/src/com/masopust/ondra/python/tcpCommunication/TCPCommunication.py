'''
Created on Jul 12, 2017

@author: Ondřej Masopust

This main function opens server socket and listens for incoming connections.
If connection is established, both server and host can communicate.
'''
import socket
import select
import sys

class TCPCommunication:
    port = 0
    
    def __init__(self, port = 5321):
        self.port = port
    
    def establishTCPConnection(self):
        serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	    serverSocket.bind(('', port))
	    serverSocket.listen(1)
	    global connectionSocket = None
    	connectionSocket, clientAddress = serverSocket.accept()
    
	    print("Connection from " + str(clientAddress) + " was successful")
	    
        message = "Raspberry Pi is connected."
        connectionSocket.send(message)
        
    def sendToHost(self, message = ""):
    	connectionSocket.send(message)
    	
    '''
    This function still waits and listens to the socket.
    It interrupts the flow of the code.
	This function needs to have its own thread.
    '''
    def listenContinuouslyToSocket(self):
    	while True:
        inputList = [connectionSocket]
        
        readyToRead, readyToWrite, error = select.select(inputList, [], [])
        
        for sock in readyToRead:
            if sock == connectionSocket:
                data = connectionSocket.recv(1024)
                if not data:
                    connectionSocket.close()
                    print("connectionSocket closed successfully (hopefully)")
                    sys.exit()
                pass
                #Do something with the received data
                #FIXME
        

def main():
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
    main()