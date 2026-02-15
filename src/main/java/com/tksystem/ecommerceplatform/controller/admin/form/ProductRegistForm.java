package com.tksystem.ecommerceplatform.controller.admin.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRegistForm {

    Integer productId;

    @NotNull
    String productName;

    String description;

    Integer price;

    MultipartFile imageFile;

    Integer stock;

    Integer categoryId;

    Integer productStatus;

}
