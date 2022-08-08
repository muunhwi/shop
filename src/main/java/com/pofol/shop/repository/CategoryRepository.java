package com.pofol.shop.repository;

import com.pofol.shop.domain.Category;
import com.pofol.shop.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName(String name);

}
