package com.pofol.shop.domain.dto.item;

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
        name = "SUBCATEGORY_PK_GENERATOR",
        sequenceName = "SUBCATEGORY_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
@Table(name = "sub_category")
public class Subcategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUBCATEGORY_PK_GENERATOR")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "subCategory")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


}
