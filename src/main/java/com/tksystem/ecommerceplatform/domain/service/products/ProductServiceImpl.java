package com.tksystem.ecommerceplatform.domain.service.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tksystem.ecommerceplatform.domain.model.dto.products.ProductDetailDto;
import com.tksystem.ecommerceplatform.domain.model.dto.products.ProductListDto;
import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductEntity;
import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductFileEntity;
import com.tksystem.ecommerceplatform.domain.repository.products.ProductFileRepository;
import com.tksystem.ecommerceplatform.domain.repository.products.ProductRepository;
import com.tksystem.ecommerceplatform.domain.service.common.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private final ProductFileRepository relationRepository;

    private final FileStorageService fileStorageService;

    @Override
    public List<ProductListDto> getList() {

        List<ProductListDto> list = repository.getList();

        return list;

    }

    @Override
    public ProductDetailDto getProductDetail(int productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductDetail'");
    }

    @Override
    @Transactional
    public ProductEntity saveProduct(ProductEntity product, List<MultipartFile> imageFileList) {

        // 商品マスタ登録
        ProductEntity savedProduct = repository.save(product);

        // 空のファイルを除外して有効なファイルのみを抽出
        List<MultipartFile> validFiles = imageFileList.stream()
                .filter(file -> file != null && !file.isEmpty())
                .toList();

        if (validFiles.isEmpty()) {
            return savedProduct;
        }

        List<ProductFileEntity> relationList = new ArrayList<ProductFileEntity>();

        // 画像ファイルの保存
        for (int i = 0; i < validFiles.size(); i++) {

            Long fileId = fileStorageService.saveFile(validFiles.get(i), "Product/" + savedProduct.getProductId().toString());

            ProductFileEntity relation = new ProductFileEntity(
                    savedProduct.getProductId(),
                    fileId,
                    i + 1,
                    null,
                    null);
            relationList.add(relation);

        }

        // 中間テーブルの登録
        relationRepository.saveAll(relationList);

        return savedProduct;

    }

}
