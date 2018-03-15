package com.synqq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enhanced context having HashMap of names to person in context.
 * @author Ilya Yaroslavtsev
 *
 */
public class Context {
	private String[] context;
	private Map<String, List<ContextPerson>> nameToPerson = new HashMap<>();
	
	public Context(String[] context) {
		for (String fullName : context) {
			String[] splittedName = fullName.split(" ");
			ContextPerson person = new ContextPerson(fullName, splittedName);
			
			nameToPerson.put(fullName, Arrays.asList(person));
			for (String namePart : splittedName) {
				List<ContextPerson> namePartPersonList = nameToPerson.get(namePart);
				if (namePartPersonList == null) {
					namePartPersonList = new ArrayList<>();
					nameToPerson.put(namePart, namePartPersonList);
				}
				namePartPersonList.add(person);
			}
		}
	}
	
	public List<ContextPerson> getContextPersonsByName(String name) {
		return nameToPerson.get(name);
	}
}
