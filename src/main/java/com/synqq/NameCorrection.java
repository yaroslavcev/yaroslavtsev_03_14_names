package com.synqq;

/**
 * Data object containing correction for a name.
 * @author Ilya Yaroslavtsev
 *
 */
public class NameCorrection {
	String sourceName;
	String correction;
	
	public NameCorrection(String sourceName, String correction) {
		this.sourceName = sourceName;
		this.correction = correction;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getCorrection() {
		return correction;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correction == null) ? 0 : correction.hashCode());
		result = prime * result + ((sourceName == null) ? 0 : sourceName.hashCode());
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
		NameCorrection other = (NameCorrection) obj;
		if (correction == null) {
			if (other.correction != null)
				return false;
		} else if (!correction.equals(other.correction))
			return false;
		if (sourceName == null) {
			if (other.sourceName != null)
				return false;
		} else if (!sourceName.equals(other.sourceName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("[%s, %s]", sourceName, correction);
	}
}