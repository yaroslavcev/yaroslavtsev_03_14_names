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
//		List<NameCorrection> result = NamesCorrector.correct("tomorrow I have a meeting with Tim Hanks Tom Crus and Eastwud", CONTEXT);
//		List<NameCorrection> expectedResult = Arrays.asList(new NameCorrection("Tim", "Tom"), 
//				new NameCorrection("Crus", "Cruise"),
//				new NameCorrection("Eastwud", "Eastwood"));
//		assertEquals(expectedResult, result);
//
//		result = NamesCorrector.correct("Michael likes movies with Jon Way and Client East", CONTEXT);
//		expectedResult = Arrays.asList(new NameCorrection("Jon", "John"), 
//				new NameCorrection("Way", "Wayne"),
//				new NameCorrection("Client", "Clint"),
//				new NameCorrection("East", "Eastwood"));
//		assertEquals(expectedResult, result);
		
		List<NameCorrection> result = NamesCorrector.correct("Jonn invited me Jon Ham and Jon Wane, over for a lunch", CONTEXT);
		List<NameCorrection> expectedResult = Arrays.asList(new NameCorrection("Jonn", "John"), 
				new NameCorrection("Ham", "Hamm"),
				new NameCorrection("Jon", "John"),
				new NameCorrection("Wane", "Wayne"));
		assertEquals(expectedResult, result);

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
//			"Fitcher",
//			"Sherlock Holmes",
//			"John Watson");
//		//Sherlock Jon Watson
//		for (String name : list) {
//
//			String left = "Jon Watson";
//			System.out.println(name + " " + jwd.apply(left, name) + " " + soundex.difference(left, name));
//			String[] splittedName = name.split(" ");
//			if (splittedName.length > 1) {
//				System.out.println(splittedName[0] + " " + jwd.apply(left, splittedName[0]) + " " + soundex.difference(left, splittedName[0]));
//				System.out.println(splittedName[1] + " " + jwd.apply(left, splittedName[1]) + " " + soundex.difference(left, splittedName[1]));
//			}
//		}
	}
}
