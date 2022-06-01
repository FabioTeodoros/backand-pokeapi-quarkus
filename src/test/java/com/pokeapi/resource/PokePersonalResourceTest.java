package com.pokeapi.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.services.PokemonOficialAllPokemonService;
import com.pokeapi.infrastructure.services.PokemonOficialDetailService;
import com.pokeapi.infrastructure.services.PokemonPersonalService;
import io.quarkus.test.Mock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@QuarkusTest
@TestHTTPEndpoint(PokemonOficialResource.class)

class PokePersonalResourceTest {

    @Inject
    PokemonPersonalService pokemonPersonalService;

    @Test
    @DisplayName("Get personal pokemons from the bd")
    public void testGetAllOk() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());
    }


}