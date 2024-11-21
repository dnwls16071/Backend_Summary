# Process

### ✅프로세스(Process) 개념🌟

> 프로세스는 프로그램의 실행을 말한다.
* CPU 수행 상태를 나타내는 하드웨어 문맥
  * Program Counter
  * 각종 register
* 프로세스의 주소 공간
  * code, data, stack
* 프로세스 관련 커널 자료구조
  * PCB(Process Control Block)
  * Kernel Stack
  
### ✅프로세스의 상태(Process State)🌟

* 프로세스는 상태(state)가 변경되며 수행된다.
  * Running
    * CPU를 잡고 instruction을 수행중인 상태
  * Ready
    * CPU를 기다리는 상태
  * Blocked(Wait, Sleep)
    * CPU를 주어도 당장 instruction을 수행할 수 없는 상태
    * Process 자신이 요청한 이벤트가 즉시 만족되지 않아 이를 기다리는 상태
* New : 프로세스가 생성 중인 상태
* Terminated : 수행(Execution)이 끝난 상태
* Suspended(stopped) : 외부적인 이유로 프로세스 수행이 정지된 상태, 프로세스는 통째로 디스크에 Swap Out 된다.

```text
Blocked vs Suspended

* Blocked : 자신이 요청한 Event가 만족되면 Ready 상태로 돌아갈 수 있음
* Suspended : 외부에서 Resume 해줘야 Active
```

### ✅Process Control Block(PCB)🌟

<img src="https://t1.daumcdn.net/cfile/tistory/25673A5058F211C224" width="400">

> Process Management : CPU가 여러개일 때, CPU 스케줄링을 통해 관리하는 것을 말한다.
* 이 때, CPU는 각 프로세스들이 누군지 알아야 관리가 가능하다.
* 프로세스들의 특징을 갖고 있는 것이 바로 `Process MetaData`이다.
* Process MetaData
  * Process ID
  * Process State
  * Process Priority
  * CPU Register
  * Owner
  * CPU Usage
  * Memory Usage
* 이 메타 데이터는 프로세스가 생성되면 `PCB(Process Control Block)`이라는 곳에 저장된다.

> PCB(Process Control Block) : 프로세스 메타 데이터들을 저장해 놓는 곳, 한 PCB 안에는 한 프로세스의 정보가 담긴다.

```text
프로그램 실행 → 프로세스 생성 → 프로세스 주소 공간에 [코드, 데이터, 스택] 생성 → 이 프로세스 메타 데이터들이 PCB에 저장
```

PCB가 왜 필요할까? 
> CPU에서는 프로세스 상태에 따라 교체 작업이 이루어진다.
> 
> 이 때, 앞으로 다시 수행할 대기 중인 프로세스에 관한 저장 값을 PCB에 저장하는 것이다.

PCB 관리
> Linked List 방식
> 
> PCB List Head에 PCB들이 생성될 때마다 붙게 된다. 주소 값으로 연결이 이루어진 연결리스트이기 때문에 삽입/삭제가 용이함
> 
> 즉, 프로세스가 생성되면 해당 PCB가 생성되고 프로세스가 종료되면 PCB가 제거된다.

이렇게 수행 중인 프로세스를 변경할 때, CPU 레지스터 정보가 변경되는 것을 `Context Switching`이라고 한다.

### ✅문맥 교환(Context Switch)🌟

> CPU가 이전 프로세스 상태를 PCB에 보관하고 또 다른 프로세스 정보를 PCB에 읽어 레지스터에 적재하는 과정을 말한다.
* 보통 인터럽트가 발생하거나, 실행 중인 CPU 사용 허가 시간을 모두 소모했다거나 입출력을 위해 대기해야 하는 경우에 Context Switch이 발생한다.
* CPU가 다른 프로세스에게 넘어갈 때, 운영체제는 다음을 수행한다.
  * CPU를 내어주는 프로세스 상태를 그 프로세스의 PCB에 저장한다.
  * CPU를 새롭게 얻게 되는 프로세스의 상태를 그 PCB에서 읽어온다.

```text
즉, 프로세스가 READY → RUNNING, RUNNING → READY, RUNNING → WAITING처럼 상태 변경 시 발생한다.
```

