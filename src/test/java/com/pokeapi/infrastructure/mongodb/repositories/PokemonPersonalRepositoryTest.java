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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;
import static com.pokeapi.TestUtil.getLoggerMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    private FindIterable findIterable;
    @Mock
    private MongoIterable mongoIterable;
    @InjectMocks
    private PokemonPersonalRepository pokemonPersonalRepository;

    private final String ID = "1";
    private MongoException mongoException;
    private RuntimeException exception;
    private IllegalArgumentException illegalArgumentException;
    private PokemonDetail pokemonDetailMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        pokemonPersonalRepository = new PokemonPersonalRepository(mongoClient);

        doReturn(mongoDatabase).when(mongoClient).getDatabase("pokemonPersonal");
        doReturn(mongoCollection).when(mongoDatabase).getCollection("pokemonPersonal2", PokemonDetail.class);
    }

    @BeforeEach
    void mock() throws Exception {
        loggerMock = getLoggerMock(PokemonPersonalRepository.class);
    }

    @Nested
    @DisplayName("Given pokemonPersonalRepository")
    class PokemonRepository {

        @Nested
        @DisplayName("When get id return successfully")
        class SuccessGetId {
            PokemonDetail result;
            @BeforeEach
            public void mockAndAct() {
                pokemonDetailMock = new PokemonDetail();
                doReturn(findIterable).when(mongoCollection).find(eq("_id", ID));
                doReturn(pokemonDetailMock).when(findIterable).first();

                result = pokemonPersonalRepository.id(ID);
            }
            @Test
            @DisplayName("Then answer success")
            void validateReturnGetId() {
                verify(loggerMock, times(1)).info("Get Pokémon Detail Personal Id Success");
                assertEquals(pokemonDetailMock, result);
            }
        }

        @Nested
        @DisplayName("When the sent id is not exist in getPokemonDetailIdPersonal")
        class FailReturnIdProblem {
            PokemonDetail result;

            @BeforeEach
            public void mockAndAct() {
                pokemonDetailMock = new PokemonDetail();
                doReturn(findIterable).when(mongoCollection).find(eq("_id", ID));
                doReturn(null).when(findIterable).first();

                result = pokemonPersonalRepository.id(ID);
            }

            @Test
            @DisplayName("then return null")
            void validationErrorId() {
                verify(loggerMock, times(1)).info("Pokemon Detail Personal Id not exist");
                assertEquals(null, result);
            }
        }

        @Nested
        @DisplayName("When occur exception in getPokemonDetailIdPersonal")
        class FailReturnException {
            PokemonDetail result;

            @BeforeEach
            public void mockAndAct() {
                doThrow(exception).when(mongoCollection).find(any(Bson.class));
                result = pokemonPersonalRepository.id(ID);
            }

            @Test
            @DisplayName("then id has a problem")
            void validationExption() {
                assertEquals(null, result);
            }

            @Test
            @DisplayName("Then logs used in getPokemonDetailIdPersonal should be called correctly in case of exception mongoBd")
            void validationLogsGetPokemonDetailIdPersonalExceptionMongoDb() {
                assertEquals(null, result);
                verify(loggerMock, times(1))
                        .info("Exception in get Pokémons Detail Personal Id " + exception);
            }
        }

        @Nested
        @DisplayName("When occur exception in MongoBd of getPokemonDetailIdPersonal")
        class FailReturnMongoException {
            PokemonDetail result;

            @BeforeEach
            public void mockAndAct() {
                doThrow(mongoException).when(mongoCollection).find(any(Bson.class));
                result = pokemonPersonalRepository.id(ID);
            }

            @Test
            @DisplayName("Then logs used in getPokemonDetailIdPersonal should be called correctly in case of exception mongoBd")
            void validationLogsGetPokemonDetailIdPersonalExceptionMongoDb() {
                assertEquals(null, result);
                verify(loggerMock, times(1))
                        .info("Error in get Pokemon Personal Id with MongoBd");
            }
        }

        @Nested
        @DisplayName("When pokemonListPersonal return successfully")
        class ReturnListPersonal {
            PokemonBasicResponsesPokeApi result;

            @BeforeEach
            public void mockAndAct() {
                pokemonDetailMock = new PokemonDetail();
                doReturn(findIterable).when(mongoCollection).find();

            }

            @Test
            @DisplayName("Then return success pokemonListPersonal")
            void validationSuccessPokemonListPersonal() {
                doReturn(pokemonDetailMock).when(findIterable).first();


                doReturn(mongoCursor).when(mongoIterable).iterator();

                doReturn(true, false).when(mongoCursor).hasNext();
                doReturn(pokemonDetailMock).when(mongoCursor).next();

                result = pokemonPersonalRepository.list("model");
                assertEquals(pokemonDetailMock, result);
                verify(loggerMock,times(1)).info("get Pokémons list Personal");
            }
            @Test
            @DisplayName("Then the list is null")
            void validationListNull() {
                doReturn(null).when(findIterable).first();

                result = pokemonPersonalRepository.list("model");

                assertEquals(null, result);
                verify(loggerMock, times(1)).info("These Pokémons doesn't exist");
            }

        }

        @Nested
        @DisplayName("When pokemonPersonalCreate is called")
        class CreatePersonalPokemon {

            @BeforeEach
            public void mockAndAct() {
                pokemonDetailMock = new PokemonDetail();
                mongoException = new MongoException("");
                exception = new RuntimeException();
            }

            @Test
            @DisplayName("Then return create pokemonPersonal")
            void validationSuccessCreatePokemonPersonal() {
                pokemonPersonalRepository.insert(pokemonDetailMock);

                verify(mongoCollection, times(1)).insertOne(any());
                verify(loggerMock, times(1)).info("Pokemon Personal Create Success");
            }

            @Test
            @DisplayName("when an exception occurs in mongo when making an insert")
            void validationExceptionMongoInsert() {

                doThrow(mongoException).when(mongoCollection).insertOne(pokemonDetailMock);

                pokemonPersonalRepository.insert(pokemonDetailMock);

                verify(loggerMock, times(1)).info("Error with MongoBd");
            }

            @Test
            @DisplayName("when an exception occurs in insert")
            void validationExceptionInsert() {
                doThrow(exception).when(mongoCollection).insertOne(any());

                pokemonPersonalRepository.insert(pokemonDetailMock);

                verify(loggerMock, times(1)).info("Error in Insert Pokémons");
            }
        }

        @Nested
        @DisplayName("When delete pokemon personal")
        class DeletePokemonPersonal {
            @Test
            @DisplayName("When the delete is done successfully")
            void validationDeletePokemonPersonalSuccess() {
                long valueCountDocument = 1L;
                doReturn(valueCountDocument).when(mongoCollection).countDocuments(any(Bson.class));

                pokemonPersonalRepository.delete(ID);

                verify(mongoCollection, times(1)).deleteOne(any(Bson.class));
                verify(loggerMock, times(1)).info("deleted pokemon id: 1");
            }
            @Test
            @DisplayName("When the id for delete is incorrect")
            void validationDeletePersonalIncorrectId() {
                pokemonPersonalRepository.delete(ID);

                verify(loggerMock, times(1)).info("Error this id does not exists");
            }
            @Test
            @DisplayName("When occur an exception for delete in the Mongo")
            void validationDeletePokemonPersonalExceptionMongo() {
                mongoException = new MongoException("");
                doThrow(mongoException).when(mongoCollection).countDocuments(any(Bson.class));

                pokemonPersonalRepository.delete(ID);

                verify(loggerMock, times(1)).info("Error with MongoBd");

            }
        }
    }
}
