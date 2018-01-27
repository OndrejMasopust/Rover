EESchema Schematic File Version 2
LIBS:ac-dc
LIBS:adc-dac
LIBS:Altera
LIBS:analog_devices
LIBS:analog_switches
LIBS:atmel
LIBS:audio
LIBS:Battery_Management
LIBS:bbd
LIBS:Bosch
LIBS:brooktre
LIBS:Connector
LIBS:contrib
LIBS:cypress
LIBS:dc-dc
LIBS:Decawave
LIBS:device
LIBS:digital-audio
LIBS:Diode
LIBS:Display
LIBS:driver_gate
LIBS:DSP_Microchip_DSPIC33
LIBS:dsp
LIBS:elec-unifil
LIBS:ESD_Protection
LIBS:Espressif
LIBS:FPGA_Actel
LIBS:ftdi
LIBS:gennum
LIBS:Graphic
LIBS:hc11
LIBS:infineon
LIBS:intel
LIBS:interface
LIBS:intersil
LIBS:ir
LIBS:Lattice
LIBS:LED
LIBS:LEM
LIBS:linear
LIBS:Logic_74xgxx
LIBS:Logic_74xx
LIBS:Logic_CMOS_4000
LIBS:Logic_CMOS_IEEE
LIBS:logic_programmable
LIBS:Logic_TTL_IEEE
LIBS:maxim
LIBS:MCU_Microchip_PIC10
LIBS:MCU_Microchip_PIC12
LIBS:MCU_Microchip_PIC16
LIBS:MCU_Microchip_PIC18
LIBS:MCU_Microchip_PIC24
LIBS:MCU_Microchip_PIC32
LIBS:MCU_NXP_Kinetis
LIBS:MCU_NXP_LPC
LIBS:MCU_NXP_S08
LIBS:MCU_Parallax
LIBS:MCU_ST_STM8
LIBS:MCU_ST_STM32
LIBS:MCU_Texas_MSP430
LIBS:Mechanical
LIBS:memory
LIBS:microchip
LIBS:microcontrollers
LIBS:modules
LIBS:motor_drivers
LIBS:Motor
LIBS:motorola
LIBS:nordicsemi
LIBS:nxp
LIBS:onsemi
LIBS:opto
LIBS:Oscillators
LIBS:philips
LIBS:Power_Management
LIBS:power
LIBS:powerint
LIBS:pspice
LIBS:references
LIBS:regul
LIBS:Relay
LIBS:RF_Bluetooth
LIBS:rfcom
LIBS:RFSolutions
LIBS:Sensor_Current
LIBS:Sensor_Humidity
LIBS:sensors
LIBS:silabs
LIBS:siliconi
LIBS:supertex
LIBS:Switch
LIBS:texas
LIBS:Transformer
LIBS:Transistor
LIBS:triac_thyristor
LIBS:Valve
LIBS:video
LIBS:wiznet
LIBS:Worldsemi
LIBS:Xicor
LIBS:xilinx
LIBS:zetex
LIBS:Zilog
LIBS:Rover-cache
EELAYER 25 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 1 1
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Raspberry_Pi_2_3 U?
U 1 1 5A377831
P 6050 3150
F 0 "U?" H 6750 1900 50  0000 C CNN
F 1 "Raspberry_Pi_2_3" H 5650 4050 50  0000 C CNN
F 2 "Pin_Headers:Pin_Header_Straight_2x20" H 7050 4400 50  0001 C CNN
F 3 "" H 6100 3000 50  0001 C CNN
	1    6050 3150
	1    0    0    -1  
$EndComp
$Comp
L Motor_DC M?
U 1 1 5A377DE1
P 750 2200
F 0 "M?" H 850 2300 50  0000 L CNN
F 1 "Wheel-dc-motor" H 850 2000 50  0000 C CNN
F 2 "" H 750 2110 50  0001 C CNN
F 3 "" H 750 2110 50  0001 C CNN
	1    750  2200
	1    0    0    -1  
$EndComp
$Comp
L C C?
U 1 1 5A377EC4
P 1300 2250
F 0 "C?" H 1325 2350 50  0000 L CNN
F 1 "1nF" H 1325 2150 50  0000 L CNN
F 2 "" H 1338 2100 50  0001 C CNN
F 3 "" H 1300 2250 50  0001 C CNN
	1    1300 2250
	1    0    0    -1  
$EndComp
$Comp
L Ferrite_Bead L?
U 1 1 5A378049
P 1700 2000
F 0 "L?" V 1550 2025 50  0000 C CNN
F 1 "Ferrite_Bead" V 1850 2000 50  0000 C CNN
F 2 "" V 1630 2000 50  0001 C CNN
F 3 "" H 1700 2000 50  0001 C CNN
	1    1700 2000
	0    1    1    0   
$EndComp
$Comp
L Ferrite_Bead L?
U 1 1 5A37808A
P 1700 2500
F 0 "L?" V 1550 2525 50  0000 C CNN
F 1 "Ferrite_Bead" V 1850 2500 50  0000 C CNN
F 2 "" V 1630 2500 50  0001 C CNN
F 3 "" H 1700 2500 50  0001 C CNN
	1    1700 2500
	0    1    1    0   
$EndComp
$Comp
L C C?
U 1 1 5A3789DD
P 2050 2250
F 0 "C?" H 2075 2350 50  0000 L CNN
F 1 "10nF" H 2075 2150 50  0000 L CNN
F 2 "" H 2088 2100 50  0001 C CNN
F 3 "" H 2050 2250 50  0001 C CNN
	1    2050 2250
	1    0    0    -1  
