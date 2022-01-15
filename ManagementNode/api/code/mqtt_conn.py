#!/usr/bin/env python3

import ssl
import paho.mqtt.client as mqtt
from app.mgmt import Mgmt

OPEN = "open"
ALARM = "alarm"
CLOSE = "close"

client = mqtt.Client("192.168.0.66")
mn = Mgmt()

def process_message(client, userdata, message):
    msg_decoded = (str(message.payload.decode("utf-8")))
    if msg_decoded:
        result = mn.check_rfid(msg_decoded)
        if len(result) == 2:
            if result[1] == OPEN:
                client.publish(message.topic + "/listen", OPEN +"/"+result[0])
            else:
                client.publish(message.topic + "/listen", ALARM + "/" + result[0])
        else:
            client.publish(message.topic + "/listen", CLOSE)

def connect_to_broker(broker):
    client.tls_set("/code/app/ca.crt", tls_version=ssl.PROTOCOL_TLSv1_2)
    client.tls_insecure_set(True)

    client.connect(broker, 8883, 600)
    
    client.on_message = process_message
    client.loop_start()
    client.subscribe("room/+")

def disconnect_from_broker():
    client.loop_stop()
    client.disconnect()

