#!/usr/bin/env python3

import paho.mqtt.client as mqtt
import sys
from random import randint
from time import sleep

START_RFID_NBR = 40000

OPEN = "open"
ALARM = "alarm"
CLOSE = "close"

client = mqtt.Client("hotel_cert")
TEST_DATA = ['2344832389', '3820433859', '3326382912', '0093023738']

def check_rfid(topic, rfid_nbr):
    client.publish(topic, rfid_nbr)
    print("Publishing {1} on topic: {0}".format(topic, rfid_nbr))

def process_message(client, userdata, message):
    gate = sys.argv[2]
    msg_decoded = (str(message.payload.decode("utf-8"))).split("/")
    if len(msg_decoded) == 2:
        if msg_decoded[0] == OPEN and msg_decoded[1]:
            print("Gate {0}, Hello {1}".format(gate, msg_decoded[1]))
        elif msg_decoded[0] == ALARM and msg_decoded[1]:
            print("Gate {0}, Alarming, {1} you are not allowed to leave quarantine".format(gate, msg_decoded[1]))
    elif msg_decoded[0] == CLOSE:
        print("Gate {0}, keep close, not registered card".format(gate))

def connect_to_broker():
    client.tls_set('/code/ca.crt')
    client.connect(sys.argv[1], 8883, 600)
    
    client.on_message = process_message
    client.loop_start()
    topic = "room/{0}/listen".format(sys.argv[2])
    client.subscribe(topic)
    print("{0} listening on topic {1}".format(sys.argv[2], topic))

def disconnect_from_broker():
    client.loop_stop()
    client.disconnect()

def main():
    connect_to_broker()
    for i in TEST_DATA:
        sleep(2)
        check_rfid("room/" + sys.argv[2], i)
    for i in range(10):
        sleep(2)
        check_rfid("room/"+sys.argv[2], str(randint(START_RFID_NBR, 2 * START_RFID_NBR)))
    disconnect_from_broker()

if __name__ == "__main__":
    main()