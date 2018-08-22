package com.woowahan.moduchan.controller;

import com.woowahan.moduchan.dto.category.CategoryDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import support.test.AcceptanceTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiCategoryAcceptanceTest extends AcceptanceTest {
    private HttpHeaders headers;

    @Before
    public void setUp() throws Exception {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void getCategories_성공() {
        ResponseEntity<List<CategoryDTO>> response = template().exchange("/api/categories",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<List<CategoryDTO>>(){});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<CategoryDTO> categoryDTOs = response.getBody();
        assertThat(categoryDTOs.size()).isEqualTo(8);
    }

    @Test
    public void getCategory_성공() {
        ResponseEntity<CategoryDTO> response = template().getForEntity("/api/categories/2",CategoryDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo("고기반찬");
        assertThat(response.getBody().getId()).isEqualTo(2L);
    }

    @Test
    public void getCategory_없는카테고리() {
        ResponseEntity<CategoryDTO> response = template().getForEntity("/api/categories/10",CategoryDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getCategoryPage_성공() {
        ResponseEntity<List<CategoryDTO>> response = template().exchange("/api/categories/2/last/0",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<CategoryDTO>>(){});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(3);
        assertThat(response.getBody().stream().filter(categoryDTO -> categoryDTO.getTitle().equals("[키친르쎌] 부추무침 150g")).count()).isEqualTo(1);
    }

    @Test
    public void getCategoryPage_없는카테고리() {
        ResponseEntity<List<CategoryDTO>> response = template().exchange("/api/categories/10/last/0",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<CategoryDTO>>(){});
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
