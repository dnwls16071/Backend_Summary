package intermediate2.linkedlist;

public class Node {

	Object object;
	Node next;

	public Node(Object object) {
		this.object = object;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node x = this;
		sb.append("[");

		while (x != null) {
			sb.append(x.object);
			if (x.next != null) {
				sb.append("->");
			}
			x = x.next;
		}

		sb.append("]");
		return sb.toString();
	}
}
