import subprocess
import os
import sys

def mapfiles(src, target, mapfile='files.map', clean=False):


    with open(os.path.join(src, mapfile), 'rt') as fin:
        knownfiles = {}
        for l in fin.readlines():
            l = l.strip()
            if not l: continue
            k,v = l.split()
            knownfiles[k] = v        

    print(knownfiles)
    for srcfile in os.listdir(src):
        if srcfile not in knownfiles:
            if srcfile.startswith('.') or srcfile == mapfile:
                continue
            else:
                print("Don't known about", srcfile)
                sys.exit(1)

    nonignoredfiles = [k for k in knownfiles if knownfiles[k] != 'ignore']
    if clean:
        for k in nonignoredfiles:
            subprocess.call('rm -f %s/%s-%s'%(target,k,knownfiles[k]), shell=True)
    else:
        for k in nonignoredfiles:
            subprocess.call('cp %s/%s %s/%s-%s'%(src,k,target,k,knownfiles[k]), shell=True)
        
if __name__ == "__main__":
    import argparse
    parser = argparse.ArgumentParser(description=""" 
    Copy files from one directory (src) to another (target) based on
    rules from a files.map file.  The files.map file indicates the
    role of each file in the source directory, and by default lives in
    the source directory. files.map lists one file per line with the
    file's role. File with the role 'ignore' are not copied to the
    target directory. All other files are copied and given a
    corresponding suffix for their role (e.g., '-handout' in the
    target directory for a file whose role is 'handout'.""")

    parser.add_argument('--clean', action='store_true')
    parser.add_argument('src', help='src directory')
    parser.add_argument('target', help='target directory')

    args = parser.parse_args()

    mapfiles(args.src, args.target, clean=args.clean)
