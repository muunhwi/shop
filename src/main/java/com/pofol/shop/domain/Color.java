package com.pofol.shop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "COLOR_PK_GENERATOR",
        sequenceName = "COLOR_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLOR_PK_GENERATOR")
    private Long id;

    private String name;

}
