package de.codeboje.springbootbook.spamdetection.impl.fs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.codeboje.springbootbook.spamdetection.SpamDetector;
import de.codeboje.springbootbook.spamdetection.impl.SimpleSpamDetector;

public class SimpleSpamDetectorTest {

    @Test
    public void testSpamTrue() throws Exception {
        SpamDetector spamDetector = new SimpleSpamDetector("src/test/resources/words.spam");
        
        assertTrue(spamDetector.containsSpam("I LOVE VIAGRA"));
        assertTrue(spamDetector.containsSpam("Horst Fuck"));
        assertTrue(spamDetector.containsSpam("Hort@horst-porn.com"));
    }

    @Test
    public void testSpamFalse() throws Exception {
        SpamDetector spamDetector = new SimpleSpamDetector("src/test/resources/words.spam");
        
        assertFalse(spamDetector.containsSpam("I LOVE Dogs"));
        assertFalse(spamDetector.containsSpam("I LOVE Robots"));
        assertFalse(spamDetector.containsSpam("I LOVE Cats"));
    }

}
