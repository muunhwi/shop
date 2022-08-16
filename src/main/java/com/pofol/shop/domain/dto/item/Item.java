package com.pofol.shop.domain.dto.item;

import com.pofol.shop.domain.Coupon;
import com.pofol.shop.domain.sub.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "ITEM_PK_GENERATOR",
        sequenceName = "ITEM_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class Item extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_PK_GENERATOR")
    private Long id;

    private String name;

    private int price;

    private int salesRate;

    @OneToMany(mappedBy = "item")
    private List<Goods> goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private Subcategory subCategory;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImage> itemImagesList = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Coupon> coupons = new ArrayList<>();

    public void setSalesRate(int salesRate) {
        this.salesRate = salesRate;
    }
}
