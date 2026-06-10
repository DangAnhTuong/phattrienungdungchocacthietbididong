import re

with open('a:/moblie/app/src/main/java/com/danganhtuong/app/config/DataSeeder.java', 'r', encoding='utf-8') as f:
    lines = f.readlines()

out = []
for line in lines:
    if 'Category.builder()' in line:
        line = re.sub(r'\.imageUrl2\(".*?"\)', '', line)
        line = re.sub(r'\.imageUrl3\(".*?"\)', '', line)
    out.append(line)

with open('a:/moblie/app/src/main/java/com/danganhtuong/app/config/DataSeeder.java', 'w', encoding='utf-8') as f:
    f.writelines(out)
print('Fixed!')