$EndComp
$Comp
L Motor_DC M?
U 1 1 5A378B13
P 750 1250
F 0 "M?" H 850 1350 50  0000 L CNN
F 1 "Sensor-DC-motor" H 850 1050 50  0000 C CNN
F 2 "" H 750 1160 50  0001 C CNN
F 3 "" H 750 1160 50  0001 C CNN
	1    750  1250
	1    0    0    -1  
$EndComp
$Comp
L C C?
U 1 1 5A378B19
P 1300 1300
F 0 "C?" H 1325 1400 50  0000 L CNN
F 1 "1nF" H 1325 1200 50  0000 L CNN
F 2 "" H 1338 1150 50  0001 C CNN
F 3 "" H 1300 1300 50  0001 C CNN
	1    1300 1300
	1    0    0    -1  
$EndComp
$Comp
L Ferrite_Bead L?
U 1 1 5A378B1F
P 1700 1050
F 0 "L?" V 1550 1075 50  0000 C CNN
F 1 "Ferrite_Bead" V 1850 1050 50  0000 C CNN
F 2 "" V 1630 1050 50  0001 C CNN
F 3 "" H 1700 1050 50  0001 C CNN
	1    1700 1050
	0    1    1    0   
$EndComp
$Comp
L Ferrite_Bead L?
U 1 1 5A378B25
P 1700 1550
F 0 "L?" V 1550 1575 50  0000 C CNN
F 1 "Ferrite_Bead" V 1850 1550 50  0000 C CNN
F 2 "" V 1630 1550 50  0001 C CNN
F 3 "" H 1700 1550 50  0001 C CNN
	1    1700 1550
	0    1    1    0   
$EndComp
$Comp
L C C?
U 1 1 5A378B31
P 2050 1300
F 0 "C?" H 2075 1400 50  0000 L CNN
F 1 "10nF" H 2075 1200 50  0000 L CNN
F 2 "" H 2088 1150 50  0001 C CNN
F 3 "" H 2050 1300 50  0001 C CNN
	1    2050 1300
	1    0    0    -1  
$EndComp
$Comp
L +12V #PWR?
U 1 1 5A378DE1
P 9600 3300
F 0 "#PWR?" H 9600 3150 50  0001 C CNN
F 1 "+12V" H 9600 3440 50  0000 C CNN
F 2 "" H 9600 3300 50  0001 C CNN
F 3 "" H 9600 3300 50  0001 C CNN
	1    9600 3300
	1    0    0    -1  
$EndComp
$Comp
L PWR_FLAG #FLG?
U 1 1 5A378ED1
P 9250 3300
F 0 "#FLG?" H 9250 3375 50  0001 C CNN
F 1 "PWR_FLAG" H 9250 3450 50  0000 C CNN
F 2 "" H 9250 3300 50  0001 C CNN
F 3 "" H 9250 3300 50  0001 C CNN
	1    9250 3300
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A3790A2
P 10150 3700
F 0 "#PWR?" H 10150 3450 50  0001 C CNN
F 1 "GND" H 10150 3550 50  0000 C CNN
F 2 "" H 10150 3700 50  0001 C CNN
F 3 "" H 10150 3700 50  0001 C CNN
	1    10150 3700
	1    0    0    -1  
$EndComp
$Comp
L PWR_FLAG #FLG?
U 1 1 5A3790DA
P 9850 3700
F 0 "#FLG?" H 9850 3775 50  0001 C CNN
F 1 "PWR_FLAG" H 9850 3850 50  0000 C CNN
F 2 "" H 9850 3700 50  0001 C CNN
F 3 "" H 9850 3700 50  0001 C CNN
	1    9850 3700
	-1   0    0    1   
$EndComp
$Comp
L LM2576HVT-5 U?
U 1 1 5A37927D
P 9200 850
F 0 "U?" H 8800 1100 50  0000 L CNN
F 1 "LM2576HVT-5" H 9200 1100 50  0000 L CNN
F 2 "TO_SOT_Packages_THT:TO-220-5_Vertical" H 9200 600 50  0001 L CIN
F 3 "" H 9200 850 50  0001 C CNN
	1    9200 850 
	1    0    0    -1  
$EndComp
$Comp
L +12V #PWR?
U 1 1 5A379446
P 8600 750
F 0 "#PWR?" H 8600 600 50  0001 C CNN
F 1 "+12V" H 8600 890 50  0000 C CNN
F 2 "" H 8600 750 50  0001 C CNN
F 3 "" H 8600 750 50  0001 C CNN
	1    8600 750 
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A379526
P 8300 1100
F 0 "#PWR?" H 8300 850 50  0001 C CNN
F 1 "GND" H 8300 950 50  0000 C CNN
F 2 "" H 8300 1100 50  0001 C CNN
F 3 "" H 8300 1100 50  0001 C CNN
	1    8300 1100
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A379564
P 8600 950
F 0 "#PWR?" H 8600 700 50  0001 C CNN
F 1 "GND" H 8600 800 50  0000 C CNN
F 2 "" H 8600 950 50  0001 C CNN
F 3 "" H 8600 950 50  0001 C CNN
	1    8600 950 
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A379658
P 9200 1200
F 0 "#PWR?" H 9200 950 50  0001 C CNN
F 1 "GND" H 9200 1050 50  0000 C CNN
F 2 "" H 9200 1200 50  0001 C CNN
F 3 "" H 9200 1200 50  0001 C CNN
	1    9200 1200
	1    0    0    -1  
