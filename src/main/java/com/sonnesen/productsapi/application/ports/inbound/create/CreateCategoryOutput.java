package com.sonnesen.productsapi.application.ports.inbound.create;

import java.time.Instant;
import com.sonnesen.productsapi.application.domain.category.Category;
import com.sonnesen.productsapi.application.domain.category.CategoryId;

public record CreateCategoryOutput(CategoryId id,
                                   String name,
                                   String description,
                                   boolean active,
                                   Instant createdAt,
                                   Instant updatedAt,
                                   Instant deletedAt) {

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId(),
                                        category.getName(),
                                        category.getDescription(),
                                        category.isActive(),
                                        category.getCreatedAt(),
                                        category.getUpdatedAt(),
                                        category.getDeletedAt());
    }
}
