package com.synqq;

/**
 * POJO for a person from context.
 * 
 * @author Ilya Yaroslavtsev
 *
 */
public class ContextPerson {
	private String fullName;
	private String[] nameParts;
	
	public ContextPerson(String fullName, String[] nameParts) {
		super();
		this.fullName = fullName;
		this.nameParts = nameParts;
	}
	
	public String getFullName() {
		return fullName;
	}

	public String getFirstName() {
		return nameParts[0];
	}
	
	public String getLastName() {
		return (nameParts.length > 1) ? nameParts[1] : null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContextPerson other = (ContextPerson) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		return true;
	}
}