$EndComp
$Comp
L D_Schottky D?
U 1 1 5A379784
P 9800 1200
F 0 "D?" H 9800 1300 50  0000 C CNN
F 1 "D_Schottky" H 9800 1100 50  0000 C CNN
F 2 "" H 9800 1200 50  0001 C CNN
F 3 "" H 9800 1200 50  0001 C CNN
	1    9800 1200
	0    1    1    0   
$EndComp
$Comp
L GND #PWR?
U 1 1 5A37996E
P 9800 1400
F 0 "#PWR?" H 9800 1150 50  0001 C CNN
F 1 "GND" H 9800 1250 50  0000 C CNN
F 2 "" H 9800 1400 50  0001 C CNN
F 3 "" H 9800 1400 50  0001 C CNN
	1    9800 1400
	1    0    0    -1  
$EndComp
$Comp
L INDUCTOR L?
U 1 1 5A3799FD
P 10100 950
F 0 "L?" H 10100 1050 50  0000 C CNN
F 1 "100uH" H 10100 900 50  0000 C CNN
F 2 "" H 10100 950 50  0001 C CNN
F 3 "" H 10100 950 50  0001 C CNN
	1    10100 950 
	1    0    0    -1  
$EndComp
$Comp
L CP C?
U 1 1 5A379CAB
P 8300 900
F 0 "C?" H 8325 1000 50  0000 L CNN
F 1 "100uF" H 8325 800 50  0000 L CNN
F 2 "" H 8338 750 50  0001 C CNN
F 3 "" H 8300 900 50  0001 C CNN
	1    8300 900 
	1    0    0    -1  
$EndComp
$Comp
L CP C?
U 1 1 5A379D5B
P 10450 1150
F 0 "C?" H 10475 1250 50  0000 L CNN
F 1 "1500uF" H 10475 1050 50  0000 L CNN
F 2 "" H 10488 1000 50  0001 C CNN
F 3 "" H 10450 1150 50  0001 C CNN
	1    10450 1150
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A379EB4
P 10450 1350
F 0 "#PWR?" H 10450 1100 50  0001 C CNN
F 1 "GND" H 10450 1200 50  0000 C CNN
F 2 "" H 10450 1350 50  0001 C CNN
F 3 "" H 10450 1350 50  0001 C CNN
	1    10450 1350
	1    0    0    -1  
$EndComp
$Comp
L +5V #PWR?
U 1 1 5A379FE5
P 10650 950
F 0 "#PWR?" H 10650 800 50  0001 C CNN
F 1 "+5V" H 10650 1090 50  0000 C CNN
F 2 "" H 10650 950 50  0001 C CNN
F 3 "" H 10650 950 50  0001 C CNN
	1    10650 950 
	1    0    0    -1  
$EndComp
$Comp
L PWR_FLAG #FLG?
U 1 1 5A37A02C
P 11000 950
F 0 "#FLG?" H 11000 1025 50  0001 C CNN
F 1 "PWR_FLAG" H 11000 1100 50  0000 C CNN
F 2 "" H 11000 950 50  0001 C CNN
F 3 "" H 11000 950 50  0001 C CNN
	1    11000 950 
	1    0    0    -1  
$EndComp
$Comp
L Conn_01x05 J?
U 1 1 5A37C013
P 1600 7350
F 0 "J?" H 1600 7650 50  0000 C CNN
F 1 "ToF-sensor2" H 1600 7050 50  0000 C CNN
F 2 "" H 1600 7350 50  0001 C CNN
F 3 "" H 1600 7350 50  0001 C CNN
	1    1600 7350
	-1   0    0    1   
$EndComp
$Comp
L GND #PWR?
U 1 1 5A37C0FF
P 2100 7550
F 0 "#PWR?" H 2100 7300 50  0001 C CNN
F 1 "GND" H 2100 7400 50  0000 C CNN
F 2 "" H 2100 7550 50  0001 C CNN
F 3 "" H 2100 7550 50  0001 C CNN
	1    2100 7550
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A37C14F
P 2100 7150
F 0 "#PWR?" H 2100 6900 50  0001 C CNN
F 1 "GND" H 2100 7000 50  0000 C CNN
F 2 "" H 2100 7150 50  0001 C CNN
F 3 "" H 2100 7150 50  0001 C CNN
	1    2100 7150
	-1   0    0    1   
$EndComp
Text Label 1850 7150 0    60   ~ 0
GND
Text Label 1850 7550 0    60   ~ 0
GND
$Comp
L +5V #PWR?
U 1 1 5A37C2E8
P 2050 7300
F 0 "#PWR?" H 2050 7150 50  0001 C CNN
F 1 "+5V" H 2050 7440 50  0000 C CNN
F 2 "" H 2050 7300 50  0001 C CNN
F 3 "" H 2050 7300 50  0001 C CNN
	1    2050 7300
	0    1    1    0   
$EndComp
Text Label 1850 7250 0    60   ~ 0
Vcc
Text Label 1850 7350 0    60   ~ 0
Vcc
Text Label 1850 7450 0    60   ~ 0
Out
$Comp
L L293D U?
U 1 1 5A37D687
P 3050 1600
F 0 "U?" H 2850 2625 50  0000 R CNN
F 1 "L293D" H 2850 2550 50  0000 R CNN
F 2 "Housings_DIP:DIP-16_W7.62mm" H 3300 850 50  0001 L CNN
F 3 "" H 2750 2300 50  0001 C CNN
	1    3050 1600
	-1   0    0    1   
