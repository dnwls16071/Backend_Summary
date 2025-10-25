# Process Synchronization

### ✅경쟁 상태(Race Condition)🌟

공유된 자원에 대해 여러 프로세스가 동시에 접근할 때, 결과값에 영향을 줄 수 있는 상태
> 동시 접근 시 자료의 일관성을 해치는 결과가 나타남

<img src="https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fbc4ac288-f38f-434a-be40-218a0fb9a021%2FUntitled.png&blockId=6c9ad803-1898-4603-9cfe-045f93eade99">

##### Race Condition이 발생하는 경우🌟

* 커널 작업을 수행하는 중에 인터럽트 발생
  * 문제점 : 커널모드에서 데이터를 로드하여 작업을 수행하다가 인터럽트가 발생하여 같은 데이터를 조작하는 경우
  * 해결법 : 커널모드에서 작업을 수행하는 동안, 인터럽트를 disable 시켜 CPU 제어권을 가져가지 못하도록 한다.
* 사용자 프로세스가 시스템 콜을 호출하여 커널 모드로 진입해 작업을 수행하는 도중 문맥 교환이 발생할 때
  * 문제점 : 프로세스1이 커널모드에서 데이터를 조작하는 도중, Time Interrupt가 발생해 CPU 제어권이 프로세스2로 넘어가 같은 데이터를 조작하는 경우
  * 해결법 : 프로세스가 커널모드에서 작업을 하는 경우 시간이 초과되어도 CPU 제어권이 다른 프로세스에게 넘어가지 않도록 한다.
* 멀티 프로세서 환경에서 공유 메모리 내의 커널 데이터에 접근할 때
  * 문제점 : 멀티 프로세서 환경에서 2개의 CPU가 동시에 커널 내부의 공유 데이터에 접근하여 조작하는 경우
  * 해결법 : 커널 내부에 있는 각 공유 데이터에 접근할 때마다 그 데이터에 대한 lock/unlock을 하는 방법

### ✅OS에서의 Race Condition 해결 1🌟

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbxdDDQ%2FbtqDpfzjx6s%2F83ql1d0Nk8FgN5OXOvflq1%2Fimg.png">

* 위와 같은 상황에서 Race Condition이 발생하지 않도록 하는 방법 
  * 커널모드 수행 중 인터럽트 발생 시 인터럽트 처리 루틴이 실행되지 않도록 하는 것

### ✅OS에서의 Race Condition 해결 2🌟

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FBjXB2%2FbtqDohRUTi4%2FUvwd3JIVjF4ua1CraZTWWK%2Fimg.png">

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FHKCzY%2FbtqDqivy5O3%2FjX0y6kepLpnizBrkpKvF5k%2Fimg.png">

* 프로세스 A에서 수행 도중 시스템 콜을 호출 → 시스템 콜을 호출하여 OS에서 타임 슬롯을 할당하여 카운트 로직을 실행하던 도중 시간이 만료되어 인터럽트가 발생해 프로세스 B로 CPU 제어권이 넘어감
* 이 때, 프로세스 B에서 카운트 로직을 실행하고 다시 프로세스 A로 CPU 제어권을 넘겨줌
* 결과적으로 원하는 것은 프로세스 A에서 카운트 로직을 한 번 수행하고 프로세스 B에서 카운트 로직을 한 번 수행해 총 2가 증가하는 것을 상상했으나 실제로는 1만 증가되는 것을 알 수 있음
* 따라서 이런 문제를 해결하기 위해선 커널모드에서 동작을 수행 중일 때 타임 슬롯이 만료되었더라도 다른 프로세스에서 CPU를 선점하지 않도록 하는 것이 필요함
  * 유저 모드 : 제한된 명령어만 실행할 수 있는 일반 모드
  * 커널 모드 : 모든 명령어를 실행할 수 있는 특권 모드

### ✅OS에서의 Race Condition 해결 3🌟

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcIYCYT%2FbtqDmV9OFyJ%2FlnofaszDPKBK0Afx7YIWh0%2Fimg.png">

* 멀티 프로세서 환경(CPU가 여러 개)에서는 이전에 제시된 방법들로는 해결이 어려움
* 하나의 CPU에서 공유 데이터에 접근하는 과정에서 인터럽트를 발생시켰다고 하더라도 다른 CPU에서 공유 데이터에 접근하는 것이 가능하기 때문
* 그래서 커널 내부에 있는 공유 데이터에 접근할 때마다 그 데이터에 대한 lock/unlock을 하는 방법을 사용함
* lock을 걸면 lock을 소유한 프로세스만이 해당 데이터에 접근할 수 있음

### ✅Process Synchronization 문제🌟

* 공유 데이터의 동시 접근은 데이터의 불일치 문제를 발생시킬 수 있다.
* **데이터 일관성을 유지하기 위해서 협력 프로세스 간의 실행 순서를 정해주는 메커니즘이 필요**
* **Race Condition(경쟁 상태)**
  * 여러 프로세스들이 공유 데이터에 접근하는 상황
  * 데이터 최종 연산 결과는 마지막에 그 데이터를 다룬 프로세스에 따라 달라짐
* Race Condition 문제를 해결하기 위해서는 동시에 실행되는 프로세스 간 동기화가 되어야 한다.

### ✅임계 영역(Critical Section)🌟

