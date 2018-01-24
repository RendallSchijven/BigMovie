f = open('countries.csv', "r")

keys = []
values = []

f.seek(0)
for line in f:
    words = line.split("=")
    keys.append(words[0].strip())
    values.append(words[1].strip())
    words = []

f.close()
finalString = ""

for i in range(len(keys)):
    finalString+="! "+ values[i] + " = " + keys[i] + "\n"

file = open("out.txt", "w")
file.write(finalString)
file.close()
 