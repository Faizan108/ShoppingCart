package com.practice.ShoppingCart.controller;


import com.practice.ShoppingCart.service.Product.IProductService;
import com.practice.ShoppingCart.dto.ProductDto;
import com.practice.ShoppingCart.exception.ProductNotFounfException;
import com.practice.ShoppingCart.model.Product;
import com.practice.ShoppingCart.requests.AddProductRequest;
import com.practice.ShoppingCart.requests.ProductUpdateRequest;
import com.practice.ShoppingCart.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        try{
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedProducts = productService.covertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Found!",convertedProducts));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try{
             Product addedProduct = productService.addProduct(product);

            return ResponseEntity.ok(new ApiResponse("Added Successfully!",product));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Deleted Successfully!",null));
        }catch(ProductNotFounfException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try{
            Product product  = productService.getProductById(id);
            var productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Found!",productDto));
        }catch(ProductNotFounfException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProductById(@PathVariable Long id, @RequestBody ProductUpdateRequest request){
        try{
            Product product  = productService.updateProductById(request,id);
            return ResponseEntity.ok(new ApiResponse("Updated Successfully!",product));
        }catch(ProductNotFounfException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try{
            List<Product> product  = productService.getProductsByNameAndBrand(brand, name);
            List<ProductDto> convertedProducts = productService.covertedProducts(product);
            if(convertedProducts.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!",convertedProducts));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try{
            List<Product> product  = productService.getProductsByCategoryAndBrand(category,brand);
            List<ProductDto> convertedProducts = productService.covertedProducts(product);
            if(convertedProducts.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!",convertedProducts));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }
    }


    @GetMapping("/products/{name}/product")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try{
            List<Product> product  = productService.getProductsByName(name);
            List<ProductDto> convertedProducts = productService.covertedProducts(product);
            if(convertedProducts.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!",convertedProducts));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
        try{
            List<Product> product  = productService.getProductsByBrand(brand);
            List<ProductDto> convertedProducts = productService.covertedProducts(product);
            if(convertedProducts.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!",convertedProducts));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }
    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){
        try{
            List<Product> product  = productService.getProductsByCategory(category);
            List<ProductDto> convertedProducts = productService.covertedProducts(product);
            if(convertedProducts.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!",null));
            }
            return ResponseEntity.ok(new ApiResponse("Found!",convertedProducts));
        }catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }
    }


}
