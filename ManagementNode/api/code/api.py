from typing import Optional

from fastapi import FastAPI

from pydantic import BaseModel

app = FastAPI()

class Section(BaseModel):
    name: str
    start_id: int
    end_id: int
    is_closed: Optional[bool] = None
    user_name: str


@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: Optional[str] = None):
    return {"item_id": item_id, "q": q}

@app.put("/items/{item_id}")
def update_item(sect_id: int, sect: Section):
    return {"sect_name": sect.name, "sect_id": sect_id, "user_name": sect.user_name}
