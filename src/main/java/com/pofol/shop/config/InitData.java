package com.pofol.shop.config;

import com.pofol.shop.domain.*;
import com.pofol.shop.domain.sub.Address;
import com.pofol.shop.repository.*;
import com.pofol.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.pofol.shop.domain.sub.StaticContainer.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    private final MemberService memberService;
    private final BCryptPasswordEncoder encoder;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final SubcategoryRepository subCategoryRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        Member testMember = Member.builder()
                .nickname("member")
                .introduce("hello")
                .address(new Address("23131","hi","qwdq",""))
                .sex("man")
                .email("qorrkr10@naver.com")
                .phoneNumber("01011111111")
                .password(encoder.encode("dwc02207"))
                .role("ROLE_USER")
                .enabled(true)
                .accountNonLocked(true)
                .build();

        Member adminMember = Member.builder()
                .nickname("admin")
                .introduce("hello")
                .address(new Address("23131","hi","qwdq",""))
                .sex("man")
                .email("admin123@naver.com")
                .phoneNumber("01011165111")
                .password(encoder.encode("dwc02207"))
                .role("ROLE_ADMIN")
                .enabled(true)
                .accountNonLocked(true)
                .build();
        memberService.save(testMember);
        memberService.save(adminMember);

        String[] AllCategory = Stream.of(OUTER, SHIRT, PANTS, SPORTS, SHOES, BAG, ACCESSORY)
                .flatMap(Stream::of)
                .toArray(String[]::new);
        Arrays.stream(COLOR)
                .map(color -> Color.builder().name(color).build())
                .forEach(colorRepository::save);

        setSize();


        Map<String, String[]> categoryMap = new HashMap<>();

        categoryMap.put("아우터", OUTER);
        categoryMap.put("셔츠", SHIRT);
        categoryMap.put("팬츠", PANTS);
        categoryMap.put("스포츠", SPORTS);
        categoryMap.put("신발", SHOES);
        categoryMap.put("가방", BAG);
        categoryMap.put("악세서리", ACCESSORY);

        Arrays.stream(CATEGORY)
                .map((name) -> Category.builder().name(name).build())
                .forEach(categoryRepository::save);

        categoryMap.forEach((k,v) -> {
            Category category = categoryRepository.findByName(k);
            Arrays.stream(v).forEach(name -> {
                Subcategory subCategory = Subcategory.builder()
                        .category(category)
                        .name(name)
                        .build();
                subCategoryRepository.save(subCategory);
            });
        });

        getMainImage();

        LongStream.rangeClosed(1,100).forEach(i -> {
            int baseSizeIndex = randomBox(4);
            int pantsShoesSizeIndex = randomBox(5);
            int otherSizeIndex = randomBox(3);
            int index = randomBox(8);
            int categoryIndex = randomBox(30);
            double grade = Math.round(((Math.random() * 5) * 10)) / 10.0;
            String[] size = null;

            String subCategoryName = AllCategory[categoryIndex];
            Subcategory subCategory = subCategoryRepository.findByName(subCategoryName);
            String substring = UUID.randomUUID().toString().substring(0, 10);
            Subcategory findSubcategory = subCategoryRepository.findSubCategoryBySubCategoryName(subCategoryName);
            String category = findSubcategory.getCategory().getName();
            int num = 0;

            if(category.equals("아우터") || category.equals("셔츠") || category.equals("스포츠")) {
                size = BASE_SIZE;
                num = 3;
            } else if(category.equals("팬츠")) {
                size = PANTS_SIZE;
                num = 4;
            } else if(category.equals("신발")) {
                size = SHOES_SIZE;
                num = 4;
            } else {
                if(subCategoryName.equals("반지")) {
                    size = RING_SIZE;
                    num = 2;
                } else if(subCategoryName.equals("모자")){
                    size = HAT_SIZE;
                    num = 2;
                } else {
                    size = ACCESSORY_SIZE;
                    num = 2;
                }
            }

            for(int j = 0; j <= num; j++) {
                Color color = colorRepository.findByName(COLOR[j]);
                Size sizeEntity = sizeRepository.findByName(size[j]);
                List<ItemImage> images = getSubImage();

                Item item = Item.builder()
                        .name(substring)
                        .price(10000*(index+1))
                        .size(sizeEntity)
                        .color(color)
                        .salesRate(baseSizeIndex * index)
                        .reviewGrade(grade)
                        .quantity(index)
                        .subCategory(subCategory)
                        .itemImagesList(images)
                        .build();
                images.stream().forEach(image -> image.setItem(item));
                itemRepository.save(item);
            }
        });

    }

    private ItemImage getMainImage() {

        String[] serverSavedNames = {"e94344a2-a947-49.jpg",
                "bead7915-4a24-41.jpg", "81d6a475-c950-40.gif", "2e369f8e-53fb-43.jpeg", "75a653c1-6828-4c.jpg", "10860436-d637-4e.jpg"};
        int index = randomBox(6);

        return ItemImage.builder()
                .isMainImg(true)
                .location("/img/2022/8/9")
                .serverSavedName(serverSavedNames[index])
                .userCustomName("random")
                .build();
    }

    private List<ItemImage> getSubImage() {

        String[] serverSavedNames = {"b0795f00-e98c-46.jpg",
                "0681620a-248e-45.jpg", "356ef42c-178f-48.jpg", "12ec17c3-235f-45.jpg", "0c78351d-60d5-40.jpg", "82b5454a-951c-4f.jpg",
                "cfd19255-bd6b-4b.jpg","69a6a5c3-bcfe-47.jpg","0c1e96c0-b99a-4b.jpg","7aa8b9cb-94d4-46.jpg", "c809b5d0-d046-4f.jpg",
                "4ea7b783-e221-4d.jpg", "303a284f-55d8-4f.jpg","b11b7b2e-9ec3-4a.jpg"
        };

        ItemImage mainImage = getMainImage();

        List<ItemImage> list = new ArrayList<>();
        list.add(mainImage);
        for(int i = 0; i < 3; i++) {
            int index = randomBox(14);
            ItemImage itemImage = ItemImage.builder()
                    .isMainImg(false)
                    .location("/img/2022/8/9")
                    .serverSavedName(serverSavedNames[index])
                    .userCustomName("random")
                    .build();
            list.add(itemImage);
        }
        return list;
    }

    private void setSize() {

        String categoryName = null;

        for (String size : ALL_SIZE) {

            if(Arrays.asList(BASE_SIZE).contains(size)) {
                categoryName = "BASE_SIZE";
                createSize(categoryName, size);
            } else if(Arrays.asList(PANTS_SIZE).contains(size)) {
                categoryName = "PANTS_SIZE";
                createSize(categoryName, size);
            } else if(Arrays.asList(SHOES_SIZE).contains(size)) {
                categoryName = "SHOES_SIZE";
                createSize(categoryName, size);
            } else if(Arrays.asList(HAT_SIZE).contains(size)) {
                categoryName = "HAT_SIZE";
                createSize(categoryName, size);
            } else if(Arrays.asList(RING_SIZE).contains(size)) {
                categoryName = "RING_SIZE";
                createSize(categoryName, size);
            } else if(Arrays.asList(ACCESSORY_SIZE).contains(size)) {
                categoryName = "ACCESSORY_SIZE";
                createSize(categoryName, size);
            }

        }


    }

    private void createSize(String categoryName, String size) {
        Size sizeEntity = Size.builder()
                .name(size)
                .categoryName(categoryName)
                .build();
        sizeRepository.save(sizeEntity);
    }

    private int randomBox(int i) {
        return (int) (Math.random() * i);
    }


}
