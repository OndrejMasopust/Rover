'''
Created on Feb 2, 2018

@author: Ondra
'''

def Main():
    print("Hello world")
    try:
        raise ValueError("Error")
    except ValueError as err:
        print(err)
    print("Hello 2")

if __name__ == '__main__':
    Main()