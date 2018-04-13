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