package com.pofol.shop.service;

import com.pofol.shop.domain.*;
import com.pofol.shop.domain.dto.OrderFormDTO;
import com.pofol.shop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final GoodsRepository goodsRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public String cartAdd(OrderFormDTO cartDTO, Member member) {
        Optional<Goods> findGoods = goodsRepository.findGoodsByNames(cartDTO.getItemId(), cartDTO.getColor(), cartDTO.getSize());

        if(findGoods.isEmpty()) {
            return "아이템이 존재하지 않습니다.";
        }
        Goods goods = findGoods.get();
        Optional<Cart> findCart = cartRepository.findCartByGoodsIdAndMemberId(goods.getId(), member.getId());
        if(findCart.isPresent()) {
            Cart cart = findCart.get();
            cart.setCount(cart.getCount() + cartDTO.getCount());
        } else {
            Cart cart = Cart.builder()
                    .member(member)
                    .count(cartDTO.getCount())
                    .goods(goods)
                    .build();
            cartRepository.save(cart);
        }
        return "ok";
    }



}
