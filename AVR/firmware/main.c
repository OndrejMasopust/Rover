/*
 * main.c
 *
 * This program sets the AVR as an analog-digital converter, that can communicate over I2C.
 *
 * MIT License
 *
 * Copyright (c) 2018 Ondřej Masopust; Gymnázium Praha 6, Nad Alejí 1952
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Created on: Dec 29, 2017
 * Author: Ondrej Masopust
 */

#include <avr/io.h>
#include <avr/iomx8.h>
#include <avr/interrupt.h>

/** An array of 2-byte variables that hold the sensor output values.<br>
 * The value under [0] is from the sensor that measures the shorter distances.<br>
 * The value under [1] is from the sensor that measures the larger distances.
 */
uint16_t sensorValues[2];

/**
* A counter, that counts what has been sent over I2C. Base on that, next data is sent.
*/
uint8_t sentCounter = 0;

/**
* The setUpI2C() function is called at the beginning of the program and sets up everything around the I2C.
*/
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

/**
* The i2cStatus() function returns prescaled current state of the I2C.
*
* @return byte containing the status
*/
uint8_t i2cStatus(void) {
	// mask the prescaler bits
	return TWSR & 0b11111000;
}

/**
* The setUpADC() function is called at the beginning of the program and sets up everything around the analog-gigital conversion.
*/
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

/**
* When the adcStartConversion() function is called, a logic 1 is written to the ADSC bit in the ADCSRA register and new analog-digital conversion is started.
*/
void adcStartConversion(void) {
	ADCSRA |= (1 << ADSC);
}

/**
* This interrupt service routine is called when the TWI vector is set. It sends the appropriate data through the I2C and starts new conversion after all.
*/
ISR(TWI_vect) {
	// own SLA+R received, ACK sent || data byte has been transmitted, ACK received
	if (i2cStatus() == 0xA8 || i2cStatus() == 0xB8) {
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
	} else {
		// clear TWINT flag
		TWCR |= (1 << TWINT);
	}
}

/**
* This interrupt service routine is called when the ADC vector is set. It stores the converted value, switches the ADC input to the other sensor and invokes new conversion if needed.
*/
ISR(ADC_vect) {
	// assign a-d conversion result to either [0] or [1] depending on the input selected
	sensorValues[ADMUX & 0x1] = ADCL | ((ADCH << 8) & 0xFF00);
	// switch to the other analog input
	ADMUX ^= 0x1;
	// if this ISR was for the first sensor, start a new conversion for the second sensor
	if (ADMUX & (0x1 == 0x0))
		adcStartConversion();
}

/**
* The main function. It sets everything up, starts first AD conversino and then just hangs in an endless while loop.
*/
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
