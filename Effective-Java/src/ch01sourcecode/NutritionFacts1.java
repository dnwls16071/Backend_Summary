package ch01sourcecode;

public class NutritionFacts1 {
	private final int servingSize;	// 1회 제공량(필수)
	private final int servings;		// 총 n회 제공량(필수)
	private final int calories;		// 1회 제공량당(선택)
	private final int fat;			// 1회 제공량(선택)
	private final int sodium;		// 1회 제공량(선택)
	private final int carbohydrate;	// 1회 제공량(선택)

	public NutritionFacts1(int servingSize, int servings) {
		this(servingSize, servings, 0);
	}

	public NutritionFacts1(int servingSize, int servings, int calories) {
		this(servingSize, servings, calories, 0);
	}

	public NutritionFacts1(int fat, int calories, int servings, int servingSize) {
		this(servingSize, servings, calories, fat, 0);
	}

	public NutritionFacts1(int sodium, int fat, int calories, int servings, int servingSize) {
		this(servingSize, servings, calories, fat, sodium, 0);
	}

	public NutritionFacts1(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}
}
