package intermediate2.arraylist;

import java.util.Arrays;

public class CustomArrayListV3 {

	private static final int DEFAULT_CAPACITY_VALUE = 5;

	private Object[] elementData;
	private int size = 0;

	public CustomArrayListV3() {
		elementData = new Object[DEFAULT_CAPACITY_VALUE];
	}

	public CustomArrayListV3(int capacity) {
		elementData = new Object[capacity];
	}

	public Object get(int index) {
		return elementData[index];
	}

	public Object set(int index, Object element) {
		Object o = get(index);
		elementData[index] = element;
		return o;
	}

	public int indexOf(Object o) {
		for (int i = 0; i < size; i++) {
			if (elementData[i].equals(o)) {
				return i;
			}
		}
		return -1;
	}

	public void add(Object e) {
		if (size == elementData.length) {
			grow();
		}
		elementData[size] = e;
		size++;
	}

	public void add(int index, Object e) {
		for (int i = size; i > index; i--) {
			elementData[i] = elementData[i - 1];
		}
		elementData[index] = e;
		size++;
	}

	public void remove(int index) {
		for (int i = index; i < size - 1; i++) {
			elementData[i] = elementData[i + 1];
		}
		size--;
		elementData[size] = null;
	}

	private void grow() {
		Object[] newArray = Arrays.copyOf(elementData, elementData.length * 2);
        elementData = newArray;
	}

	public int size() {
		return size;
	}

	public String toString() {
		return Arrays.toString(elementData);
	}
}
