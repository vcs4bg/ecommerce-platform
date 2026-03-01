package com.tksystem.ecommerceplatform.domain.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductFileEntity;
import com.tksystem.ecommerceplatform.domain.model.entity.products.ProductFileEntity.ProductFileId;

@Repository
public interface ProductFileRepository extends JpaRepository<ProductFileEntity, ProductFileId> {

}
