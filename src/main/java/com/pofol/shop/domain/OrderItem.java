package com.pofol.shop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "ORDERITEM_PK_GENERATOR",
        sequenceName = "ORDERITEM_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERITEM_PK_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    private int totalPrice;

    private int count;

    public void setOrder(Order order) {
        this.order = order;
    }
}
