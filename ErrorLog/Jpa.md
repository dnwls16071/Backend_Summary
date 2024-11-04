### ✅No default constructor for entity 'com.jwj.concurrency.domain.Stock' org.springframework.orm.jpa.JpaSystemException:

<details>
   <summary> 에러 내용 해결 방법 (👈 Click)</summary>
<br />

* JPA @Entity 사용 시 주의사항은 다음과 같다.

    * 기본 생성자는 필수다.(파라미터가 없는 public 또는 protected 생성자)
    * final 클래스, enum, interface, inner 클래스에는 사용할 수 없다.
    * 저장할 필드에 final을 사용하면 안 된다.

* JPA가 엔티티 객체를 생성할 때는 기본 생성자를 사용한다. 따라서 이 생성자는 반드시 있어야 한다.
* 자바는 생성자가 하나도 없으면 기본적으로 기본 생성자를 만든다. 따라서 new 키워드를 통해 기본 생성자를 사용할 수 있는 것이다.
* 문제는 생성자를 하나 이상 만들면 자바는 기본 생성자를 자동으로 만들지 않는다. 따라서 이 때는 기본 생성자를 직접 만들어줘야 한다.

-----------------------
</details>