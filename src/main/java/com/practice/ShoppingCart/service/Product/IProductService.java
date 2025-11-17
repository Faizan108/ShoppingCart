package com.practice.ShoppingCart.service.Product;

import com.practice.ShoppingCart.dto.ProductDto;
import com.practice.ShoppingCart.model.Product;
import com.practice.ShoppingCart.requests.AddProductRequest;
import com.practice.ShoppingCart.requests.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProductById(ProductUpdateRequest request, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByNameAndBrand(String name,String brand);
    Long countProductsByNameAndBrand(String name,String brand);


    List<ProductDto> covertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
