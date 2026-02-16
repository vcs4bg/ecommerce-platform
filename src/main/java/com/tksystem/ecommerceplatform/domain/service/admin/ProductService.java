package com.tksystem.ecommerceplatform.domain.service.admin;

import org.springframework.web.multipart.MultipartFile;

import com.tksystem.ecommerceplatform.domain.model.dto.admin.ProductDetailDto;
import com.tksystem.ecommerceplatform.domain.model.entity.admin.ProductEntity;

public interface ProductService {

    public ProductDetailDto getProductDetail(int productId);

    public ProductEntity saveProduct(ProductEntity entity, MultipartFile imagFile);

}
