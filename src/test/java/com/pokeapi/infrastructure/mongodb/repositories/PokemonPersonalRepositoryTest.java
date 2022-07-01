package com.pokeapi.infrastructure.mongodb.repositories;

import com.mongodb.MongoException;
import com.mongodb.client.*;

import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;
import static com.pokeapi.TestUtil.getLoggerMock;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
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
    private MongoCollection mongoCollection;
    @Mock
    private MongoDatabase mongoDatabase;
    @Mock
    private FindIterable findIterable;
    @Mock
    private DeleteResult deleteResult;
    @Mock
    private UpdateResult updateResult;
    @InjectMocks
    private PokemonPersonalRepository pokemonPersonalRepository;
    private final String ID = "1";
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
    class PokemonRepositoryTest {

        @Nested
        @DisplayName("When PokemonPersonalRepository id is called")
        class IdTest {
            PokemonDetail result;
            @BeforeEach
            public void mockAndAct() {
                pokemonDetailMock = new PokemonDetail();
                doReturn(findIterable).when(mongoCollection).find(eq("_id", ID));
                doReturn(pokemonDetailMock).when(findIterable).first();

                result = pokemonPersonalRepository.id(ID);
            }
            @Test
            @DisplayName("Then return success")
            void validateReturnGetIdTest() {
                assertEquals(pokemonDetailMock, result);
            }
            @Test
            @DisplayName("Then return log success")
            void validateGetIdLogTest() {
                verify(loggerMock, times(1)).info("Get Pokémon Detail Personal Id Success");
            }
            @Test
            @DisplayName("Then id called verify collection find")
            void validateCollectionfindTest() {
                verify(mongoCollection, times(1)).find(eq("_id", ID));
            }
            @Test
            @DisplayName("Then id called verify iterable first")
            void validateIterableFirstTest() {
                verify(findIterable, times(1)).first();
            }
        }

        @Nested
        @DisplayName("When id is called and id does not exist")
        class FailReturnIdProblemTest {
            PokemonDetail result;

            @BeforeEach
            public void mockAndAct() {
                pokemonDetailMock = new PokemonDetail();
                doReturn(findIterable).when(mongoCollection).find(eq("_id", ID));
                doReturn(null).when(findIterable).first();

                result = pokemonPersonalRepository.id(ID);
            }
            @Test
            @DisplayName("Then return is null")
            void validationErrorIdTest() {
                assertNull(result);
            }
            @Test
            @DisplayName("Then return is null verify log")
            void validationErrorIdLogTest() {
                verify(loggerMock, times(1)).info("Pokemon Detail Personal Id not exist");
            }
        }

        @Nested
        @DisplayName("When id is called and occur exception")
        class FailReturnExceptionTest {
            PokemonDetail result;
            PokemonDetail pokemonDetail = new PokemonDetail();

            @BeforeEach
            public void mockAndAct() {
                doThrow(new RuntimeException()).when(mongoCollection).find(any(Bson.class));

                result = pokemonPersonalRepository.id(ID);
            }
            @Test
            @DisplayName("Then return is null")
            void validationExptionTest() {
                assertEquals(pokemonDetail, result);
            }
            @Test
            @DisplayName("Then return is null verify log")
            void validationLogExceptionTest() {
                verify(loggerMock, times(1))
                        .info("Exception in get Pokémon Detail Personal Id ");
            }
        }

        @Nested
        @DisplayName("When id is called and occur exception MongoDb")
        class FailReturnMongoExceptionTest {
            PokemonDetail result;
            PokemonDetail pokemonDetail = new PokemonDetail();

            @BeforeEach
            public void mockAndAct() {
                doThrow(new MongoException("Error mongo")).when(mongoCollection).find(any(Bson.class));

                result = pokemonPersonalRepository.id(ID);
            }
            @Test
            @DisplayName("Then return is null")
            void validationExceptionMongoTest() {
                assertEquals(pokemonDetail, result);
            }
            @Test
            @DisplayName("Then return is null verify log")
            void validationExceptionMongoLogTest() {
                verify(loggerMock, times(1))
                        .info("Error in get Pokemon Personal Id with MongoBd");
            }
        }

        @Nested
        @DisplayName("When pokemonPersonal list is called")
        class ListTest {
            List<PokemonDetail> result;
            PokemonDetail pokemonDetailMock = new PokemonDetail();
            List<PokemonDetail> pokemonDetailTest = new ArrayList<>();
            @Mock
            private FindIterable findIterable2;

            @BeforeEach
            public void mockAndAct() {
                pokemonDetailTest.add(pokemonDetailMock);
                Bson projectionMock = Projections.fields(Projections.include("name"));
                doReturn(findIterable).when(mongoCollection).find();
                doReturn(findIterable2).when(findIterable).projection(projectionMock);
                doReturn(mongoCursor).when(findIterable2).iterator();
                doReturn(true,  false).when(mongoCursor).hasNext();
                doReturn(pokemonDetailMock).when(mongoCursor).next();

                result = pokemonPersonalRepository.list();
            }
            @Test
            @DisplayName("Then return success for list")
            void validationSuccessListTest() {
                assertEquals(pokemonDetailTest, result);
            }
            @Test
            @DisplayName("Then return log success")
            void validationSuccessLogTest() {
                verify(loggerMock,times(1)).info("Get pokemon list personal success");
            }
        }
        @Nested
        @DisplayName("When list is called and return is empty")
        class ListEmptyTest {
            List<PokemonDetail> result;
            List<PokemonDetail> pokemonDetailTest = new ArrayList<>();
            @Mock
            private FindIterable findIterable2;
            @Test
            @DisplayName("Then return is empty")
            void validationListEmpty() {
                Bson projectionMock = Projections.fields(Projections.include("name"));
                doReturn(findIterable).when(mongoCollection).find();
                doReturn(findIterable2).when(findIterable).projection(projectionMock);
                doReturn(mongoCursor).when(findIterable2).iterator();
                doReturn( true, false).when(mongoCursor).hasNext();
                doReturn(empty()).when(mongoCursor).next();

                result = pokemonPersonalRepository.list();

                assertEquals(pokemonDetailTest, result);
            }
        }
        @Nested
        @DisplayName("When list is called and occur exception")
        class ListExceptionMongoTest {

            @Test
            @DisplayName("Then return exceptionMongo")
            void validationListExceptionMongoTest() {
                doThrow(new MongoException("Error mongo")).when(mongoCollection).find();

                pokemonPersonalRepository.list();

                verify(loggerMock, times(1)).info("Error with MongoBd");
            }
            @Test
            @DisplayName("Then return exception")
            void validationListExceptionTest() {
                doThrow(new RuntimeException()).when(mongoCollection).find();

                pokemonPersonalRepository.list();

                verify(loggerMock, times(1)).info("error in get Pokémons personal list");
            }
        }

        @Nested
        @DisplayName("When insert is called")
        class InsetTest {
           PokemonDetail pokemonDetailMock = new PokemonDetail();

            @Test
            @DisplayName("Then insert return success")
            void validationInsertSuccessTest() {
                pokemonPersonalRepository.insert(pokemonDetailMock);

                verify(mongoCollection, times(1)).insertOne(pokemonDetailMock);
            }
            @Test
            @DisplayName("Then insert return success log")
            void validationInsertSuccessLogTest() {
                pokemonPersonalRepository.insert(pokemonDetailMock);

                verify(loggerMock, times(1)).info("Pokemon Personal Create Success");
            }
            @Test
            @DisplayName("Then occur MongoException in insert")
            void validationExceptionMongoInsertTest() {
                doThrow(new MongoException("Error mongo")).when(mongoCollection).insertOne(pokemonDetailMock);

                pokemonPersonalRepository.insert(pokemonDetailMock);

                verify(loggerMock, times(1)).info("Error with MongoBd");
            }
            @Test
            @DisplayName("when an exception occurs in insert")
            void validationExceptionInsertTest() {
                doThrow(new RuntimeException()).when(mongoCollection).insertOne(any());

                pokemonPersonalRepository.insert(pokemonDetailMock);

                verify(loggerMock, times(1)).info("Error in Insert Pokémons");
            }
        }

        @Nested
        @DisplayName("When delete is called")
        class DeleteTest {
            long returnColletion = 1L;
            long emptyReturnColletion = 0L;
            @Test
            @DisplayName("Then the delete is done successfully")
            void validationDeleteTest() {
                doReturn(deleteResult).when(mongoCollection).deleteOne(any(Bson.class));
                doReturn(returnColletion).when(deleteResult).getDeletedCount();

                pokemonPersonalRepository.delete(ID);

                verify(mongoCollection, times(1)).deleteOne(any(Bson.class));
            }
            @Test
            @DisplayName("Then the delete is done successfully log")
            void validationDeleteLogTest() {
                doReturn(deleteResult).when(mongoCollection).deleteOne(any(Bson.class));
                doReturn(returnColletion).when(deleteResult).getDeletedCount();

                pokemonPersonalRepository.delete(ID);

                verify(loggerMock, times(1)).info("deleted pokemon id: 1");
            }
            @Test
            @DisplayName("Then the id for delete is incorrect")
            void validationDeleteIncorrectIdTest() {
                doReturn(deleteResult).when(mongoCollection).deleteOne(any(Bson.class));
                doReturn(emptyReturnColletion).when(deleteResult).getDeletedCount();

                pokemonPersonalRepository.delete(ID);

                verify(loggerMock, times(1)).info("Error this id does not exists");
            }
            @Test
            @DisplayName("Then occur an exception for delete in the Mongo")
            void validationDeleteExceptionMongoTest() {
                doThrow(new MongoException("Error mongo")).when(mongoCollection).deleteOne(any(Bson.class));

                pokemonPersonalRepository.delete(ID);

                verify(loggerMock, times(1)).info("Error with MongoBd");
            }
            @Test
            @DisplayName("Then occur an exception for delete")
            void validationDeleteExceptionTest() {
                doThrow(new RuntimeException()).when(mongoCollection).deleteOne(any(Bson.class));

                pokemonPersonalRepository.delete(ID);

                verify(loggerMock, times(1)).info("Error in delete one Pokémon");
            }
        }
        @Nested
        @DisplayName("When insert is called")
        class UpdateTest {
            PokemonDetail pokemonDetail = new PokemonDetail();
            Document command;
            long valueQuery = 1L;
            @BeforeEach
            public void mockAndAct() {
                command = new Document("$set", pokemonDetail);

                doReturn(updateResult).when(mongoCollection).updateOne(eq("_id", ID), command);
                doReturn(valueQuery).when(updateResult).getMatchedCount();

                pokemonPersonalRepository.update(ID, pokemonDetail);

            }
            @Test
            @DisplayName("Then the insert is done successfully")
            void validationInsertTest() {
                verify(mongoCollection, times(1)).updateOne(eq("_id", ID), command);
            }
            @Test
            @DisplayName("Then the insert is done successfully Log")
            void validationInsertLogTest() {
                verify(loggerMock, times(1)).info("Pokémon has been updated");
            }
        }
        @Nested
        @DisplayName("When insert is called and id does exist")
        class UpdateIdNonExistTest {
            PokemonDetail pokemonDetail = new PokemonDetail();
            long valueQuery = 0L;
            Document command;
            @Test
            @DisplayName("Then return log error")
            void validationInsertLogErrorTest() {
                command = new Document("$set", pokemonDetail);

                doReturn(updateResult).when(mongoCollection).updateOne(eq("_id", ID), command);
                doReturn(valueQuery).when(updateResult).getMatchedCount();

                pokemonPersonalRepository.update(ID, pokemonDetail);

                verify(loggerMock, times(1)).info("Error update, this id does not exists");
            }
            @Test
            @DisplayName("Then return MongoException")
            void validationInsertMongoExceptionTest() {
                command = new Document("$set", pokemonDetail);
                doThrow(new MongoException("MongoException")).when(mongoCollection).updateOne(eq("_id", ID), command);

                pokemonPersonalRepository.update(ID, pokemonDetail);

                verify(loggerMock, times(1)).info("Error with MongoBd");
            }
            @Test
            @DisplayName("Then return exception")
            void validationInsertExceptionTest() {
                command = new Document("$set", pokemonDetail);
                doThrow(new RuntimeException()).when(mongoCollection).updateOne(eq("_id", ID), command);

                pokemonPersonalRepository.update(ID, pokemonDetail);

                verify(loggerMock, times(1)).info("Error in update Pokémons");
            }
        }

    }
}
