#!/bin/sh

openssl req -x509 -nodes -sha256 -new -config CA.conf -extensions v3_ca -extfile CA.conf -keyout ca.key -out ca.crt

openssl req -nodes -sha256 -new -config mgmt.conf -keyout mgmt.key -out mgmt.csr
openssl x509 -req -sha256 -in mgmt.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out mgmt.crt

openssl req -nodes -sha256 -new -config broker.conf -keyout broker.key -out broker.csr
openssl x509 -req -sha256 -in broker.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out broker.crt

mkdir /mosquitto/certs/

mv ca.crt /mosquitto/certs/
mv broker.crt /mosquitto/certs/
mv broker.key /mosquitto/certs/
# reszte syfu tez do certs