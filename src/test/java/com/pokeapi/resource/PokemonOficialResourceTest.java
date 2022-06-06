package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
import com.pokeapi.domain.entities.PokemonList;
import com.pokeapi.infrastructure.services.*;

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
@TestHTTPEndpoint(PokemonOficialResource.class)
public class PokemonOficialResourceTest {

    @Mock
    private ViaPokemonDetailService viaPokemonDetailService;

    @Mock
    private ViaPokemonAllService viaPokemonAllService;

    @InjectMocks
    private PokemonOficialResource pokemonOficialResource;

    @BeforeAll
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup(){
        pokemonOficialResource = new PokemonOficialResource(viaPokemonDetailService, viaPokemonAllService);
    }

    @Nested
    @DisplayName("Given pokemonDetail method")
    class PokemonDetailIdTest {
        Response response;
        @Nested
        @DisplayName("When response is success")
        class SuccessTest{
            @BeforeEach
            public void mockAndAct(){
                String idMock = "id";
                PokemonDetail pokemonDetailMock = new PokemonDetail();
                pokemonDetailMock.setName("pokeTest");
                doReturn(pokemonDetailMock).when(viaPokemonDetailService).buscaPokemonDetail(idMock);

                response = pokemonOficialResource.pokemonDetail(idMock);
            }

            @Test
            @DisplayName("Then response status is success")
            void validateSuccessTest(){
                assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            }

        }

       @Nested
       @DisplayName("when it raises exceptions")
       class FailIdTest{
           @BeforeEach
           public void mockAndAct(){
               String idMock = "id";
               RuntimeException ExceptionMock = new RuntimeException();
               doThrow(ExceptionMock).when(viaPokemonDetailService).buscaPokemonDetail(idMock);

               response = pokemonOficialResource.pokemonDetail(idMock);
           }

           @Test
           @DisplayName("Then response status is no content")
           void validateExceptionTest(){
               assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
           }

       }

        @Nested
        @DisplayName("When response is status OK")
        class StatusAllOkTest {
            @BeforeEach
            public void mockAndAct() {
                PokemonList pokemonListMock = new PokemonList();
                doReturn(pokemonListMock).when(viaPokemonAllService).getAll();

                response = pokemonOficialResource.getAll();
            }

            @Test
            @DisplayName("Then response status is ok")
            void validateSuccessTest(){
                assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            }

        }

        @Nested
        @DisplayName("when it raises exceptions")
        class FailAllTest{
            @BeforeEach
            public void mockAndAct(){
                RuntimeException ExceptionMock = new RuntimeException();
                doThrow(ExceptionMock).when(viaPokemonAllService).getAll();

                response = pokemonOficialResource.getAll();
            }

            @Test
            @DisplayName("Then response status is no content")
            void validateExceptionTest(){
                assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
            }

        }

    }

}