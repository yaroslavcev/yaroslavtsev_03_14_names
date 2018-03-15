package com.synqq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * The algorithm takes two parameters:
 * 1. Context is a list of names. Each name may be a single name or a first and last
 * name pair separated by a space.
 * 2. Sentences with names which should be corrected. Names should start with an Uppercase character.
 * And return an ordered list of possible corrections.
 * 
 * @author Ilya Yaroslavtsev
 * 
 */
public class NamesCorrector {
	/**
	 * Make corrections to the text based on context.
	 * @param text Sentences with names which should be corrected. Names should start with an 
	 * Uppercase character.
	 * @param context a list of names. Each name may be a single name or a first and last name 
	 * pair separated by a space.
	 * @return
	 */
	public static List<NameCorrection> correct(String text, String[] context) {
		ContextAwareNameCorrector contextAwareCorrector = new ContextAwareNameCorrector(context);
		List<List<String>> continuousNameList = NamesExtractor.extractContinuousNameList(text);
		List<NameCorrection> result = new ArrayList<>();
		
		List<TextPerson> textPersons = new ArrayList<>();
		for (List<String> nameGroup : continuousNameList) {
			if (nameGroup.size() == 1) {
				String name = nameGroup.get(0);
				List<ContextPersonWithCorrection> contextPersonsByName = 
						contextAwareCorrector.findSimilarPersonByName(name);
				textPersons.add(new TextPerson(name, contextPersonsByName));
			} else {
				int possiblePersonCount = nameGroup.size() / 2;
				int currentPerson = 0;
				while (currentPerson < possiblePersonCount) {
					String firstName = nameGroup.get(currentPerson * 2);
					String lastName = nameGroup.get(currentPerson * 2 + 1);
					List<ContextPersonWithCorrection> contextPersonsByName = 
							contextAwareCorrector.findSimilarPersonByName(firstName, lastName);
					textPersons.add(new TextPerson(firstName, lastName, contextPersonsByName));
					currentPerson++;
				}
			}
			
		}
		
		resolveAbiguous(textPersons);
		for (TextPerson textPerson : textPersons) {
			if (textPerson.getPossibleContextPersons() != null) {
				for (ContextPersonWithCorrection possiblePerson : textPerson.getPossibleContextPersons()) {
					result.addAll(possiblePerson.getCorrections());
				}
			}
		}
		return result;
	}
	
	private static void resolveAbiguous(List<TextPerson> textPersons) {
		//Find all persons without alternatives
		Set<ContextPerson> withoutAlternatives = new HashSet<>(); 
		for (TextPerson textPerson : textPersons) {
			List<ContextPersonWithCorrection> possibleContextPersons = textPerson.getPossibleContextPersons();
			if (possibleContextPersons != null && possibleContextPersons.size() == 1) {
				possibleContextPersons.forEach(personWithCorrection -> 
					withoutAlternatives.add(personWithCorrection.getPerson()));
			}
		}
		
		//For all persons having alternatives
		//Check if one of alternatives is already presented in text
		//if so remove this alternative
		boolean hasNewAlternatives = true;
		while (hasNewAlternatives) {
			hasNewAlternatives = false;
			for (TextPerson textPerson : textPersons) {
				List<ContextPersonWithCorrection> possibleContextPersons = textPerson.getPossibleContextPersons();
				if (possibleContextPersons != null && possibleContextPersons.size() > 1) {
					Iterator<ContextPersonWithCorrection> it = possibleContextPersons.iterator();
					while (it.hasNext()) {
						ContextPerson contextPerson = it.next().getPerson();
						if (withoutAlternatives.contains(contextPerson)) {
							it.remove();
						}
					}
					if (possibleContextPersons.size() == 1) {
						possibleContextPersons.forEach(personWithCorrection -> 
							withoutAlternatives.add(personWithCorrection.getPerson()));
						hasNewAlternatives = true;
					}
				}
			}
		}
		
		//TODO resolve case when 2+ TextPerson have same alternatives
		//for now just take first
		for (TextPerson textPerson : textPersons) {
			List<ContextPersonWithCorrection> possibleContextPersons = textPerson.getPossibleContextPersons();
			if (possibleContextPersons != null && possibleContextPersons.size() > 1) {
				ContextPersonWithCorrection firstPerson = possibleContextPersons.get(0);
				possibleContextPersons.clear();
				possibleContextPersons.add(firstPerson);
			}
		}
	}
}
