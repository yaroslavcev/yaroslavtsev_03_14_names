package com.synqq;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for extraction of names (words starting with upper case letter) from text.
 * 
 * @author Ilya Yaroslavtsev
 *
 */
public class NamesExtractor {
	/**
	 * Extract list of strings representing strings consisting of names
	 * separated only by space. For example for
	 * {@code "Sherlock John Watson Alex and Peet are going to visit Mary Lee"}
	 * it will return
	 * {@code [[Sherlock, John, Watson, Alex], [Peet], [Mary, Lee]]}
	 * 
	 * @param text
	 *            text to be processed
	 * @return list of continuous names, see method description
	 */
	public static List<List<String>> extractContinuousNameList(String text) {
		List<List<String>> result = new ArrayList<>();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (Character.isUpperCase(c)) {
				List<String> continuousName = new ArrayList<>();
				i = extractContinuousName(text, i, continuousName);
				//skip the name when it is 'I' not followed by any other name
				boolean nameShouldBeSkipped = (c == 'I' && continuousName.size() == 1 && continuousName.get(0).length() == 1);
				if (!(nameShouldBeSkipped)) {
					result.add(continuousName);
				}
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
