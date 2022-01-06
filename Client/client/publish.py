#!/usr/bin/env python3

import paho.mqtt.client as mqtt
import sys
from random import randint
from time import sleep

START_RFID_NBR = 40000

client = mqtt.Client()

def check_rfid(topic, rfid_nbr):
    client.publish(topic, str(rfid_nbr))
    print("Publishing {1} on topic: {0}".format(topic, rfid_nbr))


def connect_to_broker():
    client.connect(sys.argv[1], 1883, 600)

def disconnect_from_broker():
    client.disconnect()

def main():
    connect_to_broker()
    check_rfid("room/" + sys.argv[2], 123456789)
    check_rfid("room/" + sys.argv[2], 123456788)
    for i in range(10):
        sleep(2)
        check_rfid("room/"+sys.argv[2], randint(START_RFID_NBR, 2 * START_RFID_NBR))
    disconnect_from_broker()

if __name__ == "__main__":
    main()