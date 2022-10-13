package ru.find;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FileSearchBean {
    private final int NUM_THREADS = 5;

    private final ExecutorService threadPool = Executors.newFixedThreadPool(NUM_THREADS);

    public List<File> find(File root, String pattern) {
        // ConcurrentLinkedQueue vs LinkedBlockingQueue
        //          1.41 sec              0.99 sec
        LinkedBlockingQueue<File> queue = new LinkedBlockingQueue<>();
        LinkedBlockingQueue<File> results = new LinkedBlockingQueue<>();
        FileSearchPattern fileSearchPattern = new FileSearchPattern(pattern);
        queue.add(root);

        for (int i = 0; i < NUM_THREADS; ++i) {
            threadPool.execute(() -> new SearchWorker().search(fileSearchPattern, queue, results));
        }
        try {
            threadPool.shutdown();
            threadPool.awaitTermination(Integer.MAX_VALUE, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
            //
        }
        return results.stream().sorted(Comparator.comparing(File::getAbsolutePath)).collect(Collectors.toList());
    }
}
