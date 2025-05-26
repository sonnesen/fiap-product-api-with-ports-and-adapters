package com.sonnesen.productsapi.application.ports.inbound.get;

import java.time.Instant;
import com.sonnesen.productsapi.application.domain.category.Category;
import com.sonnesen.productsapi.application.domain.category.CategoryId;

public record GetCategoryByIdOutput(CategoryId id, String name, String description, boolean isActive,
        Instant createdAt, Instant updatedAt, Instant deletedAt) {

    public static GetCategoryByIdOutput from(Category category) {
        return new GetCategoryByIdOutput(category.getId(), category.getName(), category.getDescription(),
                category.isActive(), category.getCreatedAt(), category.getUpdatedAt(), category.getDeletedAt());
    }
}
