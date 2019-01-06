# Basic
* apache vs apache tomcat (= tomcat) <br />
Apache tomcat은 WAS(web server + web container)이므로, Web서버의 기능도 가지고 있다. 그러나, apache (or nginx)와 apache tomcat을 연동하는 이유는 tomcat의 web서버 기능이 
느리기 때문이다.
다음은 tomcat과 apache의 비교내용이다. 

```
    1.    Tomcat is not as fast as Apache when it comes to static pages.
    2.    Tomcat is not as configurable as Apache.
    3.    Tomcat is not as robust as Apache.
    4.    Tomcat may not address many sites' need for functionality found only in Apache modules (e.g. Perl, PHP, etc.).
```

이런 이유에서, web server와 WAS의 역할을 나누고 서로 연동하는 방식을 사용하곤 한다.

* apache와 tomcat 연동
apache와 tomcat의 방법을 살펴보면, apache(web server)는 다음과 같은 것을 해야한다.

  * Before the first request can be served, Apache needs to load a web server adapter library (so Tomcat can communicate with Apache) and initialize it.
  * When a request arrives, Apache needs to check and see if it belongs to a servlet; if so it needs to let the adapter take the request and handle it.

Apache는 정적인 컨텐츠 제공을 하고(HTML,images, etc..) 동적인 컨텐츠를 위한 것은 Tomcat으로 forward한다.  

이렇게 셋팅한다고 했을 때, 다음과 같은 것들이 궁금할 수 있다.

```
  1. How will Apache know which request / type of requests should be forwarded to Tomcat?
  2. How will Apache forward these requests to Tomcat?
  3. How will Tomcat accept and handle these requests?
```  

그리고 위의 것들에 대해서 다음이 해답이 될 수 있다. 
```
  1.    Modify Apache's httpd.conf file.
  2.    Install a web server adapter.
  3.    Modify Tomcat's server.xml file.
```

이제 어떤 것들을 해야 하는지 한 번 알아가보자!!! (그 전에 tomcat을 알아봅시다.)

