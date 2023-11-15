package net.elau.example.springboottestcontainers.web.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.elau.example.springboottestcontainers.domain.Customer;
import net.elau.example.springboottestcontainers.repository.CustomerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest {

    @LocalServerPort
    private Integer port;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        repository.deleteAll();
    }

    @Test
    void shouldGetAllCustomers() {
        List<Customer> customers = List.of(
                new Customer(null, "John", "john@test.com", "1234567890"),
                new Customer(null, "Dennis", "dennis@test.com", "1234567890")
        );
        repository.saveAll(customers);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/customers")
                .then()
                .statusCode(200)
                .body("content", hasSize(2));
    }
}
