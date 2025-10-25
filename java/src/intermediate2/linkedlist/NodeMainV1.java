package intermediate2.linkedlist;

public class NodeMainV1 {

	public static void main(String[] args) {

		Node nodeA = new Node("A");
		nodeA.next = new Node("B");
		nodeA.next.next = new Node("C");

		System.out.println(nodeA);
	}
}
