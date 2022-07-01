package com.pokeapi.service;

import com.pokeapi.domain.entities.*;
import com.pokeapi.infrastructure.gateway.PokemonOficialBasicService;
import com.pokeapi.infrastructure.mongodb.repositories.PokemonPersonalRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.pokeapi.TestUtil.getLoggerMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(PokemonFullContractService.class)
class PokemonFullContractBasicServiceTest {
    @InjectMocks
    PokemonFullContractService pokemonFullContractService;
    @Mock
    private PokemonOficialBasicService pokemonOfficialBasicService;
    @Mock
    private PokemonPersonalRepository pokemonPersonalRepository;
    @Mock
    private PokemonBasicResponsesPoke pokemonBasicResponsesPoke;
    @Mock
    private PokemonFullContractList pokemonFullContractList;
    private final RuntimeException runtimeException = new RuntimeException();
    private Logger loggerMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        pokemonFullContractService = new PokemonFullContractService(
                pokemonOfficialBasicService,
            pokemonPersonalRepository);
        }

    @BeforeEach
    void mock() throws Exception {
        loggerMock = getLoggerMock(PokemonFullContractService.class);
    }

    @Nested
    @DisplayName("Given PokemonFullContractService")
    class PokemonServiceTest {
        @Nested
        @DisplayName("When check the returns getCount offSet small")
        class GetOfficialOffsetSmallTest {
            PokemonBasicResponse pokemonBasicResponseMock = new PokemonBasicResponse();
            PokemonFullContractList result;
            String name = "nameMock";
            PokemonFullContractList pokemonFullContractListTest = new PokemonFullContractList();
            List<PokemonBasicResponse> pokemonBasicResponseList = new ArrayList<>();
            PokemonFullContractBasic pokemonFullContractBasicTest = new PokemonFullContractBasic();
            List<PokemonFullContractBasic> pokemonFullContractBasicListTest = new ArrayList<>();

            @BeforeEach
            public void mockAndAct() {
                pokemonBasicResponseMock.setName(name);
                pokemonBasicResponseList.add(pokemonBasicResponseMock);

                pokemonFullContractBasicTest.setName("nameMock");
                pokemonFullContractBasicTest.setUrlImage("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png");
                pokemonFullContractBasicTest.setModel("official");
                pokemonFullContractBasicTest.setId("1");

                pokemonFullContractBasicListTest.add(pokemonFullContractBasicTest);

                pokemonFullContractListTest.setResults(pokemonFullContractBasicListTest);

                doReturn(pokemonBasicResponsesPoke).when(pokemonOfficialBasicService).officialList();
                doReturn(1).when(pokemonBasicResponsesPoke).getCount();
                doReturn(pokemonBasicResponseList).when(pokemonBasicResponsesPoke).getResults();

                result = pokemonFullContractService.getPokemonOfficial();
            }

            @Test
            @DisplayName("Then answer success for getOfficial getResults")
            void valadationGetOfficialResultsTest() {
               assertEquals(pokemonFullContractListTest, result);
            }
            @Test
            @DisplayName("Then check call log info")
            void validationLogSuccessReturnTest() {
                verify(loggerMock, times(1)).info("Get pokemon Official is Success");
            }
            @Test
            @DisplayName("Then check call pokemonOfficialBasicService")
            void validationServiceReturnTest() {
                verify(pokemonOfficialBasicService, times(1)).officialList();
            }
        }

        @Nested
        @DisplayName("When check the returns getCount offSet Big")
        class GetOfficialOffsetLargerTest {
            PokemonFullContractList pokemonFullContractListTest = new PokemonFullContractList();
            List<PokemonFullContractBasic> pokemonFullContractBasicListTest = new ArrayList<>();
            PokemonFullContractBasic pokemonFullContractBasicTest;
            PokemonBasicResponse pokemonBasicResponseMock = new PokemonBasicResponse();
            List<PokemonBasicResponse> pokemonBasicResponseList = new ArrayList<>();
            PokemonFullContractList result;
            String name = "nameMock";
            Integer countOfficial = 899;

            @BeforeEach
            public void mockAndAct() {
                for (int index = 0; index < countOfficial; index++) {
                    pokemonBasicResponseMock.setName(name);
                    pokemonBasicResponseList.add(pokemonBasicResponseMock);
                    pokemonFullContractBasicTest = new PokemonFullContractBasic();
                    pokemonFullContractBasicTest.setName(name);
                    pokemonFullContractBasicTest.setModel("official");
                    if(index <= 897){
                        pokemonFullContractBasicTest.setId(String.valueOf(index + 1));
                        pokemonFullContractBasicTest.setUrlImage("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id}.png".
                                replace(("{id}"), Integer.toString(index + 1)));
                    }else {
                        pokemonFullContractBasicTest.setId(String.valueOf(((10000 - 897)+index)));
                        pokemonFullContractBasicTest.setUrlImage("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/{id}.png".
                                replace("{id}",
                                Integer.toString((10000 - 897) + index)));
                    }pokemonFullContractBasicListTest.add(pokemonFullContractBasicTest);
                }pokemonFullContractListTest.setResults(pokemonFullContractBasicListTest);

                doReturn(pokemonBasicResponsesPoke).when(pokemonOfficialBasicService).officialList();
                doReturn(countOfficial).when(pokemonBasicResponsesPoke).getCount();
                doReturn(pokemonBasicResponseList).when(pokemonBasicResponsesPoke).getResults();
            }
            @Test
            @DisplayName("Then answer success for get official and index > offset small")
            void validationGetResultsOfficialOffsetBigTest() {
                result = pokemonFullContractService.getPokemonOfficial();

                assertEquals(pokemonFullContractListTest, result);
            }
        }
        @Nested
        @DisplayName("When check the exceptions pokemonGetOfficial")
        class GetOfficialExceptionTest {
            PokemonFullContractList result;
            PokemonFullContractList pokemonFullContractList = new PokemonFullContractList();
            @Test
            @DisplayName("When an excess occurs")
            void validationExceptionTest() {
                doThrow(runtimeException).when(pokemonOfficialBasicService).officialList();

                result = pokemonFullContractService.getPokemonOfficial();

                assertEquals(pokemonFullContractList, result);
            }

            @Test
            @DisplayName("When Then check call log info exception")
            void validationLogExceptionTest() {
                doThrow(runtimeException).when(pokemonOfficialBasicService).officialList();

                pokemonFullContractService.getPokemonOfficial();

                verify(loggerMock, times(1)).info("Error Service getPokemonOfficial");
            }
        }

        @Nested
        @DisplayName("When getPokemonPersonal is called")
        class GetPersonalTest {
            PokemonFullContractList pokemonFullContractListTest = new PokemonFullContractList();
            List<PokemonFullContractBasic> pokemonFullContractBasicListTest = new ArrayList<>();
            PokemonFullContractBasic pokemonFullContractBasicTest = new PokemonFullContractBasic();
            List<PokemonDetail> pokemonDetails = new ArrayList<>();
            PokemonDetail pokemonDetail = new PokemonDetail();
            PokemonFullContractList result;

            @BeforeEach
            public void mockAndAct() {
                pokemonFullContractBasicTest.setModel("personal");
                pokemonFullContractBasicTest.setUrlImage("https://rickandmortyapi.com/api/character/avatar/1.jpeg");
                pokemonFullContractBasicListTest.add(pokemonFullContractBasicTest);
                pokemonFullContractListTest.setResults(pokemonFullContractBasicListTest);

                pokemonDetails.add(pokemonDetail);

                doReturn(pokemonDetails).when(pokemonPersonalRepository).list();


                result = pokemonFullContractService.getPokemonPersonal();
            }

            @Test
            @DisplayName("Then answer success for get personal")
            void validationSuccessGetResultsPersonalTest() {
                assertEquals(pokemonFullContractListTest, result);

            }

            @Test
            @DisplayName("Then check the pokemonRepository.list")
            void validationCallRepositoryTest() {
                verify(pokemonPersonalRepository, times(1)).list();
            }
        }
        @Nested
        @DisplayName("When get Personal is called and gets an exception")
        class getPersonalExceptionTest {
            PokemonFullContractList pokemonFullContractListTest = new PokemonFullContractList();
            PokemonFullContractList result;
            @Test
            @DisplayName("Then an excess occurs")
            void validationExceptionTest() {
                doThrow(runtimeException).when(pokemonPersonalRepository).list();

                result = pokemonFullContractService.getPokemonPersonal();

                assertEquals(pokemonFullContractListTest, result);
            }
        }

        @Nested
        @DisplayName("When getPokemonAll is called")
        class GetAllTest {
            @Mock
            PokemonFullContractList pokemonFullContractList2 = new PokemonFullContractList();
            PokemonFullContractService pokemonFullContractServiceForSpy = new PokemonFullContractService(pokemonOfficialBasicService, pokemonPersonalRepository);
            PokemonFullContractService pokemonSpyService = Mockito.spy(pokemonFullContractServiceForSpy);
            List<PokemonFullContractBasic> pokemonFullContractBasicMock = new ArrayList<>();
            List<PokemonFullContractBasic> pokemonFullContractBasicList = new ArrayList<>();
            List<PokemonFullContractBasic> pokemonFullContractBasicList2 = new ArrayList<>();
            PokemonFullContractList pokemonFullContractListTest = new PokemonFullContractList();
            PokemonFullContractList result;

            @BeforeEach
            void mockAndAct() {
                pokemonFullContractList.setResults(pokemonFullContractBasicMock);
                pokemonFullContractList2.setResults(pokemonFullContractBasicMock);
                pokemonFullContractListTest.setResults(pokemonFullContractBasicMock);

                doReturn(pokemonFullContractList).when(pokemonSpyService).getPokemonOfficial();
                doReturn(pokemonFullContractBasicList).when(pokemonFullContractList).getResults();
                doReturn(pokemonFullContractList2).when(pokemonSpyService).getPokemonPersonal();
                doReturn(pokemonFullContractBasicList2).when(pokemonFullContractList2).getResults();

                result = pokemonSpyService.getPokemonAll();
            }
            @Test
            @DisplayName("Then check the results")
            void validationSetResultsGetAllTest() {
                assertEquals(pokemonFullContractListTest.getResults(), result.getResults());
            }

            @Test
            @DisplayName("Then verify call service Official")
            void validationCallServiceOfficialTest() {
                verify(pokemonSpyService, times(1)).getPokemonOfficial();
            }

            @Test
            @DisplayName("Then verify call service Personal")
            void validationCallServicePersonalTest() {
                verify(pokemonSpyService, times(1)).getPokemonPersonal();
            }

            @Test
            @DisplayName("Then validation log successfully")
            void validationLogSuccessGetAllTest() {
                verify(loggerMock, times(1)).info("Service getPokemonAll Success");
            }
        }
        @Nested
        @DisplayName("When getPokemonAll is called exception ")
        class GetAllExcepetionTest {
            PokemonFullContractService pokemonFullContractServiceForSpy = new PokemonFullContractService(pokemonOfficialBasicService, pokemonPersonalRepository);
            PokemonFullContractService pokemonSpyService = Mockito.spy(pokemonFullContractServiceForSpy);
            PokemonFullContractList result;
            @Test
            @DisplayName("Then occur exception getAll verify calls")
            void validationExceptionGetOfficialTest() {
                doThrow(new RuntimeException()).when(pokemonSpyService).getPokemonOfficial();

                result = pokemonSpyService.getPokemonAll();

                verify(loggerMock, times(1)).info("Error Service getPokemonAll");
            }
            @Test
            @DisplayName("Then occur exception getAll")
            void validationExceptionGetAllTest() {
                PokemonFullContractList pokemonFullContractListTest = new PokemonFullContractList();
                doThrow(runtimeException).when(pokemonSpyService).getPokemonOfficial();

                result = pokemonSpyService.getPokemonAll();

                assertEquals(pokemonFullContractListTest, result);
            }
        }
        @Nested
        @DisplayName("When pokemonGetValidationCalled")
        class ValidationGetModelTest {
            PokemonFullContractList result;
            PokemonFullContractService pokemonFullContractServiceForSpy = new PokemonFullContractService(pokemonOfficialBasicService, pokemonPersonalRepository);
            PokemonFullContractService pokemonSpyService = Mockito.spy(pokemonFullContractServiceForSpy);
            @Test
            @DisplayName("Then pokemonModel personal is called")
            void validationGetPersonalTest() {
                String model = "personal";
                doReturn(pokemonFullContractList).when(pokemonSpyService).getPokemonAll();

                result = pokemonSpyService.pokemonGetValidation(model);

                assertEquals(pokemonSpyService.getPokemonPersonal(), result);
            }
            @Test
            @DisplayName("Then pokemonModel personal is called verify")
            void validationCallPersonalTest() {
                String model = "personal";
                doReturn(pokemonFullContractList).when(pokemonSpyService).getPokemonAll();

                result = pokemonSpyService.pokemonGetValidation(model);

                verify(pokemonSpyService, times(1)).getPokemonPersonal();
            }
            @Test
            @DisplayName("Then pokemonModel official is called")
            void validationGetOfficialTest() {
                String model = "official";
                doReturn(pokemonFullContractList).when(pokemonSpyService).getPokemonAll();

                result = pokemonSpyService.pokemonGetValidation(model);

                assertEquals(pokemonSpyService.getPokemonOfficial(), result);
            }
            @Test
            @DisplayName("Then pokemonModel official is called verify")
            void validationCallOfficialTest() {
                String model = "official";
                doReturn(pokemonFullContractList).when(pokemonSpyService).getPokemonAll();

                result = pokemonSpyService.pokemonGetValidation(model);

                verify(pokemonSpyService, times(1)).getPokemonOfficial();
            }
            @Test
            @DisplayName("Then pokemonModel all is called")
            void validationGetAllTest() {
                String model = "all";
                doReturn(pokemonFullContractList).when(pokemonSpyService).getPokemonAll();

                result = pokemonSpyService.pokemonGetValidation(model);

                assertEquals(pokemonSpyService.getPokemonAll(), result);
            }
            @Test
            @DisplayName("Then pokemonModel all is called verify")
            void validationCallAllTest() {
                String model = "all";
                doReturn(pokemonFullContractList).when(pokemonSpyService).getPokemonAll();

                result = pokemonSpyService.pokemonGetValidation(model);

                verify(pokemonSpyService, times(1)).getPokemonAll();
            }
            @Test
            @DisplayName("Then pokemonModel have invalid model")
            void validationInvalidModelTest() {
                String model = "";
                doReturn(pokemonFullContractList).when(pokemonSpyService).getPokemonAll();

                result = pokemonSpyService.pokemonGetValidation(model);

                assertNull(result);
                verify(pokemonSpyService, times(0)).getPokemonAll();
                verify(pokemonSpyService, times(0)).getPokemonPersonal();
                verify(pokemonSpyService, times(0)).getPokemonOfficial();
            }
            @Test
            @DisplayName("Then occur exception in called pokemonGetValidation")
            void validationExceptionTest() {
                doThrow(runtimeException).when(pokemonSpyService).getPokemonAll();

                result = pokemonSpyService.pokemonGetValidation(any());

                verify(loggerMock, times(1)).info("Error Service pokemonGetValidation");
            }
        }
    }
}