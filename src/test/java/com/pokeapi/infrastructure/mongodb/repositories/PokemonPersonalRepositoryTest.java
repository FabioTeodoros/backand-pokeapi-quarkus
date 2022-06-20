package com.pokeapi.infrastructure.mongodb.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.pokeapi.domain.entities.PokemonDetail;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(PokemonPersonalRepository.class)
class PokemonPersonalRepositoryTest {

    @Mock
    private MongoClient mongoClient;
    @Mock
    private MongoCollection mongoCollection;
    @Mock
    private MongoDatabase mongoDatabase;
    @InjectMocks
    private PokemonPersonalRepository pokemonPersonalRepository;
    @Mock
    private Bson bson;
//    @InjectMocks
//    private Filters filters;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        pokemonPersonalRepository = new PokemonPersonalRepository(mongoClient);
    }

    @Nested
    @DisplayName("Given pokemonPersonalRepository")
    class PokemonRepository {

        @Nested
        @DisplayName("When getCollection return successfully")
        class SuccessGetCollectionOrigin {
            private MongoCollection resultMongo;

            @BeforeEach
            public void mockAndAct() {
                pokemonPersonalRepository = new PokemonPersonalRepository(mongoClient);
                doReturn(mongoDatabase).when(mongoClient).getDatabase("pokemonPersonal");
                doReturn(mongoCollection).when(mongoDatabase).getCollection("pokemonPersonal");
                resultMongo = pokemonPersonalRepository.getCollection();
            }
            @Test
            @DisplayName("Then answer success")
            void validateReturnGetColletionSuccess() {
                assertEquals(resultMongo, mongoCollection);
            }
        }
        @Nested
        @DisplayName("When getId return successfully")
        class SuccessGetId {
            private Bson resultBson;

            @BeforeEach
            public void mockAndArt() {
                String idMock = "idMock";

                try (MockedStatic<Filters> filters = Mockito.mockStatic(Filters.class)) {
                    filters.when(() -> Filters.eq("id", idMock)).thenReturn(bson);
                    resultBson = pokemonPersonalRepository.getId(idMock);
                }
            }
            @Test
            @DisplayName("Then answer Success filters")
            void validationReturnGetIdSuccess() {
                assertEquals(resultBson, bson);
            }
        }
        @Nested
        @DisplayName("When getPokemonDetailIdPersonal")
        class SuccessReturnList {
            List<PokemonDetail>resultPokemonDetailList = new ArrayList<>();
            List<PokemonDetail> pokemonDetailsListMock = new ArrayList<>();
            @BeforeEach
            public void mockAndArt() {
                String idMock = "1";
                doReturn(pokemonDetailsListMock).when(pokemonPersonalRepository).pokemonDetailIdPersonal(idMock);
                resultPokemonDetailList = pokemonPersonalRepository.pokemonDetailIdPersonal(idMock);
            }
            @Test
            @DisplayName("Then answer success list return")
            void validationReturnGetListPokemonDetailIdMock() {
                assertEquals(resultPokemonDetailList, pokemonDetailsListMock);
            }
        }
    }
}