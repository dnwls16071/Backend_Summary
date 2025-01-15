package intermediate2.linkedlist;

public class CustomLinkedListMainV2 {

	public static void main(String[] args) {

		CustomLinkedList customLinkedList = new CustomLinkedList();
		customLinkedList.add("A");
		customLinkedList.add("B");
		customLinkedList.add("C");

		customLinkedList.add("D", 0);
		System.out.println(customLinkedList);

		customLinkedList.remove(0);
		System.out.println(customLinkedList);
	}
}
