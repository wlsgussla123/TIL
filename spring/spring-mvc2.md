## Spring mvc 공부 정리
* mvc pattern : 사용자 인터페이스로부터 비지니스 로직을 분리하여 애플리케이션의 시각적 요소나 그 이면에서 실행되는 비지니스 로직을 서로 영향없딩 쉽게 고칠 수 있는 애플리케이션을 만들 수 있다.
	* model : 데이터를 담고있는 자바 객체
	* view : 데이터를 보여주는 역할
	* controller : 사용자의 입력을 받아 모델 객체의 데이터를 변경하거나, 모델 객체를 뷰에 전달하는 역할
	
	* High cohesion : 논리적으로 관련있는 기능을 하나의 controller로 묶고, view들을 그룹화 할 수 있다.
	* Low coupling : model, view, controller가 각각 독립적이다.
		* ex) model이 html이든, jsp든, json으로 내려가든 상관없다.



### Servlet
* Java로 웹 애플리케이션을 개발할 수 있도록 스펙과 API 제공
* 요청 당 쓰레드 (만들거나, 풀에서 가져오거나) 사용
	* process를 공유한다.
	*서블릿 인스턴스의 스레드를 사용하는 것이 아니라 (Runnable을 구현하거나, Thread를 상속하지 않는다)..
		* 요청이 들어오면, WAS에서 스레드를 생성하여 서블릿의 service()를 실행한다.

		** WAS의 스레드 수 산정
			* 무작정 thread를 늘려놓으면 process에 영향, 불필요한 resource 사용 
			* WAS에서는 스레드를 pooling해서 사용하고 있기 때문에, 스레드 수를 늘린다는 것은 스레드를 위해서 메모리 등의 자원이 많이 할당된다는 것.
			* JVM에서 OS의 스레드와 JAVA의 스레드를 맵핑하는 방법에 따라서 context switching이 발생하며 성능에 차이가 나게 된다.

* Tomcat, jetty 등은 서블릿 스펙을 구현한 서블릿 컨테이너 또는 서블릿 엔진이다.
	* Servlet application artifact가 배포되고, tomcat이 실행하는 구조
	* Servlet : 우리가 직접 servlet application을 실행할 수 없다. Servlet container가 실행하고 생명주기를 관리한다. 
	* Servlet Container : Servlet의 생명주기를 관리하며, 네트워크 통신, 스레드 병렬처리 등을 한다. 클라이언트로부터 Request를 분석하고 적절한 Servlet의 service()를 호출하여 요청을 처리한다.
		* Servlet Container가 Servlet instance의 init을 호출하여 초기화 한다.
			* 메모리에 로드되고 나면, init()을 다음 요청에는 실행 x
			* Servlet Container는 warm-up을 위해 각 servlet init()을 호출한다.
			
			* service() : Http Request와 Http Response를 만든다. service는 http method에 따라, doGet()과 doPost()로 처리를 위임한다.
			* Servlet Container의 판단에 따라, servlet을 메모리에서 내려야 할 시점에 destroy()를 호출한다.

### Servlet Listener와 Filter
* Servlet Listener
	* 웹 애플리케이션에서 발생하는 주요 이벤트를 감지하고, 각 이벤트에 특별한 작업이 필요한 경우 사용 가능
* Servlet Filter
	* 들어온 요청 전이나, 응답을 보내기 전에 특별한 처리가 필요한 경우에 사용
	* Filter는 chain 을 반드시 연결해줘야 다음으로 넘어간다.


### 스프링 IoC 컨테이너 연동
- 서블릿 app에 스프링 연동하기
	- 서플릿에서 스프링이 제공하는 IoC 컨테이너 활용
		- ContextLoaderListener
			- 동작해야 할 설정파일을 읽고 객체를 등록하고 생성한다.
			- 스프링 IoC 컨테이너 (application context)를 servlet application 생명주기에 맞춰서 binding
			- Web application에 등록되어 있는 Servlet들이 사용할 수 있도록, application context를 만들어서 Servlet context에 등록
				- servlet context : 모든 servlet이 공용으로 사용할 수 있는  context
				- Servlet이 종료될 시점에 application context 제거
			- ContextLoaderListener는 DispatcherServlet 클래스 로드보다 먼저 동작하여, 비지니스 로직층을 정의한 스프링 설정파일을 로드한다.
