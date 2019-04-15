all:
	tar xvf autograde.tar
	cp *.java LAB_NAME__/
	cp *json* LAB_NAME__/
	#cp HANDIN_FILE__ LAB_NAME__/
	(cd LAB_NAME__; ./driver.sh)

db%:
	tar xvf autograde.tar
	cp HANDIN_FILE__ LAB_NAME__/
	(cd LAB_NAME__; ./driver.sh $@)


clean:
	rm -rf *~ LAB_NAME__