$EndComp
$Comp
L +5V #PWR?
U 1 1 5A37D78A
P 3150 2700
F 0 "#PWR?" H 3150 2550 50  0001 C CNN
F 1 "+5V" H 3150 2840 50  0000 C CNN
F 2 "" H 3150 2700 50  0001 C CNN
F 3 "" H 3150 2700 50  0001 C CNN
	1    3150 2700
	-1   0    0    1   
$EndComp
$Comp
L +12V #PWR?
U 1 1 5A37D85F
P 2950 2700
F 0 "#PWR?" H 2950 2550 50  0001 C CNN
F 1 "+12V" H 2950 2840 50  0000 C CNN
F 2 "" H 2950 2700 50  0001 C CNN
F 3 "" H 2950 2700 50  0001 C CNN
	1    2950 2700
	-1   0    0    1   
$EndComp
$Comp
L GND #PWR?
U 1 1 5A37DB71
P 3050 700
F 0 "#PWR?" H 3050 450 50  0001 C CNN
F 1 "GND" H 3050 550 50  0000 C CNN
F 2 "" H 3050 700 50  0001 C CNN
F 3 "" H 3050 700 50  0001 C CNN
	1    3050 700 
	-1   0    0    1   
$EndComp
$Comp
L C C?
U 1 1 5A37DF24
P 3700 700
F 0 "C?" H 3725 800 50  0000 L CNN
F 1 "100nF" H 3725 600 50  0000 L CNN
F 2 "" H 3738 550 50  0001 C CNN
F 3 "" H 3700 700 50  0001 C CNN
	1    3700 700 
	0    1    1    0   
$EndComp
$Comp
L +5V #PWR?
U 1 1 5A37DFD9
P 3950 750
F 0 "#PWR?" H 3950 600 50  0001 C CNN
F 1 "+5V" H 3950 890 50  0000 C CNN
F 2 "" H 3950 750 50  0001 C CNN
F 3 "" H 3950 750 50  0001 C CNN
	1    3950 750 
	-1   0    0    1   
$EndComp
$Comp
L Motor_Servo_Hitec M?
U 1 1 5A396AD9
P 1750 5800
F 0 "M?" H 1550 5975 50  0000 L CNN
F 1 "Motor_Servo" H 1550 5640 50  0000 L TNN
F 2 "" H 1750 5610 50  0001 C CNN
F 3 "" H 1750 5610 50  0001 C CNN
	1    1750 5800
	-1   0    0    1   
$EndComp
$Comp
L GND #PWR?
U 1 1 5A396D81
P 2150 5650
F 0 "#PWR?" H 2150 5400 50  0001 C CNN
F 1 "GND" H 2150 5500 50  0000 C CNN
F 2 "" H 2150 5650 50  0001 C CNN
F 3 "" H 2150 5650 50  0001 C CNN
	1    2150 5650
	-1   0    0    1   
$EndComp
$Comp
L +5V #PWR?
U 1 1 5A396DDD
P 2150 5800
F 0 "#PWR?" H 2150 5650 50  0001 C CNN
F 1 "+5V" H 2150 5940 50  0000 C CNN
F 2 "" H 2150 5800 50  0001 C CNN
F 3 "" H 2150 5800 50  0001 C CNN
	1    2150 5800
	0    1    1    0   
$EndComp
$Comp
L +5V #PWR?
U 1 1 5A397360
P 2650 3650
F 0 "#PWR?" H 2650 3500 50  0001 C CNN
F 1 "+5V" H 2650 3790 50  0000 C CNN
F 2 "" H 2650 3650 50  0001 C CNN
F 3 "" H 2650 3650 50  0001 C CNN
	1    2650 3650
	1    0    0    -1  
$EndComp
$Comp
L R R?
U 1 1 5A397423
P 2650 4250
F 0 "R?" V 2730 4250 50  0000 C CNN
F 1 "51R" V 2650 4250 50  0000 C CNN
F 2 "" V 2580 4250 50  0001 C CNN
F 3 "" H 2650 4250 50  0001 C CNN
	1    2650 4250
	-1   0    0    1   
$EndComp
$Comp
L GND #PWR?
U 1 1 5A397496
P 2650 4450
F 0 "#PWR?" H 2650 4200 50  0001 C CNN
F 1 "GND" H 2650 4300 50  0000 C CNN
F 2 "" H 2650 4450 50  0001 C CNN
F 3 "" H 2650 4450 50  0001 C CNN
	1    2650 4450
	1    0    0    -1  
$EndComp
$Comp
L R R?
U 1 1 5A397992
P 3500 4350
F 0 "R?" V 3580 4350 50  0000 C CNN
F 1 "100R" V 3500 4350 50  0000 C CNN
F 2 "" V 3430 4350 50  0001 C CNN
F 3 "" H 3500 4350 50  0001 C CNN
	1    3500 4350
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A397A06
P 3500 4500
F 0 "#PWR?" H 3500 4250 50  0001 C CNN
F 1 "GND" H 3500 4350 50  0000 C CNN
F 2 "" H 3500 4500 50  0001 C CNN
F 3 "" H 3500 4500 50  0001 C CNN
	1    3500 4500
	1    0    0    -1  
