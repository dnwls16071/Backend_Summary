package intermediate2.arraylist;

public class CustomArray {

	private static final int DEFAULT_CAPACITY_VALUE = 5;
	private int[] arr;

	public CustomArray() {
		this.arr = new int[DEFAULT_CAPACITY_VALUE];
	}

	public void addFirst(int[] arr, int newValue) {
		for (int i = arr.length - 1; i > 0; i--) {
			arr[i] = arr[i-1];
		}
		arr[0] = newValue;
	}

	public void addAtIndex(int[] arr, int index, int newValue) {
		for (int i = arr.length - 1; i > index; i--) {
			arr[i] = arr[i-1];
		}
		arr[index] = newValue;
	}

	public void addLast(int[] arr, int newValue) {
		arr[arr.length - 1] = newValue;
	}
}
