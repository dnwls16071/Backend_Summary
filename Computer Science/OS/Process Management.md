# Process Management

### ✅프로세스 생성(Process Creation)

* 부모 프로세스(Parent Process)가 자식 프로세스(Children Process)를 생성
* 프로세스의 트리(계층 구조) 형성
* 프로세스는 자원을 필요로 함
  * 그 자원은 운영체제로부터 받는다.
  * 부모와 공유한다.
* 자원의 공유
  * 부모와 자식이 모든 자원을 공유하는 모델
  * 일부를 공유하는 모델
  * 전혀 공유하지 않는 모델
* 수행(Execution)
  * 부모와 자식은 공존하며 수행되는 모델
  * 자식이 종료(terminate)될 때까지 부모가 기다리는(wait) 모델

---

* 주소 공간(Address Space)
  * 자식은 부모의 공간을 복사함
  * 자식은 그 공간에 새로운 프로그램을 올림
* 유닉스의 예
  * `fork()` 시스템 콜로 새로운 프로세스를 생성
    * 부모를 그대로 복사 → 똑같은 내용을 가지는 프로세스가 2개 올라가므로 메모리가 낭비
    * 주소 공간 할당
  * `fork()` 다음에 이어지는 `exec()` 시스템 콜을 통해 새로운 프로그램을 메모리에 올림

### ✅프로세스 종료(Process Termination)

* 프로세스가 마지막 명령을 수행한 후 운영체제에게 이를 알려줌 → By `exit()` 시스템 콜 호출
  * 자식이 부모에게 데이터를 보냄 → By `wait()` 시스템 콜 호출
  * 프로세스 각종 자원들이 운영체제에게 반납됨
* 부모 프로세스가 자식 프로세스 수행을 종료시킴 → By `abort()` 시스템 콜 호출
  * 자식이 할당 자원 한계치를 넘어선 경우
  * 자식에게 할당된 테스크가 더 이상 필요하지 않은 경우
  * 부모가 종료(exit)하는 경우
    * 운영체제에서는 부모 프로세스가 종료되는 경우 자식이 더 이상 수행되도록 두지 않는다.
    * 단계적인 종료

### ✅fork() 시스템 콜🌟

* `fork()` 시스템 호출은 새로운 프로세스를 생성할 때 사용하는 방식이다.

```c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    printf("pid : %d", (int) getpid());                 // pid : 29146
    
    int rc = fork();					// 주목
    
    if (rc < 0) {
        exit(1);
    }							// (1) fork 실패
    else if (rc == 0) {		                        // (2) child 인 경우 (fork 값이 0)
        printf("child (pid : %d)", (int) getpid());
    }
    else {					        // (3) parent case
        printf("parent of %d (pid : %d)", rc, (int)getpid());
    }
}
```

```text
pid : 29146

parent of 29147 (pid : 29146)

child (pid : 29147)
```

* `fork()` 시스템 콜이 호출되는 순간, 프로세스가 하나 더 생기는데 이 떄, 생긴 프로세스(Child)는 `fork()`를 만든 프로세스(Parent)와 `거의` 동일한 복사본을 갖는다.
* 동일하다고 했기 때문에 `int rc = fork();` 코드문이 실행되는 순간 똑같은 2개의 프로그램이 동작한다고 생각하고 `fork()`가 return 될 차례라고 생각한다.
* 그 때문에 새로 생성된 프로세스(Child)는 `main`에서 시작되지 않고 `if문`에서 시작된다. 
* 그러나 콘솔에 출력된 결과를 보면 Parent 프로세스와 Child 프로세스의 PID(프로세스 식별자) 값이 다르다는 것을 볼 수 있다.
* 하지만 스케줄러가 부모를 먼저 수행할지 아닐지는 확신할 수 없다. 콘솔에 아래와 같이 출력될 수 있다는 말이다.


```text
pid : 29146

child (pid : 29147)

parent of 29147 (pid : 29146)
```

### ✅exec() 시스템 콜🌟

* `fork()`는 동일한 프로세스 내용을 여러 개 만들 때 사용한다면 `exec()`는 Child가 Parent와 다른 동작을 하게 만들고 싶을 때 사용한다.

