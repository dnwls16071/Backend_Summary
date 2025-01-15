package intermediate2.hash;

import java.util.Arrays;

public class CustomSet {

	private int[] elementData = new int[10];
	private int size = 0;

	// O(N)
	public boolean add(int value) {
		if (contains(value)) {
			return false;
		}
		elementData[size++] = value;
		return true;
	}

	// O(N)
	public boolean contains(int value) {
		for (int data : elementData) {
			if (data == value) {
                return true;
            }
		}
		return false;
	}

	public int size() {
		return size;
	}

	@Override
	public String toString() {
		return "CustomSet{" +
				"elementData=" + Arrays.toString(elementData) +
				", size=" + size +
				'}';
	}
}
