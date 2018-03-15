package com.synqq;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.junit.Test;

/**
 * Unit test for the simple NamesCorrector.
 */
public class NamesCorrectorTest {
	private static final String[] CONTEXT = {
			"John Wayne",
			"Tom Hanks",
			"Tom Cruise",
			"Clint Eastwood",
			"Jon Hamm",
			"John Nolan",
			"William",
			"Fitcher"};
	
	@Test
	public void testCorrection() throws EncoderException {
		List<NameCorrection> result = NamesCorrector.correct("tomorrow I have a meeting with Tim Hanks Tom Crus and Eastwud", CONTEXT);
		List<NameCorrection> expectedResult = Arrays.asList(new NameCorrection("Tim", "Tom"), 
				new NameCorrection("Crus", "Cruise"),
				new NameCorrection("Eastwud", "Eastwood"));
		assertEquals(expectedResult, result);

		result = NamesCorrector.correct("Michael likes movies with Jon Way and Client East", CONTEXT);
		expectedResult = Arrays.asList(new NameCorrection("Jon", "John"), 
				new NameCorrection("Way", "Wayne"),
				new NameCorrection("Client", "Clint"),
				new NameCorrection("East", "Eastwood"));
		assertEquals(expectedResult, result);
		
		result = NamesCorrector.correct("Jonn invited me Jon Ham and Jon Wane, over for a lunch", CONTEXT);
		expectedResult = Arrays.asList(new NameCorrection("Jonn", "John"), 
				new NameCorrection("Ham", "Hamm"),
				new NameCorrection("Jon", "John"),
				new NameCorrection("Wane", "Wayne"));
		assertEquals(expectedResult, result);
	}
}
