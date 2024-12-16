package ch01sourcecode;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class Pizza {

	public enum Topping { HAM, MUSHROOM, ONION, PINEAPPLE, PEPPER, SAUSAGE }
	final Set<Topping> toppings;

	abstract static class Builder<T extends Builder<T>> {
		EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
		public T addTopping(Topping topping) {
			toppings.add(Objects.requireNonNull(topping));
			return self();
		}

		// 추상 클래스에서 추상 빌더
		abstract Pizza build();

		// 하위 클래스는 이 메서드를 재정의하여 this를 반환하도록 한다.
		protected abstract T self();
	}

	Pizza(Builder<?> builder) {
		toppings = builder.toppings.clone();
	}
}
