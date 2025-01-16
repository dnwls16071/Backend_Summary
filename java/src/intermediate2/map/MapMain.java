package intermediate2.map;

import java.util.*;

public class MapMain {

	public static void main(String[] args) {

		HashMap<String, Integer> students = new HashMap<>();

		students.put("studentA", Integer.valueOf(80));
		students.put("studentB", Integer.valueOf(70));
		students.put("studentC", Integer.valueOf(60));
		students.put("studentD", Integer.valueOf(80));
		students.put("studentE", Integer.valueOf(100));

		Set<String> strings = students.keySet();
		for (String string : strings) {
			System.out.println("string = " + string);
		}

		Collection<Integer> values = students.values();
		for (Integer value : values) {
			System.out.println("value = " + value);
		}

		Set<Map.Entry<String, Integer>> entries = students.entrySet();
		for (Map.Entry<String, Integer> entry : entries) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key);
			System.out.println(value);
		}
	}
}
