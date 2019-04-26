import json
import re

def test(outputlines, outfile):
  exception = "Illegal Score annotation value. Method cannot have negative score value"
  jsonstr = '{"Correctness": 0.0, "Extra Credit 1": 0.0, "Extra Credit 2": 0.0, "Extra Credit 3": 0.0}'
  lines = "".join(outputlines)

  return (has(lines, exception) and has_json(outputlines, jsonstr))

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
