package com.sonnesen.productsapi.infrastructure.adapters.outbound.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sonnesen.productsapi.infrastructure.adapters.outbound.persistence.entity.CategoryJPAEntity;

public interface CategoryJPARepository extends JpaRepository<CategoryJPAEntity, String> {

}
