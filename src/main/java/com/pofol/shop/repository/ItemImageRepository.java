package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long > {
}
