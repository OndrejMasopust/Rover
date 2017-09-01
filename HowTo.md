#How to make your own Rover

####1. Set up an AP on the Pi
Follow this [tutorial](https://www.raspberrypi.org/documentation/configuration/wireless/access-point.md) to set up your Raspberry Pi as an AP

####2. Install these packages on the Pi
In the terminal of the Pi, type:
sudo apt-get install build-essential python-dev

####3. Enable I<sup>2</sup>C on the Pi
In the Pi terminal, type:
sudo raspi-config
And there, in "Interafcing Options", select I<sup>2</sup>C and confirm that.
You need to reboot your Pi after this.




You need to compile the VL530LX-Raspi Library:
Go to "VL53L0X_rasp_python-master" folder and in Pi terminal, type "make"