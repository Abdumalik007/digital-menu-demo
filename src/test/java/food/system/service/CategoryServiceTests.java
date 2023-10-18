package food.system.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import food.system.dto.custom.LoginRequest;
import food.system.dto.custom.LoginResponse;
import food.system.repository.CategoryRepositoryTest;
import food.system.repository.UserRepositoryTest;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryServiceTests {

    @Autowired
    private MockMvc mockMvc;
    public String baseUrl = "http://localhost:8080/api";
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
    }


//    @Test
//    public void addCategorySuccessfullyTest() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer YOUR_API_TOKEN");
//
//        RequestEntity<?> requestEntity = new RequestEntity<>(
//                headers,
//                HttpMethod.POST,
//                URI.create(baseUrl.concat("/category"))
//        );
//
//        ResponseEntity<CategoryDto> response = restTemplate.exchange(requestEntity, CategoryDto.class);
//    }



}
