# Android-Launcher mit Tutorial-Funktion

![App Übersicht]()  --> Bild


## Übersicht

Studierende aus dem dritten Semester Informatik entwickelten in Kooperation mit der [Deutschen Alzheimer Gesellschaft](https://www.deutsche-alzheimer.de) und dem [Digital Transformation Lab](https://www.hm.edu/dt-lab/) der Hochschule München vorlesungesbegleitend eigene Projekte. Ziel war es, Produkte zu entwickeln, die an Alzheimer erkrankte Menschen im Alltag unterstützen können.
Der hier vorgestellte Android-Launcher mit einer eigenen Tutorial-Funktion ist eines dieser Projekte. 

## Problemstellung

Im Gespräch mit einer Person von der Deutschen Alzheimer Gesellschaft zeigte sich, dass im Alltag sehr viele unterschiedliche Apps verwendet werden. So gibt es eine eigene App für Termine, eine für Wecker, eine für Notizen, eine um Nachrichten zu versenden und viele mehr. Aus Erfahrung mit älteren Verwandten und Bekannten wussten wir, dass viele bei der Anzahl an Apps überfordert sind und oftmals grundlegende Funktionen vergessen werden. Die produktive Verwendung eines Mobiltelefons stellt sich dadurch häufig nicht ein.  
Zur Eingrenzung der Probleme entwickelten wir UserStories, die Sie [hier](/resources_readme/UserStories.pdf) nachlesen können.

## Idee

Unsere Idee war es, eine übersichtliche Ansicht über verwendete Apps zu bieten, sodass diese Überforderung seltener auftritt. Auch für das Vergessen von Funktionen stellen wir eine Lösung bereit, die sich in Form von Tutorials wiederspiegelt.  
Um diese Idee zu realisieren, entwickelten wir eine Android-App, die den herkömmlichen Launcher (Startseite auf einem Mobiltelefon und Darstellung der installierten Apps) ersetzt.

## Lösung

Unseren ersten Entwurf und das daraus entstandene Press Release können Sie [hier](/resources_readme/Press_Release.pdf) nachlesen. Auch die entstandenen WireFrames lassen sich [hier](/resources_readme/WireFramesFull.png) anzeigen.

### Erste Verwendung

Bei der ersten Verwendung unserer App, wird der Benutzer nach seiner bevorzugten Konfigurationsmöglichkeit gefragt.  

![Auswahl der Konfigurationsart](/resources_readme/launcher_config.png)  

Zur Auswahl stehen dabei die manuelle Konfiguration, die automatische Konfiguration und die Konfiguration über eine Organisation.  
Die manuelle Konfiguration ermöglicht es dem Benutzer, von Beginn an selbst die Apps auszuwählen, die er angezeigt haben möchte.  
Die automatische Konfiguration wählt im Hintergrund automatisch die wichtigsten Apps pro Kategorie an, und zeigt sie an.
Bei der Konfiguration über eine Organisation werden die Einstellungen und Präferenzen einer gewählten Konfiguration übernommen.

### Hauptansicht

![Hauptansicht](/resources_readme/launcher_icons.png) 

Die Hauptansicht besteht aus Kacheln mit den am häufigsten verwendeten Apps. Durch Drücken auf ein Icon öffnet sich die dazugehörige App, so wie man es von anderen Launchern gewöhnt ist.  

![Apps hinzufügen](/resources_readme/launcher_icons_add_app.png)

Durch Drücken auf das Plus-Icon kann man neue Apps hinzufügen. 

### Tutorialansicht

![Tutorialansicht](/resources_readme/launcher_tutorial_list.png)

Durch ein Wischen nach links gelangt man zur Tutorialseite, die pro Kategorie Tutorials für unterstützte Apps anzeigt.  

![WhatsApp-Tutorial-Index](/resources_readme/launcher_tutorial_index.png)*Der Tutorial-Index zu der Nachrichtenapp WhatsApp*

![WhatsApp-Tutorial-Text](/resources_readme/launcher_tutorial_text.png)*Das Tutorial zu der Nachrichtenapp WhatsApp*

Durch Drücken auf ein Element wird das dazugehörige Inhaltsverzeichnis und hier wiederum das dazugehörige Kapitel geöffnet.

## FAQ

Das FAQ können Sie [hier](/resources_readme/FAQ.pdf) nachlesen.


## Lizenz
[MIT](https://choosealicense.com/licenses/mit/)
