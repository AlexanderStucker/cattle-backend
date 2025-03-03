# Cattle Management System - Messaging & Streaming

## Projektübersicht

Dieses Projekt demonstriert die Verwendung von Quarkus in einem verteilten Messaging & Streaming-System mit Kafka. Es besteht aus zwei Microservices. Einer der Services ist mit einer Datenbank angebunden.

###  Architektur

- **Cattle Backend**: 
  - Verwalten von Rindern
  - Sendet Validierungsanfragen über Kafka an den `text-validation-service`
  - Speichert validierte/nicht validierte Rinder in der Datenbank
  
- **Text Validation Service**: 
  - Empfängt Nachrichten aus `validation-requests`
  - Validiert den Namen und die Beschreibung eines Rindes
  - Sendet die Validierungsantwort zurück über `validation-responses-out`

- **Kafka**:
  - Verwendet zwei Topics:
    - `validation-requests`: Zur Kommunikation vom Backend zum Validator
    - `validation-responses-out`: Zur Rückmeldung der Validierungsergebnisse

---

## Schnellstart mit Docker

Das gesamte System kann mit Docker Compose gestartet werden. (Docker Compose-File ist im Projekt enthalten)

### Repository klonen

```sh
git clone 
cd cattle-backend
```

### Docker Images von GHCR herunterladen

Die Container-Images sind bereits in GHCR gepseicher. Ziehe die neusten Versionen herunter: 
```sh
docker pull ghcr.io/alexanderstucker/cattle-backend:latest
docker pull ghcr.io/alexanderstucker/text-validation-service:latest
```


### Dienste mit Docker Compose starten

```sh
docker-compose up -d
```

Dadurch werden folgende Dienste gestartet:
- Cattle Backend (cattle-backend)
- Text Validation Service (text-validation-service)
- Kafka (redpanda)
- PostgreSQL (Datenbank für cattle-backend)

### Dienste stoppen
```sh
docker-compose down
```

---

## Validierungsregeln

Der ValidationService stellt sicher, dass die Daten eines Rinds bestimmte Anforderungen erfüllen, bevor sie als gültig betrachtet werden. Die folgenden Validierungsfälle werden abgedeckt:

### Name-Validierung
- Falls der Name eines Rinds eines der folgenden Wörter enthält, schlägt die Validierung fehl:
    - schlecht, idiot, mistvieh, dumm, böse, alex

### Beschreibung-Validierung
- Falls die Beschreibung Zahlen (0–9) enthält, schlägt die Validierung fehl.


## Anwendungsfälle

### Rind erstellen (mit korrekter Validierung)

Request: 
```sh
curl -X POST http://localhost:8080/cattle \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Harald",
           "description": "Ich bin eine korrekte Kuh"
         }'
```

### Rind erstellen (mit misslungener Namensvalidierung) 

Request: 
```sh
curl -X POST http://localhost:8080/cattle \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Alex",
           "description": "Ich bin eine korrekte Kuh"
         }'
```

### Rind erstellen (mit misslungener Beschreibungsvalidierung) 

Request: 
```sh
curl -X POST http://localhost:8080/cattle \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Harald",
           "description": "Ich bin 13 Meter gross"
         }'
```

### Liste aller Rinder abrufen

Request: 
```sh
curl -X GET http://localhost:8080/cattle
```

### Liste aller validierten Rinder abrufen

Request: 
```sh
curl -X GET http://localhost:8080/cattle/validated
```
