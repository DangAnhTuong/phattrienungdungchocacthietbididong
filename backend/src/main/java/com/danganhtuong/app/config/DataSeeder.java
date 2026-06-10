package com.danganhtuong.app.config;

import com.danganhtuong.app.entity.Category;
import com.danganhtuong.app.entity.Product;
import com.danganhtuong.app.entity.StaffAccount;
import com.danganhtuong.app.entity.Tag;
import com.danganhtuong.app.entity.User;
import com.danganhtuong.app.repository.CategoryRepository;
import com.danganhtuong.app.repository.ProductRepository;
import com.danganhtuong.app.repository.StaffAccountRepository;
import com.danganhtuong.app.repository.TagRepository;
import com.danganhtuong.app.repository.UserRepository;
import com.danganhtuong.app.entity.Attribute;
import com.danganhtuong.app.entity.AttributeValue;
import com.danganhtuong.app.entity.ProductAttributeValue;
import com.danganhtuong.app.repository.AttributeRepository;
import com.danganhtuong.app.repository.AttributeValueRepository;
import com.danganhtuong.app.repository.ProductTagRepository;
import com.danganhtuong.app.repository.ProductCategoryRepository;
import com.danganhtuong.app.entity.ProductTag;
import com.danganhtuong.app.entity.ProductCategory;
import com.danganhtuong.app.repository.ProductAttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final StaffAccountRepository staffAccountRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductAttributeValueRepository productAttributeValueRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            jdbcTemplate.execute("DROP TABLE IF EXISTS reviews CASCADE;");
            System.out.println("====== ĐÃ XÓA BẢNG REVIEWS ĐỂ ĐƯA VỀ CHUẨN 34 BẢNG ======");
        } catch (Exception e) {}
        
        if (categoryRepository.count() == 0) {
            // MAIN CATEGORIES
            Category women = Category.builder().categoryName("Women").build();
            Category men = Category.builder().categoryName("Men").build();
            Category kids = Category.builder().categoryName("Kids").build();
            categoryRepository.saveAll(List.of(women, men, kids));

            // WOMEN SUB-CATEGORIES
            Category wNew = Category.builder().categoryName("New").parent(women).imageUrl("assets/images/w_new_category_1780321859257.png").build();
            Category wClothes = Category.builder().categoryName("Clothes").parent(women).imageUrl("assets/images/w_clothes_category_1780321876516.png").build();
            Category wShoes = Category.builder().categoryName("Shoes").parent(women).imageUrl("assets/images/w_shoes_category_1780321893290.png").build();
            Category wAccessories = Category.builder().categoryName("Accessories").parent(women).imageUrl("assets/images/w_accessories_category_1780321908183.png").build();
            categoryRepository.saveAll(List.of(wNew, wClothes, wShoes, wAccessories));

            // MEN SUB-CATEGORIES
            Category mNew = Category.builder().categoryName("New").parent(men).imageUrl("assets/images/m_new_category_1780321943193.png").build();
            Category mClothes = Category.builder().categoryName("Clothes").parent(men).imageUrl("assets/images/m_clothes_category_1780321960318.png").build();
            categoryRepository.saveAll(List.of(mNew, mClothes));

            // KIDS SUB-CATEGORIES
            Category kClothes = Category.builder().categoryName("Clothes").parent(kids).imageUrl("assets/images/k_clothes_category_1780321972617.png").build();
            categoryRepository.saveAll(List.of(kClothes));

            // WOMEN -> NEW -> SUB
            Category wNewTops = Category.builder().categoryName("Tops").parent(wNew).build();
            Category wNewShirts = Category.builder().categoryName("Shirts & Blouses").parent(wNew).build();
            Category wNewCardigans = Category.builder().categoryName("Cardigans & Sweaters").parent(wNew).build();
            Category wNewKnitwear = Category.builder().categoryName("Knitwear").parent(wNew).build();
            Category wNewBlazers = Category.builder().categoryName("Blazers").parent(wNew).build();
            Category wNewOuterwear = Category.builder().categoryName("Outerwear").parent(wNew).build();
            Category wNewPants = Category.builder().categoryName("Pants").parent(wNew).build();
            Category wNewJeans = Category.builder().categoryName("Jeans").parent(wNew).build();
            Category wNewShorts = Category.builder().categoryName("Shorts").parent(wNew).build();
            Category wNewSkirts = Category.builder().categoryName("Skirts").parent(wNew).build();
            Category wNewDresses = Category.builder().categoryName("Dresses").parent(wNew).build();
            Category wNewSneakers = Category.builder().categoryName("Sneakers").parent(wNew).build();
            Category wNewBoots = Category.builder().categoryName("Boots").parent(wNew).build();
            Category wNewBags = Category.builder().categoryName("Bags").parent(wNew).build();
            Category wNewBelts = Category.builder().categoryName("Belts").parent(wNew).build();
            categoryRepository.saveAll(List.of(wNewTops, wNewShirts, wNewCardigans, wNewKnitwear, wNewBlazers, 
                    wNewOuterwear, wNewPants, wNewJeans, wNewShorts, wNewSkirts, wNewDresses, wNewSneakers, 
                    wNewBoots, wNewBags, wNewBelts));

            // WOMEN -> CLOTHES -> SUB
            Category wTops = Category.builder().categoryName("Tops").parent(wClothes).build();
            Category wShirts = Category.builder().categoryName("Shirts & Blouses").parent(wClothes).build();
            Category wCardigans = Category.builder().categoryName("Cardigans & Sweaters").parent(wClothes).build();
            Category wKnitwear = Category.builder().categoryName("Knitwear").parent(wClothes).build();
            Category wBlazers = Category.builder().categoryName("Blazers").parent(wClothes).build();
            Category wOuterwear = Category.builder().categoryName("Outerwear").parent(wClothes).build();
            Category wPants = Category.builder().categoryName("Pants").parent(wClothes).build();
            Category wJeans = Category.builder().categoryName("Jeans").parent(wClothes).build();
            categoryRepository.saveAll(List.of(wTops, wShirts, wCardigans, wKnitwear, wBlazers, wOuterwear, wPants, wJeans));

            // WOMEN -> SHOES -> SUB
            Category wSneakers = Category.builder().categoryName("Sneakers").parent(wShoes).build();
            Category wBoots = Category.builder().categoryName("Boots").parent(wShoes).build();
            categoryRepository.saveAll(List.of(wSneakers, wBoots));

            // WOMEN -> ACCESSORIES -> SUB
            Category wBags = Category.builder().categoryName("Bags").parent(wAccessories).build();
            Category wBelts = Category.builder().categoryName("Belts").parent(wAccessories).build();
            categoryRepository.saveAll(List.of(wBags, wBelts));

            // TAGS
            Tag newTag = tagRepository.save(Tag.builder().tagName("New").build());
            Tag saleTag = tagRepository.save(Tag.builder().tagName("Sale").build());
            Tag winterTag = tagRepository.save(Tag.builder().tagName("Winter collection").build());
            Tag summerTag = tagRepository.save(Tag.builder().tagName("Summer Sale").build());

            // --- 6 MORE TOPS ---
            Product top1 = Product.builder().productName("Trendy New Top").brand("Mango").salePrice(45.0).isNewBadge(true).imageUrl("assets/images/womens_new_top_1780310094690.png").imageUrl2("assets/images/trendy_top_2.png").imageUrl3("assets/images/trendy_top_3.png").rating(4.8).ratingCount(15).build();
            Product top2 = Product.builder().productName("Elegant Red Top").brand("Zara").salePrice(50.0).isNewBadge(true).imageUrl("assets/images/elegant_red_top.png").imageUrl2("assets/images/red_top_2.png").imageUrl3("assets/images/red_top_3.png").rating(4.5).ratingCount(8).build();
            Product top3 = Product.builder().productName("Red Striped Top").brand("OVS").salePrice(43.0).isNewBadge(true).imageUrl("assets/images/red_striped_top.png").imageUrl2("assets/images/striped_top_2.png").imageUrl3("assets/images/striped_top_3.png").rating(4.0).ratingCount(12).build();
            Product top4 = Product.builder().productName("Classic White T-Shirt").brand("Mango").salePrice(30.0).comparePrice(40.0).discountTag("-25%").imageUrl("assets/images/white_tshirt.png").imageUrl2("assets/images/white_tshirt_2.png").imageUrl3("assets/images/white_tshirt_3.png").rating(5.0).ratingCount(8).build();
            Product top5 = Product.builder().productName("Summer Crop Top").brand("H&M").salePrice(25.0).imageUrl("assets/images/summer_crop_top.png").imageUrl2("assets/images/crop_top_2.png").imageUrl3("assets/images/crop_top_3.png").rating(4.2).ratingCount(18).build();
            Product top6 = Product.builder().productName("Basic Black Top").brand("Uniqlo").salePrice(22.0).comparePrice(30.0).discountTag("-26%").imageUrl("assets/images/basic_black_top.png").imageUrl2("assets/images/black_top_2.png").imageUrl3("assets/images/black_top_3.png").rating(4.9).ratingCount(40).build();
            Product top7 = Product.builder().productName("Cotton Tank Top").brand("GAP").salePrice(18.0).imageUrl("assets/images/cotton_tank_top.png").imageUrl2("assets/images/tank_top_2.png").imageUrl3("assets/images/tank_top_3.png").rating(4.6).ratingCount(25).build();
            Product top8 = Product.builder().productName("Floral Top").brand("Dorothy Perkins").salePrice(35.0).imageUrl("assets/images/floral_top.png").imageUrl2("assets/images/floral_top_2.png").imageUrl3("assets/images/floral_top_3.png").rating(4.1).ratingCount(10).build();

            // --- 6 MORE SHIRTS & BLOUSES ---
            Product shirt1 = Product.builder().productName("Modern New Shirt").brand("H&M").salePrice(38.0).isNewBadge(true).imageUrl("assets/images/womens_new_shirt_1780310106832.png").imageUrl2("assets/images/new_shirt_2.png").imageUrl3("assets/images/new_shirt_3.png").rating(4.9).ratingCount(22).build();
            Product shirt2 = Product.builder().productName("Casual Blue Shirt").brand("Uniqlo").salePrice(35.0).isNewBadge(true).imageUrl("assets/images/casual_blue_shirt_1780321992920.png").imageUrl2("assets/images/blue_shirt_2.png").imageUrl3("assets/images/blue_shirt_3.png").rating(4.6).ratingCount(12).build();
            Product shirt3 = Product.builder().productName("T-Shirt SPANISH").brand("Mango").salePrice(9.0).imageUrl("assets/images/spanish_tshirt_1780322027323.png").imageUrl2("assets/images/spanish_tshirt_2.png").imageUrl3("assets/images/spanish_tshirt_3.png").rating(4.0).ratingCount(3).build();
            Product shirt4 = Product.builder().productName("Blouse").brand("Dorothy Perkins").salePrice(14.0).comparePrice(21.0).discountTag("-20%").imageUrl("assets/images/elegant_blouse_1780322048417.png").imageUrl2("assets/images/elegant_blouse_2.png").imageUrl3("assets/images/elegant_blouse_3.png").rating(5.0).ratingCount(10).build();
            Product shirt5 = Product.builder().productName("Light blouse").brand("Dorothy Perkins").salePrice(14.0).comparePrice(21.0).discountTag("-20%").imageUrl("assets/images/light_summer_blouse_1780322066283.png").imageUrl2("assets/images/light_summer_blouse_1780322066283.png").imageUrl3("assets/images/light_summer_blouse_1780322066283.png").rating(5.0).ratingCount(10).build();
            Product shirt6 = Product.builder().productName("Oversized Shirt").brand("Zara").salePrice(45.0).isNewBadge(true).imageUrl("assets/images/oversized_shirt_1780322080575.png").imageUrl2("assets/images/oversized_shirt_1780322080575.png").imageUrl3("assets/images/oversized_shirt_1780322080575.png").rating(4.7).ratingCount(50).build();
            Product shirt7 = Product.builder().productName("Silk Blouse").brand("Massimo Dutti").salePrice(85.0).imageUrl("assets/images/silk_blouse_1780322105508.png").imageUrl2("assets/images/silk_blouse_1780322105508.png").imageUrl3("assets/images/silk_blouse_1780322105508.png").rating(4.9).ratingCount(14).build();
            Product shirt8 = Product.builder().productName("Denim Shirt").brand("Levi's").salePrice(60.0).comparePrice(75.0).discountTag("-20%").imageUrl("assets/images/denim_shirt_1780322123103.png").imageUrl2("assets/images/denim_shirt_1780322123103.png").imageUrl3("assets/images/denim_shirt_1780322123103.png").rating(4.8).ratingCount(65).build();

            // --- OTHER PRODUCTS ---
            Product p4 = Product.builder().productName("Cozy Beige Cardigan").brand("Zara").salePrice(55.0).comparePrice(75.0).discountTag("-26%").imageUrl("assets/images/womens_cardigan_1780309105038.png").imageUrl2("assets/images/womens_cardigan_1780309105038.png").imageUrl3("assets/images/womens_cardigan_1780309105038.png").rating(4.8).ratingCount(40).build();
            Product p5 = Product.builder().productName("White Knitted Sweater").brand("Mango").salePrice(45.0).imageUrl("assets/images/white_knitted_sweater_1780322138766.png").imageUrl2("assets/images/white_knitted_sweater_1780322138766.png").imageUrl3("assets/images/white_knitted_sweater_1780322138766.png").rating(4.7).ratingCount(34).build();
            Product p6 = Product.builder().productName("Black Formal Blazer").brand("Zara").salePrice(85.0).isNewBadge(true).imageUrl("assets/images/black_formal_blazer_1780322154598.png").imageUrl2("assets/images/black_formal_blazer_1780322154598.png").imageUrl3("assets/images/black_formal_blazer_1780322154598.png").rating(4.9).ratingCount(80).build();
            Product p7 = Product.builder().productName("Stylish Winter Coat").brand("Dorothy Perkins").salePrice(120.0).comparePrice(150.0).discountTag("-20%").imageUrl("assets/images/womens_outerwear_1780309127422.png").imageUrl2("assets/images/womens_outerwear_1780309127422.png").imageUrl3("assets/images/womens_outerwear_1780309127422.png").rating(4.6).ratingCount(56).build();
            Product p8 = Product.builder().productName("Wide Leg Trousers").brand("Uniqlo").salePrice(40.0).isNewBadge(true).imageUrl("assets/images/wide_leg_trousers_1780322185212.png").imageUrl2("assets/images/wide_leg_trousers_1780322185212.png").imageUrl3("assets/images/wide_leg_trousers_1780322185212.png").rating(4.4).ratingCount(15).build();
            Product p9 = Product.builder().productName("Classic Blue Denim Jeans").brand("Levi's").salePrice(60.0).comparePrice(80.0).discountTag("-25%").imageUrl("assets/images/womens_jeans_1780309143285.png").imageUrl2("assets/images/womens_jeans_1780309143285.png").imageUrl3("assets/images/womens_jeans_1780309143285.png").rating(4.8).ratingCount(120).build();
            Product p10 = Product.builder().productName("Running Sneakers").brand("Nike").salePrice(90.0).isNewBadge(true).imageUrl("assets/images/running_sneakers.png").imageUrl2("assets/images/running_sneakers.png").imageUrl3("assets/images/running_sneakers.png").rating(4.7).ratingCount(200).build();
            Product p11 = Product.builder().productName("Leather Ankle Boots").brand("Clarks").salePrice(110.0).imageUrl("assets/images/leather_ankle_boots.png").imageUrl2("assets/images/leather_ankle_boots.png").imageUrl3("assets/images/leather_ankle_boots.png").rating(4.9).ratingCount(45).build();
            Product p12 = Product.builder().productName("Elegant Handbag").brand("Guess").salePrice(75.0).comparePrice(100.0).discountTag("-25%").imageUrl("assets/images/elegant_handbag.png").imageUrl2("assets/images/elegant_handbag.png").imageUrl3("assets/images/elegant_handbag.png").rating(4.6).ratingCount(88).build();
            Product p13 = Product.builder().productName("Classic Leather Belt").brand("Levis").salePrice(30.0).imageUrl("assets/images/classic_leather_belt.png").imageUrl2("assets/images/classic_leather_belt.png").imageUrl3("assets/images/classic_leather_belt.png").rating(4.5).ratingCount(20).build();

            // --- MISSING CATEGORIES ---
            Product pShorts = Product.builder().productName("Summer Denim Shorts").brand("H&M").salePrice(25.0).isNewBadge(true).imageUrl("assets/images/summer_shorts.png").imageUrl2("assets/images/summer_shorts.png").imageUrl3("assets/images/summer_shorts.png").rating(4.7).ratingCount(35).build();
            Product pSkirts = Product.builder().productName("Floral Midi Skirt").brand("Mango").salePrice(35.0).imageUrl("assets/images/floral_skirt.png").imageUrl2("assets/images/floral_skirt.png").imageUrl3("assets/images/floral_skirt.png").rating(4.8).ratingCount(15).build();
            Product pDresses = Product.builder().productName("Elegant Evening Dress").brand("Zara").salePrice(85.0).comparePrice(100.0).discountTag("-15%").imageUrl("assets/images/elegant_dress.png").imageUrl2("assets/images/elegant_dress.png").imageUrl3("assets/images/elegant_dress.png").rating(4.9).ratingCount(120).build();

            productRepository.saveAll(List.of(
                top1, top2, top3, top4, top5, top6, top7, top8,
                shirt1, shirt2, shirt3, shirt4, shirt5, shirt6, shirt7, shirt8,
                p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, pShorts, pSkirts, pDresses
            ));

            productTagRepository.save(ProductTag.builder().product(top1).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(top1).category(wNewTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top1).category(wTops).build());
            productTagRepository.save(ProductTag.builder().product(top2).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(top2).category(wNewTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top2).category(wTops).build());
            productTagRepository.save(ProductTag.builder().product(top3).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(top3).category(wNewTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top3).category(wTops).build());
            productTagRepository.save(ProductTag.builder().product(top4).tag(saleTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(top4).category(wNewTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top4).category(wTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top5).category(wNewTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top5).category(wTops).build());
            productTagRepository.save(ProductTag.builder().product(top6).tag(saleTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(top6).category(wNewTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top6).category(wTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top7).category(wNewTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top7).category(wTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top8).category(wNewTops).build());
            productCategoryRepository.save(ProductCategory.builder().product(top8).category(wTops).build());
            productTagRepository.save(ProductTag.builder().product(shirt1).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt1).category(wNewShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt1).category(wShirts).build());
            productTagRepository.save(ProductTag.builder().product(shirt2).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt2).category(wNewShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt2).category(wShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt3).category(wNewShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt3).category(wShirts).build());
            productTagRepository.save(ProductTag.builder().product(shirt4).tag(saleTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt4).category(wNewShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt4).category(wShirts).build());
            productTagRepository.save(ProductTag.builder().product(shirt5).tag(saleTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt5).category(wNewShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt5).category(wShirts).build());
            productTagRepository.save(ProductTag.builder().product(shirt6).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt6).category(wNewShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt6).category(wShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt7).category(wNewShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt7).category(wShirts).build());
            productTagRepository.save(ProductTag.builder().product(shirt8).tag(saleTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt8).category(wNewShirts).build());
            productCategoryRepository.save(ProductCategory.builder().product(shirt8).category(wShirts).build());
            productTagRepository.save(ProductTag.builder().product(p4).tag(saleTag).build());
            productTagRepository.save(ProductTag.builder().product(p4).tag(winterTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p4).category(wCardigans).build());
            productCategoryRepository.save(ProductCategory.builder().product(p4).category(wNewCardigans).build());
            productTagRepository.save(ProductTag.builder().product(p5).tag(winterTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p5).category(wKnitwear).build());
            productCategoryRepository.save(ProductCategory.builder().product(p5).category(wNewKnitwear).build());
            productTagRepository.save(ProductTag.builder().product(p6).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p6).category(wBlazers).build());
            productCategoryRepository.save(ProductCategory.builder().product(p6).category(wNewBlazers).build());
            productTagRepository.save(ProductTag.builder().product(p7).tag(saleTag).build());
            productTagRepository.save(ProductTag.builder().product(p7).tag(winterTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p7).category(wOuterwear).build());
            productCategoryRepository.save(ProductCategory.builder().product(p7).category(wNewOuterwear).build());
            productTagRepository.save(ProductTag.builder().product(p8).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p8).category(wPants).build());
            productCategoryRepository.save(ProductCategory.builder().product(p8).category(wNewPants).build());
            productTagRepository.save(ProductTag.builder().product(p9).tag(saleTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p9).category(wJeans).build());
            productCategoryRepository.save(ProductCategory.builder().product(p9).category(wNewJeans).build());
            productTagRepository.save(ProductTag.builder().product(p10).tag(newTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p10).category(wSneakers).build());
            productCategoryRepository.save(ProductCategory.builder().product(p10).category(wNewSneakers).build());
            productTagRepository.save(ProductTag.builder().product(p11).tag(winterTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p11).category(wBoots).build());
            productCategoryRepository.save(ProductCategory.builder().product(p11).category(wNewBoots).build());
            productTagRepository.save(ProductTag.builder().product(p12).tag(saleTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(p12).category(wBags).build());
            productCategoryRepository.save(ProductCategory.builder().product(p12).category(wNewBags).build());
            productCategoryRepository.save(ProductCategory.builder().product(p13).category(wBelts).build());
            productCategoryRepository.save(ProductCategory.builder().product(p13).category(wNewBelts).build());
            productTagRepository.save(ProductTag.builder().product(pShorts).tag(newTag).build());
            productTagRepository.save(ProductTag.builder().product(pShorts).tag(summerTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(pShorts).category(wNewShorts).build());
            productCategoryRepository.save(ProductCategory.builder().product(pSkirts).category(wNewSkirts).build());
            productTagRepository.save(ProductTag.builder().product(pDresses).tag(saleTag).build());
            productCategoryRepository.save(ProductCategory.builder().product(pDresses).category(wNewDresses).build());

            // STAFF ACCOUNTS
            if (staffAccountRepository.count() == 0) {
                StaffAccount s1 = StaffAccount.builder().firstName("John").lastName("Doe").email("john@store.com").passwordHash("hashed_pw").active(true).roleName("Store Administrator").privileges("ALL").build();
                staffAccountRepository.saveAll(List.of(s1));
            }

            // USERS
            if (userRepository.count() == 0) {
                User u1 = User.builder().name("Customer One").email("cust1@gmail.com").password("pwd").provider("LOCAL").build();
                userRepository.saveAll(List.of(u1));
            }

            System.out.println("====== ĐÃ TẠO DỮ LIỆU MẪU (NEW CÓ NHIỀU CATEGORY, THÊM 8 SẢN PHẨM MỖI LOẠI) THÀNH CÔNG ======");
        }

        // Cập nhật lại ảnh do AI tạo nếu DB đã có sẵn
        List<Product> allProducts = productRepository.findAll();
        boolean changed = false;
        for (Product p : allProducts) {
            if ("Running Sneakers".equals(p.getProductName())) { p.setImageUrl("assets/images/running_sneakers.png"); changed = true; }
            if ("Leather Ankle Boots".equals(p.getProductName())) { p.setImageUrl("assets/images/leather_ankle_boots.png"); changed = true; }
            if ("Elegant Handbag".equals(p.getProductName())) { p.setImageUrl("assets/images/elegant_handbag.png"); changed = true; }
            if ("Classic Leather Belt".equals(p.getProductName())) { p.setImageUrl("assets/images/classic_leather_belt.png"); changed = true; }
        }
        if (changed) {
            productRepository.saveAll(allProducts);
            System.out.println("====== ĐÃ CẬP NHẬT ẢNH MỚI (AI GENERATED) CHO CÁC SẢN PHẨM BỊ LỖI ======");
        }

        if (attributeRepository.count() == 0) {
            Attribute sizeAttr = attributeRepository.save(Attribute.builder().attributeName("Size").build());
            Attribute colorAttr = attributeRepository.save(Attribute.builder().attributeName("Color").build());

            AttributeValue sizeS = attributeValueRepository.save(AttributeValue.builder().value("S").attribute(sizeAttr).build());
            AttributeValue sizeM = attributeValueRepository.save(AttributeValue.builder().value("M").attribute(sizeAttr).build());
            AttributeValue sizeL = attributeValueRepository.save(AttributeValue.builder().value("L").attribute(sizeAttr).build());
            AttributeValue sizeXL = attributeValueRepository.save(AttributeValue.builder().value("XL").attribute(sizeAttr).build());

            AttributeValue colorBlack = attributeValueRepository.save(AttributeValue.builder().value("Black").attribute(colorAttr).build());
            AttributeValue colorWhite = attributeValueRepository.save(AttributeValue.builder().value("White").attribute(colorAttr).build());
            AttributeValue colorRed = attributeValueRepository.save(AttributeValue.builder().value("Red").attribute(colorAttr).build());

            for (Product p : allProducts) {
                if (p.getDescription() == null || p.getDescription().isEmpty()) {
                    p.setDescription("High quality product from " + p.getBrand() + ". Designed for maximum comfort and style. Made from premium materials ensuring durability and elegance.");
                }
                productAttributeValueRepository.save(ProductAttributeValue.builder().product(p).attributeValue(sizeS).build());
                productAttributeValueRepository.save(ProductAttributeValue.builder().product(p).attributeValue(sizeM).build());
                productAttributeValueRepository.save(ProductAttributeValue.builder().product(p).attributeValue(sizeL).build());
                
                productAttributeValueRepository.save(ProductAttributeValue.builder().product(p).attributeValue(colorBlack).build());
                productAttributeValueRepository.save(ProductAttributeValue.builder().product(p).attributeValue(colorWhite).build());
            }
            productRepository.saveAll(allProducts);
            System.out.println("====== ĐÃ TẠO DỮ LIỆU ATTRIBUTE (SIZE, COLOR) VÀ DESCRIPTION ======");
        }
    }
}
