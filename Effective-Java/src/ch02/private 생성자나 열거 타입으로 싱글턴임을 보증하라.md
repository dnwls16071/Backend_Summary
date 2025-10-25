### ✅ private 생성자나 열거 타입으로 싱글턴임을 보증하라

> **싱글턴(Singleton)**
* 인스턴스를 오직 하나만 생성할 수 있는 클래스

> **사용 예시**
* 무상태 객체(Stateless)
* 설계상 유일해야 하는 시스템 컴포넌트
* DBCP(Database Connection Pool), 로그기록용 객체

> **장점**
* 한 번의 객체 생성으로 재사용이 가능해져 메모리 낭비를 방지할 수 있다.
* 또한 전역성을 갖기 때문에 다른 객체와 공유가 용이하다.

> **단점**
* 클래스를 싱글턴으로 만들면 이를 사용하는 클라이언트를 테스트하기 어려워질 수 있다.
* 타입을 인터페이스로 정의한 다음 그 인터페이스를 구현해 만든 싱글턴이 아니라면 인스턴스를 가짜 구현으로 대체할 수 없기 때문이다.

### ✅ public static final 필드 방식의 싱글턴
```java
public class Elvis {
	public static final Elvis INSTANCE = new Elvis();
	private Elvis() { 
		// ... 
	}
	
	public void leaveTheBuilding() {
		// ...
    }
}
```
* private 생성자는 public static final 필드를 초기화할 때 딱 한 번만 호출된다.
* public이나 protected 생성자가 없으므로 Elvis 클래스가 초기화될 때 만들어진 인스턴스가 현재 시스템에서 하나뿐임이 보장된다.
* 단 예외가 있다면 권한이 있는 클라이언트는 리플렉션 API인 AccessibleObject.setAccessible을 사용해 private 생성자를 호출할 수 있다.
* 이러한 공격을 방어하려면 생성자를 수정하여 두 번째 객체가 생성되려고 할 때, 예외를 던지게 하면 된다.

### ✅ 정적 팩토리 방식의 싱글턴
```java
public class Elvis {
	private static final Elvis INSTANCE = new Elvis();
	private Elvis() {
		// ...
    }
	
	public static Elvis getInstance() {
		return INSTANCE;
    }
	
	public void leaveTheBuilding() {
		// ...
    }
}
```

* 1번과 2번 방식으로 만들어진 싱글턴 클래스를 직렬화하려면 단순히 Serializable을 구현한다고 선언하는 것만으로는 부족하다.
* 모든 인스턴스 필드에 transient를 선언하고, readResolve 메서드를 제공해야만 역직렬화시에 새로운 인스턴스가 만들어지는 것을 방지할 수 있다.
* 만약 이렇게 하지 않으면 초기화해둔 인스턴스가 아닌 다른 인스턴스가 반환된다.

```java
private Object readResolve() {
	return INSTANCE;
}
```

### ✅ 열거 타입 방식의 싱글턴

```java
public enum Elvis {
	INSTANCE;
	
	public void leaveTheBuilding() {
		// ...
    }
}
```
* 싱글턴을 만드는 세 번째 방법은 원소가 하나인 열거 타입을 선언하는 것이다.
* public 필드 방식과는 비슷하지만 더 간결하고 추가 노력 없이 직렬화할 수 있다.
* 아주 복잡한 직렬화 상황이나 리플렉션 공격에서도 제 2의 인스턴스가 생기는 일을 방지할 수 있다.
* 조금 부자연스러워 보이나 대부분 상황에서는 원소가 하나뿐인 열거 타입이 싱글턴을 만드는 가장 좋은 방법이다.
* 단, 만들려는 싱글턴이 Enum 타입 외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없다.