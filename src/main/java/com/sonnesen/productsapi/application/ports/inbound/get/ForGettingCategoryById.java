package com.sonnesen.productsapi.application.ports.inbound.get;

public interface ForGettingCategoryById {

    GetCategoryByIdOutput getCategoryById(String categoryId);
}
