package com.example.pocwebflux.controller;

import com.example.pocwebflux.domain.entity.Poc;
import com.example.pocwebflux.service.PocService;
import com.example.pocwebflux.util.PocCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.blockhound.BlockHound;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
class PocControllerTest {

    @InjectMocks
    private PocController pocController;

    @Mock
    private PocService pocService;

    private final Poc poc = PocCreator.createValidPoc();

    @BeforeAll
    public static void blockHoundSetup() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        BDDMockito.when(pocService.listAll())
                .thenReturn(Flux.just(poc));

        BDDMockito.when(pocService.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.just(poc));

        BDDMockito.when(pocService.save(PocCreator.createPocDTOToBeSaved()))
                .thenReturn(Mono.just(poc));


        BDDMockito.when(pocService.delete(ArgumentMatchers.anyInt()))
                .thenReturn(Mono.empty());

        BDDMockito.when(pocService.update(PocCreator.createValidPocDTO()))
                .thenReturn(Mono.empty());
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
        StepVerifier.create(pocController.listAll())
                .expectSubscription()
                .expectNext(poc)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById return Mono with poc whe it exists")
    void findById_returnMonoPoc_whenSuccessful() {
        StepVerifier.create(pocController.findById(1))
                .expectSubscription()
                .expectNext(poc)
                .verifyComplete();
    }
    @Test
    @DisplayName("save create an poc when successful")
    void save_createPoc_whenSuccessful() {
        StepVerifier.create(pocController.save(PocCreator.createPocDTOToBeSaved()))
                .expectSubscription()
                .expectNext(poc)
                .verifyComplete();
    }

    @Test
    @DisplayName("delete removes the poc when successful")
    void delete_Poc_whenSuccessful() {
        StepVerifier.create(pocController.delete(1))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("update save update poc and returns empty mono when successful")
    void update_SaveUpdatePoc_whenSuccessful() {
        StepVerifier.create(pocController.update(1, PocCreator.createValidPocDTO()))
                .expectSubscription()
                .verifyComplete();
    }
}