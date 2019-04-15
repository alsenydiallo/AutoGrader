BUILD=./bin
CLASSPATH=/usr/share/java/hamcrest-core-1.3.jar:/usr/share/java/junit4.jar:./gson-2.8.5.jar:$(BUILD)
JAVA=java -cp $(CLASSPATH)
#JAVAC=javac -source 1.7 -target 1.7 -cp $(CLASSPATH) -sourcepath . -d $(BUILD)
JAVAC=javac -cp $(CLASSPATH) -sourcepath . -d $(BUILD)

TESTS= $(patsubst %.java,%,$(wildcard *Tests.java))


lib: bin
	mkdir tmp
	cp -r $(BUILD)/wsuv tmp
	cat MANIFEST.MF.IN | sed -e "s/DATE/`date +%y%m%d`/" > MANIFEST.MF
	jar -cfm wsuvtest.jar MANIFEST.MF -C tmp .
	rm -rf tmp MANIFEST.MF

bin: 
	mkdir $(BUILD)
	for f in $$(find . -name '*.java'); do echo $$f; $(JAVAC) $$f; done

clean:
	rm -rf $(BUILD)

