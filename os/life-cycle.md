# Program lifecycle phases
프로그램이 생성부터 배포와 실행 동안 겪는 단계들
무조건 linear한 순서로 발생하는 것이 아니고, 여러 단계가 섞일 수도 있다.

## Edit time
- the source code of program is being edited.

## Compile time
컴파일러에 의해 소스코드가 기계어로 번역되는 시간을 의미한다.

* 컴파일러에 의해 수행되는 동작
	* 성공적으로 컴파일 되기 위해서 소스 코드가 충족해야 하는 프로그래밍 언어 요구사항, 또는 컴파일 시간 동안 추론될 수 있는 프로그램 속성을 나타낸다.
	* 보통 어휘 분석, 구문 분석, 의미 분석, 코드 생성을 한다.
		* 어휘 분석 (Lexical analysis)
			* 정규 문법에 따라, 의미 있는 조각을 검출하는 단계이다.
			ex) simple is the best 에서 s, i, m, ... 은 의미없지만 simple은 의미가 있다.
		* 구문 분석 (Syntax analysis)
			* 소스 코드의 문법을 검사하는 구문
			* 어휘 분석기에서 생성한 토큰 간의 관계가 올바르게 형성되었는지 체크하는 단계
		* 의미 분석 (Semantic analysis)
			* 구문 트리와 심볼 테이블에 있는 정보들을 이용하여 소스 코드가 언어적 정의에 부합하는지 검사한다.
			* <b>type checking</b>
			* type check를 통하여 컴파일러는 피연산자와 연산자의 의미가 부합하는지를 확인한다.

		* 코드 생성
			* 분석된 소스 코드를 의미에 맞게 기계어나 어셈블리어로 변환하는 단계


## Link time
필요한 기계어 컴포넌트들을 연결하는 단계 (including externals)

* 링크 타임에 수행되는 동작들은 보통 외부에서 참조되는 객체들과 함수들의 주소 수정, 다양한 종류의 크로스 모듈 검사를 포함한다.
* 몇몇 최적화는 링크 타임에 진행되는데, 사용 가능한 프로그램 정보가 그 때서야 존재하기 때문이다.

## Distribution time
프로그램의 복사본을 유저에게 전송하는 단계.

## Installation time 
복사 되어지면 바로 실행할 수 있는 프로그램도 있지만, 그렇지 않은 프로그램도 존재한다. 그러므로, 설치 단계가 필요한데, 일단 설치가 단계가 시작되면 다음에 재설치 할 일이 없을 때까지 실행을 반복한다.

## Load time
OS가 프로그램의 실행 파일을 저장소로부터 가져오고, 메모리에 실행하기 위하여 적재하는 단계이다.

* loading이 완료되면, os는 프로그램을 시작하고 적재된 프로그램 코드에 제어권을 넘긴다.


## Run time
프로그램 실행 단계, cpu가 프로그램의 명령어를 실행한다.

* run-time 상에서 발생하는 에러
	* Division by zero
	* Running out of memory
* 예외 처리는 런타임 에러를 처리하도록 설계된 기능이며, 예측할 수 있는 오류나 일반적이지 않은 오류들뿐 아니라 완전히 예측하지 못한 상황을 잡아내는 구체적인 방안을 제공한다.

### 출처
- https://en.wikipedia.org/wiki/Program_lifecycle_phase
- https://untitledtblog.tistory.com/9
- https://en.wikipedia.org/wiki/Link_time
- https://en.wikipedia.org/wiki/Installation_(computer_programs)
- https://stackoverflow.com/questions/846103/runtime-vs-compile-time
