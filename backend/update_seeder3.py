import re

with open('a:/moblie/app/src/main/java/com/danganhtuong/app/config/DataSeeder.java', 'r', encoding='utf-8') as f:
    content = f.read()

def replace_product(match):
    full_str = match.group(0)
    url = match.group(1)
    
    # 1-4
    if 'womens_new_top' in url:
        u2, u3 = 'assets/images/trendy_top_2.png', 'assets/images/trendy_top_3.png'
    elif 'elegant_red_top' in url:
        u2, u3 = 'assets/images/red_top_2.png', 'assets/images/red_top_3.png'
    elif 'red_striped_top' in url:
        u2, u3 = 'assets/images/striped_top_2.png', 'assets/images/striped_top_3.png'
    elif 'white_tshirt' in url:
        u2, u3 = 'assets/images/white_tshirt_2.png', 'assets/images/white_tshirt_3.png'
    # 5-8
    elif 'summer_crop_top' in url:
        u2, u3 = 'assets/images/crop_top_2.png', 'assets/images/crop_top_3.png'
    elif 'basic_black_top' in url:
        u2, u3 = 'assets/images/black_top_2.png', 'assets/images/black_top_3.png'
    elif 'cotton_tank_top' in url:
        u2, u3 = 'assets/images/tank_top_2.png', 'assets/images/tank_top_3.png'
    elif 'floral_top' in url:
        u2, u3 = 'assets/images/floral_top_2.png', 'assets/images/floral_top_3.png'
    # 9-12
    elif 'womens_new_shirt' in url:
        u2, u3 = 'assets/images/new_shirt_2.png', 'assets/images/new_shirt_3.png'
    elif 'casual_blue_shirt' in url:
        u2, u3 = 'assets/images/blue_shirt_2.png', 'assets/images/blue_shirt_3.png'
    elif 'spanish_tshirt' in url:
        u2, u3 = 'assets/images/spanish_tshirt_2.png', 'assets/images/spanish_tshirt_3.png'
    elif 'elegant_blouse' in url:
        u2, u3 = 'assets/images/elegant_blouse_2.png', 'assets/images/elegant_blouse_3.png'
    else:
        # duplicate the original image
        u2, u3 = url, url
        
    return f'.imageUrl("{url}").imageUrl2("{u2}").imageUrl3("{u3}")'

# Match the entire chain of imageUrl calls
content = re.sub(r'\.imageUrl\("(.*?)"\)\.imageUrl2\("(.*?)"\)\.imageUrl3\("(.*?)"\)', replace_product, content)

with open('a:/moblie/app/src/main/java/com/danganhtuong/app/config/DataSeeder.java', 'w', encoding='utf-8') as f:
    f.write(content)
print('Done!')
