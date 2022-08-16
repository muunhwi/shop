package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c join fetch c.goods g" +
            " join fetch g.item i" +
            " join fetch g.color co" +
            " join fetch g.size s"+
            " where c.member.id = :id")
    List<Cart> findCartListByMemberId(@Param("id") Long id);

    @Query("select c from Cart c" +
            " where c.goods.id =:goodsId" +
            " and c.member.id =:memberId")
    Optional<Cart> findCartByGoodsIdAndMemberId(@Param("goodsId") Long goodsId, @Param("memberId") Long memberId);

}
