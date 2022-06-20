package com.pokeapi.infrastructure.imp;

import com.pokeapi.domain.entities.PokemonBasicResponsesPokeApi;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(AbstractPokemonBasic.class)
class AbstractPokemonBasicTest {

    private PokemonBasicResponsesPokeApi pokemonBasicResponsesPokeApi;
    private AbstractPokemonBasic abstractPokemonBasic;
    @Mock
    WebTarget webTarget;

    @Mock
    private Invocation.Builder builderMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    @DisplayName("Given AbstractPokemonBasic officialList method")
    class OfficialList {

        PokemonBasicResponsesPokeApi resultMock = new PokemonBasicResponsesPokeApi();

        @Nested
        @DisplayName("When officialList return success")
        class ValidationSuccessOfficialList{
            @BeforeEach
            public void mockAndArt() {
                //String domainMock = "https://pokeapi.co/api/v2/pokemon?limit=2000&offset=0";
                //Client clientMock = ClientBuilder.newClient();
                PokemonBasicResponsesPokeApi pokemonBasicResponsesPokeApiMock = new PokemonBasicResponsesPokeApi();
                pokemonBasicResponsesPokeApiMock.setCount(1);
                when(webTarget.request()).thenReturn(builderMock);
                //when(builderMock.get()).thenReturn(pokemonBasicResponsesPokeApiMock);

               // doReturn(pokemonBasicResponsesPokeApiMock).when(webTarget).request().get(PokemonBasicResponsesPokeApi.class);

                resultMock = abstractPokemonBasic.officialList();
            }
            @Test
            @DisplayName("Then response is success for officialList")
            void validateSuccessReturnOfficialList() {
                assertEquals(1, resultMock.getCount());
            }
        }
    }
}