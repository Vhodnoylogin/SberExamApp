package com.exam.restservice.client.tests;

import com.exam.restservice.client.requests.BasicUrlPrepared;
import common.constant.CommonNames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

class RestAppClientTests {
    Logger logger = LogManager.getLogger(RestAppClientTests.class);

    @Test
    void contextLoads() {
    }

    @Test
    void test_forkJoin() {
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
            logger.debug(result);
            Assertions.assertEquals(15, result);
        } catch (InterruptedException | ExecutionException e) {
            Assertions.fail(e);
        }

    }

    @Test
    void test_BasicUrlPrepared() {
        String url = BasicUrlPrepared.preparedURL(CommonNames.URLStorage.URL_AIRPORTS);
        logger.debug(url);
        String url_stand = BasicUrlPrepared.URL_HOST + CommonNames.URLStorage.URL_AIRPORTS;
        Assertions.assertEquals(url, url_stand);
    }
}
