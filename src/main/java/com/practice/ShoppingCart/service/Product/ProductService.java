package com.practice.ShoppingCart.service.Product;

import com.practice.ShoppingCart.repository.CategoryRepository;
import com.practice.ShoppingCart.repository.ImageRepository;
import com.practice.ShoppingCart.repository.ProductRepository;
import com.practice.ShoppingCart.dto.ImageDto;
import com.practice.ShoppingCart.dto.ProductDto;
import com.practice.ShoppingCart.exception.ProductNotFounfException;
import com.practice.ShoppingCart.model.Category;
import com.practice.ShoppingCart.model.Image;
import com.practice.ShoppingCart.model.Product;
import com.practice.ShoppingCart.requests.AddProductRequest;
import com.practice.ShoppingCart.requests.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    public final ProductRepository productRepository;
    public final CategoryRepository categoryRepository;
    public final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {

        Category category  = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category){

        return new Product(
                request.getName(),
                request.getBrand(),
                request.getInventory(),
                request.getDescription(),
                request.getPrice(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFounfException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                ()->{throw new ProductNotFounfException("Product not found");});
    }

    @Override
    public Product updateProductById(ProductUpdateRequest request, Long id) {
        return productRepository.findById(id)
                .map(existingProduct ->updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFounfException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByNameAndBrand(String name, String brand) {
        return productRepository.findByNameAndBrand(name,brand);
    }

    @Override
    public Long countProductsByNameAndBrand(String name, String brand) {
        return productRepository.countByNameAndBrand(name,brand);
    }

    @Override
    public List<ProductDto> covertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){

        ProductDto productDto = modelMapper.map(product,ProductDto.class);

        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image->modelMapper.map(image,ImageDto.class)).toList();

        productDto.setImages(imageDtos);
        return productDto;
    }
}
