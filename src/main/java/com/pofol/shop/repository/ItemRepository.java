package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query(value = "select max(i.sales_rate) from Item i", nativeQuery = true)
    int findMaxSalesRate();


}
