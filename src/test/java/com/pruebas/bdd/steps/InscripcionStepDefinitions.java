package com.pruebas.bdd.steps;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class InscripcionStepDefinitions {

    private String codigoEstudiante;
    private Long cursoId;
    private Response response;

    @Given("un estudiante nuevo con código {string}")
    public void unEstudianteNuevoConCodigo(String codigo) {
        this.codigoEstudiante = codigo;
    }

    @When("se inscribe en el curso {int}")
    public void seInscribeEnElCurso(int curso) {
        response = given()
                .contentType("application/json")
                .body("{\"codigoEstudiante\":\"" + codigoEstudiante + "\",\"cursoId\":" + curso + "}")
                .when()
                .post("http://localhost:8080/api/inscripciones");
    }

    @Given("el estudiante {string} ya inscrito en el curso {int}")
    public void elEstudianteYaInscritoEnElCurso(String codigo, int curso) {
        this.codigoEstudiante = codigo;
        this.cursoId = (long) curso;
    }

    @When("intenta inscribirse de nuevo en el curso {int}")
    public void intentaInscribirseDeNuevoEnElCurso(int curso) {
        response = given()
                .contentType("application/json")
                .body("{\"codigoEstudiante\":\"" + codigoEstudiante + "\",\"cursoId\":" + curso + "}")
                .when()
                .post("http://localhost:8080/api/inscripciones");
    }

    @Then("el sistema responde {int}")
    public void elSistemaResponde(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("el sistema rechaza con error")
    public void elSistemaRechazaConError() {
        response.then().statusCode(greaterThanOrEqualTo(400));
    }
}