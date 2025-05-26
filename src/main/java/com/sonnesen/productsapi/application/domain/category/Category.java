package com.sonnesen.productsapi.application.domain.category;

import java.time.Instant;

import lombok.Getter;

@Getter
public class Category {

    private CategoryId id;
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(final CategoryId id, final String name, final String description, final boolean active,
            final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(final String name, final String description, final boolean active) {
        final var now = Instant.now();
        final var deletedAt = active ? null : now;
        return new Category(new CategoryId(null), name, description, active, now, now, deletedAt);
    }

    public static Category with(final CategoryId id, final String name, final String description,
            final boolean isActive, final Instant createdAt, final Instant updatedAt, final Instant deletedAt) {
        return new Category(id, name, description, isActive, createdAt, updatedAt, deletedAt);
    }

    public Category update(final String name, final String description, final boolean active) {
        if (active) {
            activate();
        } else {
            deactivate();
        }
        this.name = name;
        this.description = description;
        return this;
    }

    public Category activate() {
        this.active = true;
        this.deletedAt = null;
        return this;
    }

    public Category deactivate() {
        if (this.deletedAt == null) {
            this.deletedAt = Instant.now();
        }
        this.active = false;
        return this;
    }
}
