package com.sonnesen.productsapi.application.domain.category;

import java.util.UUID;

public record CategoryId(String value) {

    public static CategoryId from(final String value) {
        try {
            return new CategoryId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category id");
        }
    }

    @Override
    public String toString() {
        return value;
    }

}
