package ru.find;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.Arrays;
import java.util.Queue;

public class SearchWorker {
    private final Logger logger = LoggerFactory.getLogger(SearchWorker.class);

    public void search(FileSearchPattern pattern, Queue<File> filePool, Queue<File> results) {
        while (!filePool.isEmpty()) {
            File current = filePool.poll();
            if (current == null) {
                return;
            }
            if (matchAndAdd(current, pattern, results) || !current.isDirectory()) {
                continue;
            }
            logger.trace("Thread {} now searching directory {}", Thread.currentThread().getName(), current);
            File[] directoryContent = current.listFiles();
            if (directoryContent == null) {
                continue;
            }
            filePool.addAll(Arrays.asList(directoryContent));
        }
    }

    private boolean matchAndAdd(File file, FileSearchPattern pattern, Queue<File> results) {
        boolean matches = file.isFile() && pattern.matches(file);
        if (matches) {
            logger.debug("Thread {} has found {}", Thread.currentThread().getName(), file.getAbsolutePath());
            results.add(file);
        }
        return matches;
    }
}