# Tomcat
- Tomcat은 servlet container이다.
  - servlet container : 서블릿을 관리하며 네트워크 통신, 서블릿의 생명주기, 스레드 기반의 병렬처리를 대행한다. (web client로부터 http 요청이 전달되면 요청을 해석하여 적정한 servlet의 service method를 호출한다.


* tomcat directory 구조
  * bin : startup / shutdown scripts를 포함하고 있는 폴더
  * conf : 설정 파일을 담고 있는 폴더
    * server.xml : tomcat main configuration file
    * web.xml : tomcat에서 deploy 되는 다양한 웹 애플리케이션에 대한 설정
  * doc: 잡다한 문서들
  * libs : tomcat에 사용되는 다양한 jar files (UNIX에서는 이 디렉토리의 모든 파일들이 tomcat의 classpath에 추가된다고 한다.)
    !! classpath란 : jvm이 프로그램을 실행할 때, 클래스 파일을 찾는데 기준이 되는 경로
  * logs : tomcat log
  * src : servlet API source files. (servlet container에 의해 구현되어야 할 인터페이스나 추상 클래스가 존재)
  * webapps : web application
  
  * work : tomcat에 의하여 자동으로 생성되는 디렉토리. complied JSP files와 같은 것들이 위치한다. (삭제하면 JSP 페이지 실행 불가)
  * classes : 추가적인 class를 classpath에 추가하기 위한 디렉토리
  
* tomcat scripts
tomcat은 Java 프로그램이기 때문에 환경설정만 한다면, command line으로도 실행 가능하다. 일반적으로 가장 중요한 script를 보자.
  * tomcat : tomcat의 main script이다. CLASSPATH, TOMCAT_HOME and JAVA_HOME과 같은 환경변수를 설정하고 적절한 comamnd line 매개변수와 tomcat을 실행한다.
  
  * startup : tomcat을 background에 실행한다.
  * shutdown : tomcat 실행을 중단한다.
  
* tomcat's configuration files
  * server.xml : global configuration for tomcat
  * web.xml : configuration for tomcat's various contexts


## server.xml
* two goals
  * providing initial configuration for tomcat's components
  * tomcat을 위한 구조 지정, server.xml에 지정된대로 tomcat이 component를 인스턴스화 하고 빌드 및 실행하는 것을 허용
  
* server.xml's elements
  * Server : single tomcat server를 정의한다. (전체 파일 설정의 최상위 element)
  * Service : 하나의 Engine과 결합된 Connnector들의 모임을 나타냄. 기본 값은 Catalina로 되어있고, 에러 로그 및 관리 툴은 이 이름으로 식별한다.
  * Engine : servlet의 인스턴스를 표시한다. Connector로 부터 전달된 요청을 처리한다. 기본 값은 name="Catalina" defaultHost="localhost" 으로 되어있고, 이름으로 에러 로그 및 관리 툴을 식별. defaultHost 속성은 server.xml에 정의 되어있지 않은 <Host>의 요구가 있을 경우 발송되는 가상 호스트를 지정한다.
  * Host : Engine에 관련된 가상 호스트를 정의한다. (host는 특정 가상호스트로의 모든 요청을 다룬다. 하나의 context는 특정 애플리케이션의 모든 요청을 다룸)

```
<Engine name="Catalina" defaultHost="localhost">
	...
	<Host name="localhost" appBase="webapps" unpackWARs="true" autoDeploy="true">
	</Host>
	
	<Host name="127.0.0.1" appBase="test/" unpackWARs="true" autoDeploy="true">
	</Host>
	...
</Engine>
```

1. localhost로 접속 시 : ${tomcat_path}/webapps/ 실행 -  톰켓 기본 페이지
2. 127.0.0.1로 접속 시 : ${tomcat_path}/test/ 실행 - 기본적으로 폴더는 생성해주어야 한다.

  * Connector : web server나 user's browser의 연결을 나타냄. tomcat의 worker thread와 read/write와 request/response의 소켓 연결 등을 관리한다. Connector는 요청을 받고 응답을 하는 end point이다. 각 connector는 요청을 처리하기 위해 container에 전달한다.
    * The handler class.
    * The TCP/IP port where the handler listens.
    * The TCP/IP backlog for the handler server socket.
    
  * Context : 각 Context는 web application을 배치하는 tomcat의 계층 구조를 나타낸다.
    1. path는 context가 위치한 곳이다. full path이거나 ContextManager's home에 상대 경로로 나타낼 수 있다.
    2. reloadable flag를 설정하여 shutdown and restart 없이 reload가 가능하다.
    
  * Context Manager : ContextInterceptors, RequestInterceptors, Contexts and their Connectors를 위한 configuration과 구조를 나타낸다. 
  * ContextInterceptor & RequestInterceptor : ContextManager의 event를 listen한다. 
    * ContextInterceptor : startup and shutdown events of Tomcat을 listen
    * RequestInterceptor : user request의 다양한 단계들을 listen 한다.



## web.xml
* Web application의 deployment descriptor
* WEB-INF 아래에 위치하고, 모든 Web application들은 하나의 Web.xml을 갖는다.
  * WEB-INF : This directory contains all things related to the application that aren’t in the document root of the application. 
  * WEB-INF에 디렉토리에 포함된 파일은 컨테이너에 의해 클라이언트에 직접 제공될 수 없다. 
  	* Servlet context에서 getResource에 의하여 노출될 수 있음.
	* 브라우저에서 Context Root 하위의 정보에 대해 접근할 수 있지만 WEB-INF에는 접근할 수 없다는 뜻이기 때문에, url로 Jsp 파일 등에 대한 직접 접근을 제한할 수 있다.
	* 반대로 브라우저가 참조해야 하는 .js 파일이나 .css 파일 등은 WEB-INF가 아닌 루트 폴더 아래에 static 디렉토리를 만들어서 하위에 넣기도 한다.
  * WEB-INF/classes : 서블릿을 포함한 자바 클래스 파일들이 위치.
  * WEB-INF/lib : jar 파일이 여기에 위치. WEB-INF/classes와 동일한 자바 바이트 코드가 존재할 수 있는데, 이런 경우 톰캣 클래스 로더는 
  WEB-INF/classes를 먼저 찾아 메모리에 로드한다. (WEB-INF/lib에 있는 클래스는 무시됨)
  
  * 톰캣 클래스 로더 <b>추후에 톰캣 클래스 로더에 관하여 조사해보자 </b>
  	* 다음과 같은 순서대로 클래스를 찾는다
  		1. 자바 코어 라이브러리
		2. 웹 애플리케이션 WEB-INF/classes
		3. 웹 애플리케이션 WEB-INF/lib
		4. CATALINA_HOME/lib
  * META-INF
  	* 자바 패키징 기술인 jar의 일부
	* META-INF폴더는 자바에서 설정관련 파일을 저장하는 폴더
	* jar 파일들을 풀어보면 META-INF 폴더 아래 MANIFEST.MF 라는 파일이 있고 사양서 내용이 있다.
  	* manifest
		* The manifest is a special file that can contain information about the files packaged in a JAR file. 	  	 	  * jar file에 대한 스펙이나 사용 메뉴얼을 갖고있다고 생각하면 된다.