```c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
    printf("pid : %d", (int) getpid());                                 // pid : 29146
    
    int rc = fork();					                // 주목
    
    if (rc < 0) {
        exit(1);
    }									// (1) fork 실패
    else if (rc == 0) {					                // (2) child 인 경우 (fork 값이 0)
        printf("child (pid : %d)", (int) getpid());
        char *myargs[3];
        myargs[0] = strdup("wc");		                        // 내가 실행할 파일 이름
        myargs[1] = strdup("p3.c");		                        // 실행할 파일에 넘겨줄 argument
        myargs[2] = NULL;				                // end of array
        execvp(myarges[0], myargs);		                        // wc 파일 실행.
        printf("this shouldn't print out")                              // 실행되지 않음.
    }
    else {								// (3) parent case
        int wc = wait(NULL)				                // 추가된 부분
        printf("parent of %d (wc : %d / pid : %d)", wc, rc, (int)getpid());
    }
}
```

* `exec()` 시스템 콜은 현재 프로세스를 새로운 프로그램으로 덮어쓴다. 
* 새로운 프로세스를 생성하지 않고, 현재 프로세스의 메모리 공간에 새로운 프로그램을 로드한다.

### ✅wait() 시스템 콜🌟

```c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char *argv[]) {
    printf("pid : %d", (int) getpid()); // pid : 29146
    
    int rc = fork();					                // 주목
    
    if (rc < 0) {
        exit(1);
    }									// (1) fork 실패
    else if (rc == 0) {					                // (2) child 인 경우 (fork 값이 0)
        printf("child (pid : %d)", (int) getpid());
    }
    else {								// (3) parent case
        int wc = wait(NULL)				                // 추가된 부분
        printf("parent of %d (wc : %d / pid : %d)", wc, rc, (int)getpid());
    }
}
```

* `wait()` 시스템 콜은 Child 프로세스가 종료될 때까지 기다리는 작업이다.
* 프로세스 A가 `wait()` 시스템 콜을 호출하면 커널은 Child 프로세스가 종료될 때까지 프로세스 A를 Sleep 시킨다.
* Child 프로세스가 종료되면 커널은 프로세스 A를 깨운다.

### ✅exit() 시스템 콜🌟

* 프로세스 종료를 담당하는 시스템 콜
  * 모든 자원을 반납, 부모 프로세스에게 알림 
  * 자발적 종료
    * 마지막 statement 수행 후 `exit()` 시스템 콜을 통해
    * 프로그램에 명시적으로 적어주지 않아도 `main` 함수가 리턴되는 위치에 컴파일러가 넣어줌
  * 비자발적 종료
    * 부모 프로세스가 자식 프로세스를 강제 종료시킴
      * 자식 프로세스가 한계치를 넘어서는 자원 요청
      * 자식에게 할당된 테스크가 더 이상 필요하지 않음
    * 키보드로 kill, break 등을 친 경우
    * 부모가 종료하는 경우
      * 프로세스의 세계에선 자식 프로세스가 먼저 죽고 그 다음에 부모 프로세스가 죽는 수순을 거침
      * 부모 프로세스가 종료하기 전에 자식들이 먼저 종료됨

### ✅프로세스 간 협력

* 독립적 프로세스
  * 프로세스는 각자의 주소 공간을 가지고 수행되므로 원칙적으로 하나의 프로세스는 다른 프로세스 수행에 영향을 미치지 못함
* 협력 프로세스
  * 프로세스 협력 메커니즘을 통해 하나의 프로세스가 다른 프로세스의 수행에 영향을 미칠 수 있음
* 프로세스 간 협력 메커니즘
  * 메시지를 전달하는 방법
    * message passing : 커널을 통해 메시지 전달
  * 주소 공간을 공유하는 방법
    * shared memory : 서로 다른 프로세스 간에도 일부 주소 공간을 공유하게 하는 shared memory 메커니즘이 있음
    * thread : 쓰레드 간에는 주소 공간 공유가 가능하므로 협력 역시 가능