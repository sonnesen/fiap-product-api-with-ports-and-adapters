package com.sonnesen.productsapi.application.service;

import java.util.Objects;
import com.sonnesen.productsapi.application.domain.category.Category;
import com.sonnesen.productsapi.application.domain.category.CategoryId;
import com.sonnesen.productsapi.application.domain.pagination.Page;
import com.sonnesen.productsapi.application.domain.pagination.Pagination;
import com.sonnesen.productsapi.application.exceptions.NotFoundException;
import com.sonnesen.productsapi.application.ports.inbound.create.CreateCategoryInput;
import com.sonnesen.productsapi.application.ports.inbound.create.CreateCategoryOutput;
import com.sonnesen.productsapi.application.ports.inbound.create.ForCreatingCategory;
import com.sonnesen.productsapi.application.ports.inbound.delete.ForDeletingCategoryById;
import com.sonnesen.productsapi.application.ports.inbound.get.ForGettingCategoryById;
import com.sonnesen.productsapi.application.ports.inbound.get.GetCategoryByIdOutput;
import com.sonnesen.productsapi.application.ports.inbound.list.ForListingCategories;
import com.sonnesen.productsapi.application.ports.inbound.list.ListCategoryOutput;
import com.sonnesen.productsapi.application.ports.inbound.update.ForUpdatingCategory;
import com.sonnesen.productsapi.application.ports.inbound.update.UpdateCategoryInput;
import com.sonnesen.productsapi.application.ports.inbound.update.UpdateCategoryOutput;
import com.sonnesen.productsapi.application.ports.outbound.repository.CategoryRepository;
import jakarta.inject.Named;

@Named
public class CategoryService implements ForCreatingCategory, ForUpdatingCategory,
        ForDeletingCategoryById, ForGettingCategoryById, ForListingCategories {

    private final CategoryRepository categoryRepository;

    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = Objects.requireNonNull(categoryRepository);
    }

    @Override
    public CreateCategoryOutput createCategory(final CreateCategoryInput input) {
        final var newCategory = Category.newCategory(input.name(), input.description());
        final var category = categoryRepository.create(newCategory);
        return CreateCategoryOutput.from(category);
    }

    @Override
    public UpdateCategoryOutput updateCategory(final UpdateCategoryInput input) {
        final var categoryId = CategoryId.from(input.id());

        final var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with ID %s not found.".formatted(categoryId)));

        category.update(input.name(), input.description(), input.isActive());
        this.categoryRepository.update(category);

        return UpdateCategoryOutput.from(category);
    }

    @Override
    public void deleteCategoryById(final String input) {
        final var categoryId = CategoryId.from(input);
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Pagination<ListCategoryOutput> listCategories(final Page page) {
         return categoryRepository.findAll(page).mapItems(ListCategoryOutput::from);
    }

    @Override
    public GetCategoryByIdOutput getCategoryById(final String input) {
        final var categoryId = CategoryId.from(input);

        return categoryRepository.findById(categoryId)
                .map(GetCategoryByIdOutput::from)
                .orElseThrow(() -> new NotFoundException("Category with ID %s not found.".formatted(categoryId)));
    }

}
