package com.wanggs.seataproduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<ProductEntity, Long> {
}
