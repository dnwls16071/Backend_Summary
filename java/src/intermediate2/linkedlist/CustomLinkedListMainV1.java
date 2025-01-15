package intermediate2.linkedlist;

public class CustomLinkedListMainV1 {

	public static void main(String[] args) {

		CustomLinkedList customLinkedList = new CustomLinkedList();
		customLinkedList.add("A");
		customLinkedList.add("B");
		customLinkedList.add("C");

		System.out.println(customLinkedList.size());
		System.out.println(customLinkedList.get(1));
		System.out.println(customLinkedList.indexOf("C"));
		customLinkedList.set("D", 2);
		System.out.println(customLinkedList.getNode(2));

		customLinkedList.add("E");
		customLinkedList.add("F");

		System.out.println(customLinkedList);
	}
}
