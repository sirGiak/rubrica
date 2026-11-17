path=src/main/java/it/giacomo
version=1.0
nomeApp=Rubrica
runnableJar=$(nomeApp)-$(version).jar
iconaApp=iconaApp.png

build:
	mvn clean package

run:
	java -jar target/$(runnableJar)

deb:
	jpackage --input ./ --main-jar target/original-$(runnableJar) --main-class it.giacomo.app.App --name "Rubrica" --app-version "$(version)" --type deb --dest ./release --icon $(iconaApp)

install-deb:
	sudo apt install ./release/rubrica_$(version)_amd64.deb

.PHONY: buil run deb