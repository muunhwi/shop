package com.pofol.shop.repository.filter;


import com.pofol.shop.domain.Item;
import com.pofol.shop.domain.ItemImage;
import com.pofol.shop.domain.dto.*;

import com.querydsl.core.Tuple;
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
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pofol.shop.domain.QCategory.category;
import static com.pofol.shop.domain.QItem.*;
import static com.pofol.shop.domain.QSubcategory.subcategory;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemListRepository {

    private final JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public Page<ItemDTO> getFilterItemList(ItemCondition condition, Pageable pageable) {

        OrderSpecifier<?>[] orders = getOrderSpecifiers(condition);

        List<Item> items = queryFactory.select(item)
                .from(item)
                .innerJoin(item.subCategory, subcategory)
                .fetchJoin()
                .innerJoin(subcategory.category, category)
                .fetchJoin()
                .where(
                        categoryEq(condition.getCategory()),
                        subCategoryEq(condition.getSubcategory()),
                        colorEq(condition.getColor()),
                        sizeEq(condition.getSize())
                ).orderBy(
                        orders
                ).offset(pageable.getOffset()
                ).limit(pageable.getPageSize())
                .fetch();

        List<ItemDTO> list = items.stream().map(i -> {
            ItemDTO itemDTO = new ItemDTO(
                    i.getId(),
                    i.getName(),
                    i.getPrice(),
                    i.getQuantity(),
                    i.getSize().getName(),
                    i.getColor().getName(),
                    i.getReviewGrade(),
                    i.getSalesRate(),
                    i.getSubCategory().getName(),
                    i.getSubCategory().getCategory().getName());
            Optional<ItemMainImageDTO> mainImage = i.getItemImagesList()
                    .stream()
                    .filter(ItemImage::getIsMainImg)
                    .map(img -> new ItemMainImageDTO(img.getLocation(), img.getServerSavedName()))
                    .findFirst();
            itemDTO.setMainImage(mainImage.orElseThrow(() -> {throw new EntityNotFoundException();}));
            return itemDTO;
        }).collect(Collectors.toList());


        JPAQuery<Long> countQuery = queryFactory.select(item.count())
                .from(item)
                .innerJoin(item.subCategory, subcategory)
                .innerJoin(subcategory.category, category)
                .where(
                        categoryEq(condition.getCategory()),
                        subCategoryEq(condition.getSubcategory()),
                        colorEq(condition.getColor()),
                        sizeEq(condition.getSize())
                );

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

    }

    @Transactional(readOnly = true)
    public List<ItemTop3DTO> findTop3BySalesRate() {

        List<Item> list = queryFactory.selectFrom(item)
                .orderBy(item.salesRate.desc())
                .limit(3L).fetch();

        List<ItemTop3DTO> top3 = list.stream().map(i -> {
                    ItemTop3DTO itemTop3DTO =
                            new ItemTop3DTO(
                                    i.getId(),
                                    i.getName(),
                                    i.getPrice(),
                                    i.getSize().getName(),
                                    i.getColor().getName());
                    Optional<ItemMainImageDTO> imageDTO = i.getItemImagesList()
                            .stream()
                            .filter(ItemImage::getIsMainImg)
                            .map(img -> new ItemMainImageDTO(img.getLocation(), img.getServerSavedName()))
                            .findFirst();
                    itemTop3DTO.setMainImage(imageDTO.orElseThrow(() -> {
                        throw new EntityNotFoundException();
                    }));
                    return itemTop3DTO;
                }).collect(Collectors.toList());

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

        if(condition.getHighestReviewGrade() != null) {
            orderList.add(highestReviewGrade(true));
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
            log.info("color = {}", color);
            return item.color.id.eq(color);
        }
        return null;
    }

    private BooleanExpression sizeEq(Long size) {
        if(size != null) {
            return item.size.id.eq(size);
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

    private OrderSpecifier<Double> highestReviewGrade(Boolean isTrue) {
        if(isTrue != null) {
            return item.reviewGrade.desc();
        }
        return null;
    }




}
