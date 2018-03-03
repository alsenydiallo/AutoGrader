BUILD=./bin
CLASSPATH=/usr/share/java/hamcrest-core-1.3.jar:/usr/share/java/junit4.jar:$(BUILD)
CLASSPATH=/Applications/Eclipse-Mars.app/Contents/Eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar:/Applications/Eclipse-Mars.app/Contents/Eclipse/plugins/org.hamcrest.core_1.3.0.v201303031735.jar:$(BUILD)
JAVA=java -cp $(CLASSPATH)
JAVAC=javac -source 1.7 -target 1.7 -cp $(CLASSPATH) -d $(BUILD)

TESTRUNNER=wsuv.autolab.JUnitTester
JAVAPRJ_SRC=$(wildcard src-warmorcold/*.java)
JAVATEST_SRC=$(wildcard src-lib/wsuv/autolab/*.java)
TESTS=$(patsubst %.java,%,$(wildcard src-warmorcold/*Tests.java))
TESTS=$(patsubst src-warmorcold/%,%,$(patsubst %.java,%,$(wildcard src-warmorcold/*Tests.java)))

bin: $(JAVAPRJ_SRC) $(JAVATEST_SRC)
	echo "TESTS ARE: $(TESTS)"
	mkdir -p bin
	$(JAVAC) $(JAVAPRJ_SRC) $(JAVATEST_SRC)
	cp -f src-warmorcold/*config bin/

wsuvtest.jar: bin $(JAVATEST_SRC)
	mkdir tmp
	cp -r bin/wsuv tmp
	cat MANIFEST.MF.IN | sed -e "s/DATE/`date +%y%m%d`/" > MANIFEST.MF
	cp $(JAVATEST_SRC) tmp/wsuv/autolab/
	jar -cfm wsuvtest.jar MANIFEST.MF -C tmp . 
	rm -rf tmp MANIFEST.MF

run: bin
	$(JAVA) $(TESTRUNNER) $(TESTS)

clean:
	rm -rf bin
	rm -rf tmp
	rm -f wsuvtest.jar
