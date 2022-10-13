package ru.find.test;

import org.junit.Test;
import ru.find.FileSearchPattern;
import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileSearchPatternTest {

    @Test
    public void matches_substitutePatternAtTheStart() {
        FileSearchPattern pattern = new FileSearchPattern("*.a");
        assertFalse(pattern.matches(new File("name.java")));
        assertTrue(pattern.matches(new File("name.a")));
    }

    @Test
    public void matches_substitutePatternAtTheEnd() {
        FileSearchPattern pattern = new FileSearchPattern("start*");
        assertTrue(pattern.matches(new File("start")));
        assertTrue(pattern.matches(new File("startsandcontinues")));
        assertTrue(pattern.matches(new File("start.extension")));
        assertFalse(pattern.matches(new File("notstrarts")));
    }

    @Test
    public void matches_substitutePatternAtTheMiddle() {
        FileSearchPattern pattern = new FileSearchPattern("st*.mp3");
        assertTrue(pattern.matches(new File("straisand.mp3")));
        assertTrue(pattern.matches(new File("st.mp3")));
        assertFalse(pattern.matches(new File("starships")));
        assertFalse(pattern.matches(new File("stmp")));
    }

    @Test
    public void matches_severalPatterns() {
        FileSearchPattern pattern = new FileSearchPattern("aa*bb*cc");
        assertTrue(pattern.matches(new File("aabbcc")));
        assertTrue(pattern.matches(new File("aazzbbcc")));
        assertFalse(pattern.matches(new File("aacc")));
    }

    @Test
    public void matches_caseSensitiveMatch() {
        FileSearchPattern pattern = new FileSearchPattern("AA");
        assertTrue(pattern.matches(new File("AA")));
        assertFalse(pattern.matches(new File("aa")));
    }
}
