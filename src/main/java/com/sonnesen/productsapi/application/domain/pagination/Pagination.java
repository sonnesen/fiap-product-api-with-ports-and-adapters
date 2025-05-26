package com.sonnesen.productsapi.application.domain.pagination;

import java.util.List;
import java.util.function.Function;

public record Pagination<T>(int currentPage, int perPage, long total, List<T> items) {

    public <R> Pagination<R> mapItems(final Function<T, R> mapper) {
        return new Pagination<>(currentPage, perPage, total, items.stream().map(mapper).toList());
    }
    
}
