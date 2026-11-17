path=src/main/java/it/giacomo
version=1.0
nomeApp=Rubrica
runnableJar=target/original-$(nomeApp)-$(version).jar
iconaApp=iconaApp.png

build:
	mvn clean package

run:
	java -jar $(runnableJar)

deb:
	jpackage --input ./ --main-jar $(runnableJar) --main-class it.giacomo.app.App --name "Rubrica" --app-version "$(version)" --type deb --dest ./release --icon $(iconaApp)

install-deb:
	sudo apt install ./release/rubrica_$(version)_amd64.deb
	
.PHONY: buil run deb