$EndComp
$Comp
L R R?
U 1 1 5A3A88AB
P 9950 3200
F 0 "R?" V 10030 3200 50  0000 C CNN
F 1 "0R1" V 9950 3200 50  0000 C CNN
F 2 "" V 9880 3200 50  0001 C CNN
F 3 "" H 9950 3200 50  0001 C CNN
	1    9950 3200
	0    1    1    0   
$EndComp
$Comp
L R R?
U 1 1 5A3A8AB3
P 9950 3400
F 0 "R?" V 10030 3400 50  0000 C CNN
F 1 "0R1" V 9950 3400 50  0000 C CNN
F 2 "" V 9880 3400 50  0001 C CNN
F 3 "" H 9950 3400 50  0001 C CNN
	1    9950 3400
	0    1    1    0   
$EndComp
$Comp
L INA219 U?
U 1 1 5A3A9891
P 9950 2550
F 0 "U?" H 9650 2975 50  0000 R CNN
F 1 "INA219" H 9650 2900 50  0000 R CNN
F 2 "" H 10000 2200 50  0001 L CNN
F 3 "" H 10600 1850 50  0001 C CNN
	1    9950 2550
	1    0    0    1   
$EndComp
$Comp
L TCST2103 U?
U 1 1 5A3AB3ED
P 3150 3900
F 0 "U?" H 2910 4250 50  0000 C CNN
F 1 "TCST2103" H 2900 3550 50  0000 L CNN
F 2 "" H 3150 3400 50  0000 C CNN
F 3 "" H 3060 3895 50  0001 C CNN
	1    3150 3900
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A3AC670
P 9950 2100
F 0 "#PWR?" H 9950 1850 50  0001 C CNN
F 1 "GND" H 9950 1950 50  0000 C CNN
F 2 "" H 9950 2100 50  0001 C CNN
F 3 "" H 9950 2100 50  0001 C CNN
	1    9950 2100
	-1   0    0    1   
$EndComp
NoConn ~ 9550 2600
NoConn ~ 9550 2500
$Comp
L +3.3V #PWR?
U 1 1 5A3ACEFB
P 3500 3600
F 0 "#PWR?" H 3500 3450 50  0001 C CNN
F 1 "+3.3V" H 3500 3740 50  0000 C CNN
F 2 "" H 3500 3600 50  0001 C CNN
F 3 "" H 3500 3600 50  0001 C CNN
	1    3500 3600
	1    0    0    -1  
$EndComp
$Comp
L +3.3V #PWR?
U 1 1 5A3AD09A
P 6150 1600
F 0 "#PWR?" H 6150 1450 50  0001 C CNN
F 1 "+3.3V" H 6150 1740 50  0000 C CNN
F 2 "" H 6150 1600 50  0001 C CNN
F 3 "" H 6150 1600 50  0001 C CNN
	1    6150 1600
	1    0    0    -1  
$EndComp
$Comp
L PWR_FLAG #FLG?
U 1 1 5A3ADD65
P 6500 1600
F 0 "#FLG?" H 6500 1675 50  0001 C CNN
F 1 "PWR_FLAG" H 6500 1750 50  0000 C CNN
F 2 "" H 6500 1600 50  0001 C CNN
F 3 "" H 6500 1600 50  0001 C CNN
	1    6500 1600
	1    0    0    -1  
$EndComp
$Comp
L LLC_5V_3V3 U?
U 1 1 5A3CCE91
P 7350 5500
F 0 "U?" H 7050 5850 50  0000 L CNN
F 1 "LLC_5V_3V3" H 7500 5850 50  0000 L CNN
F 2 "" H 7450 5150 50  0001 L CNN
F 3 "" H 7150 6100 50  0001 C CNN
	1    7350 5500
	1    0    0    -1  
$EndComp
$Comp
L +5V #PWR?
U 1 1 5A3CD093
P 7300 5000
F 0 "#PWR?" H 7300 4850 50  0001 C CNN
F 1 "+5V" H 7300 5140 50  0000 C CNN
F 2 "" H 7300 5000 50  0001 C CNN
F 3 "" H 7300 5000 50  0001 C CNN
	1    7300 5000
	1    0    0    -1  
$EndComp
$Comp
L +3.3V #PWR?
U 1 1 5A3CD104
P 7500 5000
F 0 "#PWR?" H 7500 4850 50  0001 C CNN
F 1 "+3.3V" H 7500 5140 50  0000 C CNN
F 2 "" H 7500 5000 50  0001 C CNN
F 3 "" H 7500 5000 50  0001 C CNN
	1    7500 5000
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A3CD175
P 7400 6000
F 0 "#PWR?" H 7400 5750 50  0001 C CNN
F 1 "GND" H 7400 5850 50  0000 C CNN
F 2 "" H 7400 6000 50  0001 C CNN
F 3 "" H 7400 6000 50  0001 C CNN
	1    7400 6000
	1    0    0    -1  
$EndComp
$Comp
L C C?
U 1 1 5A3CDF1D
P 9200 2500
F 0 "C?" H 9225 2600 50  0000 L CNN
F 1 "C" H 9225 2400 50  0000 L CNN
F 2 "" H 9238 2350 50  0001 C CNN
F 3 "" H 9200 2500 50  0001 C CNN
	1    9200 2500
	1    0    0    -1  
$EndComp
$Comp
L GND #PWR?
U 1 1 5A3CE00A
P 9200 2750
F 0 "#PWR?" H 9200 2500 50  0001 C CNN
F 1 "GND" H 9200 2600 50  0000 C CNN
F 2 "" H 9200 2750 50  0001 C CNN
F 3 "" H 9200 2750 50  0001 C CNN
	1    9200 2750
	1    0    0    -1  
