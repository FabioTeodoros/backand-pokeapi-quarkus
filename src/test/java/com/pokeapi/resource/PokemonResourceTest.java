package com.pokeapi.resource;

import com.mongodb.MongoException;
import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.domain.entities.PokemonFullContractFull;
import com.pokeapi.infrastructure.gateway.PokemonFullService;
import com.pokeapi.infrastructure.gateway.PokemonOfficialDetailService;
import com.pokeapi.infrastructure.mongodb.repositories.PokemonPersonalRepository;

import io.quarkus.test.common.http.TestHTTPEndpoint;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(PokemonResource.class)
class PokemonResourceTest {

    @Mock
    private PokemonPersonalRepository pokemonPersonalRepository;
    @Mock
    private PokemonOfficialDetailService pokemonOfficialDetailService;
    @Mock
    private PokemonFullService pokemonFullService;
    @InjectMocks
    private PokemonResource pokemonResource;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        pokemonResource = new PokemonResource(
                pokemonPersonalRepository
                , pokemonOfficialDetailService
                , pokemonFullService);
    }

    @Nested
    @DisplayName("Given pokemonResource method")
    class Pokemons {
        Response response;

        @Nested
        @DisplayName("When official id response is success")
        class SuccessTestPokemonOfficial {
            @BeforeEach
            public void mockAndAct() {
                String idMock = "id";
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                doReturn(pokemonDetailMock).when(pokemonOfficialDetailService).buscaPokemonDetail(idMock);
                response = pokemonResource.detailOfficialId(idMock);
            }
            @Test
            @DisplayName("Then response status is success for pokemon official")
            void validateSuccessPokemonOfficialTest() {
                assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("when exceptions occur for pokemon official")
        class FailPokemonOfficialTest {
            @BeforeEach
            public void mockAndAct() {
                String idMock = "id";
                RuntimeException exceptionMock = new RuntimeException();
                doThrow(exceptionMock).when(pokemonOfficialDetailService).buscaPokemonDetail(idMock);
                response = pokemonResource.detailOfficialId(idMock);
            }
            @Test
            @DisplayName("Then response is internal server error for pokemon official")
            void validateExceptionOfficialTest() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When list pokémon response is success for pokemon official")
        class SuccessListOfficialTest {
            @BeforeEach
            public void mockAndAct() {
                String modelMock = "modelMock";
                PokemonFullContractFull pokemonFullContractFullMock = new PokemonFullContractFull();
                doReturn(pokemonFullContractFullMock).when(pokemonFullService).pokemonGetValidation(modelMock);
                response = pokemonResource.list(modelMock);
            }
            @Test
            @DisplayName("Then response status is success for pokemons list")
            void validateSuccessListPokemonsTest() {
                assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When illegal argument exceptions occur for pokemons list")
        class FailListPokemonsillegalArgumentTest {
            @BeforeEach
            public void mockAndArt() {
                String modelMock = "modelMock";
                IllegalArgumentException exceptionMock = new IllegalArgumentException();
                doThrow(exceptionMock).when(pokemonFullService).pokemonGetValidation(modelMock);
                response = pokemonResource.list(modelMock);
            }
            @Test
            @DisplayName("Then response is not found for validate exception pokemons list")
            void validateExceptionListPokemonsTest() {
                assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When mongo exceptions occur pokemons list")
        class FailListPokemonsMongoTest {
            @BeforeEach
            public void mockAndArt() {
                String modelMock = "modelMock";
                MongoException exceptionMock = new MongoException("");
                doThrow(exceptionMock).when(pokemonFullService).pokemonGetValidation(modelMock);
                response = pokemonResource.list(modelMock);
            }
            @Test
            @DisplayName("Then response is internal error for mongo exception")
            void validateMongoExceptionListPokemonsTest() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When exception occurs")
        class FailListPokemonsException {
            @BeforeEach
            public void mockAndArt() {
                String modelMock = "modelMock";
                RuntimeException exceptionMock = new RuntimeException();
                doThrow(exceptionMock).when(pokemonFullService).pokemonGetValidation(modelMock);
                response = pokemonResource.list(modelMock);
            }
            @Test
            @DisplayName("Then response is internal error for exception")
            void validateExceptionListOfficialTest() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When list pokemons and the answer is success")
        class ValidateSuccessListPokemonPersonalTest {
            @BeforeEach
            public void mockAndArt() {
                String idMock = "idMock";
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                doReturn(pokemonDetailMock).when(pokemonPersonalRepository).id(idMock);
                response = pokemonResource.detailPersonalId(idMock);
            }
            @Test
            @DisplayName("Then response for pokémon personal id is success")
            void validationSuccessPokemonPersonalIdTest() {
                assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When occur exception for pokemon id")
        class FailPokemonIdPersonalExceptionTest {
            @BeforeEach
            public void mockAndArt() {
                String idMock = "idMock";
                RuntimeException exceptionMock = new RuntimeException("");
                doThrow(exceptionMock).when(pokemonPersonalRepository).id(idMock);
                response = pokemonResource.detailPersonalId(idMock);
            }
            @Test
            @DisplayName("Then response for pokémon id is exception")
            void validationExceptionPokemonIdPersonalTest() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When create pokemons and the answer is success")
        class ValidateSuccessCreatePokemonPersonalTest {
            @BeforeEach
            public void mockAndArt() {
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                response = pokemonResource.Insert(pokemonDetailMock);
            }
            @Test
            @DisplayName("Then the answer will be success for pokemon create")
            void validationSuccessCreatePokemonPersonal() {
                assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When mongo exceptions occur for pokemon create")
        class FailMongoExceptionCreatePokemonPersonal {
            @BeforeEach
            public void mockAndArt() {
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                MongoException exceptionMock = new MongoException("");
                doThrow(exceptionMock).when(pokemonPersonalRepository).insert(pokemonDetailMock);
                response = pokemonResource.Insert(pokemonDetailMock);
            }
            @Test
            @DisplayName("Then answer is Internal error for Mongo exception for pokemon create")
            void validationMongoExceptionCreatePokemonPersonal() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When exception occur for pokemon create")
        class FailExceptionCretePokemonPersonal {
            @BeforeEach
            public void mockAndArt() {
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                RuntimeException exceptionMock = new RuntimeException();
                doThrow(exceptionMock).when(pokemonPersonalRepository).insert(pokemonDetailMock);
                response = pokemonResource.Insert(pokemonDetailMock);
            }
            @Test
            @DisplayName("Then response is internal error for exception for pokemon create")
            void validationExceptionCreatePokemonPersonal() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When delete pokemons and the answer is success ")
        class ValidateSuccessDeletePokemonPersonal {
            @BeforeEach
            public void mockAndArt() {
                String idMock = "idMock";
                response = pokemonResource.deleteId(idMock);
            }
            @Test
            @DisplayName("Then the answer will be success for pokemon delete")
            void validationSuccessDelete() {
                assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When mongo exceptions occur for pokemon delete")
        class FailMongoExceptionDeletePokemonPersonal {
            @BeforeEach
            public void mockAndArt() {
                String idMock = "idMock";
                MongoException exceptionMock = new MongoException("");
                doThrow(exceptionMock).when(pokemonPersonalRepository).delete(idMock);
                response = pokemonResource.deleteId(idMock);
            }
            @Test
            @DisplayName("Then response is Internal error for Mongo exception for pokemon create")
            void validationMongoExceptionCreatePokemonPersonal() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When exception occur for pokemon delete")
        class FailExceptionDeletePokemonPersonal {
            @BeforeEach
            public void mockAndArt() {
                String idMock = "idMock";
                RuntimeException exceptionMock = new RuntimeException();
                doThrow(exceptionMock).when(pokemonPersonalRepository).delete(idMock);
                response = pokemonResource.deleteId(idMock);
            }
            @Test
            @DisplayName("Then response is internal error for exception for pokemon delete")
            void validationExceptionCreatePokemonPersonal() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When update pokemons and the answer is success ")
        class ValidationSuccessUpdatePokemonPersonal {
            @BeforeEach
            public void mockAndArt() {
                String idMock = "idmock";
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                response = pokemonResource.updateId(idMock, pokemonDetailMock);
            }
            @Test
            @DisplayName("Then the answer will be success for pokemon update")
            void validationSuccessUpdatePokemonTest() {
                assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When mongo exceptions occur for pokemon update")
        class FailMongoExceptionUpdatePokemonPersonal {
            @BeforeEach
            public void mockAndArt() {
                String idMock = "idMock";
                MongoException exceptionMock = new MongoException("");
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                doThrow(exceptionMock).when(pokemonPersonalRepository).update(idMock, pokemonDetailMock);
                response = pokemonResource.updateId(idMock, pokemonDetailMock);
            }
            @Test
            @DisplayName("Then response is Internal error for Mongo exception for pokemon update")
            void validationMongoExceptionCreatePokemonPersonal() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }

        @Nested
        @DisplayName("When exception occur for pokemon update")
        class FailExceptionUpdatePokemonPersonal {
            @BeforeEach
            public void mockAndArt() {
                String idMock = "idMock";
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                RuntimeException exceptionMock = new RuntimeException();
                doThrow(exceptionMock).when(pokemonPersonalRepository).update(idMock, pokemonDetailMock);
                response = pokemonResource.updateId(idMock, pokemonDetailMock);
            }
            @Test
            @DisplayName("Then response is internal error for exception for pokemon update")
            void validationExceptionUpdatePokemonPersonal() {
                assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
            }
        }
    }
}