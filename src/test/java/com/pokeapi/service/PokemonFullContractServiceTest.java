package com.pokeapi.service;

import com.pokeapi.domain.entities.PokemonBasicResponse;
import com.pokeapi.domain.entities.PokemonBasicResponsesPokeApi;
import com.pokeapi.domain.entities.PokemonFullContract;
import com.pokeapi.domain.entities.PokemonFullContracts;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@TestHTTPEndpoint(PokemonFullContractService.class)
class PokemonFullContractServiceTest {
    @InjectMocks
    PokemonFullContractService pokemonFullContractService;
    @Mock
    private PokemonOficialBasicService pokemonOfficialBasicService;
    @Mock
    private PokemonPersonalRepository pokemonPersonalRepository;
    @Mock
    private PokemonBasicResponsesPokeApi pokemonBasicResponsesPokeApi;
    @Mock
    private List<PokemonBasicResponse> pokemonBasicResponseList;
    @Mock
    private PokemonFullContracts pokemonFullContracts;
    @Mock
    private List<PokemonFullContract> pokemonFullContractList;
    private RuntimeException exception = new RuntimeException();
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
        @DisplayName("When the getPersonal is called")
        class GetOfficialTest {
            PokemonBasicResponse pokemonBasicResponseMock = new PokemonBasicResponse();
            PokemonFullContracts result;

            @BeforeEach
            public void mockAndAct() {
                pokemonBasicResponseList = new ArrayList<>();
                pokemonBasicResponseMock.setName("nameMock");
                pokemonBasicResponseList.add(pokemonBasicResponseMock);

                doReturn(pokemonBasicResponsesPokeApi).when(pokemonOfficialBasicService).officialList();
                doReturn(1).when(pokemonBasicResponsesPokeApi).getCount();
                doReturn(pokemonBasicResponseList).when(pokemonBasicResponsesPokeApi).getResults();

                result = pokemonFullContractService.getPokemonOfficial();
            }
            @Test
            @DisplayName("Then check the so called pokemonOfficialBasicService OfficialList")
            void validationCallOfficialListTest() {
                verify(pokemonOfficialBasicService, times(1)).officialList();
            }
            @Test
            @DisplayName("Then check the so called getCount")
            void validationCallGetCountTest() {
                verify(pokemonBasicResponsesPokeApi,times(1)).getCount();
            }
            @Test
            @DisplayName("Then check the so called getResults")
            void validationCallGetResults() {
                verify(pokemonBasicResponsesPokeApi, times(1)).getResults();
            }
//            @Test
//            @DisplayName("Then check the so called")
//            void

            @Test
            @DisplayName("Then answer success for get official and index > offset small")
            void validationGetOfficialOffsetBigTest() {
                Integer countOfficial = 899;
                for(int index = 0; index < countOfficial; index ++){
                    pokemonBasicResponseMock.setName("nameMock"+index);
                    pokemonBasicResponseList.add(pokemonBasicResponseMock);
                }
                doReturn(pokemonBasicResponsesPokeApi).when(pokemonOfficialBasicService).officialList();

                doReturn(countOfficial).when(pokemonBasicResponsesPokeApi).getCount();
                doReturn(pokemonBasicResponseList).when(pokemonBasicResponsesPokeApi).getResults();

                pokemonFullContractService.getPokemonOfficial();

                verify(loggerMock, times(1)).info("Get pokemon Official is Success");
                assertNotNull(pokemonFullContractService.getPokemonOfficial());
            }
            @Test
            @DisplayName("When an excess occurs")
            void validationException(){
                doThrow(exception).when(pokemonOfficialBasicService).officialList();

                pokemonFullContractService.getPokemonOfficial();

                verify(loggerMock, times(1)).info("Error Service getPokemonOfficial");
                assertEquals(null, pokemonFullContractService.getPokemonOfficial());
            }
        }

