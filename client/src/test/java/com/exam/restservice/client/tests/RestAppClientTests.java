package com.exam.restservice.client.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@SpringBootTest
class RestAppClientTests {
    Logger logger = LogManager.getLogger(RestAppClientTests.class);

    @Test
    void contextLoads() {
    }

    @Test
    void forkJoin() {
        try {
            ForkJoinPool forkJoinPool = new ForkJoinPool(2);
            List<Integer> iterator = List.of(1, 2, 3, 4, 5);
            var result = forkJoinPool.submit(
                    () -> iterator.parallelStream()
                            .reduce(
                                    0
                                    , (acc, x) -> acc += x
                                    , Integer::sum
                            )
            ).get();
            logger.info(result);
            Assertions.assertEquals(15, result);
        } catch (InterruptedException | ExecutionException e) {
            Assertions.fail(e);
        }

    }

}
