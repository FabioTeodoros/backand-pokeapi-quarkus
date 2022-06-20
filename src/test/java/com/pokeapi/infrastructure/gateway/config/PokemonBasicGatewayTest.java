package com.pokeapi.infrastructure.gateway.config;

import com.pokeapi.domain.entities.PokemonBasicResponsesPokeApi;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PokemonBasicGatewayTest {

    @Mock
    WebTarget webTarget;
    @Mock
    Invocation.Builder builderMock;
    @InjectMocks
    PokemonBasicGateway pokemonBasicGateway;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        pokemonBasicGateway = new PokemonBasicGateway();
    }

    @Nested
    @DisplayName("Given pokemonBasicGateway")
    class pokemonBasic {
        PokemonBasicResponsesPokeApi resultMock = new PokemonBasicResponsesPokeApi();
        PokemonBasicResponsesPokeApi pokemonBasicResponsesPokeApiMock = new PokemonBasicResponsesPokeApi();
        @BeforeEach
        public void mockAndAct() {
            pokemonBasicResponsesPokeApiMock.setCount(1);
            when(webTarget.request()).thenReturn(builderMock);
            //when(builderMock.get()).thenReturn();
            resultMock = pokemonBasicGateway.officialList();
        }
        @Test
        @DisplayName("When officialList return success")
        void validateSuccessReturnOfficialList() {
            assertEquals(1, resultMock.getCount());
        }
    }
}