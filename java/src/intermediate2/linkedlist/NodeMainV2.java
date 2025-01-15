package intermediate2.linkedlist;

public class NodeMainV2 {

	public static void main(String[] args) {

		Node nodeA = new Node("A");
		nodeA.next = new Node("B");
		nodeA.next.next = new Node("C");

		printAll(nodeA);
		getLastNode(nodeA);
		Node node = getNode(2, nodeA);
		System.out.println(node);

		add(nodeA, "D");
		printAll(nodeA);
	}

	private static void printAll(Node node) {
		Node target = node;
		while (target != null) {
			System.out.println(target.object);
            target = target.next;
        }
	}

	private static Node getLastNode(Node node) {
		Node target = node;
        while (target.next != null) {
            target = target.next;
        }
        System.out.println(target.object);
		return target;
	}

	private static Node getNode(int index, Node node) {
		Node target = node;
		for (int i = 0; i < index; i++) {
			target = target.next;
		}
		return target;
	}

	private static void add(Node node, Object object) {
		Node lastNode = getLastNode(node);
		lastNode.next = new Node(object);
	}
}
