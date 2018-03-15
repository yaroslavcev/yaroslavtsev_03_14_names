package com.synqq;

import java.util.List;

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
