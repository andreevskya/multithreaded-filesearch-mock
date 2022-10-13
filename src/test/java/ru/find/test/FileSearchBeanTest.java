package ru.find.test;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.find.FileSearchBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileSearchBeanTest {
    private static String root;

    @BeforeClass
    public static void beforeAll() throws IOException {
        String tmpDir = Paths.get(System.getProperty("java.io.tmpdir"), "filesearchtest").toString();
        File dir = Files.createDirectories(Path.of(tmpDir)).toFile();
        dir.deleteOnExit();
        root = dir.getAbsolutePath();
        TmpFileCreator.createTempFiles(tmpDir,
                "ddd3", "a/bbb", "a/aaa1", "a/aaa2", "a/b/ddd1", "ddd4", "a/ddd2", "z/ddd0"
        );
    }

    @Test
    public void search_sortsResultAlphabetically() {
        FileSearchBean find = new FileSearchBean();
        List<File> result = find.find(new File(root), "ddd*");
        Assertions.assertThat(result).containsExactlyElementsOf(Arrays.asList(
                Paths.get(root, "a/b/ddd1").toFile(),
                Paths.get(root, "a/ddd2").toFile(),
                Paths.get(root, "ddd3").toFile(),
                Paths.get(root, "ddd4").toFile(),
                Paths.get(root, "z/ddd0").toFile()
        ));
    }


}
