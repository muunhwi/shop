package com.pofol.shop.repository;

import com.pofol.shop.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    @Query(value = "select max(i.sales_rate) from Item i", nativeQuery = true)
    int findMaxSalesRate();

}
