package intermediate1.enumeration;

public class GradeMain {

	public static void main(String[] args) {

		// 모든 ENUM 반환
		Grade[] grades = Grade.values();
		for (Grade grade : grades) {
			System.out.println("grade.name = " + grade.name() + "ordinal = " + grade.ordinal());
		}

		// 입력받은 문자열을 ENUM으로 변환
		String input = "GOLD";
		Grade grade = Grade.valueOf(input);
		System.out.println("gold = " + grade);
	}
}
