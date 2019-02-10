# Spring MVC workflow

Spring mvc 동작원리에 대해서 찾은 것을 정리해보자.

<img width="740" alt="2019-02-10 12 03 27" src="https://user-images.githubusercontent.com/2508849/52528898-710d0380-2d2c-11e9-81c6-bcb4c6d53fc1.png">

1. Client request가 dispatcher servlet에 도달
	* Dispatcher servlet은 web.xml에 등록되어 있음. [tomcat](https://github.com/wlsgussla123/TIL/blob/master/tomcat/apache-tomcat.md) 을 공부하면서 web.xml을 간략하게 봤었다.
		* 브라우저가 java servlet에 접근하기 위해서 was에 필요한 정보를 알려주어야 한다.
			* web.xml은 deploy할 때 Servlet의 정보를 설정해준다.
				* 배포할 Servlet이 무엇인가
				* 해당 Servlet에 어떤 URL이 맵핑되어 있는가
	* 보통 DispatcherServlet의 url pattern이 *로 되어있어, 모든 요청을 받는다.

2. DispatcherServlet은 HandlerMapping에 적절한 Controller를 선택하도록 위임한다.
	*  HandlerMapping : 해당 컨트롤러에 대한 정보들을 갖고있다.
		* 클래스, 메서드, 파라미터 타입, 리턴 타입 등 결정된 컨트롤러의 모든 정보를 갖고있다.
3. HandlerMapping이 controller 정보를 준다.

4.  DispatcherServlet이 HandlerAdapter를 이용하여 실제 그 controller를 호출할 수 있다. 
	* 컨트롤러가 결정되었다 하더라도, 타입 등에 따라 호출하는 방식이 다르기 때문에 DispatcherServlet에서는 알 방법이 없다. 그래서 컨트롤러 타입을 지원하는 HandlerAdapter라는 것이 존재하는데, 이를 이용한다.
	* how to find controller by handlermapping?
		* https://stackoverflow.com/questions/8643007/which-is-a-controller-and-which-is-handlermapping-in-spring-mvc
		* HandlerInterface를 구현한 다수의 구현체가 존재하는데, 이를 설정하여 찾는 방식을 정할 수 있다. 
		* 만약 설정되어 있지 않다면, `BeanNameUrlHandlerMapping`가 default handler mapping으로 사용될 것이다. 
			* BeanNameUrlHandlerMapping: URL과 일치하는 이름을 갖는 빈을 컨트롤러로 사용한다.

5. Controller가 request를 전달받고 Service layer를 호출하는 등의 행위를 통하여 비지니스 로직을 처리한다.
	* logic을 처리하고 view name을 return 한다.

6. DispatcherServlet이 응답 결과를 생성할 뷰 객체를 구하기 위하여 ViewResolver를 이용한다.
	* ViewResolver 객체는 ModelAndView 객체에 담긴 뷰 이름을 이용해서 View 객체를 찾거나 생성해서 리턴한다.
	* ViewResolver: **A  _ViewResolver_  determines both what kind of views are served by the dispatcher and from where they are served**.

7. ViewResolver가 View를 찾고 dispatcherServlet에 넘긴다.
8. DispatcherServlet이 View 객체를 호출한다.
9. View는 model 데이터를 렌더링하고 응답한다. 
  	

### 출처
* https://java2blog.com/spring-mvc-tutorial/
* https://gmlwjd9405.github.io/2018/10/29/web-application-structure.html
* https://terasolunaorg.github.io/guideline/1.0.1.RELEASE/en/Overview/SpringMVCOverview.html
* https://www.baeldung.com/spring-dispatcherservlet
* https://stackoverflow.com/questions/23325111/spring-mvc-handlermapping-vs-handleradapter/23325291
* http://wiki.gurubee.net/display/DEVSTUDY/02.HandlerMapping
