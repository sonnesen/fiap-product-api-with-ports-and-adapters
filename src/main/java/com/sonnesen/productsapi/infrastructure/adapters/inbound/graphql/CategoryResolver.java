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
import com.sonnesen.productsapi.application.ports.inbound.list.ListCategoryOutput;
import com.sonnesen.productsapi.infrastructure.adapters.inbound.rest.mapper.CategoryMapper;

@Controller
public class CategoryResolver { // Adapter for GraphQL API

    private final ForListingCategories listingCategoriesPort;
    private final ForGettingCategoryById gettingCategoryByIdPort;
    private final CategoryMapper categoryMapper;

    public CategoryResolver(final ForListingCategories listingCategoriesPort,
                            final ForGettingCategoryById gettingCategoryByIdPort,
                            final CategoryMapper categoryMapper) {
        this.listingCategoriesPort = listingCategoriesPort;
        this.gettingCategoryByIdPort = gettingCategoryByIdPort;
        this.categoryMapper = categoryMapper;
    }

    @QueryMapping
    public CategoryDTO getCategory(@Argument final UUID categoryId) {
        return categoryMapper.toDTO(gettingCategoryByIdPort.getCategoryById(categoryId.toString()));
    }

    @QueryMapping
    public PaginatedCategoriesDTO listCategories(@Argument final Integer page, @Argument final Integer perPage) {
        Pagination<ListCategoryOutput> listCategories = listingCategoriesPort.listCategories(new Page(page, perPage));
        Pagination<CategoryDTO> categories = listCategories.mapItems(categoryMapper::toDTO);

        PaginatedCategoriesDTO paginatedCategories = new PaginatedCategoriesDTO()
            .items(categories.items())
            .page(categories.currentPage())
            .perPage(categories.perPage())
            .total(categories.total());

        return paginatedCategories;
    }

}
