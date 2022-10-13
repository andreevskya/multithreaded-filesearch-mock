package ru.find;

import java.io.File;
import java.util.regex.Pattern;

public class FileSearchPattern {

    private final Pattern pattern;

    public FileSearchPattern(String mask) {
        mask = mask
                .replace(".", "\\.")
                .replace("*", ".*");
        pattern = Pattern.compile(mask);
    }

    public boolean matches(File file) {
        if (file.isDirectory()) {
            return false;
        }
        return pattern.matcher(file.getName()).matches();
    }
}
