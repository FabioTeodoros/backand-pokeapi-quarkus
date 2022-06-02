package com.pokeapi.resource;

import com.pokeapi.domain.entities.PokemonDetail;
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
    private PokemonOficialAllPokemonService pokemonOficialAllPokemonService;

    @InjectMocks
    private PokemonOficialResource pokemonOficialResource;

    @BeforeAll
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup(){
        pokemonOficialResource = new PokemonOficialResource(viaPokemonDetailService, pokemonOficialAllPokemonService);
    }

   @Nested
    @DisplayName("Given pokemonDetail method")
    class pokemonDetail{
        Response response;
        @Nested
        @DisplayName("When response is success")
        class successTest{
            @BeforeEach
            public void mockAndAct(){
                String idMock = "id";
                PokemonDetail pokemonDetailMock = new PokemonDetail();

                pokemonDetailMock.setName("pokeTest");

                doReturn(pokemonDetailMock).when(viaPokemonDetailService).buscaPokemonDetail(idMock);

                response = pokemonOficialResource.pokemonDetail(idMock);
            }

            @Test
            @DisplayName("Then response status is ok ")
            void validateSuccessTest(){
                assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            }

        }

       @Nested
       @DisplayName("when it raises exceptions")
       class failTest{
           @BeforeEach
           public void mockAndAct(){
               String idMock = "id";
               //PokemonDetail pokemonDetailMock = new PokemonDetail();
               RuntimeException runtimeMock = new RuntimeException();

               doThrow(runtimeMock).when(viaPokemonDetailService).buscaPokemonDetail(idMock);

               response = pokemonOficialResource.pokemonDetail(idMock);
           }

           @Test
           @DisplayName("Then response status is no content ")
           void validateExceptionTest(){
               assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
           }

       }
    }

}