package ch01sourcecode;

import static ch01sourcecode.NyPizza.Size.MEDIUM;
import static ch01sourcecode.Pizza.Topping.ONION;
import static ch01sourcecode.Pizza.Topping.PEPPER;

public class PizzaClient {

	public static void main(String[] args) {
		NyPizza nyPizza = new NyPizza.Builder(MEDIUM)
				.addTopping(PEPPER)
				.addTopping(ONION)
				.build();
		Calzone calzone = new Calzone.Builder()
				.addTopping(PEPPER)
				.addTopping(ONION)
				.sauceInside()
				.build();

		System.out.println(nyPizza);
		System.out.println(calzone);
	}
}
