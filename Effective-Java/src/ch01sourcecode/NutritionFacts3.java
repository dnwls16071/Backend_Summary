package ch01sourcecode;

public class NutritionFacts3 {
	private final int servingSize;	// 1회 제공량(필수)
	private final int servings;		// 총 n회 제공량(필수)
	private final int calories;		// 1회 제공량당(선택)
	private final int fat;			// 1회 제공량(선택)
	private final int sodium;		// 1회 제공량(선택)
	private final int carbohydrate;	// 1회 제공량(선택)

	public static class Builder {
		// 필수 매개변수
		private final int servingSize;
		private final int servings;

		// 선택 매개변수
		private int calories = 0;
		private int fat = 0;
		private int sodium = 0;
		private int carbohydrate = 0;

		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
            this.servings = servings;
		}

		public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

		public Builder fat(int fat) {
			this.fat = fat;
            return this;
        }

		public Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

		public Builder carbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
            return this;
        }

		public NutritionFacts3 build() {
			return new NutritionFacts3(this);
		}
	}

	private NutritionFacts3(Builder builder) {
		servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
	}
}
