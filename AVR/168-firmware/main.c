/*
 * main.c
 *
 *  Created on: Dec 29, 2017
 *      Author: Ondra
 *	With a help of CalcProgrammer1's instructable: https://www.instructables.com/id/ATTiny-USI-I2C-The-detailed-in-depth-and-infor/
 */

#include <avr/io.h>
#include <avr/iomx8.h>
#include <avr/interrupt.h>

// define variables that are shared via I2C
uint16_t sensorShort = 0x22D;
uint16_t sensorLong = 0x3AD;
// define a counter, that counts what has been sent
uint8_t sentCounter = 0;

uint8_t twiStatus() {
	// mask the prescaler bits
	return TWSR & 0b11111000;
}

ISR(TWI_vect) {
	// own SLA+R received, ACK sent || data byte has been transmitted, ACK received
	if (twiStatus() == 0xA8 || twiStatus() == 0xB8) {
		// TWDR = 0x34;
		switch (sentCounter) {
			case 0:
				// send the MSB of sensorShort
				TWDR = sensorShort >> 8;
				break;
			case 1:
				// send the LSB of sensorShort
				TWDR = sensorShort & 0xFF;
				break;
			case 2:
				// send the MSB of sensorLong
				TWDR = sensorLong >> 8;
				break;
			case 3:
				// send the LSB of sensorLong
				TWDR = sensorLong & 0xFF;
				// set TWEA to '0' because this is the last byte to be sent
				TWCR &= ~(1 << TWEA);
				break;
		}
		sentCounter++;

		// data byte has been transmitted and NACK has been received
	} else if (twiStatus() == 0xC0) {
		// set TWEA to '1' in order to send ACK, when this device is addressed later
		TWCR |= (1 << TWEA);
		sentCounter = 0;

		// last data byte has been sent and ACK was received (NACK was expected)
	} else if (twiStatus() == 0xC8) {
		// just send something random
		TWDR = 0xFF;
		// set TWEA to '1' in order to send ACK, when this device is addressed later
		TWCR |= (1 << TWEA);
		sentCounter = 0;
	}


	// clear TWINT flag
	TWCR |= (1 << TWINT);
}

int main(void) {
	// set the device address to be 10 and disable 'general call recognition'
	TWAR = 0b00010100;
	// enable generation of ACK
	TWCR |= (1 << TWEA);
	// enable TWI
	TWCR |= (1 << TWEN);
	// enable TWI interrupt
	TWCR |= (1 << TWIE);
	// clear TWINT flag
	TWCR |= (1 << TWINT);

	// enable interrupts globally
	sei();

	while(1) {

	}

	return 0; // never reached
}
