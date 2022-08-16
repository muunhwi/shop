package com.pofol.shop.domain.dto.item;

import com.pofol.shop.domain.sub.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "CATEGORY_PK_GENERATOR",
        sequenceName = "CATEGORY_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CATEGORY_PK_GENERATOR")
    private Long id;

    private String name;


}
