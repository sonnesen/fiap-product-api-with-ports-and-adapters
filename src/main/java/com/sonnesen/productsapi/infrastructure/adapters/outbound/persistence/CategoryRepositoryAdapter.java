package com.sonnesen.productsapi.infrastructure.adapters.outbound.persistence;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import com.sonnesen.productsapi.application.domain.category.Category;
import com.sonnesen.productsapi.application.domain.category.CategoryId;
import com.sonnesen.productsapi.application.domain.pagination.Page;
import com.sonnesen.productsapi.application.domain.pagination.Pagination;
import com.sonnesen.productsapi.application.ports.outbound.repository.CategoryRepository;
import com.sonnesen.productsapi.infrastructure.adapters.outbound.persistence.entity.CategoryJPAEntity;
import com.sonnesen.productsapi.infrastructure.adapters.outbound.persistence.repository.CategoryJPARepository;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final CategoryJPARepository categoryJPARepository;

    @Override
    @Transactional
    public Category create(final Category aCategory) {
        return save(aCategory);
    }

    @Override
    public Pagination<Category> findAll(final Page page) {
        final var withPage = Pageable.ofSize(page.perPage()).withPage(page.currentPage());
        final var pageResult = categoryJPARepository.findAll(withPage);

        final var pagination = new Pagination<>(
            pageResult.getNumber(),
            pageResult.getSize(),
            pageResult.getTotalElements(),
            pageResult.map(CategoryJPAEntity::toCategory).toList()
        );

        return pagination;
    }

    @Override
    public Optional<Category> findById(final CategoryId anId) {
        return categoryJPARepository.findById(anId.value()).map(CategoryJPAEntity::toCategory);
    }

    @Override
    @Transactional
    public void deleteById(final CategoryId anId) {
        categoryJPARepository.deleteById(anId.value());
    }

    @Override
    @Transactional
    public Category update(final Category aCategory) {
        return save(aCategory);
    }

    private Category save(final Category category) {
        return categoryJPARepository.save(CategoryJPAEntity.of(category)).toCategory();
    }

}
