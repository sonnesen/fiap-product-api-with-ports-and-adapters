package com.sonnesen.productsapi.infrastructure.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CategoryControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @DisplayName("Given a valid category ID when calls getCategory then return a category with response 200")
    void givenAValidCategoryId_whenCallsGetCategory_thenShouldReturnACategory() {
        final String expectedCategoryId = "559e30e7-27a4-4db4-aca8-f8b1f5cd66bd";
        final ResponseEntity<String> response = restTemplate.getForEntity("/categories/{id}", String.class, expectedCategoryId);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final DocumentContext documentContext = JsonPath.parse(response.getBody());
        final String id = documentContext.read("$.id", String.class);
        final String name = documentContext.read("$.name", String.class);
        final String description = documentContext.read("$.description", String.class);
        final String active = documentContext.read("$.active", String.class);
        final String createdAt = documentContext.read("$.createdAt", String.class);
        final String updatedAt = documentContext.read("$.updatedAt", String.class);
        final String deletedAt = documentContext.read("$.deletedAt", String.class);

        assertThat(id).isEqualTo(expectedCategoryId);
        assertThat(name).isEqualTo("Category 1");
        assertThat(description).isEqualTo("Description 1");
        assertThat(active).isEqualTo("true");
        assertThat(createdAt).isNotNull();
        assertThat(updatedAt).isNotNull();
        assertThat(deletedAt).isNull();
    }

    @Test
    @DisplayName("Given an invalid category ID when calls getCategory then return response 404")
    void givenAnInvalidCategoryId_whenCallsGetCategory_thenShouldReturnNotFoundResponse() {
        final String expectedCategoryId = "559e30e7-27a4-4db4-aca8-f8b1f5cd66be";
        final ResponseEntity<String> response = restTemplate.getForEntity("/categories/{id}", String.class, expectedCategoryId);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Category with ID %s not found.".formatted(expectedCategoryId));
    }

    @Test
    @DisplayName("Given a category ID with invalid UUID value when calls getCategory then return response 400")
    void givenACategoryIdWithInvalidUuidValue_whenCallsGetCategory_thenShouldReturnNotFoundResponse() {
        final String expectedCategoryId = "1";
        final ResponseEntity<String> response = restTemplate.getForEntity("/categories/{id}", String.class, expectedCategoryId);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Given a valid catetory request when calls createCategory then create a new category and return response 201")
    void givenAValidCategoryRequest_whenCallsCreateCategory_thenShouldReturnCreatedResponse() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String requestBody = """
                {
                    "name": "Category 6",
                    "description": "Description 6",
                    "active": true
                }
                """;
        
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        
        final ResponseEntity<String> response = restTemplate.postForEntity("/categories", request, String.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final DocumentContext documentContext = JsonPath.parse(response.getBody());
        final String id = documentContext.read("$.id", String.class);
        final String name = documentContext.read("$.name", String.class);
        final String description = documentContext.read("$.description", String.class);
        final String active = documentContext.read("$.active", String.class);
        final String createdAt = documentContext.read("$.createdAt", String.class);
        final String updatedAt = documentContext.read("$.updatedAt", String.class);
        final String deletedAt = documentContext.read("$.deletedAt", String.class);

        assertThat(response.getHeaders().getLocation()).isNotNull();        
        assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/categories/" + id, String.class);

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("Category 6");
        assertThat(description).isEqualTo("Description 6");
        assertThat(active).isEqualTo("true");
        assertThat(createdAt).isNotNull();
        assertThat(updatedAt).isNotNull();
        assertThat(deletedAt).isNull();
    }

    @Test
    @DisplayName("Given a valid inactive category request when calls createCategory then return a new category and return response 201")
    void givenAValidInactiveCategoryRequest_whenCallsCreateCategory_thenShouldReturnCreatedResponse() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String requestBody = """
                {
                    "name": "Category 7",
                    "description": "Description 7",
                    "active": false
                }
                """;
        
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        
        final ResponseEntity<String> response = restTemplate.postForEntity("/categories", request, String.class);        
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final DocumentContext documentContext = JsonPath.parse(response.getBody());
        final String id = documentContext.read("$.id", String.class);
        final String name = documentContext.read("$.name", String.class);
        final String description = documentContext.read("$.description", String.class);
        final String active = documentContext.read("$.active", String.class);
        final String createdAt = documentContext.read("$.createdAt", String.class);
        final String updatedAt = documentContext.read("$.updatedAt", String.class);
        final String deletedAt = documentContext.read("$.deletedAt", String.class);
        
        assertThat(response.getHeaders().getLocation()).isNotNull();        
        assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/categories/" + id, String.class);

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("Category 7");
        assertThat(description).isEqualTo("Description 7");
        assertThat(active).isEqualTo("false");
        assertThat(createdAt).isNotNull();
        assertThat(updatedAt).isNotNull();
        assertThat(deletedAt).isNotNull();
    }

    @Test
    @DisplayName("Given a valid category ID when calls deleteCategory then should delete the category and return response 204")
    void givenAValidCategoryId_whenCallsDeleteCategory_thenShouldReturnNoContentResponse() {
        final String expectedCategoryId = "a96678e0-3d5e-49b1-a5ad-ebad3bfa9342";
        final ResponseEntity<String> responseBeforeDelete = restTemplate.getForEntity("/categories/{id}", String.class, expectedCategoryId);
        
        assertThat(responseBeforeDelete.getStatusCode()).isEqualTo(HttpStatus.OK);

        final ResponseEntity<Void> response = restTemplate.exchange("/categories/{id}", HttpMethod.DELETE, null, Void.class, expectedCategoryId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                
        final ResponseEntity<String> responseAfterDelete = restTemplate.getForEntity("/categories/{id}", String.class, expectedCategoryId);
        
        assertThat(responseAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseAfterDelete.getBody()).isEqualTo("Category with ID %s not found.".formatted(expectedCategoryId));
    }

    @Test
    @DisplayName("Given an invalid category ID when calls deleteCategory then should do nothing and return 204 response")
    void givenAnIValidCategoryId_whenCallsDeleteCategory_thenShouldReturnNoContentResponse() {
        final String expectedCategoryId = "a96678e0-3d5e-49b1-a5ad-ebad3bfa9343";
        final ResponseEntity<String> responseBeforeDelete = restTemplate.getForEntity("/categories/{id}", String.class, expectedCategoryId);
        
        assertThat(responseBeforeDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseBeforeDelete.getBody()).isEqualTo("Category with ID %s not found.".formatted(expectedCategoryId));

        final ResponseEntity<Void> response = restTemplate.exchange("/categories/{id}", HttpMethod.DELETE, null, Void.class, expectedCategoryId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);        
    }

    @Test
    @DisplayName("Given a category ID with invalid UUID value when calls deleteCategory then should return response 400")
    void givenACategoryIdWithInvalidUuidValue_whenCallsDeleteCategory_thenShouldReturnBadRequestResponse() {
        final String expectedCategoryId = "1";
        final ResponseEntity<Void> response = restTemplate.exchange("/categories/{id}", HttpMethod.DELETE, null, Void.class, expectedCategoryId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Given a valid active category when calls updateCategory then should update the category and return response 200")
    void givenAValidCategoryRequest_whenCallsUpdateCategory_thenShouldReturnOkResponse() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String requestBody = """
                {
                    "name": "Category 2",
                    "description": "New description for category 2",
                    "active": false
                }
                """;
        
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        
        final ResponseEntity<String> responseBeforeUpdate = restTemplate.getForEntity("/categories/{id}", String.class, "d1f9ae3e-4de7-4cb6-8e77-ac4939386a42");
        
        assertThat(responseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);

        final ResponseEntity<String> response = restTemplate.exchange("/categories/{id}", HttpMethod.PUT, request, String.class, "d1f9ae3e-4de7-4cb6-8e77-ac4939386a42");
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final DocumentContext documentContext = JsonPath.parse(response.getBody());
        final String id = documentContext.read("$.id", String.class);
        final String name = documentContext.read("$.name", String.class);
        final String description = documentContext.read("$.description", String.class);
        final String active = documentContext.read("$.active", String.class);
        final String createdAt = documentContext.read("$.createdAt", String.class);
        final String updatedAt = documentContext.read("$.updatedAt", String.class);
        final String deletedAt = documentContext.read("$.deletedAt", String.class);

        assertThat(id).isEqualTo("d1f9ae3e-4de7-4cb6-8e77-ac4939386a42");
        assertThat(name).isEqualTo("Category 2");
        assertThat(description).isEqualTo("New description for category 2");
        assertThat(active).isEqualTo("false");
        assertThat(createdAt).isNotNull();
        assertThat(updatedAt).isNotNull();
        assertThat(deletedAt).isNotNull();        
    }

    @Test
    @DisplayName("Given a valid inactive category when calls updateCategory then should update the category and return response 200")
    void givenAValidInactiveCategoryRequest_whenCallsUpdateCategory_thenShouldReturnOkResponse() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String requestBody = """
                {
                    "name": "Category 2",
                    "description": "New description for category 2",
                    "active": false
                }
                """;
        
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        
        final ResponseEntity<String> responseBeforeUpdate = restTemplate.getForEntity("/categories/{id}", String.class, "d1f9ae3e-4de7-4cb6-8e77-ac4939386a42");
        
        assertThat(responseBeforeUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);

        final ResponseEntity<String> response = restTemplate.exchange("/categories/{id}", HttpMethod.PUT, request, String.class, "d1f9ae3e-4de7-4cb6-8e77-ac4939386a42");
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final DocumentContext documentContext = JsonPath.parse(response.getBody());
        final String id = documentContext.read("$.id", String.class);
        final String name = documentContext.read("$.name", String.class);
        final String description = documentContext.read("$.description", String.class);
        final String active = documentContext.read("$.active", String.class);
        final String createdAt = documentContext.read("$.createdAt", String.class);
        final String updatedAt = documentContext.read("$.updatedAt", String.class);
        final String deletedAt = documentContext.read("$.deletedAt", String.class);

        assertThat(id).isEqualTo("d1f9ae3e-4de7-4cb6-8e77-ac4939386a42");
        assertThat(name).isEqualTo("Category 2");
        assertThat(description).isEqualTo("New description for category 2");
        assertThat(active).isEqualTo("false");
        assertThat(createdAt).isNotNull();
        assertThat(updatedAt).isNotNull();
        assertThat(deletedAt).isNotNull();        
    }
    
    @Test
    @DisplayName("Given a valid active category with an invalid category ID when calls updateCategory then should return 404 response")
    void givenAValidCategoryRequestWithAnInvalidCategoryId_whenCallsUpdateCategory_thenShouldReturnNotFoundResponse() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String requestBody = """
                {
                    "name": "Invalid category",
                    "description": "Invalid category description",
                    "active": true
                }
                """;
        
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        
        final ResponseEntity<String> response = restTemplate.exchange("/categories/{id}", HttpMethod.PUT, request, String.class, "d1f9ae3e-4de7-4cb6-8e77-ac4939386a43");
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Given valid params when calls listCategories then should return a list of categories and 200 response")
    void givenValidParams_whenCallsListCategories_thenShouldReturnAListOfCategories() {
        final ResponseEntity<String> response = restTemplate.getForEntity("/categories", String.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        final DocumentContext documentContext = JsonPath.parse(response.getBody());
        
        final int page = documentContext.read("$.page", Integer.class);
        final int perPage = documentContext.read("$.perPage", Integer.class);
        final int total = documentContext.read("$.total", Integer.class);
        final int totalOfItems = documentContext.read("$.items.length()", Integer.class);
        
        final String id = documentContext.read("$.items[0].id", String.class);
        final String name = documentContext.read("$.items[0].name", String.class);
        final String description = documentContext.read("$.items[0].description", String.class);
        final String active = documentContext.read("$.items[0].active", String.class);
        final String createdAt = documentContext.read("$.items[0].createdAt", String.class);
        final String updatedAt = documentContext.read("$.items[0].updatedAt", String.class);
        final String deletedAt = documentContext.read("$.items[0].deletedAt", String.class);

        assertThat(page).isEqualTo(0);
        assertThat(perPage).isEqualTo(10);
        assertThat(total).isEqualTo(totalOfItems);
        assertThat(id).isEqualTo("559e30e7-27a4-4db4-aca8-f8b1f5cd66bd");
        assertThat(name).isEqualTo("Category 1");
        assertThat(description).isEqualTo("Description 1");
        assertThat(active).isEqualTo("true");
        assertThat(createdAt).isNotNull();
        assertThat(updatedAt).isNotNull();
        assertThat(deletedAt).isNull();
    }

}
