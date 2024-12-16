package ch01sourcecode;

public class Calzone extends Pizza {

	private final Boolean sauceInside;

	public static class Builder extends Pizza.Builder<Builder> {

		private boolean sauceInside = false;

		public Builder sauceInside() {
			sauceInside = true;
			return this;
		}

		@Override
		public Calzone build() {
			return new Calzone(this);
		}

		@Override
		protected Builder self() {
			return this;
		}
	}

	private Calzone(Builder builder) {
		super(builder);
		sauceInside = builder.sauceInside;
	}
}
