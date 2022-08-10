package com.pofol.shop.repository.filter;

import com.pofol.shop.App;
import com.pofol.shop.domain.Size;
import com.pofol.shop.domain.dto.ItemCondition;
import com.pofol.shop.domain.dto.ItemDTO;
import com.pofol.shop.repository.ItemRepository;
import com.pofol.shop.repository.SizeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {App.class})
class ItemFilterRepositoryTest {

    @Autowired
    ItemFilterRepository itemFilterRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("아무 조건 없이 쿼리")
    public void filterQuery_1() throws Exception {

        ItemCondition condition = new ItemCondition();
        Pageable pageable = PageRequest.of(0, 10);

        Page<ItemDTO> list = itemFilterRepository.getFilterItemList(condition, pageable);

        List<ItemDTO> content = list.getContent();
        assertEquals(0, list.getNumber());
        assertEquals(10, list.getSize());
        assertEquals(11, list.getTotalPages());
        assertEquals(101, list.getTotalElements());
        assertEquals(true, list.isFirst());

    }

    @Test
    @DisplayName("조건 color 추가 쿼리")
    public void filterQuery_2() throws Exception {
        ItemCondition condition = new ItemCondition();
        condition.setColor(0L);
        Pageable pageable = PageRequest.of(0, 10);

        Page<ItemDTO> list = itemFilterRepository.getFilterItemList(condition, pageable);

        List<ItemDTO> content = list.getContent();
        for (ItemDTO itemDTO : content) {
            assertEquals("블랙", itemDTO.getColor());
        }
    }

    @Test
    @DisplayName("조건 size 추가 쿼리")
    public void  filterQuery_3() throws Exception {
        ItemCondition condition = new ItemCondition();
        condition.setSize(2L);
        Pageable pageable = PageRequest.of(0, 10);

        Page<ItemDTO> list = itemFilterRepository.getFilterItemList(condition, pageable);
        Optional<Size> size = sizeRepository.findById(2L);

        List<ItemDTO> content = list.getContent();
        for (ItemDTO itemDTO : content) {
            assertEquals(size.get().getName(), itemDTO.getSize());
        }
    }

    @Test
    @DisplayName("조건 max price=50000 추가 ")
    public void filterQuery_4() throws Exception {
        ItemCondition condition = new ItemCondition();
        condition.setMaxPrice(50000);
        Pageable pageable = PageRequest.of(0, 10);

        Page<ItemDTO> list = itemFilterRepository.getFilterItemList(condition, pageable);

        List<ItemDTO> content = list.getContent();
        for (ItemDTO itemDTO : content) {
            assertThat(itemDTO.getPrice()).isLessThanOrEqualTo(50000);
        }
    }

    @Test
    @DisplayName("조건 min price = 20000 max price = 50000 추가 ")
    public void filterQuery_5() throws Exception {
        ItemCondition condition = new ItemCondition();
        condition.setMinPrice(20000);
        condition.setMaxPrice(50000);
        Pageable pageable = PageRequest.of(0, 10);

        Page<ItemDTO> list = itemFilterRepository.getFilterItemList(condition, pageable);

        List<ItemDTO> content = list.getContent();
        for (ItemDTO itemDTO : content) {
            assertThat(itemDTO.getPrice()).isGreaterThanOrEqualTo(20000);
            assertThat(itemDTO.getPrice()).isLessThanOrEqualTo(50000);
        }
    }

    @Test
    @DisplayName("가장 높은 가격으로 정렬")
    public void filterQuery_6() throws Exception {
        ItemCondition condition = new ItemCondition();
        condition.setHighestPrice(true);
        Pageable pageable = PageRequest.of(0, 10);

        Page<ItemDTO> list = itemFilterRepository.getFilterItemList(condition, pageable);

        List<ItemDTO> content = list.getContent();
        assertEquals(80000,content.get(0).getPrice());
    }

    @Test
    @DisplayName("가장 높은 판매율로 정렬")
    public void filterQuery_7() throws Exception {
        ItemCondition condition = new ItemCondition();
        condition.setHighestSalesRate(true);
        Pageable pageable = PageRequest.of(0, 10);


        Page<ItemDTO> list = itemFilterRepository.getFilterItemList(condition, pageable);
        int maxSalesRate = itemRepository.findMaxSalesRate();

        List<ItemDTO> content = list.getContent();
        assertEquals(maxSalesRate,content.get(0).getSalesRate());
    }

    @Test
    @DisplayName("가장 높은 리뷰평가 정렬")
    public void filterQuery_8() throws Exception {
        ItemCondition condition = new ItemCondition();
        condition.setHighestReviewGrade(true);
        Pageable pageable = PageRequest.of(0, 10);

        Page<ItemDTO> list = itemFilterRepository.getFilterItemList(condition, pageable);

        List<ItemDTO> content = list.getContent();
        assertEquals(5,content.get(0).getReviewGrade());
    }


}