JC = javac
JFLAGS = -g -d ./bin -classpath ./bin:./lib/commons-cli-1.3.1.jar

all: Dictionary.class TranslationFile.class IgnoredSet.class Cli.class Translator.class Translator.jar cleanbin

%.class: src/%.java
	$(JC) $(JFLAGS) $<

Translator.jar:
	cd bin; jar cfm $@ manifest.txt *.class

.PHONY : clean

clean :
	rm -f bin/*.class src/*~ src/*# *.txt *~ ./#* bin/*.jar;

.PHONY : cleanclass

cleanbin :
	rm -f bin/*.class