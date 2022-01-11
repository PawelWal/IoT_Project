from typing import Optional

from fastapi import FastAPI

from fastapi.middleware.cors import CORSMiddleware

from pydantic import BaseModel, SecretStr

from app.mgmt import Mgmt
import app.mqtt_conn as mqtt

app = FastAPI(root_path="/api")
mn = Mgmt()
mqtt.connect_to_broker("192.168.1.114")

# origins = ["http://localhost:8080",
#            "http://192.168.1.114:8080",
#            "http://localhost",
#            "http://192.168.1.114"]

origins = ['*']
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class Section(BaseModel):
    name: str
    start_id: int
    end_id: int
    is_closed: Optional[bool] = None
    user_name: str


class FindGuest(BaseModel):
    surname: SecretStr

class RfidToRoom(BaseModel):
    room_id: int
    rfid_id: int


@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: Optional[str] = None):
    return {"item_id": item_id, "q": q}

@app.put("/items/{item_id}")
def update_item(sect_id: int, sect: Section):
    return {"sect_name": sect.name, "sect_id": sect_id, "user_name": sect.user_name}

@app.get("/countries")
async def get_countries():
    return mn.get_countries()

@app.post("/findGuest")
def find_guests(findGuest: FindGuest):
    return mn.find_guests(findGuest.surname.get_secret_value())

@app.get("/getfindGuest")
def find_guests(surname: str):
    return mn.find_guests(surname)

@app.post("/addRifdToRoom")
async def add_rfid_to_room():
    pass

# @app.post("/getRfids")
# async def get_rfids():
#     return mn.get_rfids()
#
# @app.post("/getRooms")
# async def get_rooms():
#     return mn.get_rooms()
