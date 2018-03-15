package com.synqq;

import java.util.List;

class TextPerson {
	String firstName;
	String lastName;
	List<ContextPersonWithCorrection> possibleContextPersons;
	
	public TextPerson(String firstName, String lastName, List<ContextPersonWithCorrection> possibleContextPersons) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.possibleContextPersons = possibleContextPersons;
	}
	
	public TextPerson(String firstName, List<ContextPersonWithCorrection> possibleContextPersons) {
		this.firstName = firstName;
		this.lastName = null;
		this.possibleContextPersons = possibleContextPersons;
	}

	public List<ContextPersonWithCorrection> getPossibleContextPersons() {
		return possibleContextPersons;
	}
}