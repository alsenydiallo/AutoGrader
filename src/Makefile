# Build *-HANDOUT files from *-SOLUTION files

all:
	#python map-files.py ../src-java .
	python map-files.py ../src-lib .
	#python map-files.py ../src-tests .
	#for f in *java-SOLUTION; do python2 skeletonify.py $$f $${f%SOLUTION}HANDOUT; done

clean:
	rm -f *-HANDOUT *~
	python map-files.py --clean ../src-lib .
	#python map-files.py --clean ../src-tests .
	#python map-files.py --clean ../src-java .
