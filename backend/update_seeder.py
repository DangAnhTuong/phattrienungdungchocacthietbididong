import re

with open('a:/moblie/app/src/main/java/com/danganhtuong/app/config/DataSeeder.java', 'r', encoding='utf-8') as f:
    content = f.read()

images = [
    'assets/images/elegant_red_top.png',
    'assets/images/red_striped_top.png',
    'assets/images/white_tshirt.png',
    'assets/images/summer_crop_top.png',
    'assets/images/floral_top.png',
    'assets/images/floral_skirt.png',
    'assets/images/elegant_dress.png'
]

def replace_product(match):
    full_str = match.group(0)
    url = match.group(1)
    
    # Check if we have specific generated images for this product
    if 'womens_new_top' in url:
        u2, u3 = 'assets/images/trendy_top_2.png', 'assets/images/trendy_top_3.png'
    elif 'elegant_red_top' in url:
        u2, u3 = 'assets/images/red_top_2.png', 'assets/images/red_top_3.png'
    elif 'red_striped_top' in url:
        u2, u3 = 'assets/images/striped_top_2.png', 'assets/images/striped_top_3.png'
    elif 'white_tshirt' in url:
        u2, u3 = 'assets/images/white_tshirt_2.png', 'assets/images/white_tshirt_3.png'
    else:
        # random deterministic selection
        idx = hash(url) % len(images)
        u2 = images[idx]
        u3 = images[(idx + 1) % len(images)]
        
    return full_str + f'.imageUrl2("{u2}").imageUrl3("{u3}")'

content = re.sub(r'\.imageUrl\(\"(.*?)\"\)', replace_product, content)

with open('a:/moblie/app/src/main/java/com/danganhtuong/app/config/DataSeeder.java', 'w', encoding='utf-8') as f:
    f.write(content)
print('Done!')
