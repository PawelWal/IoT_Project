FROM python:3.9
WORKDIR /code
COPY ./mqtt_connect.py /code/mqtt_connect.py
COPY ./start_service /code/start_service
RUN chmod +x /code/mqtt_connect.py
RUN chmod +x /code/start_service
RUN python -m pip install --user paho-mqtt