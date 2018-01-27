'''
Created on Aug 3, 2017

@author: Ondrej Masopust
'''
import sys
sys.path.append('/Users/Ondra/Documents/Programming/Maturita/Project/Python/src/')

from com.masopust.ondra.python.tcpCommunication.TCPCommunication import TCPCommunication
from com.masopust.ondra.python.sensors.Sensors import Sensors
from queue import LifoQueue

def Main():
    #create server and wait for connection
    tcpCommunication = TCPCommunication()
    tcpCommunication.establishTCPConnection()
    
    #create a queue, that will share, if host sent 'stop'
    #the queue has maxsize = 2
    q = LifoQueue(2)
    #initialize sensors
    sensors = Sensors(q)
    #sensors.initSens()
    #tcpCommunication.sendToHost('Sensors initialized. Starting measuring.')
    #start measuring
    sensors.start()
    
    #listen to commands
    while True:
        data = tcpCommunication.handleRecvAndSend()
        if data != '':
            if data == 'stop': #'stop' returned from the tcpCommunication.handleRecvAndSend() function
                q.put(True)
                # wait for the ACK from the sensors thread
                while not q.full():
                    pass
                break
            else:
                print(data)
                #check for commands
    print('Client disconnected - terminating program')
    #terminate all threads

if __name__ == '__main__':
    Main()