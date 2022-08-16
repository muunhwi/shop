package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.item.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeRepository extends JpaRepository<Size, Long> {

    Size findByName(String name);
    List<Size> findByCategoryName(String name);

}
