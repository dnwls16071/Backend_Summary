package intermediate1.immutable;

public class ImmutableObj {

	private final int value;

	public ImmutableObj(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	// 반환 타입을 불변 객체로
	public ImmutableObj add(int number) {
		return new ImmutableObj(value + number);
	}
}
