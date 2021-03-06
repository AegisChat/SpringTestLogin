package application.Tag;

import java.io.Serializable;

public final class Tag implements Serializable{

	private String tag;

	public Tag() {

	}

	public Tag(String t) {
		tag = t;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String t) {
		tag = t;
	}

	public boolean equals(Tag comparedTag) {
		if(tag.equalsIgnoreCase(comparedTag.getTag())) {
			return true;
		}else
			return false;
	}

	@Override
	public String toString() {
		return tag;
	}
}
