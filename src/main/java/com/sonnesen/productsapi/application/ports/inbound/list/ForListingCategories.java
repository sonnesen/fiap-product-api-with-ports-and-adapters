package com.sonnesen.productsapi.application.ports.inbound.list;

import com.sonnesen.productsapi.application.domain.pagination.Page;
import com.sonnesen.productsapi.application.domain.pagination.Pagination;

public interface ForListingCategories {

    Pagination<ListCategoryOutput> listCategories(Page page);

}
