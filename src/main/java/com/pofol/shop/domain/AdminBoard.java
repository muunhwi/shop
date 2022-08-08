package com.pofol.shop.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "ADMIN_BOARD_PK_GENERATOR",
        sequenceName = "ADMIN_BOARD_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class AdminBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMIN_BOARD_PK_GENERATOR")
    private Long id;


}
