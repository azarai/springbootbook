package de.codeboje.springbootbook.spamdetection.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import de.codeboje.springbootbook.spamdetection.SpamDetector;

/**
 * Simple Spam Detector - checks for unwanted words.
 * 
 */
public class SimpleSpamDetector implements SpamDetector {

	private List<String> spamWords = new ArrayList<String>();

	public SimpleSpamDetector(String filename) throws IOException {
		FileInputStream fis = new FileInputStream(new File(filename));
		this.spamWords = IOUtils.readLines(fis);
	}

	@Override
	public boolean containsSpam(String value) {

		for (String spam : spamWords) {
			if (value.toLowerCase().contains(spam)) {
				return true;
			}
		}
		return false;
	}

}
