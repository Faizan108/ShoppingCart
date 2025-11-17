package com.practice.ShoppingCart.service.Image;

import com.practice.ShoppingCart.repository.ImageRepository;
import com.practice.ShoppingCart.service.Product.IProductService;
import com.practice.ShoppingCart.dto.ImageDto;
import com.practice.ShoppingCart.exception.ResourceNotFoundException;
import com.practice.ShoppingCart.model.Image;
import com.practice.ShoppingCart.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("Image not found"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,
                ()->{throw new ResourceNotFoundException("Image not found");
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long product_id) {
        Product product = productService.getProductById(product_id);
        List<ImageDto> savedImages = new ArrayList<>();
        for(MultipartFile file: files){
            try {
                Image image = new Image();
                image.setFileType(file.getContentType());
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String downloadUrl = "/api/v1/images/image/download/";
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);
                image.setDownloadUrl(downloadUrl + savedImage.getId());
                imageRepository.save(image);

                ImageDto imageDto = new ImageDto();
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setId(savedImage.getId());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImages.add(imageDto);
            }catch (IOException | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImages;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId)  {
        Image image = getImageById(imageId);
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        try {
            image.setImage(new SerialBlob(file.getBytes()));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
