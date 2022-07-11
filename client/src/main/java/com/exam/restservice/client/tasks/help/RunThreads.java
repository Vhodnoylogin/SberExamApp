package com.exam.restservice.client.tasks.help;

import com.exam.restservice.client.requests.CostbILRequestSender;
import com.exam.restservice.client.requests.RequestSender;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RunThreads {
    protected static int NUM_OF_EXECUTORS = 4;

    public static void run(
            Logger logger
            , String url
            , Map<String, ?> constParams
            , BiFunction<RequestSender<String>, Map<String, ?>, Map<String, Boolean>> func
            , String key
            , List<Object> iterator
    ) {
        RequestSender<String> req = CostbILRequestSender.builderCostbILRequestSender()
                .setUrl(url)
                .build();
        Function<Map<String, ?>, Map<String, Boolean>> funnc = (p) -> func.apply(req, p);

        ForkJoinPool forkJoinPool = new ForkJoinPool(NUM_OF_EXECUTORS);
        try {
            int countSucceed = forkJoinPool.submit(
                    () -> iterator.parallelStream()
                            .map(x -> new HashMap<String, Object>() {{
                                put(key, x);
                            }})
                            .peek(x -> x.putAll(constParams))
                            .map(funnc)
                            .map(x -> x.entrySet().stream()
                                    .reduce(
                                            true
                                            , (acc, val) -> acc & val.getValue()
                                            , (v1, v2) -> v1 & v2
                                    )
                            )
                            .reduce(
                                    0
                                    , (acc, x) -> acc += (x ? 1 : 0)
                                    , Integer::sum
                            )
            ).get();
            logger.info("Success task number = " + countSucceed);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e);
        }
    }

    public static void old() {
////        ExecutorService pool = Executors.newFixedThreadPool(2);
////        List<Future<Boolean>> futures = LongStream.rangeClosed(1, n).boxed()
////                .map(
////                        (x) -> (Callable<Boolean>) () -> runningPart(req, x, Thread.currentThread().getName())
////                )
////                .map(pool::submit)
////                .collect(Collectors.toList());
////
////        AtomicInteger countSucceed = new AtomicInteger();
////        while (!futures.isEmpty()) {
////            try {
////                Thread.sleep(500);
////                futures = futures.stream()
////                        .filter(x -> {
////                            try {
////                                countSucceed.addAndGet(x.get() ? 1 : 0);
////                            } catch (InterruptedException | ExecutionException e) {
//////                                throw new RuntimeException(e);
////                            }
////                            return !(x.isDone() || x.isCancelled());
////                        })
////                        .collect(Collectors.toList());
////
////            } catch (InterruptedException e) {
////                throw new RuntimeException(e);
////            }
////        }
////        System.out.println("Success task number = " + countSucceed);
////        pool.shutdown();
    }
}
