package com.pofol.shop.repository.filter;


import com.pofol.shop.domain.dto.ItemCondition;
import com.pofol.shop.domain.dto.ItemDTO;
import com.pofol.shop.domain.dto.QItemDTO;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
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

import java.util.ArrayList;
import java.util.List;

import static com.pofol.shop.domain.QCategory.category;
import static com.pofol.shop.domain.QItem.*;
import static com.pofol.shop.domain.QSubcategory.subcategory;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemFilterRepository {

    private final JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public Page<ItemDTO> getFilterItemList(ItemCondition condition, Pageable pageable) {

        OrderSpecifier<?>[] orders = getOrderSpecifiers(condition);

        List<ItemDTO> list = queryFactory.select(new QItemDTO(
                item.name,
                item.price,
                item.quantity,
                item.size,
                item.color,
                item.reviewGrade,
                item.salesRate,
                subcategory.name,
                category.name
        ))
                .from(item)
                .innerJoin(item.subCategory, subcategory)
                .innerJoin(subcategory.category, category)
                .where(
                        categoryEq(condition.getCategory()),
                        subCategoryEq(condition.getCategory()),
                        colorEq(condition.getColor()),
                        sizeEq(condition.getSize()),
                        priceRange(condition.getMinPrice(), condition.getMaxPrice())
                ).orderBy(
                    orders
                ).offset(pageable.getOffset()
                ).limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(item.count())
                .from(item)
                .innerJoin(item.subCategory, subcategory)
                .innerJoin(subcategory.category, category)
                .where(
                        colorEq(condition.getColor()),
                        sizeEq(condition.getSize()),
                        priceRange(condition.getMinPrice(), condition.getMaxPrice())
                );

        return PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);


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

    private BooleanExpression categoryEq(String category) {
        if(StringUtils.hasText(category)) {
            return item.subCategory.category.name.eq(category);
        }
        return null;
    }

    private BooleanExpression subCategoryEq(String category) {
        if(StringUtils.hasText(category)) {
            return item.subCategory.name.eq(category);
        }
        return null;
    }


    private BooleanExpression colorEq(String color) {
        if(StringUtils.hasText(color)) {
            return item.color.eq(color);
        }
        return null;
    }

    private BooleanExpression sizeEq(String size) {
        if(StringUtils.hasText(size)) {
            return item.size.eq(size);
        }
        return null;
    }

    private BooleanExpression priceRange(Integer min, Integer max) {

        if(min == null && max != null) {
            return item.price.goe(0).and(item.price.loe(max));
        } else if( min != null && max != null) {
            return item.price.goe(min).and(item.price.loe(max));
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
