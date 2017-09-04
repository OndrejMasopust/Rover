'''
Created on Aug 3, 2017

@author: Ondřej Masopust
'''

from com.masopust.ondra.python.tcpCommunication.TCPCommunication import TCPCommunication
from com.masopust.ondra.python.sensors.Sensors import Sensors

def Main():
    #create server and wait for connection
    tcpCommunication = TCPCommunication()
    tcpCommunication.establishTCPConnection()
    
    #initialize sensors
    sensors = Sensors
    sensors.initSens()
    tcpCommunication.sendToHost('Sensors initialized. Starting measuring.')
    #start measuring
    sensors.run()
    
    #listen to commands
    while True:
        data = tcpCommunication.listenContinuouslyToSocket()
        if data != '':
            if data == 'stop':
                break
            #check for commands
    print('Client disconnected - terminating program')
    #terminate all threads

if __name__ == '__main__':
    Main()