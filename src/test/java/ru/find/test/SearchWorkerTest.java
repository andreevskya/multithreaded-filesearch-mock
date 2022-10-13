package ru.find.test;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.find.FileSearchPattern;
import ru.find.SearchWorker;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SearchWorkerTest {
    private static String root;

    @BeforeClass
    public static void beforeAll() throws IOException {
        String tmpDir = Paths.get(System.getProperty("java.io.tmpdir"), "test123654789").toString();
        File dir = Files.createDirectories(Path.of(tmpDir)).toFile();
        dir.deleteOnExit();
        root = dir.getAbsolutePath();
        TmpFileCreator.createTempFiles(tmpDir, "ccc", "a/bbb", "a/aaa1", "a/aaa2", "a/b/ddd1", "ddd2");
    }

    @Test
    public void search_followsDirectoriesRecursive() {
        Queue<File> queue = new LinkedList<>();
        Queue<File> results = new LinkedList<>();
        SearchWorker worker = new SearchWorker();
        queue.add(new File(root));
        worker.search(
                new FileSearchPattern("ddd*"),
                queue,
                results
        );
        Assertions.assertThat(new ArrayList<>(results)).containsOnly(
                Paths.get(root, "a/b/ddd1").toFile(),
                Paths.get(root, "ddd2").toFile()
        );
    }
}
