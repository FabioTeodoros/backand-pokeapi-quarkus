package com.pokeapi.infrastructure.mongodb.repositories;

import com.mongodb.MongoException;
import com.mongodb.client.*;

import com.pokeapi.domain.entities.PokemonBasicResponsesPokeApi;
import com.pokeapi.domain.entities.PokemonDetail;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;
import static com.pokeapi.TestUtil.getLoggerMock;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(PokemonPersonalRepository.class)
class PokemonPersonalRepositoryTest {

    private Logger loggerMock;
    @Mock
    private MongoClient mongoClient;
    @Mock
    private MongoCursor mongoCursor;
    @Mock
    private Document document;
    @Mock
    private MongoCollection mongoCollection;
    @Mock
    private MongoDatabase mongoDatabase;
    @Mock
    private FindIterable iterable;
    @InjectMocks
    private PokemonPersonalRepository pokemonPersonalRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        pokemonPersonalRepository = new PokemonPersonalRepository(mongoClient);

        doReturn(mongoDatabase).when(mongoClient).getDatabase("pokemonPersonal");
        doReturn(mongoCollection).when(mongoDatabase).getCollection("pokemonPersonal");
    }
    @BeforeEach
    void mock() throws Exception {
        loggerMock = getLoggerMock(PokemonPersonalRepository.class);
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
                resultMongo = pokemonPersonalRepository.collection();
            }

            @Test
            @DisplayName("Then answer success")
            void validateReturnGetColletionSuccess() {
                assertEquals(resultMongo, mongoCollection);
            }
        }

        @Nested
        @DisplayName("When getPokemonDetailIdPersonal return successfully")
        class SuccessReturnListDetailIdPersonal {
            List<PokemonDetail> result;
            Long valueCountDocument = 1L;
            String id = "1";

            @BeforeEach
            public void mockAndAct() {

                doReturn(valueCountDocument).when(mongoCollection).countDocuments(any(Bson.class));
                doReturn(iterable).when(mongoCollection).find(any(Bson.class));
                doReturn(mongoCursor).when(iterable).iterator();
                doReturn(true, true, true, false).when(mongoCursor).hasNext();
                doReturn(document).when(mongoCursor).next();

                result = pokemonPersonalRepository.pokemonDetailIdPersonal(id);
            }

            @Test
            @DisplayName("then return success for list getPokemonDetailIdPersonal size")
            void validationReturnGetListPokemonDetailIdMockSize() {
                assertEquals(3, result.size());
            }

            @Test
            @DisplayName("Then logs used in getPokemonDetailIdPersonal should be called correctly on success")
            void validationLogsGetPokemonDetailIdPersonalSuccess() {
                verify(loggerMock, times(3))
                        .info("get detailed Pokémons Personal");
            }
        }

        @Nested
        @DisplayName("When the sent id is not exist in getPokemonDetailIdPersonal")
        class FailReturnIdProblem {
            RuntimeException exceptionMock = new RuntimeException();
            List<PokemonDetail> result;
            String id = "-1";
            Long valueCountDocument = -1L;

            @BeforeEach
            public void mockAndAct() {
                doReturn(valueCountDocument).when(mongoCollection).countDocuments(any(Bson.class));
                result = pokemonPersonalRepository.pokemonDetailIdPersonal(id);
            }

            @Test
            @DisplayName("then return list is empty")
            void validationErrorId() {
                assertEquals(pokemonPersonalRepository.pokemonDetailIdPersonal(id).size(), result.size());
            }

            @Test
            @DisplayName("Then logs used in getPokemonDetailIdPersonal should be called correctly in case of get failure")
            void validationLogsGetPokemonDetailIdPersonalFailed() {
                verify(loggerMock, times(1))
                        .info("Pokemon id not exist");
            }
        }

        @Nested
        @DisplayName("When the mongobd has a problem in getPokemonDetailIdPersonal")
        class FailReturnMongoHasProblem {
            List<PokemonDetail> result;
            String id = "1";

            @BeforeEach
            public void mockAndAct() {
                MongoException exceptionMock = new MongoException("");

                doThrow(exceptionMock).when(mongoCollection).countDocuments(any(Bson.class));

                result = pokemonPersonalRepository.pokemonDetailIdPersonal(id);
            }

            @Test
            @DisplayName("then id has a problem")
            void validationExptionMongo() {
                assertEquals(pokemonPersonalRepository.pokemonDetailIdPersonal(id).size(), result.size());
            }

            @Test
            @DisplayName("Then logs used in getPokemonDetailIdPersonal should be called correctly in case of exception mongoBd")
            void validationLogsGetPokemonDetailIdPersonalExceptionMongoDb() {
                verify(loggerMock, times(1))
                        .info("Error in get PokemonPersonalId with MongoBd");
            }
        }

        @Nested
        @DisplayName("When occur exception in getPokemonDetailIdPersonal")
        class FailReturnException {
            List<PokemonDetail> result;
            String id = "1";

            @BeforeEach
            public void mockAndAct() {
                RuntimeException exceptionMock = new RuntimeException();

                doThrow(exceptionMock).when(mongoCollection).countDocuments(any(Bson.class));

                result = pokemonPersonalRepository.pokemonDetailIdPersonal(id);
            }

            @Test
            @DisplayName("then id has a problem")
            void validationExptionMongo() {
                assertEquals(pokemonPersonalRepository.pokemonDetailIdPersonal(id).size(), result.size());
            }

            @Test
            @DisplayName("Then logs used in getPokemonDetailIdPersonal should be called correctly in case of exception mongoBd")
            void validationLogsGetPokemonDetailIdPersonalExceptionMongoDb() {
                verify(loggerMock, times(1))
                        .info("Exception in get Pokémons");
            }
        }

        @Nested
        @DisplayName("When pokemonListPersonal return successfully")
        class SuccessReturnListPersonal {
            Long valueCountDocument = 1L;
            PokemonBasicResponsesPokeApi result;
            String model = "modelMock";

            @BeforeEach
            public void mockAndAct() {
                doReturn(iterable).when(mongoCollection).find();
                doReturn(mongoCursor).when(iterable).iterator();
                doReturn(valueCountDocument).when(mongoCollection).countDocuments(any(Bson.class));
                doReturn(document).when(mongoCursor).next();
                doReturn("1").when(document).getString("name");
                doReturn("2").when(document).getString("url");
                doReturn("3").when(document).getString("id");

                result = pokemonPersonalRepository.pokemonListPersonal(model);
            }

            @Test
            @DisplayName("then return success pokemonListPersonal")
            void validationSuccessPokemonListPersonal() {
                assertEquals(result, pokemonPersonalRepository.pokemonListPersonal(model));
            }
        }

        @Nested
        @DisplayName("When pokemonPersonalCreate return successfully")
        class SuccessReturnCreatePersonalPokemon {
            Long valueCountDocument = 1L;
            PokemonDetail pokemonDetailMock = new PokemonDetail();

            @BeforeEach
            public void mockAndAct() {
                String name = "1";
                pokemonPersonalRepository.pokemonPersonalCreate(pokemonDetailMock);
                doReturn(valueCountDocument).when(mongoCollection).countDocuments(eq("0"));

            }

            @Test
            @DisplayName("Then return create pokemonPersonal")
            void validationSuccessCreatePokemonPersonal() {
                verify(mongoCollection, times(1)).insertOne(document);
            }
        }
    }
}

//document.getString("id")