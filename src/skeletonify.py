import re
import os
def parse_signature(line):
    words = re.split('([\t \(\)\{])', line)


    assert not 'abstract' in words, "Expected a concrete method/constructor"
    assert not 'synchronized' in words, "Expected a non-synchronized method/constructor"
    assert '{' in words, "Expected an open block '{' brace"

    nb_words = [w for w in words if w.strip() != ""]


    pos_paren = nb_words.index('(')
    name = nb_words[pos_paren-1]

    # for a constructor the return type will be 'public'
    assert 'public' in nb_words, "Expected a public method/constructor"
    return_type = nb_words[pos_paren-2]
    if return_type == 'public':
        return_type == None
        return_statement = ["// TODO: implement this"]
    elif return_type in ['double', 'float', 'int', 'char', 'short', 'long']:
        return_statement = ["// TODO: implement this", "return 0;"]
    elif return_type == 'boolean':
        return_statement = ["// TODO: implement this", "return false;"]
    elif return_type == 'void':
        return_statement = ["// TODO: implement this", "return;"]
    else:
        return_type = "object"
        return_statement = ["// TODO: implement this", "return null;"]

    return (name, return_type, return_statement)

def get_leading_whitespace(line):
    wstokens = re.split('(\s*)', line)
    whitespace = []
    i = 0
    while wstokens[i].strip() == "":
        whitespace.append(wstokens[i])
        i += 1
    whitespace = "".join(whitespace)

    return whitespace

def skeletonify(infilename, outfilename, verbose=False):

    fin = open(infilename, 'r')

    assert not os.path.exists(outfilename)

    fout = open(outfilename, 'w')

    implement_this = None
    implement_re = re.compile('[Pp]art \d.*:.*[Ii]mplement this')
    in_javadoc = False
    lines = fin.readlines()
    n = len(lines)
    i = 0

    while i < n:
        line = lines[i].rstrip('\n')
        if verbose: print "consuming: '%s'"%(lines[i].rstrip('\n'))
        if "begin solution" in line:
            # then skip all the lines until the end of the solution
            while not "end solution" in lines[i]:
                i += 1
                if verbose: print "Looking at: '%s'"%(lines[i].rstrip('\n'))
                if "begin solution" in lines[i]:
                    raise Exception("Nested 'begin solution'!")
            i += 1
            if i >= n:
                break

            line = lines[i].rstrip('\n')
            if verbose: print "consuming: '%s'"%(line)

        if "end solution" in line:
            raise Exception("'end solution' without a 'begin'!")

        print >>fout, line
        if verbose: print "echoing: '%s'"%(line)

        if line.strip().startswith('/**'):
            in_javadoc = True
        # check for an implement comment:
        if in_javadoc and implement_re.search(line):
            implement_this = line

        if in_javadoc and line.strip().endswith('*/'):
            in_javadoc = False
            if implement_this:
                i += 1
                while i < n and lines[i].strip() == "":
                    print >>fout, lines[i].rstrip('\n')
                    i += 1
                # now at the function signiture
                sig = lines[i].rstrip('\n')
                sig_indent = get_leading_whitespace(sig)
                (name, rt, rs) = parse_signature(sig)
                print >>fout, sig

                print "Implement:", implement_this.strip()
                print "          ", sig

                # find first subsequent non-blank line to
                # determine proper indentention
                i += 1
                while lines[i].strip() == "":
                    i += 1
                interior_indent = get_leading_whitespace(lines[i])

                # add the stub
                for r in rs:
                    print >>fout, interior_indent+r
                print >>fout, sig_indent+'}'

                # consume the remaining method body
                brace_level = 1
                while brace_level > 0:
                    brace_level += lines[i].count('{')
                    brace_level -= lines[i].count('}')
                    i += 1
                # we've gone now one line too far given our increment
                # below ... so go back one
                i -= 1
                implement_this = False

        i += 1

if __name__ == "__main__":
    import sys
    skeletonify(sys.argv[1], sys.argv[2])
