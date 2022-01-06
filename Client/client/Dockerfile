FROM python:3.9
WORKDIR /code
COPY ./listen.py /code/listen.py
COPY ./publish.py /code/publish.py
COPY ./start_service /code/start_service
RUN chmod +x /code/listen.py
RUN chmod +x /code/publish.py
RUN chmod +x /code/start_service
RUN python -m pip install --user paho-mqtt