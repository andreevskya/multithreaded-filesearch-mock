package ru.find.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TmpFileCreator {

    public static void createTempFiles(String root, String... files) throws IOException {
        File tmpFile;
        for (String file : files) {
            if (!file.contains("/")) {
                tmpFile = Files.createFile(Paths.get(root, file)).toFile();
                tmpFile.deleteOnExit();
                continue;
            }
            String directoryPart = file.substring(0, file.lastIndexOf("/"));
            String namePart = file.substring(file.lastIndexOf("/") + 1);
            Path directoryPath = Paths.get(root, directoryPart);
            File dir = Files.createDirectories(directoryPath).toFile();
            dir.deleteOnExit();
            tmpFile = Files.createFile(Paths.get(dir.getAbsolutePath(), namePart)).toFile();
            tmpFile.deleteOnExit();
        }
    }
}
