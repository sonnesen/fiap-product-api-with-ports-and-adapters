package com.sonnesen.productsapi.application.ports.outbound.repository;

import java.util.Optional;
import com.sonnesen.productsapi.application.domain.category.Category;
import com.sonnesen.productsapi.application.domain.category.CategoryId;
import com.sonnesen.productsapi.application.domain.pagination.Page;
import com.sonnesen.productsapi.application.domain.pagination.Pagination;

public interface CategoryRepository {

    Category create(Category aCategory);

    Category update(Category aCategory);

    Optional<Category> findById(CategoryId anId);

    Pagination<Category> findAll(Page page);

    void deleteById(CategoryId anId);

}
