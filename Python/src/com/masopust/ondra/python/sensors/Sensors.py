'''
Created on Sep 4, 2017

@author: Ondra
'''

import threading

class Sensors (threading.Thread):
    
    def __init__(self):
        pass
    
    def run(self):
        while True:
            self.measure()
        
        pass
    
    def initSens(self):
        pass
    
    def measure(self):
        pass
        
    