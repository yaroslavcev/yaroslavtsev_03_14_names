package com.synqq;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.junit.Test;
import static org.junit.Assert.*;

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
		List<NameCorrection> result = NamesCorrector.correct("Michael likes movies with Jon Way and Client East", CONTEXT);
		List<NameCorrection> expectedResult = Arrays.asList(new NameCorrection("Jon", "John"), 
				new NameCorrection("Way", "Wayne"),
				new NameCorrection("Client", "Clint"),
				new NameCorrection("East", "Eastwood"));
		assertEquals(expectedResult, result);
//		JaroWinklerDistance jwd = new JaroWinklerDistance();
//		Soundex soundex = new Soundex();
//		System.out.println(jwd.apply("Jon", "John"));
//		System.out.println(jwd.apply("Wane", "Wayne"));
//		System.out.println(jwd.apply("Jon Wane", "John Wayne"));
//		System.out.println(soundex.difference("John", "Jon"));
////		
////		System.out.println(jwd.apply("Eastwood", "Eastwud"));
////		System.out.println(soundex.difference("Eastwood", "Eastwud"));
//		//System.out.println(jwd.apply("Ivan Toth", "I Toth"));
//		List<String> list = Arrays.asList("John Wayne",
//			"Tom Hanks",
//			"Tom Cruise",
//			"Clint Eastwood",
//			"Jon Hamm",
//			"John Nolan",
//			"William",
//			"Fitcher");
//		for (String name : list) {
//			System.out.println(name + " " + jwd.apply("Michael", name) + " " + soundex.difference("Michael", name));
//		}
	}
}
