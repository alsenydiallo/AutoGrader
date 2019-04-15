---
general:
  name: LAB_NAME__
  description: ''
  display_name: LAB_NAME_DISPLAY__
  handin_filename: HANDIN_FILE__
  handin_directory: handin
  max_grace_days: 0
  handout: LAB_NAME__-handout.zip
  writeup: writeup/LAB_NAME__.html
  max_submissions: -1
  disable_handins: false
  max_size: 2
  has_svn: false
  category_name: Lab
problems:
- name: Correctness
  description: ''
  max_score: SCORE_MAX_CORRECTNESS__
  optional: false
- name: TA/Design/Readability
  description: ''
  max_score: SCORE_MAX_TA__
  optional: false
autograder:
  autograde_timeout: AUTOGRADE_TIMEOUT__
  autograde_image: AUTOGRADE_IMAGE__
  release_score: true
