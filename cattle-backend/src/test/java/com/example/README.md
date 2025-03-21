# Cattle-Backend Load Testing mit Artillery

Diese Anleitung beschreibt, wie du Last- und Performance-Tests auf das Cattle-Backend mit Artillery durchführen kannst.

Das Test-Szenario besteht aus drei Schritten:
1. **POST** → Ein neues Cattle-Objekt anlegen
2. **GET** → Alle Cattle abrufen
3. **DELETE** → Das erstellte Cattle löschen


## Voraussetzungen

- **Node.js** (Version 14 oder höher)
  → [Node.js Download](https://nodejs.org/)
- **Artillery** global installieren:
  
    ```bash
    npm install -g artillery
    ```

## Test lokal ausführen

- Einmaliger Testlauf:
```bash
   cd cattle-backend
   artillery run src\test\java\com\example\artillery\cattle-loadtest.yml --output src\test\java\com\example\reports/artillery/cattle-loadtest-report.json
 ```
- HTML-Report erstellen:
 ```bash
   artillery report --output src\test\java\com\example\reports/artillery/cattle-loadtest-report.html src\test\java\com\example\reports/artillery/cattle-loadtest-report.json
 ```
- Report anzeigen:
```bash
   start src\test\java\com\example\reports/artillery/cattle-loadtest-report.html
```


## Anpassungen am Test

|Option         |   Beschreibung |
| --------      |   ----------- |
|arrivalCount   |	Anzahl Benutzer in dieser Phase (simultan)|
|duration       |	Dauer der Phase in Sekunden|
|arrivalRate    |	Ankunftsrate von Nutzern pro Sekunde|
|capture        |	Speichert einen Wert aus einer Response für spätere Requests|
|randomInt      |	Zufällige Zahl zur eindeutigen Datenerzeugung|

In der Datei cattle-loadtest.yml kann so die Anzahl User erhöht werden und die Last geprüft werden.

---

# Cattle-Backend Load Testing mit Apache JMeter

Diese Anleitung beschreibt, wie du Last- und Performance-Tests auf das Cattle-Backend mit Apache JMeter lokal durchführst und die Reports generierst.

Das Test-Szenario besteht aus drei Schritten:
1. **POST** → Ein neues Cattle-Objekt anlegen  
2. **GET** → Alle Cattle abrufen  
3. **DELETE** → Das erstellte Cattle löschen  

## Voraussetzungen

**Java JDK** (Version 8 oder höher empfohlen)  
 **Apache JMeter** (Getestet mit Version 5.6.2)

- Download JMeter:  
🔗 [JMeter Downloads](https://jmeter.apache.org/download_jmeter.cgi)

- Nach dem Entpacken, JMeter CLI oder GUI ausführen:  
    - GUI: `bin/jmeter.bat`  
    - CLI (empfohlen für automatisierte Tests): `bin/jmeter`  

## Test lokal ausführen

- JMeter Non-GUI Command:
```bash
cd cattle-backend
jmeter -n -t src\test\java\com\example\jmeter\cattle-loadtest.jmx -l reports/jmeter/results.jtl -e -o src\test\java\com\example\reports\jmeter
```
- HTML-Report über CLI öffnen:
 ```bash
start src\test\java\com\example\reports/jmeter/index.html

```

## Parameter erklärt

|Option         |   Beschreibung |
| --------      |   ----------- |
|-n     |	Non-GUI Modus (empfohlen für Performance Tests)|
|-t     |	Pfad zur JMX-Testplan Datei|
|-l     |	Pfad zur Ergebnisdatei (.jtl)|
|-e     |	Generiere HTML-Report basierend auf .jtl|
|-o     |	Zielordner für den HTML-Report|

## Eigenheiten
Da der Test nur durchgeführt werden kann, wenn Zielordner der Results leer ist, wurde diesem Projekt ein Skript angehängt. Das Skript löscht die JMeter Testresults und führt direkt danach einen neuen Test aus

Ausführbar mit: 
 ```bash
.\run-jmeter.ps1

```

---

# Vergleich JMeter vs Artillery

|                   |Option                                                             |   Beschreibung |
|-------            | --------                                                          |   ----------- |
|Technologie        | Java-basiert                                                      | Node.js-basiert |
|Einsatzgebiet      | Komplexe Last- und Funktionstests, APIs, Web-Apps, Datenbanken    | Fokus auf API-, Microservice- und Event-basierte Tests |
Benutzeroberfläche  | GUI & CLI                                                         | Nur CLI (optional Dashboards über Plugins) |
Protokolle          | HTTP, HTTPS, FTP, JDBC, JMS, SOAP, LDAP u.v.m.                    | HTTP, HTTPS, WebSockets, Socket.io |
Testpläne           | .jmx XML-Dateien (visuell erstellbar)                             | YAML / JSON (textbasiert & leicht lesbar) |
Installation        | Etwas schwergewichtiger (Java + JMeter)	                        | sehr leichtgewichtig (Node.js + npm install) |
Lastgenerierung     | Hohe Last auf mehreren Maschinen möglich                          | Leichtgewichtig, limitiert bei sehr hoher Last |
Reporting           | HTML Reports, Live-Result Trees, viele Listener                   | JSON-Reports, HTML-Reports, CLI-Dashboards |
Erweiterbarkeit	    | Sehr hohe Erweiterbarkeit durch Plugins und Java-Code             | Leicht mit Node.js-Modulen erweiterbar
CI/CD-Integration   | Mittel (manuelle Integration nötig)                               | Sehr einfach (npm, CLI-first, minimal setup)
Cloud-Integration   | Azure Load Testing unterstützt JMeter direkt                      | Artillery Pro (kostenpflichtig) bietet  Cloud-Support|

## Jemter - Vorteile 

GUI & Non-GUI Modus
→ Einfaches Erstellen & Bearbeiten von Testplänen via GUI
Sehr mächtig & flexibel
→ Unterstützt viele Protokolle (HTTP, FTP, SOAP, JDBC usw.)
Detaillierte Reports & Analyse
→ HTML-Berichte, Fehleranalyse, Echtzeit-Monitoring
Skalierbar
→ Verteilte Lasttests auf mehreren Maschinen
Gut für komplexe Szenarien
→ Threads, Loops, Timers, Assertions, Pre-/Post-Processors
Open Source & große Community
→ Zahlreiche Plugins verfügbar
Kompatibel mit Azure Load Testing
→ Direkte Unterstützung für Cloud-Loadtests

## JMeter - Nachteile
Komplexere Einrichtung
→ Java + JMeter Installation nötig
Ressourcenhungrig bei hoher Last
→ Hohes RAM/CPU bei GUI & großen Szenarien
XML-basierte Testpläne sind unübersichtlich
→ Nicht so leicht versionierbar wie YAML/JSON
Steile Lernkurve bei komplexen Szenarien
→ Einarbeitung in Elemente & Controller notwendig
CLI-Nutzung etwas umständlich
→ Erfordert zusätzliche Optionen für Reports usw.

## Artillery - Vorteile
Leichtgewichtig & Schnell startklar
→ Nur Node.js & NPM benötigt
Einfaches Setup & Konfiguration
→ YAML-basierte Testpläne, sehr lesbar & versionierbar
CI/CD-Integration super easy
→ Perfekt für GitHub Actions, GitLab, Azure Pipelines
Gute Performance bei moderater Last
→ Ideal für REST-APIs & Microservices
Out-of-the-box Reporting
→ JSON & HTML Reports mit wenigen CLI-Befehlen
Plugins & Hooks (JS)
→ Anpassbar & erweiterbar mit JavaScript
Einfach zu automatisieren
→ Skripting und dynamische Payloads einfach umsetzbar

## Artillery - Nachteile
Nur CLI (kein GUI)
→ Kein visuelles Tool zum Bauen von Tests
Weniger Protokollunterstützung
→ Fokus auf HTTP, WebSockets (kein JDBC, SOAP usw.)
Begrenzte Lastgenerierung auf einer Maschine
→ Für sehr große Tests wird Artillery Pro oder verteilte Setups benötigt
Keine eingebaute Unterstützung für Azure Load Testing
→ Cloud-Integration nur über die kostenpflichtige Pro-Version
Weniger Detaillierte Metriken als JMeter
→ Keine komplexen Assertions, Listener oder Live-Graphs

## JMeter - Vorteile
- GUI & Non-GUI Modus
    - Einfaches Erstellen & Bearbeiten von Testplänen via GUI
- Sehr mächtig & flexibel
    - Unterstützt viele Protokolle (HTTP, FTP, SOAP, JDBC usw.)
-Detaillierte Reports & Analyse
    - HTML-Berichte, Fehleranalyse, Echtzeit-Monitoring
- Skalierbar
    - Verteilte Lasttests auf mehreren Maschinen
- Gut für komplexe Szenarien
    - Threads, Loops, Timers, Assertions, Pre-/Post-Processors
- Open Source & große Community
    - Zahlreiche Plugins verfügbar
- Kompatibel mit Azure Load Testing
    - Direkte Unterstützung für Cloud-Loadtests

## JMeter - Nachteile
- Komplexere Einrichtung
    - Java + JMeter Installation nötig
- Ressourcenhungrig bei hoher Last
    - Hohes RAM/CPU bei GUI & großen Szenarien
- XML-basierte Testpläne sind unübersichtlich
    - Nicht so leicht versionierbar wie YAML/JSON
- Steile Lernkurve bei komplexen Szenarien
    - Einarbeitung in Elemente & Controller notwendig
- CLI-Nutzung etwas umständlich
    - Erfordert zusätzliche Optionen für Reports usw.

## Artillery - Vorteile
- Leichtgewichtig & Schnell startklar
    - Nur Node.js & NPM benötigt
- Einfaches Setup & Konfiguration
    - YAML-basierte Testpläne, sehr lesbar & versionierbar
- CI/CD-Integration super easy
    - Perfekt für GitHub Actions, GitLab, Azure Pipelines
- Gute Performance bei moderater Last
    - Ideal für REST-APIs & Microservices
- Out-of-the-box Reporting
    - JSON & HTML Reports mit wenigen CLI-Befehlen
- Plugins & Hooks (JS)
    - Anpassbar & erweiterbar mit JavaScript
- Einfach zu automatisieren
    - Skripting und dynamische Payloads einfach umsetzbar

## Artillery - Nachteile
- Nur CLI (kein GUI)
    - Kein visuelles Tool zum Bauen von Tests
- Weniger Protokollunterstützung
    - Fokus auf HTTP, WebSockets (kein JDBC, SOAP usw.)
- Begrenzte Lastgenerierung auf einer Maschine
    - Für sehr große Tests wird Artillery Pro oder verteilte Setups benötigt
- Keine eingebaute Unterstützung für Azure Load Testing
    - Cloud-Integration nur über die kostenpflichtige Pro-Version
- Weniger Detaillierte Metriken als JMeter
    - Keine komplexen Assertions, Listener oder Live-Graphs

## Empfehlungen
| Anwendungsfall                                             | Tool |
| ---------------                                            | ----------- |
|Komplexe, verteilte Tests mit verschiedenen Protokollen     |JMeter |
|Große Test-Suites mit vielen Abhängigkeiten (DB, MQs etc.)  |JMeter|
|Schnelle, leichte REST-API & Microservice Tests             |Artillery|
|Einfaches CI/CD Testing in Pipelines                        |Artillery|
|Tests mit wenig Overhead & leichter Lesbarkeit              |Artillery |
|Integration in Azure Load Testing (Cloud Lasttests)         |JMeter|