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

- 스프링이 제공하는 서블릿 구현체 DispatcherServlet
	- Front controller pattern
		- 모든 요청을 컨트롤러 하나가 받아서, 요청을 처리할 핸들러에 전달
	- Dispatcher Servlet
		- Front controller 역할
		- Root WebApplication Context를 상속받는 application context를 하나 더 만든다.
			- (기존의 Servlet context에 application context가 존재할 경우 상속)
			- Root WebApplication Context는 서블릿 간 공유 가능
				- 공통으로 사용할 bean들 등록
			- Servlet WebApplication Context는 그 Dispatcher servlet scope 안에서만 사용할 수 있다.

	- 스프링 구동 할 때
		- 서블릿 컨테이너가 먼저 뜨고, 컨테이너 내의 서블릿 애플리케이션에 스프링을 연동 (DispatcherServlet이나 ContextLoaderListener)
	- 스프링 부트로 구동 할 때
		- Java application이 먼저 뜨고, 그 안에 embedded tomcat이 내장 서버로 뜬다. embedded tomcat 안에다가 서블릿을 코드로 등록


### DispatcherServlet
1. 요청을 분석한다. (locale, multi-part 등)
2. 요청을 처리할 핸들러를 찾는다. (핸들러 맵핑에 위임)
3. 해당 핸들러를 실행할 수 있는 핸들러 어댑터를 찾는다.
4. 핸들러 어댑터를 사용해서 핸들러의 응답을 처리한다. (reflection 이용)
5. (부가적으로) 예외가 발생했다면, 예외 처리 핸들러에 요청을 위임
6. 핸들러의 리턴 값을 보고, 어떻게 처리할지 판단
	- 뷰 이름에 해당하는 뷰를 찾아서 모델 데이터를 렌더링
	
- DispatcherServlet이 사용하는 HandlerMapping, ViewResolver 등은 어디서 오는가?
	- 필요한 bean을 생성하여 등록해주어야 함.
	- VewResolver 목록에 매칭되는 bean들을 넣는다.
		- 없으면, default view resolver (config 파일에 정의 가능)
		
- DispatcherServlet은 초기화 되는 과정에서 여러가지 인터페이스들을 초기화 한다.
	- MultipartResolver, LocaleResolver, ThemeResolver, HandlerMapping 등..
	- MultipartResolver : 파일 업로드 요청을 처리하는 인터페이스
		- HTTP header의 Content-Type이 multipart
		- binary 데이터를 부분 부분 쪼개서 보낸다.
			- MultipartResolver 구현체에 위임하여 이 데이터를 처리할 수 있도록...
				- HttpServletRequest를 MultipartHttpServletRequest로 변환해주어 요청이 담고있는 File을 꺼낼 수 있는 API 제공
	- LocaleResolver : 지역정보 확인에 사용되는 인터페이스
	- ThemeResolver : 애플리케이션에 설정된 테마를 파악하고 변경할 수 있는 인터페이스
	- HandlerMapping: 요청을 처리할 핸들러를 찾는 인터페이스
		- RequestMappingHandlerMapping (annotation 기반)
		- Url 기반의 HandlerMapping도 존재
	- HandlerAdapter : HandlerMapping이 찾아낸, 핸들러를 처리하는 인터페이스
	- HandlerExceptionResolver : @ExceptionHandler로 예외 발생 처리에 활용
	- RequestToViewNameTranslator : 핸들러에서 뷰 이름을 명시적으로 리턴하지 않을 경우, 요청을 기반으로 view 이름을 판단하는 인터페이스
	- ViewResolver : view 이름에 해당하는 view를 찾아내는 인터페이스
		- 기본적으로 InternalViewResolver가 등록
		- .jsp를 기본적으로 지원
	- FlashMapManager : redirect 할 때, 요청 매개변수를 사용하지 않고 데이터를 전달하고 정리할 때 사용한다.
		- ex) Post 요청이 오고, get으로 redirect할 때 요청 파라미터로 데이터를 매번 전송할 필요 없음


** web.xml 말고 WebApplicationInitializer에 자바 코드로 서블릿 등록도 가능함.
** 스프링 부트는 java application을 실행하면, embedded tomcat을 구동. 그리고 tomcat에 DispatcherServlet을 등록해준다.
	- 스프링 부트 자동 설정이 해줌.
	- 스프링 부트의 주관에 따라 여러 인터페이스 구현체를 빈으로 등록
