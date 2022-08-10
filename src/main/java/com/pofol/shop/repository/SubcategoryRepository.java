package com.pofol.shop.repository;

import com.pofol.shop.domain.Category;
import com.pofol.shop.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SubcategoryRepository extends JpaRepository<Subcategory,Long> {

    Subcategory findByName(String name);

    @Query( value = " select sc from Subcategory sc" +
            " join fetch sc.category c" +
            " where sc.name = :name")
    Subcategory findSubCategoryBySubCategoryName(@Param("name") String name);

    @Query( value = " select sc from Subcategory sc" +
            " join fetch sc.category c" +
            " where sc.id = :id")
    Subcategory findSubCategoryBySubCategoryId(@Param("id") Long id);

    @Query( value = " select sc from Subcategory sc" +
            " join fetch sc.category c" +
            " where c.name = :name")
    List<Subcategory> findSubCategoriesNameByCategoryName(@Param("name") String name);

    @Query( value = " select sc from Subcategory sc" +
            " join fetch sc.category c" +
            " where c.id = :id")
    List<Subcategory> findSubCategoriesNameByCategoryId(@Param("id") Long id);



}
