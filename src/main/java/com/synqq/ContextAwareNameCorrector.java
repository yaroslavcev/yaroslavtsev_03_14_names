package com.synqq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.text.similarity.JaroWinklerDistance;

/**
 * Context aware name corrector. Uses Jaro Winkler distance and Soundex difference to find names in context similar
 * to a name provided in parameter. Soundex difference and Jaro Winkler distance are treated as similarly important
 * and have the same normalized weight.
 *  
 * @author Ilya Yaroslavtsev
 *
 */
public class ContextAwareNameCorrector {
	private static final double DEFAULT_NAME_SIMILARITY_THRESHOLD = 0.8;
	private final double nameSimilarityThreshold;
	private List<ContextPerson> personsFromContext;
	private JaroWinklerDistance jwd = new JaroWinklerDistance();
	private Soundex soundex = new Soundex();
	
	/**
	 * Construct new corrector based on provided context and threshold.
	 * @param context context to be used
	 * @param nameSimilarityThreshold the value used to make decision if names are similar or not, 
	 * names which have "Similarity" greater than nameSimilarityThreshold consider to be suitable for
	 * correction 
	 */
	public ContextAwareNameCorrector(String[] context, double nameSimilarityThreshold) {
		this.nameSimilarityThreshold = nameSimilarityThreshold;
		personsFromContext = new ArrayList<>(context.length);
		for (String fullName : context) {
			String[] splittedName = fullName.split(" ");
			ContextPerson person = new ContextPerson(fullName, splittedName);
			personsFromContext.add(person);
		}
	}
	
	public ContextAwareNameCorrector(String[] context) {
		this(context, DEFAULT_NAME_SIMILARITY_THRESHOLD);
	}
	
	/**
	 * Find persons and corresponding correction (if needed) in the context using ONLY first name or last name.
	 * @param name a name from text which is not a part of full name
	 * @return list persons from the context whose last name or first name is similar to the
	 * provided name, each matching person has corresponding correction if needed 
	 */
	public List<ContextPersonWithCorrection> findSimilarPersonByName(String name) {
		List<ContextPersonWithCorrection> result = new ArrayList<>();

		for (ContextPerson contextPerson : personsFromContext) {
			double firstnameSimilarity = measureSimilarity(contextPerson.getFirstName(), name);
			double lastnameSimilarity = 0.0;
			if (contextPerson.getLastName() != null) {
				lastnameSimilarity = measureSimilarity(contextPerson.getLastName(), name);
			}
			
			if (firstnameSimilarity > nameSimilarityThreshold || lastnameSimilarity> nameSimilarityThreshold) {
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

	/**
	 * Find persons and corresponding correction (if needed) in the context using BOTH first name or last name.
	 * @param firstName a name from text which is a part of full name and seems to be first name
	 * @param lastName a name from text which is a part of full name and seems to be last name
	 * @return list persons from the context whose last name AND first name is similar to the
	 * provided name, each matching person has corresponding correction if needed 
	 */
	public List<ContextPersonWithCorrection> findSimilarPersonByName(String firstName, String lastName) {
		List<ContextPersonWithCorrection> result = new ArrayList<>();

		for (ContextPerson contextPerson : personsFromContext) {
			if (contextPerson.getLastName() == null) {
				continue;
			}
			double firstnameSimilarity = measureSimilarity(contextPerson.getFirstName(), firstName);
			double lastnameSimilarity = measureSimilarity(contextPerson.getLastName(), lastName);
			
			if (firstnameSimilarity > nameSimilarityThreshold && lastnameSimilarity> nameSimilarityThreshold) {
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
