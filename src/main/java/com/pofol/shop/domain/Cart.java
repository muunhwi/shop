package com.pofol.shop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "CART_PK_GENERATOR",
        sequenceName = "CART_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_PK_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    private int count;

    public void setCount(int count) {
        this.count = count;
    }
}
