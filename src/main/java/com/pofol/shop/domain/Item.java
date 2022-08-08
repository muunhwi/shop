package com.pofol.shop.domain;

import com.pofol.shop.domain.sub.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private int quantity;

    private String size;

    private String color;

    private Double reviewGrade;

    private int salesRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private Subcategory subCategory;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImagesList = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Coupon> coupons = new ArrayList<>();


}
