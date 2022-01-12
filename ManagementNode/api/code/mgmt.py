import mariadb
import random


class Mgmt:
    def __init__(self):
        self._conn = mariadb.connect(
            user="root",
            password="p@ss_123",
            host="database",
            database="hotel")

    def block_rfid_card(self, guest_id: int):
        pass

    def get_countries(self):
        cur = self._conn.cursor()
        countries = {}
        cur.execute("SELECT * FROM Countries")
        for country_id, name in cur:
            countries[country_id] = name
        cur.close()
        return countries

    def add_guest(self, name: str, surname: str, doc_no: str, phone: int, email: str,
                  address: str, zip_code: str, city: str, country_code: int):
        guest_id = 1
        return guest_id

    def get_guest(self, guest_id: int):
        pass

    def assign_room(self, guest_id: int, room_id: int):
        pass

    def get_guest_card(self, guest_id: int):
        pass

    def check_rfid(self, rfid_nbr: int):
        cur = self._conn.cursor()
        cmd = "SELECT name, surname, IF(validUntil <= NOW(), 1, 0) FROM Guests RIGHT JOIN CheckIns ON Guests.id = CheckIns.FK_guest RIGHT JOIN RFIDs ON RFIDs.id = CheckIns.FK_rfid WHERE status=1 AND RFIDs.RFID_no={0}".format(rfid_nbr)
        cur.execute(cmd)
        msg = []
        for name, surname, quarantineEnded in cur:
            if not msg:
                if quarantineEnded == 1:
                    msg = [name +" "+ surname, "open"]
                else:
                    msg = [name +" "+ surname, "alarm"]
        cur.close()
        return msg

    def find_guests(self, surname: str):
        cur = self._conn.cursor()
        cmd = "SELECT DISTINCT Guests.id, name, surname, RFID_no, phone_no, email FROM Guests LEFT JOIN CheckIns ON Guests.id = CheckIns.FK_guest LEFT JOIN RFIDs ON RFIDs.id = CheckIns.FK_rfid WHERE surname='{0}'".format(surname)
        cur.execute(cmd)
        result = []
        for guest_id, name, surname, RFID_no, phone_no, email in cur:
            result.append({"id": guest_id, "name": name, "surname": surname, "RFID": RFID_no, "phone": phone_no, "email": email})
        cur.close()
        return result

    def get_rfids(self):
        cur = self._conn.cursor()
        cur.execute("SELECT id, RFID_no FROM RFIDs WHERE status=1")
        cards = []
        for card_id, nbr in cur:
            cards.append({"id": card_id, "RFID_no": nbr})
        return cards

    def get_free_rooms(self):
        cur = self._conn.cursor()
        cur.execute("SELECT DISTINCT number FROM Rooms LEFT JOIN CheckIns ON Rooms.number = CheckIns.FK_room WHERE CheckIns.validUntil <= CURDATE() OR id is NULL")
        rooms = []
        for nbr in cur:
            rooms.append(nbr[0])
        return rooms

    def __check_rfid(self, rfid_id: str):
        for pair in self.get_rfids():
            if pair["id"] == rfid_id:
                return True
        return False

    def add_rfid_to_room(self, rfid_id: str, room_id: str):
        cur = self._conn.cursor()
        token = random.randint(10000, 100000)
        if room_id in self.get_free_rooms() and self.__check_rfid(rfid_id):

            cont = True
            while cont:
                if self.check_if_token_exists(token):
                    token = random.randint(10000, 100000)
                else:
                    cont = False
            cmd = "INSERT INTO Tokens VALUES({0}, {1}, {2}, {3}, CURDATE())".format(token, room_id, rfid_id, 1)
            cur.execute(cmd)
            cur.close()
            if self.check_if_token_exists(token):
                return {"token": token}

    def check_if_token_exists(self, token_nbr: int):
        cur = self._conn.cursor()
        cmd = "SELECT id FROM Tokens WHERE id={0}".format(str(token_nbr))
        cur.execute(cmd)
        output = None
        for nbr in cur:
            output = nbr[0]
        cur.close()
        return output

    def get_room_info(self, room_id: int):
        cur = self._conn.cursor()
        cmd = "SELECT Guests.id, name, surname, RFID_no, phone_no, email FROM Rooms JOIN CheckIns ON Rooms.number=CheckIns.FK_room JOIN RFIDs ON CheckIns.FK_rfid = RFIDs.id JOIN Guests ON CheckIns.FK_guest = Guests.id WHERE Rooms.number={0} AND (validUntil IS NULL OR validUntil >= CURDATE())".format(room_id)
        cur.execute(cmd)
        guest = {}
        for g_id, name, surname, rfid, phone, email in cur:
            guest = {"id": g_id, "name": name, "surname": surname, "RFID": rfid, "phone": phone, "email": email}
        cur.close()
        return guest
