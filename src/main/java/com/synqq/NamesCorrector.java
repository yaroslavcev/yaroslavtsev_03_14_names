package com.synqq;

import java.util.ArrayList;
import java.util.List;

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
	 * Make corrections to the text based on context and use Jaro-Winkler distance 
	 * between names in the text in the context.
	 * @param text Sentences with names which should be corrected. Names should start with an 
	 * Uppercase character.
	 * @param context a list of names. Each name may be a single name or a first and last name 
	 * pair separated by a space.
	 * @return
	 */
	public static List<NameCorrection> correct(String text, String[] context) {
		Context enhancedContext = new Context(context);
		List<List<String>> continuousNameList = NamesExtractor.extractContinuousNameList(text);
		List<NameCorrection> result = new ArrayList<>();
		for (List<String> nameGroup : continuousNameList) {
			if (nameGroup.size() == 1) {
				enhancedContext.getContextPersonsByName(nameGroup.get(0));
			}
		}
		return result;
	}
}
