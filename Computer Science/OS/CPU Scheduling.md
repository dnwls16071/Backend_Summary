# CPU Scheduling

### ✅CPU and I/O Bursts in Program Execution

> 프로그램의 실행이라 함은 CPU Burst와 I/O Burst의 반복이라 할 수 있다.
* CPU Burst : load Store, add Store, read from file, store increment, index, write to file
* I/O Burst : wait for I/O

### ✅CPU Burst Time의 분포

<img src="https://velog.velcdn.com/images%2Fdolarge%2Fpost%2Fef511da2-db86-4f8c-8e7a-6d76966e0af3%2Fimage.png">

* 여러 종류의 Job이 섞여있기 때문에 CPU 스케줄링이 필요하다.
* I/O bounded Job : CPU를 아주 짧게 쓰고 I/O를 많이 하는 작업
* CPU bounded Job : CPU를 오래 쓰고 I/O를 적게 하는 작업

### ✅프로세스 특성 분류

* 프로세스는 그 특성에 따라 다음과 같이 두 가지로 나눔
  * I/O-bound Process
    * CPU를 잡고 계산하는 시간보다 I/O에 많은 시간이 필요한 Job(아주 짧은 CPU Bursts)
  * CPU-bound Process
    * 계산 위주의 Job(약한 빈도지만 아주 긴 CPU Bursts)

### ✅CPU Scheduler & Dispatcher

* CPU Scheduler
  * **READY 상태의 프로세스 중에서** 이번에 CPU를 줄 프로세스를 고른다.
* Dispatcher
  * CPU의 제어권을 CPU Scheduler에 의해 선택된 프로세스에게 넘긴다.
  * 이 과정을 문맥 교환이라 한다.
* CPU 스케줄링이 필요한 경우는 다음과 같은 프로세스의 상태 변화가 있는 경우이다.
  * Running → Blocked(I/O 요청 시스템 콜) : I/O 작업과 같은 오랜 시간이 소요되는 경우 가지고 있어봐야 의미가 없으므로 대기 상태로 변경, 다른 프로세스에게 CPU를 양도
  * Running → Ready(타이머 인터럽트)
  * Blocked → Ready(I/O 완료 후 인터럽트)
  * Terminate

### ✅스케줄링

> CPU를 잘 사용하기 위해 프로세스를 배정하는 것
* 조건 : 오버헤드 ↓ / 사용률 ↑ / 기아 현상 ↓
* 목표
  * Batch System : 가능하면 많은 일을 수행, 시간보다는 처리량을 우선시
  * Interactive System : 빠른 응답 시간, 적은 대기 시간
  * Real-time System : 기한 맞추기

### ✅Algorithm Evaluation

* Queueing Models
  * 확률 분포로 주어지는 arrival rate와 service rate 등을 통해 각종 performance index 값을 계산
* Implementation(구현) & Measurement(성능 측정)
  * 실제 시스템에 알고리즘을 구현하여 실제 작업에 대해서 성능을 측정 비교
* Simulation(모의 실험)
  * 알고리즘을 모의 프로그램으로 작성 후 trace를 입력으로 하여 결과를 비교

### ✅선점 / 비선점 스케줄링

* 선점(Preemptive) : OS가 CPU의 사용권을 선점할 수 있는 경우, 강제 회수 역시 가능
* 비선점(NonPreemptive) : 프로세스 종료 or I/O 등의 이벤트가 있을 때까지 실행 보장

### ✅프로세스의 상태 전이🌟

<img src="https://user-images.githubusercontent.com/13609011/91695344-f2dfae80-eba8-11ea-9a9b-702192316170.jpeg">

* Admitted : 프로세스 생성이 가능하여 승인됨
* Scheduler Dispatch : 준비 상태(Ready)에 있는 프로세스 중 하나를 선택하여 실행시키는 것
* Interrupt : 예외, 입출력, 이벤트 등이 발생하여 현재 실행 중인 프로세스(Running)를 준비 상태(Ready)로 바꾸고 해당 작업을 먼저 처리하는 것
* I/O or Event Wait : 실행 중인 프로세스(Running)가 입출력이나 이벤트를 처리해야 하는 경우, 입출력/이벤트가 모두 끝날 때까지 대기 상태(Waiting)로 만드는 것
* I/O or Event Completion : 입출력/이벤트가 끝난 프로세스(Waiting)를 준비 상태(Ready)로 전환하여 스케줄러에 의해 선택될 수 있도록 하는 것

### ✅CPU 스케줄링의 종류🌟

* 비선점 스케줄링
  * FCFS
  * SJF
  * HRN
* 선점 스케줄링
  * Priority Scheduling
  * Round Robin
  * Multilevel-Queue
  * Multilevel-Feedback-Queue

### ✅FCFS(First-Come First-Served)

* 큐에 도착한 순서대로 CPU를 할당하는 방식
* 실행 시간이 긴 것이 앞에 위치할수록 평균 대기 시간이 길어짐

### ✅SJF(Shortest-Job-First)

* 수행시간이 가장 짧다고 판단되는 작업을 먼저 수행
* FCFS보다 평균 대기 시간 감소, 짧은 작업에 유리

### ✅HRN(Highest Response-ratio Next)

* 우선순위를 계산하여 점유 불평등을 보완한 방법(SJF의 단점을 보완)
* 우선순위 = (대기시간 + 실행시간) / (실행시간)

### ✅Priority Scheduling

* 정적/동적으로 우선순위를 부여하며 우선순위가 높은 순서대로 처리
* 우선순위가 낮은 프로세스는 무한정 기다리는 기아 현상이 발생
* 에이징 기법으로 기아 현상 문제 해결 가능

### ✅Round Robin

* FCFS에 의해 프로세스들이 보내지면 각 프로세스는 동일한 `Time Quantum` 만큼 CPU를 할당 받음
* `Time Quantum` 혹은 `Time Slice`, 우리 말로는 타임 슬롯이라고 함
* 할당 시간이 길어지면 FCFS와 같아지게 되고 작으면 Context Switch가 잦아져 오버헤드가 증가함

### ✅Multilevel Queue

<img src="https://user-images.githubusercontent.com/13609011/91695428-16a2f480-eba9-11ea-8d91-17d22bab01e5.png">

* 작업들을 `우선순위에 따른` 여러 종류의 그룹으로 나누어 여러 개의 큐를 이용하는 기법

### ✅Multilevel Feedback Queue

<img src="https://user-images.githubusercontent.com/13609011/91695480-2a4e5b00-eba9-11ea-8dbf-390bf0a73c10.png">

* 우선순위가 낮은 큐들이 실행되지 못하는 것을 방지하고자 각 큐마다 `Time Quantum`을 할당
* 우선순위가 높은 큐는 낮은 `Time Quantum`을 가지고 우선순위가 낮은 큐는 높은 `Time Quantum`을 가짐
