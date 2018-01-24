'''
Created on Aug 3, 2017

@author: Ondrej Masopust
'''
import sys
sys.path.append('/Users/Ondra/Documents/Programming/Maturita/Project/Python/src/')

from com.masopust.ondra.python.tcpCommunication.TCPCommunication import TCPCommunication
from com.masopust.ondra.python.sensors.Sensors import Sensors

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