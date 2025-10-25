package intermediate2.hashset;

import java.util.Objects;

public class Member {

	private String id;

	public Member(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;
		Member member = (Member) object;
		return Objects.equals(id, member.id);
	}
}
