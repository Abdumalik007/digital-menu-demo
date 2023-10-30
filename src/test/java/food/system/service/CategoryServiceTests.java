package food.system.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import food.system.dto.CategoryDto;
import food.system.dto.custom.LoginRequest;
import food.system.dto.custom.LoginResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryServiceTests {

    @Autowired
    private MockMvc mockMvc;
    public String baseUrl = "http://localhost:8080/api";
    private static String token;

    @Test
    @Order(1)
    public void getAndSetJwtToken() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("admin")
                .password("123456")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(loginRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        String responseBody = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = objectMapper.readerFor(new TypeReference<LoginResponse>() {});
        LoginResponse response = reader.readValue(responseBody);
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getToken());
        token = response.getToken();
    }

    @Test
    @Order(2)
    public void addCategorySuccessfully() throws Exception {
        String categoryName = "Oshlar";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/category/" + categoryName)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        String responseBody = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = new ObjectMapper().readerFor(new TypeReference<CategoryDto>() {});
        CategoryDto category = reader.readValue(responseBody);
        Assertions.assertNotNull(category);
        assertEquals("Oshlar", category.getName());
        Assertions.assertEquals(1, category.getId());

    }



    @Test
    @Order(3)
    public void addCategoryWithExistedName() throws Exception {
        String categoryName = "Oshlar";

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/category/" + categoryName)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(500));
    }


    @Order(4)
    @Test
    public void updateCategoryWithExistedName() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/category/" + "Shorvalar")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        mockMvc.perform(requestBuilder);

        requestBuilder = MockMvcRequestBuilders
                .put("/category")
                .param("id", "1")
                .param("name", "Shorvalar")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(500));
    }



    @Test
    @Order(5)
    public void updateCategorySuccessfully() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/category")
                .param("id", "1")
                .param("name", "Somsalar")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token);

        String responseBody = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = new ObjectMapper().readerFor(new TypeReference<CategoryDto>() {});
        CategoryDto category = reader.readValue(responseBody);
        Assertions.assertNotNull(category);
        assertEquals("Somsalar", category.getName());
        Assertions.assertNotNull(category.getId());

    }





}
