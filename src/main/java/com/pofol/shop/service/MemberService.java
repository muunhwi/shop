package com.pofol.shop.service;

import com.pofol.shop.domain.*;
import com.pofol.shop.domain.dto.item.Goods;
import com.pofol.shop.domain.dto.item.Item;
import com.pofol.shop.domain.dto.order.*;
import com.pofol.shop.domain.dto.item.ItemImageDTO;
import com.pofol.shop.domain.dto.member.JoinMemberForm;
import com.pofol.shop.domain.sub.Address;
import com.pofol.shop.domain.sub.DeliveryStatus;
import com.pofol.shop.domain.sub.OrderStatus;
import com.pofol.shop.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final BCryptPasswordEncoder encoder;
    private final OrderRepository orderRepository;
    private final GoodsRepository goodsRepository;

    public Long save(Member customer) {
        Member saveCustomer = memberRepository.save(customer);
        return saveCustomer.getId();
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<CartDTO> findCartsByMemberId(Long memberId) {
        List<Cart> list = cartRepository.findCartListByMemberId(memberId);
        return getCartDTOList(list);
    }

    public OrderCartDTO findOrderCartByMember(Member member) {
        Address address = member.getAddress();

        List<CartDTO> carts = findCartsByMemberId(member.getId());
        return OrderCartDTO.builder()
                .baseAddress(address.getBaseAddress())
                .detailAddress(address.getDetailAddress())
                .extraAddress(address.getExtraAddress())
                .zoneCode(address.getZoneCode())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .carts(carts)
                .build();
    }

    public void saveOrderInfo(Member member, checkoutDTO checkoutDTO) {
        List<Cart> carts = cartRepository.findCartListByMemberId(member.getId());

        carts.forEach(c -> {
            Item item = c.getGoods().getItem();
            int salesRate = item.getSalesRate();
            item.setSalesRate(salesRate + c.getCount());
        });

        List<OrderItem> list = carts.stream()
                .map(c -> {
                    return OrderItem.builder()
                            .goods(c.getGoods())
                            .totalPrice(c.getCount() * c.getGoods().getItem().getPrice())
                            .count(c.getCount())
                            .build();
                        }
                ).collect(Collectors.toList());

        Delivery delivery = Delivery.builder()
                .deliveryStatus(DeliveryStatus.waiting)
                .address(new Address(checkoutDTO.getZoneCode(), checkoutDTO.getBaseAddress(), checkoutDTO.getDetailAddress(), checkoutDTO.getExtraAddress()))
                .deliveryEmail(checkoutDTO.getEmail())
                .deliveryPhoneNumber(checkoutDTO.getPhoneNumber())
                .build();

        Order order = Order.builder()
                .delivery(delivery)
                .orderItem(list)
                .orderStatus(OrderStatus.ORDER)
                .member(member)
                .build();

        for (OrderItem orderItem : list) {
            orderItem.setOrder(order);
        }

        orderRepository.save(order);
    }

    public void updateCartCount(Long id, int count) {
        Optional<Cart> findCart = cartRepository.findById(id);
        Cart cart = findCart.orElseThrow();
        cart.setCount(count);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("email = {}", email);
        Optional<Member> findMember =  memberRepository.findByEmail(email);

        return findMember.orElseGet(() -> findMember.orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다.")));
    }

    public void formToMember(JoinMemberForm form) {
        Member member = Member.builder()
                .email(form.getEmail())
                .phoneNumber(form.getPhoneNumber())
                .sex(form.getSex())
                .address(new Address(form.getZoneCode(), form.getBaseAddress(), form.getDetailAddress(), form.getExtraAddress()))
                .password(encoder.encode(form.getPassword()))
                .accountNonLocked(true)
                .role("ROLE_USER")
                .enabled(true)
                .build();
        memberRepository.save(member);
    }

    private List<CartDTO> getCartDTOList(List<Cart> list) {
        return list.stream()
                .map(c -> {
                    CartDTO build = CartDTO.builder()
                            .id(c.getId())
                            .count(c.getCount())
                            .color(c.getGoods().getColor().getName())
                            .size(c.getGoods().getSize().getName())
                            .name(c.getGoods().getItem().getName())
                            .price(c.getGoods().getItem().getPrice())
                            .mainImage(c.getGoods()
                                    .getItem()
                                    .getItemImagesList().stream()
                                    .filter(img -> img.getIsMainImg())
                                    .map(img -> {
                                                ItemImageDTO build1 = ItemImageDTO.builder()
                                                        .serverSavedName(img.getServerSavedName())
                                                        .location(img.getLocation())
                                                        .build();
                                                return build1;
                                            }
                                    ).findFirst().orElseThrow()
                            )
                            .build();
                    return build;
                }).collect(Collectors.toList());
    }

}
