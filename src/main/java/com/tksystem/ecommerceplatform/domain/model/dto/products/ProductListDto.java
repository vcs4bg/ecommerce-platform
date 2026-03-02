package com.tksystem.ecommerceplatform.domain.model.dto.products;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDto {

    private Long productId;

    private String imageUrl;

    private String productName;

    private BigDecimal price;

    public String getImageUrl() {
        if (!StringUtils.hasText(imageUrl)) {
            return "";
        }
        return imageUrl;
    }

}
