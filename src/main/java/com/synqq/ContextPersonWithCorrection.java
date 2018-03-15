package com.synqq;

import java.util.List;

/**
 * POJO for grouping of person and corresponding corrections that must be done for TextPerson
 * @author Ilya Yaroslavtsev
 *
 */
public class ContextPersonWithCorrection {
	ContextPerson person;
	List<NameCorrection> corrections;

	public ContextPersonWithCorrection(ContextPerson person, List<NameCorrection> corrections) {
		this.person = person;
		this.corrections = corrections;
	}

	public ContextPerson getPerson() {
		return person;
	}

	public List<NameCorrection> getCorrections() {
		return corrections;
	}

}
