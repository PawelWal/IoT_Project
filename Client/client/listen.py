#!/usr/bin/env python3

import paho.mqtt.client as mqtt
import sys

OPEN = "open"
ALARM = "alarm"
CLOSE = "close"

client = mqtt.Client()

def process_message(client, userdata, message):
    gate = sys.argv[2]
    msg_decoded = (str(message.payload.decode("utf-8"))).split("/")
    if len(msg_decoded == 2):
        if msg_decoded[0] == OPEN and msg_decoded[1]:
            print("Gate {0}, Hello {1}".format(gate, msg_decoded[1]))
        elif msg_decoded[0] == ALARM and msg_decoded[1]:
            print("Gate {0},Alarming, {1} you are not allowed to leave quarantine".format(gate, msg_decoded[1]))
        elif msg_decoded[0] == CLOSE:
            print("Gate {0}, keep close, not registered card".format(gate))

def connect_to_broker():
    client.connect(sys.argv[1], 1883, 600)
    client.on_message = process_message
    client.loop_start()
    client.subscribe("room/"+sys.argv[2]+"/listen")

def disconnect_from_broker():
    client.loop_stop()
    client.disconnect()

def main():
    connect_to_broker()
    disconnect_from_broker()

if __name__ == "__main__":
    main()