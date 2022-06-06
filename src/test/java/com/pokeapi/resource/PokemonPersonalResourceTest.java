package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.infrastructure.services.PokemonPersonalService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(PokemonPersonalResource.class)
public class PokemonPersonalResourceTest {

    @Mock
    private PokemonPersonalService pokemonPersonalService;

    @InjectMocks
    private PokemonPersonalResource pokemonPersonalResource;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        pokemonPersonalResource = new PokemonPersonalResource(pokemonPersonalService);
    }

    @Nested
    @DisplayName("Given pokemonPersonalDetail method")
    class PokemonPersonalDetailIdTest {
        Response response;

        @Nested
        @DisplayName("When response is success")
        class SuccessAllTest {
            @BeforeEach
            public void mockAndAct() {
                List<PokemonDetail> pokemonDetailMock = new ArrayList<PokemonDetail>();
                doReturn(pokemonDetailMock).when(pokemonPersonalService).listPersonalBd();

                response = pokemonPersonalResource.listToPersonalBd();
            }

            @Test
            @DisplayName("Then response status is success")
            void validateSuccessTest() {
                assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            }
        }
        @Nested
        @DisplayName("when it raises exceptions")
        class FailAllTest {
            @BeforeEach
            public void mockAndAct() {
                RuntimeException exceptionMock = new RuntimeException();
                doThrow(exceptionMock).when(pokemonPersonalService).listPersonalBd();

                response = pokemonPersonalResource.listToPersonalBd();
            }

            @Test
            @DisplayName("Then response status is no content")
            void validateExceptionTest() {
                assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
            }

        }

        @Nested
        @DisplayName("When response is success")
        class SuccessIdTest {
            @BeforeEach
            public void mockAndAct() {
                String idMock = "id";
                List<PokemonDetail> pokemonDetailMock = new ArrayList<PokemonDetail>();
                doReturn(pokemonDetailMock).when(pokemonPersonalService).listDetailId(idMock);

                response = pokemonPersonalResource.pokemonPersonalDetailId(idMock);
            }

            @Test
            @DisplayName("Then response status is success")
            void validateSuccessTest() {
                assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            }
        }
            @Nested
            @DisplayName("when it raises exceptions")
            class FailIdTest {
                @BeforeEach
                public void mockAndAct() {
                    String idMock = "id";
                    RuntimeException exceptionMock = new RuntimeException();
                    doThrow(exceptionMock).when(pokemonPersonalService).listDetailId(idMock);

                    response = pokemonPersonalResource.pokemonPersonalDetailId(idMock);
                }

                @Test
                @DisplayName("Then response status is no content")
                void validateExceptionTest() {
                    assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
                }

            }

        }

    }
