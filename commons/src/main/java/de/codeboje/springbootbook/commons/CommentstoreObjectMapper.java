package de.codeboje.springbootbook.commons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

public class CommentstoreObjectMapper extends ObjectMapper {
	private static final DateFormat SDF = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.S'Z'");

	public CommentstoreObjectMapper() {
		super();
		setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		setDateFormat(SDF);
	}
}
