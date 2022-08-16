package com.pofol.shop.repository;

import com.pofol.shop.domain.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g" +
            " join fetch g.color c" +
            " join fetch g.size s" +
            " where g.item.id = :id")
    List<Goods> findGoodsByItemId(@Param("id") Long id);

    @Query("select g from Goods g " +
            "join fetch g.color c " +
            "join fetch g.size s " +
            "where g.item.id = :itemId " +
            "and c.name = :color " +
            "and s.name = :size")
    Optional<Goods> findGoodsByNames(@Param("itemId") Long itemId, @Param("color") String color, @Param("size") String size);

}
