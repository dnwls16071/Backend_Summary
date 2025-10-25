package intermediate1.object;

public class ObjectMain {

	public static void main(String[] args) {

		Child child = new Child();

		child.childMethod();
		child.parentMethod();

		Parent parent = new Parent();

		parent.parentMethod();
		//parent.childMethod();
	}
}
