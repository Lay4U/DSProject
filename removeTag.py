import urllib.request
import sys
import re


path = 'https://en.wikipedia.org/wiki/Distributed_computing'

with urllib.request.urlopen(path) as response:
	html = response.read().decode("utf-8")

body = re.search('<body.*</body>', html, re.I|re.S)

if (body is None) :
	print ("No <body> in html")
	exit()

body = body.group()
body = re.sub('<script.*?>.*?</script>', '', body, 0, re.I|re.S)

text = re.sub('<.+?>', '', body, 0, re.I|re.S)

result = re.sub('&nbsp;| |\t|\r|\n', ' ', text)
f = open("result.txt", 'w', encoding='utf-8')
f.write(result)
f.close()
print (result)
print ('html = ', len(html), ', text = ', len(text), ', nospace = ', len(result))