$EndComp
$Comp
L +3.3V #PWR?
U 1 1 5A3AD6C0
P 9450 2300
F 0 "#PWR?" H 9450 2150 50  0001 C CNN
F 1 "+3.3V" H 9450 2440 50  0000 C CNN
F 2 "" H 9450 2300 50  0001 C CNN
F 3 "" H 9450 2300 50  0001 C CNN
	1    9450 2300
	1    0    0    -1  
$EndComp
$Comp
L Battery BT?
U 1 1 5A3CFE67
P 10450 3500
F 0 "BT?" H 10550 3600 50  0000 L CNN
F 1 "Battery" H 10550 3500 50  0000 L CNN
F 2 "" V 10450 3560 50  0001 C CNN
F 3 "" V 10450 3560 50  0001 C CNN
	1    10450 3500
	1    0    0    -1  
$EndComp
NoConn ~ 7800 5750
NoConn ~ 6950 5750
$Comp
L ATMEGA168-20PU U?
U 1 1 5A6C4B72
P 4550 6400
F 0 "U?" H 3700 7700 50  0000 L BNN
F 1 "ATMEGA168-20PU" H 4900 5050 50  0000 L BNN
F 2 "DIL28" H 4550 6400 50  0001 C CIN
F 3 "" H 4550 6400 50  0001 C CNN
	1    4550 6400
	-1   0    0    1   
$EndComp
$Comp
L Crystal Y?
U 1 1 5A6C5D29
P 3300 6950
F 0 "Y?" H 3300 7100 50  0000 C CNN
F 1 "Crystal" H 3300 6800 50  0000 C CNN
F 2 "" H 3300 6950 50  0001 C CNN
F 3 "" H 3300 6950 50  0001 C CNN
	1    3300 6950
	0    1    1    0   
$EndComp
$Comp
L C C?
U 1 1 5A6C60CA
P 2850 6750
F 0 "C?" H 2875 6850 50  0000 L CNN
F 1 "22pF" H 2875 6650 50  0000 L CNN
F 2 "" H 2888 6600 50  0001 C CNN
F 3 "" H 2850 6750 50  0001 C CNN
	1    2850 6750
	0    1    1    0   
$EndComp
$Comp
L C C?
U 1 1 5A6C6152
P 2850 7100
F 0 "C?" H 2875 7200 50  0000 L CNN
F 1 "22pF" H 2875 7000 50  0000 L CNN
F 2 "" H 2888 6950 50  0001 C CNN
F 3 "" H 2850 7100 50  0001 C CNN
	1    2850 7100
	0    1    1    0   
$EndComp
$Comp
L GND #PWR?
U 1 1 5A6C66D8
P 2600 6950
F 0 "#PWR?" H 2600 6700 50  0001 C CNN
F 1 "GND" H 2600 6800 50  0000 C CNN
F 2 "" H 2600 6950 50  0001 C CNN
F 3 "" H 2600 6950 50  0001 C CNN
	1    2600 6950
	0    1    1    0   
$EndComp
$Comp
L C C?
U 1 1 5A6C6C69
P 6250 7050
F 0 "C?" H 6275 7150 50  0000 L CNN
F 1 "100nF" H 6275 6950 50  0000 L CNN
F 2 "" H 6288 6900 50  0001 C CNN
F 3 "" H 6250 7050 50  0001 C CNN
	1    6250 7050
	1    0    0    -1  
$EndComp
Wire Wire Line
	750  2000 1550 2000
Wire Wire Line
	750  2500 1550 2500
Connection ~ 1300 2000
Connection ~ 1300 2500
Wire Wire Line
	1300 2100 1300 2000
Wire Wire Line
	1300 2400 1300 2500
Wire Wire Line
	2050 2100 2050 2000
Wire Wire Line
	1850 2000 2550 2000
Wire Wire Line
	2050 2400 2050 2500
Wire Wire Line
	1850 2500 2400 2500
Wire Wire Line
	750  1050 1550 1050
Wire Wire Line
	750  1550 1550 1550
Connection ~ 1300 1050
Connection ~ 1300 1550
Wire Wire Line
	1300 1150 1300 1050
Wire Wire Line
	1300 1450 1300 1550
Wire Wire Line
	2050 1150 2050 1050
Wire Wire Line
	1850 1050 2550 1050
Wire Wire Line
	2050 1450 2050 1550
Wire Wire Line
	1850 1550 2250 1550
Wire Wire Line
	9850 3700 10450 3700
Wire Wire Line
	8300 750  8700 750 
Connection ~ 8600 750 
Wire Wire Line
	8300 1050 8300 1100
Wire Wire Line
	8600 950  8700 950 
Wire Wire Line
	9200 1200 9200 1150
Wire Wire Line
	9800 1050 9800 950 
Wire Wire Line
	9700 950  9850 950 
Wire Wire Line
	9800 1400 9800 1350
Connection ~ 9800 950 
Wire Wire Line
	9700 750  10450 750 
Wire Wire Line
	10450 750  10450 1000
Wire Wire Line
	10350 950  11000 950 
Connection ~ 10450 950 
Wire Wire Line
	10450 1350 10450 1300
Connection ~ 10650 950 
Wire Wire Line
	1800 7150 2100 7150
Wire Wire Line
	1800 7550 2100 7550
