package com.sonnesen.productsapi.application.ports.inbound.update;

import java.time.Instant;
import com.sonnesen.productsapi.application.domain.category.Category;
import com.sonnesen.productsapi.application.domain.category.CategoryId;

public record UpdateCategoryOutput(CategoryId id, String name, String description, boolean isActive,
        Instant createdAt, Instant updatedAt, Instant deletedAt) {

    public static UpdateCategoryOutput from(final Category category) {
        return new UpdateCategoryOutput(category.getId(), category.getName(), category.getDescription(),
                category.isActive(), category.getCreatedAt(), category.getUpdatedAt(), category.getDeletedAt());
    }
}
