package com.sonnesen.productsapi.infrastructure.adapters.inbound.graphql;

import java.util.UUID;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import com.sonnesen.categories.model.CategoryDTO;
import com.sonnesen.categories.model.PaginatedCategoriesDTO;
import com.sonnesen.productsapi.application.domain.pagination.Page;
import com.sonnesen.productsapi.application.domain.pagination.Pagination;
import com.sonnesen.productsapi.application.ports.inbound.get.ForGettingCategoryById;
import com.sonnesen.productsapi.application.ports.inbound.list.ForListingCategories;
import com.sonnesen.productsapi.infrastructure.adapters.inbound.rest.mapper.CategoryMapper;

@Controller
public class CategoryResolver {

    private final ForListingCategories categoryListUseCase;
    private final ForGettingCategoryById categoryGetByIdUseCase;
    private final CategoryMapper categoryMapper;

    public CategoryResolver(final ForListingCategories categoryListUseCase,
            final ForGettingCategoryById categoryGetByIdUseCase,
            final CategoryMapper categoryMapper) {
        this.categoryListUseCase = categoryListUseCase;
        this.categoryGetByIdUseCase = categoryGetByIdUseCase;
        this.categoryMapper = categoryMapper;
    }

    @QueryMapping
    public CategoryDTO getCategory(@Argument final UUID categoryId) {
        return categoryMapper.toDTO(categoryGetByIdUseCase.getCategoryById(categoryId.toString()));
    }

    // @QueryMapping
    public PaginatedCategoriesDTO listCategories(final Integer page, final Integer perPage) {
        Pagination<CategoryDTO> categories = categoryListUseCase
                .listCategories(new Page(page, perPage)).mapItems(categoryMapper::toDTO);

        PaginatedCategoriesDTO paginatedCategories = new PaginatedCategoriesDTO()
                .items(categories.items()).page(categories.currentPage())
                .perPage(categories.perPage()).total(categories.total());

        return paginatedCategories;
    }


}
