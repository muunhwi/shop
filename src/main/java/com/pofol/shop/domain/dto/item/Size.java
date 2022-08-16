package com.pofol.shop.domain.dto.item;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "SIZE_PK_GENERATOR",
        sequenceName = "SIZE_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIZE_PK_GENERATOR")
    private Long id;

    private String name;

    private String categoryName;


}