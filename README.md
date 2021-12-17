# Management Node

## Serwisy
1. Baza danych
2. Serwer Http
3. Broker MQTT
4. Węzeł resp api
5. Węzeł przetwarzania i zarządzania

## Uruchamianie:
* Wymagania: 
1. zaintalowany Docker desktop 
2. Dostęp do internetu
3. Folder z plikami jest udostępniony dla dockerów settings->resources->file sharing

* Uruchamianie:
	```bash
		docker compose up -d 
	```

* Sprawdzenie działania:
	```bash 
		docker compose ps 
	```

* Wyłączenie:
	```bash 
		docker compose down 
	```

