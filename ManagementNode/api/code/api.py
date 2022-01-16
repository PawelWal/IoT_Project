from typing import Optional

from fastapi import FastAPI

from fastapi.middleware.cors import CORSMiddleware

from pydantic import BaseModel, SecretStr

from app.mgmt import Mgmt
import app.mqtt_conn as mqtt

app = FastAPI(root_path="/api")
mn = Mgmt()
mqtt.connect_to_broker("192.168.0.66")

origins = ['*']
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class FindGuest(BaseModel):
    surname: SecretStr

class RfidToRoom(BaseModel):
    room_id: int
    rfid_id: int

class Room(BaseModel):
    room_id: int


@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.post("/countries")
def get_countries():
    return mn.get_countries()

@app.post("/findGuest")
def find_guests(findGuest: FindGuest):
    return mn.find_guests(findGuest.surname.get_secret_value())

@app.post("/addRifdToRoom")
def add_rfid_to_room(rdifToRoom: RfidToRoom):
    return mn.add_rfid_to_room(rdifToRoom.rfid_id, rdifToRoom.room_id)

@app.post("/getRfids")
def get_rfids():
    return mn.get_rfids()

@app.post("/getRooms")
def get_free_rooms():
    return mn.get_free_rooms()

@app.post("/getRoomInfo")
def get_room_info(room: Room):
    return mn.get_room_info(room.room_id)
