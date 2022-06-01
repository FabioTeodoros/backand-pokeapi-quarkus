package com.pokeapi.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import com.pokeapi.infrastructure.services.PokemonOficialAllPokemonService;
import com.pokeapi.infrastructure.services.PokemonOficialDetailService;
import io.quarkus.test.Mock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@QuarkusTest
@TestHTTPEndpoint(PokemonOficialResource.class)
public class PokemonOficialResourceTest {

    @Inject
    PokemonOficialAllPokemonService pokemonOficialAllPokemonService;
    @Inject
    PokemonOficialDetailService pokemonOficialDetailService;

    @Test
    @DisplayName("check the endpoint pokemon")
    public void testGetAllOk() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pokemon")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("count", is ("1126"), "name", anything(), "url", anything());
    }

    @Test
    @DisplayName("check the endpoint bad link pokemon")
    public void testGetAllBadLink() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/pokemon1")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("check the endpoint bad link pokemon")
    public void testGetAllNotLink() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("check the endpoint ID")
    public void testGetDetailOk() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/1")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", is ("1")
                        , "height", is ("7")
                        , "weight", is ("69")
                        ,"name", is ("bulbasaur")
                        ,"type", anything()
                        ,"abilities", anything()
                        ,"sprites", anything()
                        ,"base_experience", anything());
    }

    @Test
    @DisplayName("check the endpoint not ID Link")
    public void testGetDetailNotLink() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
            }

    @Test
    @DisplayName("check the endpoint ID negative ID link")
    public void testGetDetailNegativeID() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/-1")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("check the endpoint unknown number ID")
    public void testGetDetailUnknownNumberID() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/%")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}