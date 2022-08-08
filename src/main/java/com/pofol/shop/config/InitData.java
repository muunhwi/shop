package com.pofol.shop.config;

import com.pofol.shop.domain.*;
import com.pofol.shop.domain.sub.Address;
import com.pofol.shop.repository.CategoryRepository;
import com.pofol.shop.repository.ItemRepository;
import com.pofol.shop.repository.SubcategoryRepository;
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

        String[] categoryName = {"아우터", "셔츠", "팬츠", "스포츠", "신발", "가방", "악세서리"};
        String[] outer ={"멘투맨", "반팔", "카라티", "후드티", "긴팔"};
        String[] shirt = {"반팔셔츠", "긴팔셔츠"};
        String[] pants = {"하프팬츠", "슬랙스", "트래이닝팬츠", "코튼팬츠", "밴딩팬츠"};
        String[] sports = {"상의", "하의"};
        String[] shoes = {"캐주얼화", "슬리퍼", "구두", "부츠"};
        String[] bag = {"메신져/크로스팩", "지갑", "백팩", "숄더/토트백", "클러치"};
        String[] accessory = {"기타", "반지", "타이", "시계", "안경/선글라스","모자", "목걸이/팔찌"};
        String[] AllCategory = Stream.of(outer, shirt, pants, sports, shoes, bag, accessory)
                .flatMap(Stream::of)
                .toArray(String[]::new);


        Map<String, String[]> categoryMap = new HashMap<>();

        categoryMap.put("아우터", outer);
        categoryMap.put("셔츠", shirt);
        categoryMap.put("팬츠", pants);
        categoryMap.put("스포츠", sports);
        categoryMap.put("신발", shoes);
        categoryMap.put("가방", bag);
        categoryMap.put("악세서리", accessory);

        Arrays.stream(categoryName)
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


        LongStream.rangeClosed(1,100).forEach(i -> {
            int baseSizeIndex = randomBox(4);
            int pantsShoesSizeIndex = randomBox(5);
            int otherSizeIndex = randomBox(3);
            int index = randomBox(8);
            int categoryIndex = randomBox(30);
            double grade = Math.round(((Math.random() * 5) * 10)) / 10.0;
            String size = null;

            String subCategoryName = AllCategory[categoryIndex];
            Subcategory subCategory = subCategoryRepository.findByName(subCategoryName);
            String substring = UUID.randomUUID().toString().substring(0, 10);

            Subcategory findSubcategory = subCategoryRepository.findCategoryNameBySubCategory(subCategoryName);
            String category = findSubcategory.getCategory().getName();

            if(category.equals("아우터") || category.equals("셔츠") || category.equals("스포츠")) {
                size = BASE_SIZE[baseSizeIndex];
            } else if(category.equals("팬츠")) {
                size = PANTS_SIZE[pantsShoesSizeIndex];
            } else if(category.equals("신발")) {
                size = SHOES_SIZE[pantsShoesSizeIndex];
            } else {
                if(subCategoryName.equals("반지")) {
                    size = RING_SIZE[otherSizeIndex];
                } else if(subCategoryName.equals("모자")){
                    size = HAT_SIZE[otherSizeIndex];
                } else {
                    size = ACCESSORY_SIZE[otherSizeIndex];
                }
            }

            Item item = Item.builder()
                    .name(substring)
                    .price(10000*(index+1))
                    .size(size)
                    .color(COLOR[index])
                    .salesRate(baseSizeIndex * index)
                    .reviewGrade(grade)
                    .quantity(index)
                    .subCategory(subCategory)
                    .build();

            if(i == 99) {
                Item test = Item.builder()
                        .name(substring+"test")
                        .price(10000*(index+1))
                        .size(size)
                        .color(COLOR[index])
                        .salesRate(baseSizeIndex * index)
                        .reviewGrade(5.0)
                        .quantity(index)
                        .subCategory(subCategory)
                        .build();
                itemRepository.save(test);
            }

            itemRepository.save(item);
        });

    }

    private int randomBox(int i) {
        return (int) (Math.random() * i);
    }


}
