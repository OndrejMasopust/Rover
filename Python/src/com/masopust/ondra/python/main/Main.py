'''
Created on Aug 3, 2017

@author: Ondrej Masopust
'''
import sys
sys.path.append('/Users/Ondra/Documents/Programming/Maturita/Project/Python/src/')

from com.masopust.ondra.python.motors.Motors import Motors
from com.masopust.ondra.python.motors.Direction import Direction
from com.masopust.ondra.python.tcpCommunication.TCPCommunication import TCPCommunication
from com.masopust.ondra.python.sensors.Sensors import Sensors
from com.masopust.ondra.python.servos import Servos
from queue import LifoQueue


def Main():
    # create server and wait for connection
    tcpCommunication = TCPCommunication()
    tcpCommunication.establishTCPConnection()
    
    # create a queue, that will share, if host sent 'stop'
    # the queue has maxsize = 2
    q = LifoQueue(2)
    # initialize sensors
    sensors = Sensors(q, tcpCommunication)
    # sensors.initSens()
    # tcpCommunication.sendToHostWrapper('Sensors initialized. Starting measuring.')
    # start measuring
    sensors.start()
    
    # initialize an instance that controls the main motor that rotates the wheels 
    mainMotor = Motors(5, [0, 1])
    # initialze an instance that controls the servo that turns the wheels
    servo = Servos()
    
    # listen to commands
    while True:
        data = tcpCommunication.handleRecvAndSend()
        if data != '':
            if data == 'stop':  # 'stop' returned from the tcpCommunication.handleRecvAndSend() function
                q.put(True)
                # wait for the ACK from the sensors thread
                while not q.full():
                    pass
                servo.clean()
                mainMotor.clean()
                break
            # 'mr' motor run command
            elif 'mr' in data:
                speed = int(data[2:])
                if speed > 100:
                    mainMotor.setDirection(Direction.FORWARD)
                else:
                    mainMotor.setDirection(Direction.BACKWARD)
                mainMotor.run( abs(speed - 100) )
            # 'ms' motor stop command
            elif data == 'ms':
                mainMotor.stop()
            # 'tr' turn command
            elif 'tr' in data:
                servo.setPosition( int(data[2:]) )
            else:
                print(data)

    print('Client disconnected - terminating program')
    # terminate all threads


if __name__ == '__main__':
    Main()
