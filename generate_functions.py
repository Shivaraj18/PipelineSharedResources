#!/usr/bin/env python3

import os
os.chdir('functions')
function_dir = os.getcwd()
print("function_dir: " + function_dir)

### Create variable containing all shared functions
whole_text = ""
for filename in os.listdir(function_dir):
	with open(filename) as fp:
		v = fp.read()
		whole_text += v

### Clean up duplicate lines from merge
whole_text = whole_text.replace('#!/usr/bin/env groovy', '')
whole_text = whole_text.replace('return this', '')

### Add lines only at top and bottom
whole_text_final = ''
whole_text_final += '#!/usr/bin/env groovy'
whole_text_final += "\n"
whole_text_final += whole_text
whole_text_final += "\n"
whole_text_final += 'return this'
#print(whole_text_final)
f = open('functions2.txt', 'a+')
f.write(str(whole_text_final))
