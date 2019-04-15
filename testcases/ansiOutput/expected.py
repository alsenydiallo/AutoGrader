import json
import re


expected = """
Number of tests to execute: 2
-------------------------------------------------------------------------------
\x1b[32mStarting: \x1b[0mcheckExtraCredit
\x1b[32mFinished: \x1b[0mcheckExtraCredit

\x1b[32mStarting: \x1b[0mcheckNormal
\x1b[32mFinished: \x1b[0mcheckNormal

-------------------------------------------------------------------------------
Number of tests executed: 2
Number Passed/Failed/Ignored: 2/0/0
Maximum points possible: 100.0
Total points obtained: 100.0
{"Extra Credit":50.0,"Correctness":50.0}
"""


def test(outputlines, outfile):
  lines = "".join(outputlines)

  return has(lines, expected, 'ansi colored test results')


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
