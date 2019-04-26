import json
import re

expected = """
Number of tests to execute: 2
-------------------------------------------------------------------------------
Starting: checkExtraCredit
 Annotations:
   - @wsuv.autolab.Category(value="Extra Credit")
   - @org.junit.Test(timeout=0, expected=org.junit.Test$None.class)
Failed: checkExtraCredit
Hint: test timed out after 20 milliseconds
Finished: checkExtraCredit

Starting: checkNormal
 Annotations:
   - @org.junit.Test(timeout=0, expected=org.junit.Test$None.class)
Finished: checkNormal

-------------------------------------------------------------------------------
Number of tests executed: 2
Number Passed/Failed/Ignored: 1/1/0
Maximum points possible: 100.0
Total points obtained: 50.0
{"Extra Credit":0.0,"Correctness":50.0}
"""

def test(outputlines, outfile):
  lines = "".join(outputlines)

  return has(lines, expected, "expected test output")

def has(actualoutput, substring, tagline=None):
  if tagline is None:
    tagline = substring[:60]
    if len(substring) > 60:
      tagline = tagline + "..."

  ok = (substring in actualoutput)
    
  print(" - Looking for '%s' in output [%s]"%(tagline, "OK" if ok else "FAIL"))
  return ok

def has_json(outputlines, jsonstring, tagline=None):
  if tagline is None:
    tagline = jsonstring[:60]
    if len(jsonstring) > 60:
      tagline = tagline + "..."

  expectedjson = json.loads(jsonstring)
  ansi_escape = re.compile(r'\x1B\[[0-?]*[ -/]*[@-~]')
  
  obtainedjson = json.loads(ansi_escape.sub('', outputlines[-1]))
  ok = (obtainedjson == expectedjson)
    
  print(" - looking for expected json ('%s') at end of output [%s]"%(tagline, "OK" if ok else "FAIL"))
  return ok

