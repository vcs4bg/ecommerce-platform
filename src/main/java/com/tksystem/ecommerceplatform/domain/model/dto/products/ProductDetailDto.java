package com.tksystem.ecommerceplatform.domain.model.dto.products;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductDetailDto {

    Integer productId;

    String productName;

    String description;

    Integer price;

    MultipartFile imageFile;

    Integer stock;

    Integer categoryId;

    Integer productStatus;

}
