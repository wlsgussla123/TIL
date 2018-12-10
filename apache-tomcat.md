# Basic
* apache vs apache tomcat (= tomcat)
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

이제 어떤 것들을 해야 하는지 한 번 알아가보자!!!