Wire Wire Line
	1800 7250 2050 7250
Wire Wire Line
	2050 7250 2050 7350
Wire Wire Line
	2050 7350 1800 7350
Connection ~ 2050 7300
Wire Wire Line
	3150 2700 3150 2600
Wire Wire Line
	2950 2700 2950 2600
Wire Wire Line
	3150 700  3150 800 
Wire Wire Line
	2850 700  3550 700 
Wire Wire Line
	2950 700  2950 800 
Connection ~ 3050 700 
Wire Wire Line
	2850 700  2850 800 
Connection ~ 2950 700 
Wire Wire Line
	3250 700  3250 800 
Connection ~ 3150 700 
Wire Wire Line
	3850 700  3950 700 
Wire Wire Line
	2550 1050 2550 1400
Connection ~ 2050 1050
Wire Wire Line
	2550 1600 2250 1600
Wire Wire Line
	2250 1600 2250 1550
Connection ~ 2050 1550
Connection ~ 2050 2000
Wire Wire Line
	2550 2200 2400 2200
Wire Wire Line
	2400 2200 2400 2500
Connection ~ 2050 2500
Wire Wire Line
	2150 5800 2050 5800
Wire Wire Line
	2050 5700 2150 5700
Wire Wire Line
	2150 5700 2150 5650
Wire Wire Line
	2650 4100 2750 4100
Wire Wire Line
	2650 4400 2650 4450
Wire Wire Line
	3500 3600 3500 3700
Wire Wire Line
	3500 4100 3400 4100
Wire Wire Line
	2750 3700 2650 3700
Wire Wire Line
	2650 3700 2650 3650
Wire Wire Line
	3500 3700 3400 3700
Wire Wire Line
	10150 2950 10150 3400
Wire Wire Line
	10150 3200 10100 3200
Wire Wire Line
	10150 3400 10100 3400
Wire Wire Line
	9800 3200 9750 3200
Wire Wire Line
	9750 2950 9750 3400
Wire Wire Line
	9750 3400 9800 3400
Wire Wire Line
	9250 3300 9750 3300
Connection ~ 9750 3300
Connection ~ 9600 3300
Connection ~ 9750 3200
Connection ~ 10150 3200
Wire Wire Line
	9950 2150 9950 2100
Wire Wire Line
	9450 2300 9450 2350
Wire Wire Line
	9200 2350 9550 2350
Wire Wire Line
	6150 1700 6500 1700
Wire Wire Line
	6500 1700 6500 1600
Wire Wire Line
	7300 5950 7300 6000
Wire Wire Line
	7300 6000 7450 6000
Wire Wire Line
	7450 6000 7450 5950
Connection ~ 7400 6000
Wire Wire Line
	7300 5100 7300 5000
Wire Wire Line
	7450 5100 7500 5100
Wire Wire Line
	7500 5100 7500 5000
Wire Wire Line
	9200 2750 9200 2650
Wire Wire Line
	2450 4800 2450 5900
Wire Wire Line
	2450 5900 2050 5900
Connection ~ 10150 3700
Wire Wire Line
	10450 3300 10150 3300
Connection ~ 10150 3300
Connection ~ 9450 2350
Wire Wire Line
	10350 2500 10400 2500
Wire Wire Line
	10400 2500 10400 1850
Wire Wire Line
	10400 1850 8800 1850
Wire Wire Line
	10350 2600 10500 2600
Wire Wire Line
	10500 2600 10500 1750
Wire Wire Line
	10500 1750 8700 1750
Wire Wire Line
	6950 2250 8700 2250
Wire Wire Line
	8700 2250 8700 1750
Wire Wire Line
	8800 1850 8800 2350
Wire Wire Line
	8800 2350 6950 2350
Connection ~ 8650 2350
Connection ~ 8500 2250
Wire Wire Line
	3500 4100 3500 4200
Connection ~ 3250 700 
Wire Wire Line
	3950 700  3950 750 
Wire Wire Line
	3550 1600 5500 1600
Wire Wire Line
	3550 1400 5400 1400
Wire Wire Line
	3550 1800 5300 1800
Wire Wire Line
	4800 2650 3700 2650
Wire Wire Line
	3700 2650 3700 2000
Wire Wire Line
	3700 2000 3550 2000
Wire Wire Line
	3600 2750 3600 2200
Wire Wire Line
	3600 2200 3550 2200
Connection ~ 3500 4150
Wire Wire Line
	4800 2650 4800 3850
Wire Wire Line
	4900 2650 4900 4800
Wire Wire Line
	4900 4800 2450 4800
Wire Wire Line
	2250 6600 3650 6600
Wire Wire Line
	1800 7450 2250 7450
Wire Wire Line
	2250 7450 2250 6600
Wire Wire Line
	3000 6750 3500 6750
Wire Wire Line
	3300 6750 3300 6800
Wire Wire Line
	3500 6750 3500 6850
Wire Wire Line
	3500 6850 3650 6850
Connection ~ 3300 6750
Wire Wire Line
	3650 6950 3500 6950
Wire Wire Line
	3500 6950 3500 7100
Wire Wire Line
	3500 7100 3000 7100
Connection ~ 3300 7100
Wire Wire Line
	2700 6750 2600 6750
Wire Wire Line
	2600 6750 2600 7100
Wire Wire Line
	2600 7100 2700 7100
