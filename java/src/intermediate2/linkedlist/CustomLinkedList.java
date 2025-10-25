package intermediate2.linkedlist;

public class CustomLinkedList {

	private Node first;
	private int size = 0;

	public void add(Object object) {
		Node node = new Node(object);
		if (first == null) {
			first = node;
		} else {
			Node lastNode = getLastNode();
			lastNode.next = node;
		}
		size++;
	}

	public void add(Object object, int index) {
		Node newNode = new Node(object);
		if (index == 0) {
			newNode.next = first;
			first = newNode;
		} else {
			Node prev = getNode(index - 1);
			newNode.next = prev.next;
			prev.next = newNode;
		}
		size++;
	}

	public void remove(int index) {
		Node node = getNode(index);
		if (index == 0) {
			first = node.next;
		} else {
			Node prev = getNode(index - 1);
			prev.next = node.next;
		}
		node.next = null;
		node.object = null;
		size--;
	}

	public Node getLastNode() {
		Node target = first;
        while (target.next!= null) {
            target = target.next;
        }
		return target;
	}

	public void set(Object object, int index) {
		Node node = getNode(index);
		node.object = object;
	}

	public Node get(int index) {
		return getNode(index);
	}

	public Node getNode(int index) {
		Node target = first;
		for (int i = 0; i < index; i++) {
			target = target.next;
		}
		return target;
	}

	public int indexOf(Object o) {
		int index = 0;
		for (Node target = first; target != null; target = target.next) {
			if (target.object.equals(o)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int size() {
		return size;
	}

	@Override
	public String toString() {
		return "CustomLinkedListV1{" +
				"first=" + first +
				", size=" + size +
				'}';
	}
}
