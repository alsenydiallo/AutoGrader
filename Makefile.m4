
#

SHELL := /bin/bash
LAB = LAB_NAME__
# Or, set it indirectly...
# LAB = $(notdir $(PWD))

all: build test-autograder-soln

#handout $(LAB)-handout.tar $(LAB)-handout.zip test-student-soln test-student-stub test-autograder-soln test-autograder-stub deployment

build:
	(rm -rf $(LAB)-handout; mkdir $(LAB)-handout)
	(cd src-lib; make)
	(cd src; make)
	for f in src/*-handout; do cp -p $$f `echo $$f | sed -e "s/^src/$(LAB)-handout/" -e "s/-handout$$//"`; done
	#for f in src/*-HANDOUT; do cp -p $$f `echo $$f | sed -e "s/^src/$(LAB)-handout/" -e "s/-HANDOUT$$//"`; done
	#cp writeup/$(LAB).html $(LAB)-handout

handout:
	# Rebuild the handout directory that students download
	(rm -rf $(LAB)-handout; mkdir $(LAB)-handout)
	(cd src-lib; make)
	(cd src; make)
	(cd writeup; markdown_py -f $(LAB).html README.mkd)
	for f in src/*-handout; do cp -p $$f `echo $$f | sed -e "s/^src/$(LAB)-handout/" -e "s/-handout$$//"`; done
	for f in src/*-HANDOUT; do cp -p $$f `echo $$f | sed -e "s/^src/$(LAB)-handout/" -e "s/-HANDOUT$$//"`; done
	cp writeup/$(LAB).html $(LAB)-handout

autograde.tar:
	# Solution files and student stubs
	(rm -rf $(LAB)-autograde; mkdir $(LAB)-autograde)
	cp -pf $(LAB)-handout/* $(LAB)-autograde
	for f in src/*-submission; do cp -p $$f `echo $$f | sed -e "s/^src/$(LAB)-autograde/" -e "s/-submission$$//"`; done
	tar cvf autograde.tar --transform "s/-autograde//" $(LAB)-autograde
	rm -rf $(LAB)-autograde

$(LAB)-handout.tar: handout
	# Build *-handout.tar  -- use '-s' to strip the '-handout' from the path name
	tar cvf $(LAB)-handout.tar --transform "s/-handout//" $(LAB)-handout

$(LAB)-handout.zip: handout
	zip -r $(LAB)-handout.zip $(LAB)-handout

test-student-stub: handout $(LAB)-handout.tar
	(rm -rf test-student-stub; mkdir test-student-stub)
	cp -pf $(LAB)-handout.tar test-student-stub/autograde.tar
	# ASSUMPTION: students will rewrite (and submit) >=1 of the handout files
	# so, copy all the handout files here...
	for f in src/*-HANDOUT; do cp -p $$f `echo $$f | sed -e "s/^src/test-student-stub/" -e "s/-HANDOUT$$//"`; done
	cp -p autograde-Makefile test-student-stub/Makefile

test-student-soln: handout $(LAB)-handout.tar
	(rm -rf test-student-soln; mkdir test-student-soln)
	cp -pf $(LAB)-handout.tar test-student-soln/autograde.tar
	# ASSUMPTION: students will rewrite (and submit) >=1 of the handout files
	# so, copy all the handout files here...
	for f in src/*-SOLUTION; do cp -p $$f `echo $$f | sed -e "s/^src/test-student-soln/" -e "s/-SOLUTION$$//"`; done
	cp -p autograde-Makefile test-student-soln/Makefile

test-autograder-stub: handout autograde.tar
	(rm -rf test-autograder-stub; mkdir test-autograder-stub)
	cp -p autograde.tar test-autograder-stub/
	for f in src/*-HANDOUT; do cp -p $$f `echo $$f | sed -e "s/^src/test-autograder-stub/" -e "s/-HANDOUT$$//"`; done
	cp -p autograde-Makefile test-autograder-stub/Makefile
	if [ -d testcases/_stub ]; then for f in src/*-HANDOUT; do cp -p $$f `echo $$f | sed -e "s/^src/testcases\/_stub/" -e "s/-HANDOUT$$//"`; done; fi

test-autograder-soln: build autograde.tar
	(rm -rf test-autograder-soln; mkdir test-autograder-soln)
	cp -p autograde.tar test-autograder-soln/
	#for f in src/*-SOLUTION; do cp -p $$f `echo $$f | sed -e "s/^src/test-autograder-soln/" -e "s/-SOLUTION$$//"`; done
	cp -p autograde-Makefile test-autograder-soln/Makefile
	#if [ -d testcases/_solution ]; then for f in src/*-SOLUTION; do cp -p $$f `echo $$f | sed -e "s/^src/testcases\/_solution/" -e "s/-SOLUTION$$//"`; done; fi

testhelp:
	@echo "LAB_NAME__ tests:"
	@for f in $$(ls -d test-*); do echo "   - $$f"; done

deployment:
	mkdir -p deployment/
	cp $(LAB).yml deployment/
	cp $(LAB).rb deployment/
	cp autograde.tar deployment/
	cp autograde-Makefile deployment/
	cp $(LAB)-handout.tar deployment/
	cp $(LAB)-handout.zip deployment/
	cp -r writeup deployment/
	tar -cf	deploy-$(LAB).tar --transform "s/deployment/$(LAB)/" deployment/

clean:
	# Clean the entire lab directory tree.  Note that you can run
	# "make clean; make" at any time while the lab is live with no
	# adverse effects.
	rm -rf out       # for any IntelliJ build cruft
	rm -f autograde.tar $(LAB)-handout.tar deploy-$(LAB).tar
	rm -f $(LAB)-handout.zip
	find . -name "*~" -exec rm {} \;
	if [ -d testcases/_stub ]; then shopt -s nullglob; for f in src/*-HANDOUT; do rm -f `echo $$f | sed -e "s/^src/testcases\/_stub/" -e "s/-HANDOUT$$//"`; done; fi
	if [ -d testcases/_solution ]; then shopt -s nullglob; for f in src/*-SOLUTION; do rm -f `echo $$f | sed -e "s/^src/testcases\/_solution/" -e "s/-SOLUTION$$//"`; done; fi
	(cd src; make clean)
	(cd src-lib; make clean)	
	# WAS: (cd test-autograder; make clean)
	rm -rf $(LAB)-autograde
	rm -rf writeup/$(LAB).html
	rm -rf test-student-soln
	rm -rf test-student-stub
	rm -rf test-autograder-soln
	rm -rf test-autograder-stub
	rm -rf $(LAB)-handout
	rm -rf deployment

distclean:
	make clean
	for i in `find . -name "*.m4"`; do j=`echo $$i | m4 ./meta/macros - | tr -d '[:space:]' | sed -e 's/\.m4$$//'`; echo "rm -f $$j"; rm -f $$j; done

#
# CAREFULL!!! This will delete all student records in the logfile and
# in the handin directory. Don't run this once the lab has started.
# Use it to clean the directory when you are starting a new version
# of the lab from scratch, or when you are debugging the lab prior
# to releasing it to the students.
#
cleanallfiles:
	# Reset the lab from scratch.
	make distclean
	rm -f log.txt
	rm -rf handin/*
