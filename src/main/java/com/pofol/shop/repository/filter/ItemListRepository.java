package com.pofol.shop.repository.filter;


import com.pofol.shop.domain.dto.item.Item;
import com.pofol.shop.domain.dto.item.ItemCondition;
import com.pofol.shop.domain.dto.item.ItemDTO;
import com.pofol.shop.domain.dto.item.ItemImageDTO;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pofol.shop.domain.QCategory.category;
import static com.pofol.shop.domain.QColor.*;
import static com.pofol.shop.domain.QGoods.goods;
import static com.pofol.shop.domain.QItem.*;
import static com.pofol.shop.domain.QSize.*;
import static com.pofol.shop.domain.QSubcategory.subcategory;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemListRepository {

    private final JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public Page<ItemDTO> getFilterItemList(ItemCondition condition, Pageable pageable) {

        OrderSpecifier<?>[] orders = getOrderSpecifiers(condition);

        List<Item> items = queryFactory.select(item).from(item)
                .innerJoin(item.subCategory, subcategory)
                .innerJoin(subcategory.category, category)
                .where(
                        categoryEq(condition.getCategory()),
                        subCategoryEq(condition.getSubcategory()),
                        item.id.in(
                                queryFactory.select(goods.item.id).from(goods)
                                        .innerJoin(goods.color, color)
                                        .innerJoin(goods.size, size)
                                        .where(
                                                colorEq(condition.getColor()),
                                                sizeEq(condition.getSize())
                                        )
                        )
                ).orderBy(
                        orders
                ).limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(item.count()).from(item)
                .innerJoin(item.subCategory, subcategory)
                .innerJoin(subcategory.category, category)
                .where(
                        categoryEq(condition.getCategory()),
                        subCategoryEq(condition.getSubcategory()),
                        item.id.in(
                                queryFactory.select(goods.item.id).from(goods)
                                        .innerJoin(goods.color, color)
                                        .innerJoin(goods.size, size)
                                        .where(
                                                colorEq(condition.getColor()),
                                                sizeEq(condition.getSize())
                                        )
                        )
                );

        List<ItemDTO> list = getItemDTOList(items);


        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);
    }

    @Transactional(readOnly = true)
    public List<ItemDTO> getSliceItemList() {

        List<Item> items = queryFactory.select(item).from(item)
                .innerJoin(item.subCategory, subcategory)
                .innerJoin(subcategory.category, category)
                .where(
                        item.salesRate.goe(10),
                        item.id.in(
                                queryFactory.select(goods.item.id).from(goods)
                                        .innerJoin(goods.color, color)
                                        .innerJoin(goods.size, size)
                        )
                ).offset(1)
                .limit(12)
                .fetch();

        return getItemDTOList(items);


    }


    @Transactional(readOnly = true)
    public List<ItemDTO> findTop3BySalesRate() {
        List<Item> items = queryFactory.select(item).from(item)
                .orderBy(item.salesRate.asc())
                .limit(3)
                .fetch();
        List<ItemDTO> top3 = getItemDTOList(items);
        return top3;
    }


    private OrderSpecifier<?>[] getOrderSpecifiers(ItemCondition condition) {
        List<OrderSpecifier<?>> orderList = new ArrayList<>();

        if(condition.getHighestPrice() != null) {
            orderList.add(highestPrice(true));
        }

        if(condition.getHighestSalesRate() != null) {
            orderList.add(highestSalesRate(true));
        }

        if(condition.getLowestPrice() != null) {
            orderList.add(lowestPrice(true));
        }



        int size = orderList.size();
        OrderSpecifier<?>[] arr = orderList.toArray(new OrderSpecifier[size]);
        return arr;
    }

    private BooleanExpression categoryEq(Long category) {
        if(category != null) {
            log.info("category = {}", category);
            return item.subCategory.category.id.eq(category);
        }
        return null;
    }

    private BooleanExpression subCategoryEq(Long category) {
        if(category != null) {
            log.info("subcategory = {}", category);
            return item.subCategory.id.eq(category);
        }
        return null;
    }


    private BooleanExpression colorEq(Long color) {
        if(color != null) {
            return goods.color.id.eq(color);
        }
        return null;
    }

    private BooleanExpression sizeEq(Long size) {
        if(size != null) {
            return goods.size.id.eq(size);
        }
        return null;
    }


    private OrderSpecifier<Integer> highestPrice(Boolean isTrue) {
        if(isTrue != null) {
            return item.price.desc();
        }
        return null;
    }

    private OrderSpecifier<Integer> lowestPrice(Boolean isTrue) {
        if(isTrue != null) {
            return item.price.asc();
        }
        return null;
    }

    private OrderSpecifier<Integer> highestSalesRate(Boolean isTrue) {
        if(isTrue != null) {
            return item.salesRate.desc();
        }
        return null;
    }

    private List<ItemDTO> getItemDTOList(List<Item> items) {
        return items.stream()
                .map(i -> {
                    ItemDTO itemDTO = ItemDTO.builder()
                            .id(i.getId())
                            .name(i.getName())
                            .price(i.getPrice())
                            .mainImage(i.getItemImagesList()
                                    .stream()
                                    .filter(img -> img.getIsMainImg())
                                    .map(img -> new ItemImageDTO(img.getLocation(), img.getServerSavedName()))
                                    .findFirst().orElseThrow(() -> {
                                        throw new EntityNotFoundException();
                                    }))
                            .build();
                    return itemDTO;
                }).collect(Collectors.toList());
    }


}
