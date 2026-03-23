package com.practice.ShoppingCart.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.practice.ShoppingCart.model.Category;
import com.practice.ShoppingCart.model.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {

    private Long Id;

    private  String name;
    private  String brand;
    private  int inventory;
    private  String description;
    private BigDecimal price;
    private Category category;
    private List<ImageDto> images;
    
}
