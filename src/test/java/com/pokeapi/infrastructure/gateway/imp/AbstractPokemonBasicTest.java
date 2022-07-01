package com.pokeapi.infrastructure.gateway.imp;

import com.pokeapi.domain.entities.PokemonBasicResponsesPoke;
import com.pokeapi.infrastructure.imp.AbstractPokemonBasic;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import java.net.http.WebSocket;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(AbstractPokemonBasic.class)
class AbstractPokemonBasicTest {

    @Mock
    private WebTarget webTarget;

    @Mock
    private Client client;
    @Mock
    private ClientBuilder clientBuilder;
    @Mock
    private Invocation invocation;

    @Mock
    private PokemonBasicResponsesPoke pokemonBasicResponsesPoke;

    @Mock
    private AbstractPokemonBasic abstractPokemonBasic;

    @Mock
    private PokemonBasicGateway pokemonBasicGateway;

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
            Client client;
            WebSocket.Builder builderMock;

            @BeforeEach
            public void mockAndAct() {

            }
            @Test
            @DisplayName("")
            void validationGatewayTest() {
                doReturn(clientBuilder.build()).when(webTarget).request();

                abstractPokemonBasic.officialList();

                verify(webTarget, times(1)).request();
            }
        }
    }
}