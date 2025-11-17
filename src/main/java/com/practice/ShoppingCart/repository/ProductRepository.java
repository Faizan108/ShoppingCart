package com.practice.ShoppingCart.repository;

import com.practice.ShoppingCart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>{

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByNameAndBrand(String name, String brand);

    Long countByNameAndBrand(String name, String brand);
}
