FROM eclipse-mosquitto
COPY ./mosquitto.conf /mosquitto/config/mosquitto.conf
COPY ./CA.conf /mosquitto/CA.conf
COPY ./broker.conf /mosquitto/broker.conf
COPY ./mgmt.conf /mosquitto/mgmt.conf

RUN apk add --update openssl

WORKDIR /mosquitto

COPY ./script.sh /mosquitto/script.sh
RUN chmod +x /mosquitto/script.sh
RUN sh /mosquitto/script.sh