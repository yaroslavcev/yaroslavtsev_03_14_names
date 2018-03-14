package com.synqq;

import java.util.List;

/**
 * The algorithm takes two parameters:
 * 1. Context is a list of names. Each name may be a single name or a first and last
 * name pair separated by a space.
 * 2. Sentences with names which should be corrected. Names should start with an Uppercase character.
 * And return an ordered list of possible corrections.
 */
public class NamesCorrector {
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
		return null;
	}
}
