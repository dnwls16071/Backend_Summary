### ✅ 인스턴스화를 막으려거든 private 생성자를 사용하라

```java
public class UtilityClass {
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd"; // 정적 멤버

	// 생성자 명시 X

	public static String convertDateToString(Date date) {
		return new SimpleDateFormat(FULL_DATE_FORMAT).format(date);
	}
}
```

* 위와 같이 정적 멤버만 담은 유틸리티 클래스는 인스턴스로 만들어 쓰려고 설계한 것이 아니다.
* 하지만 생성자를 명시하지 않으면 컴파일러가 자동으로 기본 생성자를 만들어준다.
* 즉, 매개변수를 받지 않는 public 기본 생성자가 만들어지며 사용자는 이 생성자가 자동 생성된 것인지는 구분할 수 없다.
* 실제로 동작하는 코드를 보면 대략적으로 아래와 같다.

```java
UtilityClass utilityClass = new UtilityClass(); // 컴파일러에 의해 자동으로 만들어지는 기본 생성자
String formattedToday = utilityClass.convertDateToString(new Date());
```

* 컴파일러가 기본 생성자를 만드는 경우는 오직 명시된 생성자가 없을 때 뿐이다. 따라서 private 생성자를 추가해주면 클래스의 인스턴스화를 막을 수 있다.

```java
public class UtilityClass {
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd"; // 정적 멤버

    // 기본 생성자가 만들어지는 것을 방지할 수 있다.(인스턴스화 방지용)
	private UtilityClass() {
		throw new AssertionError();
    }

	public static String convertDateToString(Date date) {
		return new SimpleDateFormat(FULL_DATE_FORMAT).format(date);
	}
}
```

* 명시적 생성자가 private이니 클래스 바깥에서는 접근할 수 없다.
* AssertionError를 꼭 호출할 필요는 없으나, 클래스 안에서 실수로라도 생성자를 호출하지 않도록 해준다.
* 이 코드는 어떤 환경에서도 클래스가 인스턴스화 되는 것을 막아준다.
* 하지만 생성자가 분명히 존재하는데 호출할 수는 없다니 그다지 직관적이지는 않다. 따라서 앞의 코드처럼 적절히 주석을 달아주자.
* 또한 이 방식은 상속을 불가능하게 하는 효과도 있다.
* 모든 생성자는 명시적이든 묵시적이든 상위 클래스 생성자를 호출하게 되는데 이렇게 되면 위의 UtilityClass의 생성자가 private이므로 하위 클래스가 상위 클래스의 생성자에 접근할 길이 막혀버린 것이다.