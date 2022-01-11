import mariadb


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
        pass

    def get_rooms(self):
        pass