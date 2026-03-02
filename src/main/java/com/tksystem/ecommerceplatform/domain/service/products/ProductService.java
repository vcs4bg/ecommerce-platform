package com.tksystem.ecommerceplatform.domain.service.products;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tksystem.ecommerceplatform.domain.model.dto.products.ProductDetailDto;
import com.tksystem.ecommerceplatform.domain.model.dto.products.ProductListDto;
import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductEntity;

public interface ProductService {

    public List<ProductListDto> getList();

    public ProductDetailDto getProductDetail(int productId);

    public ProductEntity saveProduct(ProductEntity entity, List<MultipartFile> imageFileList);

}
