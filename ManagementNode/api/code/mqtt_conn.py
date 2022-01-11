#!/usr/bin/env python3

import paho.mqtt.client as mqtt
from app.mgmt import Mgmt

OPEN = "open"
ALARM = "alarm"
CLOSE = "close"


client = mqtt.Client()
mn = Mgmt()

def process_message(client, userdata, message):
    msg_decoded = (str(message.payload.decode("utf-8")))
    if msg_decoded:
        result = mn.check_rfid(msg_decoded)
        if len(result) == 2:
            if result[1] == OPEN:
                client.publish(message.topic + "/listen", OPEN +"/"+result[0], 1)
            else:
                client.publish(message.topic + "/listen", ALARM + "/" + result[0], 1)
        else:
            client.publish(message.topic + "/listen", CLOSE, 1)

def connect_to_broker(broker):
    client.connect(broker, 1883, 600)
    client.on_message = process_message
    client.loop_start()
    client.subscribe("room/+", qos=1)

def disconnect_from_broker():
    client.loop_stop()
    client.disconnect()

