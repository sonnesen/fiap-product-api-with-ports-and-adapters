package com.sonnesen.productsapi.application.ports.inbound.update;

public record UpdateCategoryInput(String id, String name, String description, boolean isActive) {

}
