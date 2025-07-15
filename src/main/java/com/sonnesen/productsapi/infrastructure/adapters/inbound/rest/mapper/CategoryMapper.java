package com.sonnesen.productsapi.infrastructure.adapters.inbound.rest.mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.sonnesen.categories.model.CategoryDTO;
import com.sonnesen.categories.model.CreateCategoryDTO;
import com.sonnesen.categories.model.UpdateCategoryDTO;
import com.sonnesen.productsapi.application.domain.category.CategoryId;
import com.sonnesen.productsapi.application.ports.inbound.create.CreateCategoryInput;
import com.sonnesen.productsapi.application.ports.inbound.create.CreateCategoryOutput;
import com.sonnesen.productsapi.application.ports.inbound.get.GetCategoryByIdOutput;
import com.sonnesen.productsapi.application.ports.inbound.list.ListCategoryOutput;
import com.sonnesen.productsapi.application.ports.inbound.update.UpdateCategoryInput;
import com.sonnesen.productsapi.application.ports.inbound.update.UpdateCategoryOutput;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", expression = "java(java.util.UUID.fromString(output.id().toString()))")
    @Mapping(target = "createdAt", expression = "java(mapOffsetDateTime(output.createdAt()))")
    @Mapping(target = "deletedAt", expression = "java(mapOffsetDateTime(output.deletedAt()))")
    @Mapping(target = "updatedAt", expression = "java(mapOffsetDateTime(output.updatedAt()))")
    CategoryDTO toDTO(CreateCategoryOutput output);

    @Mapping(target = "id", expression = "java(java.util.UUID.fromString(output.id().toString()))")
    CategoryDTO toDTO(ListCategoryOutput output);

    List<CategoryDTO> toDTO(List<ListCategoryOutput> output);

    @Mapping(target = "active", source = "isActive")
    CategoryDTO toDTO(GetCategoryByIdOutput output);

    @Mapping(target = "active", source = "isActive")
    CategoryDTO toDTO(UpdateCategoryOutput output);

    CreateCategoryInput fromDTO(CreateCategoryDTO dto);

    @Mapping(target = "id", source = "categoryId")
    UpdateCategoryInput fromDTO(String categoryId, UpdateCategoryDTO dto);

    default OffsetDateTime mapOffsetDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(instant, java.time.ZoneOffset.UTC);
    }

    default String map(CategoryId value) {
        return value.toString();
    }

}
