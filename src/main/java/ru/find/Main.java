package ru.find;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.time.Duration;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int MIN_POSSIBLE_ARGUMENTS = 2;
    private static final int MAX_POSSIBLE_ARGUMENTS = 3;
    private static final String ARGUMENT_PATTERN = "-name";

    public static void main(String[] args) {
        if (args.length == MIN_POSSIBLE_ARGUMENTS && ARGUMENT_PATTERN.equals(args[0])) {
            search(".", args[1]);
            return;
        }
        if (args.length == MAX_POSSIBLE_ARGUMENTS && ARGUMENT_PATTERN.equals(args[1])) {
            search(args[0], args[2]);
            return;
        }
        displayHelp();
        System.exit(-1);
    }

    private static void search(String root, String pattern) {
        long startTime = System.currentTimeMillis();
        List<File> files = new FileSearchBean().find(new File(root), pattern);
        long endTime = System.currentTimeMillis();
        Duration timespan = Duration.ofMillis(endTime - startTime);
        files.forEach(f -> System.out.println(f.getAbsolutePath()));
        logger.info("File search took {}", timespan);
    }

    private static void displayHelp() {
        System.out.println("Usage:\nfind <root> -name <pattern>\nor\nfind -name pattern");
    }
}