* N개의 프로세스가 공유 데이터를 동시에 사용하기 원하는 경우
* 각 프로세스의 코드 세그먼트에는 공유 데이터에 접근하는 코드인 Critical Section이 존재
* **하나의 프로세스가 Critical Section에 있을 때, 다른 모든 프로세스는 Critical Section에 들어갈 수 없도록 해야 한다.**

### ✅Critical Section 프로그램적 해결법의 충족 조건🌟

* **Mutual Exclusion(상호 배제)**
  * 프로세스 P가 Critical Section 부분을 수행 중이라면 다른 모든 프로세스들은 Critical Section에 들어가면 안 된다.
* **Progress(진행)**
  * Critical Section에 아무도 없는 상태에서 Critical Section에 들어가고자 하는 프로세스가 있다면 곧바로 들어가게 해주어야 한다.
* **Bounded Waiting(유한 대기)**
  * 프로세스가 Critical Section에 들어가려고 요청한 후부터 그 요청이 허용될 때까지 다른 프로세스들이 Critical Section에 들어가는 횟수에 한계가 있어야 한다.

### ✅세마포어(Semaphores)🌟

* 공유된 자원에 대해 동시에 접근하면 문제가 발생할 수 있다.
* 이 때, 공유된 자원 데이터는 한 번에 하나의 프로세스만 접근할 수 있도록 제한을 두어야 한다.
* **세마포어** : 멀티 프로그래밍 환경에서 공유 자원에 대한 접근을 제어하는 방법
  * 세마포어 P 연산 : 임계 구역 들어가기 전에 수행(프로세스 진입 여부를 자원의 개수(S)를 통해 결정)
  * 세마포어 V 연산 : 임계 구역에서 나올 때 수행(자원 반납 알림, 대기 중인 프로세스를 깨우는 신호)

```c
P(S);

// --- 임계 구역 ---

V(S);
```

### ✅뮤텍스(Mutex)🌟

* 임계 구역을 가진 쓰레드들의 실행 시간을 서로 겹치지 않고 각각 단독으로 실행되게 하는 기술
* lock : 현재 임계 구역에 들어갈 권한을 얻는 것
* unlock : 현재 임계 구역을 모두 사용했음을 알림
 
### ✅식사하는 철학자 문제(Dining Philosophers Problem)란?

<img src="https://user-images.githubusercontent.com/20302410/51963180-1363f600-24a6-11e9-8fa2-ca2b40589b1c.png">

* 다섯 명의 철학자가 원탁에 앉아 있고, 식탁 중앙에 식사가 있고, 양 옆에 젓가락 한 짝씩 있다.
* 각각의 철학자는 다른 철학자에게 말을 할 수 없다.
* 이 떄, 철학자가 식사를 하기 위해서는 양 옆의 젓가락 짝 하나씩을 집어 들어야 한다.
* 데드락(DeadLock) 가능성이 있다.
* 모든 철학자가 동시에 배가 고파져 왼쪽 젓가락 짝을 집은 경우 오른쪽 젓가락은 자신의 오른쪽에 있는 철학자가 잡게 되는 구조이기 때문에 무한정 대기해야 한다.

### ✅데드락(DeadLock)🌟

> 두 개 이상의 프로세스나 쓰레드가 서로 자원을 얻지 못해서 다음 처리를 하지 못하고 무한히 다음 자원을 기다리는 상태
* 멀티 프로그래밍 환경에서 한정된 자원을 얻기 위해 서로 경쟁하는 상황에서 발생
* 한 프로세스가 자원을 요청했을 때, 동시에 그 자원을 사용할 수 없는 상황이 발생할 수 있음. 이 때, 프로세스는 대기 상태로 진입함
* 대기 상태에 들어간 프로세스들이 실행 상태로 변경될 수 없는 것을 데드락, 교착 상태라고 함
* 데드락 발생 조건(4가지가 모두 성립해야 데드락이 발생, 단 하나라도 성립하지 않으면 데드락 해결이 가능)
  * 상호 배제(Mutual Exclusion) : 자원은 한 프로세스만 사용할 수 있음
  * 점유와 대기(Hold and Wait) : 최소한 하나의 자원을 점유하고 있으면서 다른 프로세스에게 할당되어 사용되는 자원을 추가로 점유하기 위해 대기하는 프로세스가 존재해야 함
  * 비선점(Non preemption) : 다른 프로세스에게 할당된 자원은 강제로 빼앗을 수 없음
  * 순환 대기(Circular Wait) : 순환 형태로 자원을 대기하고 있어야 함
* 데드락 처리
  * 예방(Prevention) : 교착 상태 발생 조건 중 하나를 제거하면서 해결한다. → 자원 낭비가 심하다
  * 회피(Avoidance) : 교착 상태 발생 시 피해나가는 방법 → 은행원 알고리즘
    * `은행원 알고리즘` : 프로세스가 자원을 요구할 때, 시스템은 자원을 할당한 후에도 안정 상태로 남아있게 되는지를 사전에 검사하여 교착 상태 회피
  * 탐지(Detction) & 회복(Recovery) : 자원 할당 그래프를 통해 교착 상태를 탐지, 교착 상태 일으킨 프로세스를 종료하거나 할당된 자원을 해제시켜 회복


### ✅모니터(Monitor)🌟

* 동시 수행중인 프로세스 사이에서 abstract data type의 안전한 공유를 보장하기 위한 high-level synchronization construct
* 모니터 내에서는 한 번에 하나의 프로세스만이 활동 가능
* 프로그래머가 동기화 제약 조건을 명시적으로 코딩할 필요가 없음