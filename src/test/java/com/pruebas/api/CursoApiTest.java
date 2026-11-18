package com.pruebas.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CursoApiTest {

    @BeforeAll
    static void setup() {
        baseURI = "http://localhost";
        port = 8080;
    }

    // Escenario 1
    @Test
    @DisplayName("Escenario 1 - Consultar todos los cursos")
    void consultarTodosLosCursos() {
        given()
                .when()
                .get("/api/cursos")
                .then()
                .statusCode(200)
                .body("$", is(not(empty())));
    }
    // Escenario 2
    @Test
    @DisplayName("Escenario 2 - Consultar curso con id 1")
    void consultarCursoPorId() {
        given()
                .when()
                .get("/api/cursos/1")
                .then()
                .statusCode(200)
                .body("nombre", equalTo("Pruebas de Software"))
                .body("precio", greaterThan(0f));
    }
  }