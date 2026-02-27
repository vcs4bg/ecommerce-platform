package com.tksystem.ecommerceplatform.web.controller.products.form;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRegistForm {

    Long productId;

    @NotNull
    String productName;

    String description;

    BigDecimal price;

    MultipartFile imageFile;

    Integer stock;

    Long categoryId;

    Integer productStatus;

    public ProductEntity toEntity() {

        return new ProductEntity(
                productId,
                productName,
                description,
                price,
                null,
                stock,
                categoryId,
                productStatus,
                null,
                null);

    }

}
