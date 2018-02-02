'''
Created on Feb 1, 2018

@author: Ondrej Masopust
'''

from enum import Enum


class Direction(Enum):
    '''
    This class is an enum that contains constants that are used in the setDirection function in the Motors class.
    '''
    # Constants for the mainMotor instance of the Motors class.
    FORWARD = True
    BACKWARD = False
    # Constants for the sensorMotor instance of the Motors class.
    RIGHT = True
    LEFT = False
        
