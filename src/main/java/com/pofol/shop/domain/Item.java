package com.pofol.shop.domain;

import com.pofol.shop.domain.sub.BaseEntity;
import com.querydsl.core.annotations.QueryProjection;
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

    private int quantity;

    @OneToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @OneToOne
    @JoinColumn(name = "color_id")
    private Color color;

    private Double reviewGrade;

    private int salesRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private Subcategory subCategory;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemImage> itemImagesList = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<Coupon> coupons = new ArrayList<>();


}
