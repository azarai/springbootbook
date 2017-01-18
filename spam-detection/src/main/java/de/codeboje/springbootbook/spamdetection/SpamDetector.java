package de.codeboje.springbootbook.spamdetection;



/**
 * Interface for detecting spam comments
 *
 */
public interface SpamDetector {

	boolean containsSpam(String value);
}
