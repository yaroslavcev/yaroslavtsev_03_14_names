package com.synqq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerDistance;

/**
 * Enhanced context having HashMap of names to person in context.
 * @author Ilya Yaroslavtsev
 *
 */
public class Context {
	private static final double NAME_SIMILARITY_THRESHOLD = 0.8;
	private List<ContextPerson> personsFromContext;
	private JaroWinklerDistance jwd = new JaroWinklerDistance();
	private Soundex soundex = new Soundex();
	
	public Context(String[] context) {
		personsFromContext = new ArrayList<>(context.length);
		for (String fullName : context) {
			String[] splittedName = fullName.split(" ");
			ContextPerson person = new ContextPerson(fullName, splittedName);
			personsFromContext.add(person);
		}
	}

	public List<ContextPersonWithCorrection> findSimilarPersonByName(String name) {
		List<ContextPersonWithCorrection> result = new ArrayList<>();

		for (ContextPerson contextPerson : personsFromContext) {
			double firstnameSimilarity = measureSimilarity(contextPerson.getFirstName(), name);
			double lastnameSimilarity = 0.0;
			if (contextPerson.getLastName() != null) {
				lastnameSimilarity = measureSimilarity(contextPerson.getLastName(), name);
			}
			
			if (firstnameSimilarity > NAME_SIMILARITY_THRESHOLD || lastnameSimilarity> NAME_SIMILARITY_THRESHOLD) {
				NameCorrection nameCorrection = null;
				if (lastnameSimilarity > firstnameSimilarity) {
					nameCorrection = new NameCorrection(name, contextPerson.getLastName());
				} else {
					nameCorrection = new NameCorrection(name, contextPerson.getFirstName());
				}
				if (!nameCorrection.getCorrection().equals(nameCorrection.getSourceName())) {
					result.add(new ContextPersonWithCorrection(contextPerson, Arrays.asList(nameCorrection)));
				}
			}
		}
		return result;
	}

	public List<ContextPersonWithCorrection> findSimilarPersonByName(String firstName, String lastName) {
		List<ContextPersonWithCorrection> result = new ArrayList<>();

		for (ContextPerson contextPerson : personsFromContext) {
			if (contextPerson.getLastName() == null) {
				continue;
			}
			double firstnameSimilarity = measureSimilarity(contextPerson.getFirstName(), firstName);
			double lastnameSimilarity = measureSimilarity(contextPerson.getLastName(), lastName);
			
			if (firstnameSimilarity > NAME_SIMILARITY_THRESHOLD && lastnameSimilarity> NAME_SIMILARITY_THRESHOLD) {
				List<NameCorrection> corrections = new ArrayList<>();
				if (!firstName.equals(contextPerson.getFirstName())) {
					corrections.add(new NameCorrection(firstName, contextPerson.getFirstName()));
				}
				if (!lastName.equals(contextPerson.getLastName())) {
					corrections.add(new NameCorrection(lastName, contextPerson.getLastName()));
				}
				if (corrections.size() > 0) {
					result.add(new ContextPersonWithCorrection(contextPerson, corrections));
				}
			}
		}
		return result;
	}
	
	private double measureSimilarity(String s1, String s2) {
		Double jwdResult = jwd.apply(s1, s2);
		Double soundexResult;
		try {
			soundexResult = soundex.difference(s1, s2) / 4.0;
		} catch (EncoderException e) {
			// TODO Add warning log
			soundexResult = jwdResult;
		}
		
		return (jwdResult + soundexResult) / 2;
	}
}
