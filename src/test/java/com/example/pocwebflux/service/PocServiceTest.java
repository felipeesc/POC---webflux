package com.example.pocwebflux.service;

import com.example.pocwebflux.domain.PocDTO;
import com.example.pocwebflux.domain.entity.Poc;
import com.example.pocwebflux.repository.PocRepository;
import com.example.pocwebflux.util.PocCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
class PocServiceTest {

    @InjectMocks
    private PocService pocService;

    @Mock
    private PocRepository pocRepository;

    private final Poc poc = PocCreator.createValidPoc();

    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        BDDMockito.when(pocRepository.findAll())
                .thenReturn(Flux.just(poc));

        BDDMockito.when(pocRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.just(poc));

        BDDMockito.when(pocRepository.save(PocCreator.createPocToBeSaved()))
                .thenReturn(Mono.just(poc));
    }

    @Test
    void blockHoundWorks() {
        try {
            FutureTask<?> task = new FutureTask<>(() -> {
                Thread.sleep(0);
                return "";
            });
            Schedulers.parallel().schedule(task);

            task.get(10, TimeUnit.SECONDS);
            Assertions.fail("should fail");
        } catch (Exception e) {
            Assertions.assertTrue(e.getCause() instanceof BlockingOperationError);
        }
    }

    @Test
    @DisplayName("findAll returns a flux of poc")
    void findAll_returnFluxOfPoc_whenSuccessful() {
        StepVerifier.create(pocService.listAll())
                .expectSubscription()
                .expectNext(poc)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById return Mono with poc whe it exists")
    void findById_returnMonoPoc_whenSuccessful() {
        StepVerifier.create(pocService.findById(1))
                .expectSubscription()
                .expectNext(poc)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById return Mono error when poc does not exist")
    void findById_returnMonoPoc_whenEmptyMonoIsReturn() {
        BDDMockito.when(pocRepository.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        StepVerifier.create(pocService.findById(1))
                .expectSubscription()
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    @DisplayName("save create an poc when successful")
    void save_createPoc_whenSuccessful() {
        PocDTO pocToBeSaved = PocCreator.createPocDTOToBeSaved();
        StepVerifier.create(pocService.save(pocToBeSaved))
                .expectSubscription()
                .expectNext(poc)
                .verifyComplete();
    }
}