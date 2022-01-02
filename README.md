# Management Node

## Serwisy
1. Baza danych
2. Serwer Http
3. Broker MQTT
4. Węzeł resp api, wykorzystujący fastapi 
5. Węzeł przetwarzania i zarządzania

## Uruchamianie:
* Wymagania: 
1. Zaintalowany Docker desktop 
2. Dostęp do internetu
3. Folder z plikami jest udostępniony dla dockerów settings->resources->file sharing

* Uruchamianie:
	```docker compose up -d```
<<<<<<< HEAD
	

* Uruchamianie z przebudowniem obrazów:
	```docker compose up -d --build```
=======
>>>>>>> 70f8fd8fe1dd5f97bf414d03c9b13b195d81cc1d

* Sprawdzenie działania:
	```docker compose ps```

* Wyłączenie:
	```docker compose down```
<<<<<<< HEAD
	
## Użycie:
* FastApi dokumentacja naszego api, dostęne pod adresem [localhost]: http://localhost:8080/docs

=======
>>>>>>> 70f8fd8fe1dd5f97bf414d03c9b13b195d81cc1d

