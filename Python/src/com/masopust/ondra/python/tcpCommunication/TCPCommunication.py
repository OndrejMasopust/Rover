'''
Created on Jul 12, 2017

@author: Ondrej Masopust

The main function opens server socket and listens for incoming connections.
If connection is established, both server and host can communicate.
'''
import socket
import select
import sys
import time

class TCPCommunication:
    global port
    
    def __init__(self, port = 5321):
        self.port = port
    
    def establishTCPConnection(self):
        serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        serverSocket.bind(('', self.port)) #on RPi, maybe the '' would need to change to socket.gethostname()
        serverSocket.listen(1)
        global clientSocket
        clientSocket, clientAddress = serverSocket.accept()
        
        print("Connection from " + str(clientAddress) + " was successful")
        
        time.sleep(5)
        
        while True:
            potential_writers = [clientSocket]
        
            readyToRead, readyToWrite, error = select.select([], potential_writers, [])
        
            if clientSocket in readyToWrite:
                message = "Raspberry Pi is connected."
                self.sendToHost(message)
                break
    
    @staticmethod
    def sendToHost(message = "null"):
        #check if the message ends with '\r\n' and if not, add it
        if not("\r\n" in message):
            message += "\r\n"
        #chceck for hacky and carky, because it is not possible to decode
        clientSocket.sendall(message.encode(encoding='utf_8', errors='strict'))

    '''
    This function still waits and listens to the socket.
    It interrupts the flow of the code.
	This function needs to have its own thread.
    '''
    def handleRecvAndSend(self):
        while True:
            potential_readers = [clientSocket, sys.stdin]
            potential_writers = [clientSocket]
        
            readyToRead, readyToWrite, error = select.select(potential_readers, potential_writers, [])
        
            for reader in readyToRead:
                if reader == clientSocket:
                    data = clientSocket.recv(4096)
                    if not data:
                        clientSocket.close()
                        print("connectionSocket closed successfully (hopefully)")
                        return 'stop'
                        sys.exit()
                    else: return data
                if reader == sys.stdin:
                    if clientSocket in readyToWrite:
                        message = sys.stdin.readline()
                        self.sendToHost(message)
                    else:
                        print("ERROR: Message could not be sent because the client socket is not ready to write. Try sending it again later.")
                    
        

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