        @Nested
        @DisplayName("When the get is personal")
        class GetPersonal{
            PokemonBasicResponse pokemonBasicResponseMock = new PokemonBasicResponse();
            PokemonFullContracts pokemonFullContractsTest = new PokemonFullContracts();
            String model = "modelMock";
            PokemonFullContracts result;
            @BeforeEach
            void mockAndAct() {
                pokemonBasicResponseList = new ArrayList<>();
                pokemonBasicResponseMock.setName("nameMock");
                pokemonBasicResponseList.add(pokemonBasicResponseMock);
            }
            @Test
            @DisplayName("Then answer success for get personal")
            void validationSuccessGetPersonalTest(){
                doReturn(pokemonBasicResponsesPokeApi).when(pokemonPersonalRepository).list(model);
                doReturn(1).when(pokemonBasicResponsesPokeApi).getCount();
                doReturn(pokemonBasicResponseList).when(pokemonBasicResponsesPokeApi).getResults();

                result = pokemonFullContractService.getPokemonPersonal(model);

               pokemonFullContractsTest.setCount(1);

               verify(loggerMock, times(1)).info("Get pokemon Official is Success");
               assertEquals(pokemonFullContractsTest.getCount(),result.getCount());
            }
            @Test
            @DisplayName("Then an excess occurs")
            void validationExceptionTest(){
                doThrow(exception).when(pokemonPersonalRepository).list(model);

                pokemonFullContractService.getPokemonPersonal(model);

                verify(loggerMock, times(1)).info("Error Service getPokemonPersonal");
                assertEquals(null, pokemonFullContractService.getPokemonOfficial());
            }
        }
        @Nested
        @DisplayName("When getPokemonAll is called")
        class GetAllTest {
            @Mock
            PokemonFullContracts pokemonFullContracts2 = new PokemonFullContracts();
            PokemonFullContractService pokemonFullContractServiceForSpy  = new PokemonFullContractService(pokemonOfficialBasicService, pokemonPersonalRepository);
            PokemonFullContractService pokemonSpyService = Mockito.spy(pokemonFullContractServiceForSpy);
            PokemonFullContract pokemonFullOff = new PokemonFullContract();
            PokemonFullContract pokemonFullPes = new PokemonFullContract();
            PokemonFullContracts result;
            String model = "modelMock";

            @BeforeEach
            void mockAndAct() {
                pokemonFullContractList = new ArrayList<>();
                pokemonFullOff.setName("nameOfficial");
                pokemonFullPes.setName("namePessoal");
                pokemonFullContractList.add(pokemonFullOff);
                pokemonFullContractList.add(pokemonFullPes);

                doReturn(pokemonFullContracts).when(pokemonSpyService).getPokemonOfficial();
                doReturn(1).when(pokemonFullContracts).getCount();
                doReturn(pokemonFullContracts2).when(pokemonSpyService).getPokemonPersonal(model);
                doReturn(2).when(pokemonFullContracts2).getCount();
                doReturn(pokemonFullContractList).when(pokemonFullContracts).getResults();
                doReturn(pokemonFullContractList).when(pokemonFullContracts2).getResults();

                result = pokemonSpyService.getPokemonAll(model);
            }
            @Test
            @DisplayName("Then check the counter")
            void validationSetCountGetAllTest(){
                PokemonFullContracts pokemonFullContractsTest = new PokemonFullContracts();
                pokemonFullContractsTest.setCount(3);
                assertEquals(pokemonFullContractsTest.getCount(), result.getCount());
            }
            @Test
            @DisplayName("Then check the results")
            void validationSetResultsGetAllTest(){
                List<PokemonFullContract> pokemonFullContractTest = new ArrayList<>();
                pokemonFullContractTest.add(pokemonFullOff);
                pokemonFullContractTest.add(pokemonFullPes);
                pokemonFullContractTest.add(pokemonFullOff);
                pokemonFullContractTest.add(pokemonFullPes);

                PokemonFullContracts pokemonFullContractsTest = new PokemonFullContracts();
                pokemonFullContractsTest.setResults(pokemonFullContractTest);

                assertEquals(pokemonFullContractsTest.getResults(), result.getResults());
            }
            @Test
            @DisplayName("Then verify call getCount official")
            void validationCallGetCountOfficialTest(){
                verify(pokemonSpyService.getPokemonOfficial(), times(1)).getCount();
            }
            @Test
            @DisplayName("Then verify call getCount personal")
            void validationCallGetCountPersonalTest(){
                verify(pokemonSpyService.getPokemonPersonal(model), times(1)).getCount();
            }
            @Test
            @DisplayName("Then verify call getResults official")
            void validationCallGetResultsOfficialTest(){
                verify(pokemonSpyService.getPokemonOfficial(), times(1)).getResults();
            }
            @Test
            @DisplayName("Then verify call getResults personal")
            void validationCallGetResultsPersonalTest(){
                verify(pokemonSpyService.getPokemonPersonal(model), times(1)).getResults();
            }
        }
    }
}