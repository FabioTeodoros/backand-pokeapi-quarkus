package com.pokeapi.infrastructure.gateway.config;

import com.pokeapi.domain.entities.PokemonBasicResponsesPoke;
import com.pokeapi.infrastructure.imp.AbstractPokemonBasic;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import javax.ws.rs.client.WebTarget;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(PokemonDetailGateway.class)
class PokemonDetailGatewayTest {

    @Mock
    private WebTarget webTarget;

    @Mock
    private Client client;
    @Mock
    private ClientBuilder builder;

    @Mock
    private PokemonBasicResponsesPoke pokemonBasicResponsesPoke;

    @Mock
    private AbstractPokemonBasic abstractPokemonBasic;

    @InjectMocks
    PokemonBasicGateway pokemonBasicGateway;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {

    }

    @Nested
    @DisplayName("Given PokemonDetailGateway")
    class DetailGatewayTest {

        @Nested
        @DisplayName("When pokemonDetailGateway is called")
        class GatewayTest {

            String domain = "https://pokeapi.co/api/v2/pokemon?limit=2000&offset=0";

            @BeforeEach
            public void mockAndAct() {

            }
            @Test
            @DisplayName("")
            void validationGatewayTest() {
                doReturn(webTarget).when(client).target(domain);
                doReturn(builder.build()).when(webTarget).request();

                abstractPokemonBasic.officialList();

                verify(webTarget, times(1)).request();
            }
        }
    }
}