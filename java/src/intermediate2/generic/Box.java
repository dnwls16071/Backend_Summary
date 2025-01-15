package intermediate2.generic;

public class Box<T> {

	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}
}
