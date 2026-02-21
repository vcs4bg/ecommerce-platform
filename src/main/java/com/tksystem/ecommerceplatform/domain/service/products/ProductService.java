package com.tksystem.ecommerceplatform.domain.service.products;

import org.springframework.web.multipart.MultipartFile;

import com.tksystem.ecommerceplatform.domain.model.dto.products.ProductDetailDto;
import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductEntity;

public interface ProductService {

    public ProductDetailDto getProductDetail(int productId);

    public ProductEntity saveProduct(ProductEntity entity, MultipartFile imagFile);

}
