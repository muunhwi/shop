package com.pofol.shop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "ITEM_IMG_PK_GENERATOR",
        sequenceName = "ITEM_IMG_PK_SEQ",
        initialValue = 1,
        allocationSize = 50
)
@EqualsAndHashCode(of="id")
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_IMG_PK_GENERATOR")
    private Long id;

    private String location;
    private String userCustomName;
    private String serverSavedName;
    private Boolean isMainImg;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;


}
