/*
 * main.c
 *
 * Created on: Dec 29, 2017
 * Author: Ondrej Masopust
 */

#include <avr/io.h>
#include <avr/iomx8.h>
#include <avr/interrupt.h>

/* Define an array of variables that hold the sensor output values
 * The value under [0] is from the sensor that measures the shorter distances
 * The value under [1] is from the sensor that measures the larger distances
 */
uint16_t sensorValues[2];

// define a counter, that counts what has been sent
uint8_t sentCounter = 0;

void setUpI2C(void) {
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
}

uint8_t i2cStatus(void) {
	// mask the prescaler bits
	return TWSR & 0b11111000;
}

void setUpADC(void) {
	// set the voltage reference to be AVcc
	ADMUX |= (1 << REFS0);
	// enable ADC interrupts
	ADCSRA |= (1 << ADIE);
	// set clock prescaler to be 128
	ADCSRA |= (1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0);
	// disable digital input buffer on all ADC pins except the I2C pins
	DIDR0 |= (1 << ADC3D) | (1 << ADC2D) | (1 << ADC1D) | (1 << ADC0D);
	// enable ADC
	ADCSRA |= (1 << ADEN);
}

void adcStartConversion(void) {
	ADCSRA |= (1 << ADSC);
}

ISR(TWI_vect) {
	// own SLA+R received, ACK sent || data byte has been transmitted, ACK received
	if (i2cStatus() == 0xA8 || i2cStatus() == 0xB8) {
		// TWDR = 0x34;
		switch (sentCounter) {
			case 0:
				// send the MSB of sensorShort
				TWDR = (sensorValues[0] >> 8) & 0xFF;
				break;
			case 1:
				// send the LSB of sensorShort
				TWDR = sensorValues[0] & 0xFF;
				break;
			case 2:
				// send the MSB of sensorLong
				TWDR = (sensorValues[1] >> 8) & 0xFF;
				break;
			case 3:
				// send the LSB of sensorLong
				TWDR = sensorValues[1] & 0xFF;
				// set TWEA to '0' because this is the last byte to be sent
				TWCR &= ~(1 << TWEA);
				break;
		}
		sentCounter++;
		// clear TWINT flag
		TWCR |= (1 << TWINT);

		// data byte has been transmitted and NACK has been received
	} else if (i2cStatus() == 0xC0) {
		// set TWEA to '1' in order to send ACK, when this device is addressed later
		TWCR |= (1 << TWEA);
		sentCounter = 0;
		// clear TWINT flag
		TWCR |= (1 << TWINT);
		// start a new conversion
		adcStartConversion();

		// last data byte has been sent and ACK was received (NACK was expected)
	} else if (i2cStatus() == 0xC8) {
		// just send something random
		TWDR = 0xFF;
		// set TWEA to '1' in order to send ACK, when this device is addressed later
		TWCR |= (1 << TWEA);
		sentCounter = 0;
		// clear TWINT flag
		TWCR |= (1 << TWINT);
		// start a new conversion
		adcStartConversion();
	}
}

ISR(ADC_vect) {
	// assign a-d conversion result to either [0] or [1] depending on the input selected
	sensorValues[ADMUX & 0x1] = ADCL | ((ADCH << 8) & 0xFF00);
	// switch to the other analog input
	ADMUX ^= 0x1;
	// if this ISR was for the first sensor, start a new conversion for the second sensor
	if (ADMUX & (0x1 == 0x0))
		adcStartConversion();
}

int main(void) {
	// set unused pins as inputs
	DDRD &= 0x00;
	DDRC &= 0b11110011;
	DDRB &= 0b11000000;
	// set pull-ups in unused pins - reduce power consumption
	PORTD |= 0xFF;
	PORTC |= 0b00001100;
	PORTB |= 0b00111111;

	setUpI2C();

	setUpADC(); // a first measurement is taken from ADC0
	// start the first conversion
	adcStartConversion();

	// enable interrupts globally
	sei();

	while(1) {

	}

	return 0; // never reached
}
