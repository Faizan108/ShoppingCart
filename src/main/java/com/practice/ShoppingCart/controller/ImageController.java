package com.practice.ShoppingCart.controller;

import com.practice.ShoppingCart.service.Image.IImageService;
import com.practice.ShoppingCart.dto.ImageDto;
import com.practice.ShoppingCart.exception.ResourceNotFoundException;
import com.practice.ShoppingCart.model.Image;
import com.practice.ShoppingCart.responses.ApiResponse;


import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/uploads")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files,@RequestParam Long productId){
        try {
            List<ImageDto> savedImages = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload is Success!",savedImages));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload is Failed!",e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
   public ResponseEntity<Resource> downloadUrl(@PathVariable Long imageId) throws SQLException {
       Image image = imageService.getImageById(imageId);
       ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));

       return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
               .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+image.getFileName()+"\"")
               .body(resource);

   }

   @PutMapping("image/{imageId}/update")
   public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){

      Image image = imageService.getImageById(imageId);
      if(image!=null){
          try {
              imageService.updateImage(file,imageId);
              return ResponseEntity.ok(new ApiResponse("Updated Successfully!",null));
          } catch (ResourceNotFoundException | IOException e) {
              return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
          }
      }
         return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed!",INTERNAL_SERVER_ERROR));

   }

    @DeleteMapping("image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){

        Image image = imageService.getImageById(imageId);
        if(image!=null){
            try {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Deleted Successfully!",null));
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
            }
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed!",INTERNAL_SERVER_ERROR));

    }




}
