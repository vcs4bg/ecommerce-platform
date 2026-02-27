package com.tksystem.ecommerceplatform.domain.service.products;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tksystem.ecommerceplatform.domain.model.dto.products.ProductDetailDto;
import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductEntity;
import com.tksystem.ecommerceplatform.domain.repository.products.ProductRepository;
import com.tksystem.ecommerceplatform.domain.service.common.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final FileStorageService fileStorageService;

    @Override
    public ProductDetailDto getProductDetail(int productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductDetail'");
    }

    @Override
    public ProductEntity saveProduct(ProductEntity product, MultipartFile imageFile) {
        // 画像ファイルの保存
        Long fileId = fileStorageService.saveFile(imageFile, "Product/");

        // 商品マスタ登録
        product.setFileId(fileId);
        ProductEntity savedProduct = repository.save(product);

        return savedProduct;
    }

}
