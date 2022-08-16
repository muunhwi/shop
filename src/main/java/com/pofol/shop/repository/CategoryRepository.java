package com.pofol.shop.repository;

import com.pofol.shop.domain.dto.item.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(String name);



}