* System Call이나 Interrupt 발생시 반드시 Context Switching이 일어나는 것은 아니다.
  * 외부에서 Interrupt를 발생시켰거나 사용자 프로세스에서 운영체제 코드를 사용하기 위해 시스템 콜을 호출하면 CPU 제어권이 커널로 넘어가는데 이 과정이 Context Switch를 나타내는 것은 아니다.
  * 사용자 프로세스에서 오래 걸리는 I/O 요청을 처리하면 그만큼 오래 걸리기 때문에 I/O 요청을 처리한 후 프로세스에게 CPU를 넘겨줘봤자 당장 프로세스가 다시 실행될 수는 없기에 이 과정은 Context Switch라고 할 수 있다.

### ✅Context Switch Overhead

```text
프로세스를 수행하다가 입출력 이벤트가 발생해서 대기 상태로 전환시킨다.
이 때, CPU를 그냥 놀게 놔두는 것보다 다른 프로세스를 수행시키는 것이 효율적이다.
```
* 위와 같은 경우라면 프로세스 간 이동이 수반되기 때문에 오버헤드를 감수해야 한다.

### ✅스케줄러(Scheduler)

* Long-term Scheduler(장기 스케줄러)
  * 시작 프로세스 중 어떤 것들을 Ready Queue로 보낼지 결정
* Short-term Scheduler(단기 스케줄러)
  * 어떤 프로세스를 다음 번에 Running 시킬지 결정
* Medium-term Scheduler(중기 스케줄러)
  * 여유 공간 마련을 위해 프로세스를 통째로 메모리에서 디스크로 쫓아냄

### ✅프로세스(Process) vs 쓰레드(Thread)🌟

> 프로세스 : 메모리 상에서 실행 중인 프로그램
> 
> 쓰레드 : 프로세스 안에서 실행되는 여러 흐름 단위

기본적으로 프로세스마다 최소 1개의 쓰레드를 소유한다.

프로세스는 각각 별도의 주소 공간을 확보한다.
- Code : 전역 변수, 정적 변수, 배열 등
  - 초기화된 데이터는 Data 영역에 저장
  - 초기화되지 않은 데이터는 BSS 영역에 저장
- Heap : 동적 할당 시 사용
- Stack : 지역 변수, 매개 변수, 리턴 값 등

**쓰레드는 Stack만 따로 할당받고 나머지(Data, Code, OS Resource)는 공유한다.**

- 쓰레드는 독립적인 동작을 수행하기 위해 존재 = 독립적으로 함수를 호출할 수 있어야 함
- 함수의 매개 변수, 지역 변수 등을 저장하는 Stack 영역은 독립적으로 할당받아야 함

```text
프로세스는 자신만의 고유 공간 및 자원을 할당받아 사용하는 데 반해, 쓰레드는 다른 쓰레드와 공간 및 자원을 공유하면서 사용한다는 점에서 차이가 있다.
```

<img src="https://goodgid.github.io/assets/img/os/what_is_thread_1.png">

- 다중 쓰레드로 구성된 테스크 구조에서 하나의 서버 쓰레드가 Blocked 상태인 동안에도 동일한 테스크 내의 다른 쓰레드가 실행(Running)되어 빠른 처리가 가능하다.
- 동일한 일을 수행하는 다중 쓰레드가 협력하여 높은 처리율(Throughput)과 성능 향상을 얻을 수 있다.
- 쓰레드를 사용하면 병렬성을 높일 수 있다.

**쓰레드의 이점**

* 반응성(Responsiveness) : 하나의 쓰레드가 Blocked 상태인 경우 다른 쓰레드가 이어서 실행된다.
* 자원 공유(Resource Sharing) : 여러 개의 쓰레드가 바이너리 코드, 데이터, 자원 등을 공유한다.
* MP 아키텍처(MP Architectures) : 각각의 쓰레드는 다른 프로세서에서 병렬적으로 운용된다.

**커널의 구현**

* 커널에 의해 지원이 되는 경우 → 커널 쓰레드
* 라이브러리에 의해 지원이 되는 경우 → 유저 쓰레드