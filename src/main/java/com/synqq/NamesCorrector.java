package com.synqq;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The algorithm takes two parameters:
 * 1. Context is a list of names. Each name may be a single name or a first and last
 * name pair separated by a space.
 * 2. Sentences with names which should be corrected. Names should start with an Uppercase character.
 * And return an ordered list of possible corrections.
 */
public class NamesCorrector {
	private static final Pattern NAME_PATTERN = Pattern.compile("\\p{Upper}\\p{Lower}+(\\s+\\p{Upper}\\p{Lower}+)*");
	
	public static class Correction {
		String sourceName;
		String correction;
		
		public Correction(String sourceName, String correction) {
			this.sourceName = sourceName;
			this.correction = correction;
		}

		public String getSourceName() {
			return sourceName;
		}

		public String getCorrection() {
			return correction;
		}
	}
	
	/**
	 * Make corrections to the text based on context and use Jaro-Winkler distance 
	 * between names in the text in the context.
	 * @param text Sentences with names which should be corrected. Names should start with an 
	 * Uppercase character.
	 * @param context a list of names. Each name may be a single name or a first and last name 
	 * pair separated by a space.
	 * @return
	 */
	public static List<Correction> correct(String text, String[] context) {
		List<List<String>> continuousNameList = extractContinuousNameList(text);
		return null;
	}
	
	/**
	 * Extract list of strings representing strings consisting of names separated only by space. For example for 
	 * {@code "Sherlock John Watson Alex and Peet are going to visit Mary Lee"} it will return
	 * {@code [[Sherlock, John, Watson, Alex], [Peet], [Mary, Lee]]} 
	 * @param text text to be processed
	 * @return list of continuous names, see method description
	 */
	private static List<List<String>> extractContinuousNameList(String text) {
		List<List<String>> result = new ArrayList<>();
		for (int i = 0; i < text.length(); i++){
		    char c = text.charAt(i);
		    if (Character.isUpperCase(c)) {
		    	List<String> continuousName = new ArrayList<>();
		    	i = extractContinuousName(text, i, continuousName);
		    	result.add(continuousName);
		    }
		}
		return result;
	}
	
	private static int extractContinuousName(String text, int startIndex, List<String> continuousName) {
    	int i = startIndex;
		StringBuilder name = new StringBuilder();
    	name.append(text.charAt(startIndex));
    	while (++i < text.length()) {
    		char c = text.charAt(i);
    		if (Character.isLowerCase(c)) {
    			name.append(c);
    		} else {
    			continuousName.add(name.toString());
    			while (c == ' ' && i + 1 < text.length()) {
    				c = text.charAt(++i);
    			}
				if (Character.isUpperCase(c)) {
					name = new StringBuilder();
					name.append(c);
				} else {
					break;
				}
    		}
    	}
    	
    	if (i >= text.length()) {
    		continuousName.add(name.toString());
    	}
    	
    	return i;
	}
}
