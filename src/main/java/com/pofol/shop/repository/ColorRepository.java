package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.item.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {

    Color findByName(String name);

}