Connection ~ 2600 6950
$Comp
L C C?
U 1 1 5A6C71B5
P 5650 6700
F 0 "C?" H 5675 6800 50  0000 L CNN
F 1 "100nF" H 5675 6600 50  0000 L CNN
F 2 "" H 5688 6550 50  0001 C CNN
F 3 "" H 5650 6700 50  0001 C CNN
	1    5650 6700
	1    0    0    -1  
$EndComp
$Comp
L C C?
U 1 1 5A6C7447
P 5950 7050
F 0 "C?" H 5975 7150 50  0000 L CNN
F 1 "100nF" H 5975 6950 50  0000 L CNN
F 2 "" H 5988 6900 50  0001 C CNN
F 3 "" H 5950 7050 50  0001 C CNN
	1    5950 7050
	1    0    0    -1  
$EndComp
Wire Wire Line
	5650 6850 5650 6950
Wire Wire Line
	5650 6950 5550 6950
Wire Wire Line
	5650 5350 5550 5350
Wire Wire Line
	5650 5150 5650 6550
Wire Wire Line
	5550 5250 5650 5250
Connection ~ 5650 5350
Wire Wire Line
	5950 6900 5950 6400
Wire Wire Line
	5650 6400 6250 6400
Connection ~ 5650 6400
Wire Wire Line
	6250 6400 6250 6900
Connection ~ 5950 6400
Wire Wire Line
	5950 7250 5950 7200
Wire Wire Line
	5550 7250 5950 7250
Wire Wire Line
	6250 7550 6250 7200
Wire Wire Line
	5550 7550 6250 7550
$Comp
L +5V #PWR?
U 1 1 5A6C7EEF
P 5700 7200
F 0 "#PWR?" H 5700 7050 50  0001 C CNN
F 1 "+5V" H 5700 7340 50  0000 C CNN
F 2 "" H 5700 7200 50  0001 C CNN
F 3 "" H 5700 7200 50  0001 C CNN
	1    5700 7200
	1    0    0    -1  
$EndComp
$Comp
L +5V #PWR?
U 1 1 5A6C7FEB
P 5850 7500
F 0 "#PWR?" H 5850 7350 50  0001 C CNN
F 1 "+5V" H 5850 7640 50  0000 C CNN
F 2 "" H 5850 7500 50  0001 C CNN
F 3 "" H 5850 7500 50  0001 C CNN
	1    5850 7500
	1    0    0    -1  
$EndComp
Wire Wire Line
	5700 7200 5700 7250
Connection ~ 5700 7250
Wire Wire Line
	5850 7500 5850 7550
Connection ~ 5850 7550
$Comp
L GND #PWR?
U 1 1 5A6C83B1
P 5650 5150
F 0 "#PWR?" H 5650 4900 50  0001 C CNN
F 1 "GND" H 5650 5000 50  0000 C CNN
F 2 "" H 5650 5150 50  0001 C CNN
F 3 "" H 5650 5150 50  0001 C CNN
	1    5650 5150
	-1   0    0    1   
$EndComp
Connection ~ 5650 5250
NoConn ~ 7800 5600
NoConn ~ 6950 5600
Wire Wire Line
	7800 5450 8650 5450
Wire Wire Line
	8650 5450 8650 2350
Wire Wire Line
	8500 2250 8500 5300
Wire Wire Line
	8500 5300 7800 5300
Wire Wire Line
	6950 5450 5800 5450
Wire Wire Line
	5800 5450 5800 4900
Wire Wire Line
	5800 4900 5500 4900
Wire Wire Line
	5500 4900 5500 5000
Wire Wire Line
	5500 5000 3400 5000
Wire Wire Line
	3400 5000 3400 6200
Wire Wire Line
	3400 6200 3650 6200
Wire Wire Line
	6950 5300 5900 5300
Wire Wire Line
	5900 5300 5900 4800
Wire Wire Line
	5900 4800 5400 4800
Wire Wire Line
	5400 4800 5400 4900
Wire Wire Line
	5400 4900 3300 4900
Wire Wire Line
	3300 4900 3300 6300
Wire Wire Line
	3300 6300 3650 6300
Wire Wire Line
	4900 2650 5150 2650
Wire Wire Line
	6250 1850 6250 1700
Connection ~ 6250 1700
Wire Wire Line
	6150 1700 6150 1600
Wire Wire Line
	4600 3050 5150 3050
Wire Wire Line
	3550 1200 4100 1200
Wire Wire Line
	4100 1200 4100 3350
Wire Wire Line
	4100 3350 5150 3350
Wire Wire Line
	6950 3150 7600 3150
Wire Wire Line
	7600 3150 7600 1350
Wire Wire Line
	7600 1350 5500 1350
Wire Wire Line
	5500 1350 5500 1600
Wire Wire Line
	5400 1400 5400 1250
Wire Wire Line
	5400 1250 7700 1250
Wire Wire Line
	7700 1250 7700 3350
Wire Wire Line
	7700 3350 6950 3350
Wire Wire Line
	5300 1800 5300 1150
Wire Wire Line
	5300 1150 7800 1150
Wire Wire Line
	7800 1150 7800 2650
Wire Wire Line
	7800 2650 6950 2650
Wire Wire Line
	4800 3850 5150 3850
Wire Wire Line
	4600 3050 4600 4150
Wire Wire Line
	3600 2750 4700 2750
Wire Wire Line
	4700 2750 4700 3950
Wire Wire Line
	4700 3950 5150 3950
Wire Wire Line
	4600 4150 3500 4150
$EndSCHEMATC
