package com.practice.ShoppingCart.service.Image;

import com.practice.ShoppingCart.dto.ImageDto;
import com.practice.ShoppingCart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface IImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long product_id);
    void updateImage(MultipartFile file, Long imageId) throws IOException;
}
