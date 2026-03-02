package com.tksystem.ecommerceplatform.domain.repository.products;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tksystem.ecommerceplatform.domain.model.dto.products.ProductListDto;
import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
            SELECT new com.tksystem.ecommerceplatform.domain.model.dto.products.ProductListDto(
                p.productId,
                fm.filePath,
                p.productName,
                p.price
            )
            FROM ProductEntity p
                LEFT JOIN ProductFileEntity pf
                ON p.productId = pf.productId
                AND pf.displayOrder = 1
                LEFT JOIN FileManageEntity fm
                ON pf.fileId = fm.fileId
            ORDER BY p.productId
            """)
    List<ProductListDto> getList();

}
