package com.synqq;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class NamesExtractorTest {
	@Test
	public void testExtraction() {
		List<List<String>> result = NamesExtractor.extractContinuousNameList("tomorrow I have a meeting with Tim Hanks Tom Crus and Eastwud");
		List<List<String>> expectedResult = Arrays.asList(Arrays.asList("Tim", "Hanks", "Tom", "Crus"),
				Arrays.asList("Eastwud"));
		assertEquals(expectedResult, result);
		
		result = NamesExtractor.extractContinuousNameList("Michael likes movies with Jon Way and Client East");
		expectedResult = Arrays.asList(Arrays.asList("Michael"),
				Arrays.asList("Jon", "Way"),
				Arrays.asList("Client", "East"));
		assertEquals(expectedResult, result);
		
		result = NamesExtractor.extractContinuousNameList("Jonn invited me Jon Ham and Jon Wane, over for a lunch");
		expectedResult = Arrays.asList(Arrays.asList("Jonn"),
				Arrays.asList("Jon", "Ham"),
				Arrays.asList("Jon", "Wane"));
		assertEquals(expectedResult, result);
	}
}
