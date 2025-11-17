package com.practice.ShoppingCart.service.Category;

import com.practice.ShoppingCart.model.Category;

import java.util.List;

public interface ICategoryService {

    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    void deleteCategoryById(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category,Long id);
}
