package com.sonnesen.productsapi.infrastructure.adapters.inbound.rest;

import java.net.URI;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.sonnesen.categories.api.CategoriesApi;
import com.sonnesen.categories.model.CategoryDTO;
import com.sonnesen.categories.model.CreateCategoryDTO;
import com.sonnesen.categories.model.PaginatedCategoriesDTO;
import com.sonnesen.categories.model.UpdateCategoryDTO;
import com.sonnesen.productsapi.application.domain.pagination.Page;
import com.sonnesen.productsapi.application.domain.pagination.Pagination;
import com.sonnesen.productsapi.application.ports.inbound.create.ForCreatingCategory;
import com.sonnesen.productsapi.application.ports.inbound.delete.ForDeletingCategoryById;
import com.sonnesen.productsapi.application.ports.inbound.get.ForGettingCategoryById;
import com.sonnesen.productsapi.application.ports.inbound.list.ForListingCategories;
import com.sonnesen.productsapi.application.ports.inbound.update.ForUpdatingCategory;
import com.sonnesen.productsapi.infrastructure.adapters.inbound.rest.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoriesApi {  // Adapter for REST API

    private final ForCreatingCategory creatingCategoryPort;
    private final ForListingCategories listingCategoriesPort;
    private final ForGettingCategoryById gettingCategoryByIdPort;
    private final ForDeletingCategoryById deletingCategoryByIdPort;
    private final ForUpdatingCategory updatingCategoryPort;
    private final CategoryMapper categoryMapper;

    @Override
    public ResponseEntity<CategoryDTO> createCategory(final CreateCategoryDTO body) {
        final var useCaseInput = categoryMapper.fromDTO(body);
        final var useCaseOutput = creatingCategoryPort.createCategory(useCaseInput);
        final var uri = URI.create("/categories/" + useCaseOutput.id());
        return ResponseEntity.created(uri).body(categoryMapper.toDTO(useCaseOutput));
    }

    @Override
    public ResponseEntity<Void> deleteCategory(final UUID categoryId) {
        deletingCategoryByIdPort.deleteCategoryById(categoryId.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CategoryDTO> getCategory(final UUID categoryId) {
        final var output = categoryMapper.toDTO(gettingCategoryByIdPort.getCategoryById(categoryId.toString()));
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<CategoryDTO> updateCategory(final UUID categoryId, final UpdateCategoryDTO body) {
        final var input = categoryMapper.fromDTO(categoryId.toString(), body);
        final var output = updatingCategoryPort.updateCategory(input);
        return ResponseEntity.ok(categoryMapper.toDTO(output));
    }

    @Override
    public ResponseEntity<PaginatedCategoriesDTO> listCategories(final Integer page, final Integer perPage) {
        Pagination<CategoryDTO> categories = listingCategoriesPort.listCategories(new Page(page, perPage))
                                                                  .mapItems(categoryMapper::toDTO);

        PaginatedCategoriesDTO paginatedCategories = new PaginatedCategoriesDTO()
            .items(categories.items())
            .page(categories.currentPage())
            .perPage(categories.perPage())
            .total(categories.total());

        return ResponseEntity.ok(paginatedCategories);
    }

}
