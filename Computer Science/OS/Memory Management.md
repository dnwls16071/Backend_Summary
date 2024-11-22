# Memory Management

### ✅메인 메모리(Main Memory)

> 메인 메모리는 CPU가 직접 접근할 수 있는 기억장치
> 프로세스가 실행되려면 프로그램이 메인 메모리에 올라와야 함

* CPU는 레지스터가 지시하는대로 메모리에 접근하여 다음에 수행할 명령어를 가져옴
* 명령어 수행 시 메모리에 필요한 데이터가 없다면 데이터를 우선 가져와야 함
* 이 역할을 하는 것이 MMU(Memory Management Unit, 메모리 관리 장치)
* MMU는 논리 주소를 물리 주소로 변환
* 메모리 보호나 캐시 관리 등 CPU가 메인 메모리에 접근하는 것을 총괄하는 하드웨어

<img src="https://velog.velcdn.com/images/ddosang/post/a4c4a59b-1338-4455-bbd1-41da115e1cb4/image.png">

### ✅동적 로딩(Dynamic Loading)

* 프로세스 전체를 메모리에 한 번에 미리 다 올리는 것이 아니라 해당 루틴이 호출될 때 메모리에 load하는 것
* Loading : 메모리에 올리는 것

### ✅오버레이(Overlay)

* 메모리에 프로세스 부분 중 실제로 필요한 정보만을 올림
* 프로세스 크기가 메모리보다 클 때 유용
* 운영체제 지원없이도 사용자에 의해서 구현 가능

### ✅스와핑(Swapping)

* 프로세스를 일시적으로 메모리에서 내쫓는 것
* Swap In : 우선순위가 높은 프로세스를 메모리에 올림
* Swap Out : 우선순위가 낮은 프로세스를 메모리에서 쫓아냄

### ✅Static Linking vs Dynamic Linking

* Linking을 실행 시간까지 미루는 기법
* 정적(Static Linking)
  * 라이브러리가 프로그램 실행 코드에 포함됨
  * 실행 파일의 크기가 커짐
  * 동일한 라이브러리를 각각의 프로세스가 메모리에 올리므로 메모리 낭비
* 동적(Dynamic Linking)
  * 라이브러리가 실행 시 연결됨
  * 라이브러리 루틴 위치를 찾기 위한 stub이라는 작은 코드를 둠
  * 라이브러리가 이미 메모리에 있다면 그 루틴의 주소로 가고 없으면 디스크에서 읽어옴

### ✅메모리 관리 기법 - 연속 메모리 관리🌟

> 프로그램 전체가 하나의 커다란 공간에 연속적으로 할당되어야 함
* 고정 분할 기법 : 주기억장치가 고정된 파티션으로 분할(내부 단편화 발생)
* 가변 분할 기법 : 파티션들이 동적 생성되며 자신의 크기와 같은 파티션에 적재(외부 단편화 발생)

### ✅메모리 관리 기법 - 불연속 메모리 관리🌟

> 프로그램의 일부가 서로 다른 주소 공간에 할당될 수 있는 기법
* 페이징(Paging)
  * 페이지 : 고정 사이즈의 작은 프로세스 조각
  * 고정 크기
  * 내부 단편화 발생
  * 페이징 기법에서 논리 주소를 물리 주소로 변환하기 위해 **페이지 테이블**을 사용한다.

<img src="https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc2083926-d3c5-4f7f-8ede-b9124ce1c27c%2F%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-05-18_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_10.06.31.png&blockId=46beb016-47b7-4d65-acad-6702150e5e0b">

* 세그먼테이션(Segmentation)
  * 세그먼트 : 가변 사이즈의 논리적 블록이 연속적 공간에 배치되는 것
  * 가변 크기
  * 외부 단편화 발생
  * 메모리 사용 효율 개선

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcdfYHZ%2FbtsIhMehv4X%2FTNaXnLBKYKcvLgJbkfkIOk%2Fimg.png">

