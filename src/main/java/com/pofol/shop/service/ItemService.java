package com.pofol.shop.service;

import com.pofol.shop.domain.dto.item.*;
import com.pofol.shop.repository.*;
import com.pofol.shop.repository.filter.ItemListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemListRepository itemListRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final CategoryRepository categoryRepository;
    private final GoodsRepository goodsRepository;

    public Page<ItemDTO> getFilterItemList(ItemCondition condition, Pageable pageable) {
        return itemListRepository.getFilterItemList(condition, pageable);
    }
    public List<ItemDTO> findTop3BySalesRate() {
        return itemListRepository.findTop3BySalesRate();
    }
    public ItemFilterDTO findItemFilterDTOssByCategoryName(Long categoryId, Long subCategoryId) {

        List<Subcategory> subcategories = subcategoryRepository.findSubCategoriesNameByCategoryId(categoryId);
        List<Color> colors = getColors();
        List<Size> size = getSize(subCategoryId);
        return new ItemFilterDTO(subcategories, colors, size);

    }
    public ItemFilterDTO findItemFilterByNoParamFilter() {

        List<Subcategory> subcategories = subcategoryRepository.findAll();
        List<Color> colors = getColors();
        List<Size> size = getSize(null);
        return new ItemFilterDTO(subcategories, colors, size);

    }
    public Item mapToItemSave(ItemFormDTO itemFormDTO) {
        Subcategory subcategory = subcategoryRepository.findSubCategoryBySubCategoryName(itemFormDTO.getSubcategory());
        Color color = colorRepository.findByName(itemFormDTO.getColor());
        Size size = sizeRepository.findByName(itemFormDTO.getSize());

        Item item = Item.builder()
                .name(itemFormDTO.getName())
                .salesRate(0)
                .subCategory(subcategory)
                .price(itemFormDTO.getPrice())
                .build();
        Item save = itemRepository.save(item);
        return save;
    }
    public List<Size> getSize(Long subCategoryId) {

        if(subCategoryId == null) {
            return sizeRepository.findAll();
        }

        Subcategory subcategory = subcategoryRepository.findSubCategoryBySubCategoryId(subCategoryId);
        String category = subcategory.getCategory().getName();
        String subCategoryName = subcategory.getName();
        String size = null;

        if(category == null) {
            return sizeRepository.findAll();
        } else if(category.equals("아우터") || category.equals("셔츠") || category.equals("스포츠")) {
            size ="BASE_SIZE";
        } else if(category.equals("팬츠")) {
            size = "PANTS_SIZE";
        } else if(category.equals("신발")) {
            size = "SHOES_SIZE";
        } else {
            if(subCategoryName == null) {
                size ="ACCESSORY_SIZE";
            } else if(subCategoryName.equals("반지")) {
                size = "RING_SIZE";
            } else if(subCategoryName.equals("모자")){
                size = "HAT_SIZE";
            } else {
                size = "ACCESSORY_SIZE";
            }
        }

        return  sizeRepository.findByCategoryName(size);

    }
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }
    public List<Color> getColors() {
        return colorRepository.findAll();
    }
    public ItemOverviewDTO getItemOverview(Long id) {

        Optional<Item> findItem = itemRepository.findById(id);

        Item item = findItem.orElseThrow(EntityNotFoundException::new);
        List<Goods> goods = goodsRepository.findGoodsByItemId(id);

        return ItemOverviewDTO.builder()
                .id(item.getId())
                .color(goods.stream()
                        .map(g -> {
                            ColorDTO build = ColorDTO.builder()
                                            .id(g.getColor().getId())
                                            .name(g.getColor().getName())
                                            .build();
                                    return build;
                                }
                        ).distinct()
                        .sorted((c1,c2) -> {
                            if(c1.getId() > c2.getId()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }).collect(Collectors.toList()))
                .size(goods.stream()
                        .map(s -> {
                            SizeDTO build1 = SizeDTO.builder()
                                    .id(s.getSize().getId())
                                    .name(s.getSize().getName())
                                    .build();
                            return build1;
                        })
                        .distinct()
                        .sorted((s1,s2) -> {
                            if(s1.getId() > s2.getId()) {
                                return 1;
                            } else {
                                return -1;
                            }
                        }).collect(Collectors.toList()))
                .name(item.getName())
                .price(item.getPrice())
                .salesRate(item.getSalesRate())
                .Images(item.getItemImagesList()
                        .stream()
                        .filter(img -> !img.getIsMainImg())
                        .map(img -> ItemImageDTO
                                .builder()
                                .location(img.getLocation())
                                .serverSavedName(img.getServerSavedName())
                                .build())
                        .collect(Collectors.toList()))
                .mainImage(item.getItemImagesList()
                        .stream()
                        .filter(ItemImage::getIsMainImg)
                        .map(img -> ItemImageDTO
                                .builder()
                                .location(img.getLocation())
                                .serverSavedName(img.getServerSavedName())
                                .build())
                        .findFirst().orElseThrow(() -> {
                            throw new EntityNotFoundException();
                        }))
                .build();
    }
    public List<ItemDTO> getSliceItemList() {
        return itemListRepository.getSliceItemList();
    }

}
