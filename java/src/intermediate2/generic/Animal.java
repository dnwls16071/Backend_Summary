package intermediate2.generic;

public abstract class Animal {

	private String name;
	private int size;

	public Animal(String name, int size) {
		this.name = name;
		this.size = size;
	}

	public void sound() {
		System.out.println("sound()");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Animal{" +
				"name='" + name + '\'' +
				", size=" + size +
				'}';
	}
}
