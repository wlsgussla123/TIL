# Classloader
Java 클래스 로더란, JRE의 일부로서 런타임 동안에 동적으로 자바 클래스를 jvm에 로딩하는 것을 담당한다. 따라서,jvm이 java program을 실행하기 위해서 기본 파일이나
파일 시스템을 알 필요가 없다.
즉, .class 바이트 코드를 읽어들여 클래스 객체를 생성하는 역할을 담당한다.

  * 클래스 로더는 라이브러리를 위치시키고 내용물을 읽으며 라이브러리들 안에 포함된 클래스들을 읽는 역할을 한다.
    * 로딩은 일반적으로 요청이 오면 이루어짐. (프로그램이 클래스를 요청하지 않으면 로딩되지 않는다.)
    * 클래스는 클래스로더에 의해 한 번만 로드된다.
    
  * JVM의 클래스로더는 계층구조를 가지며, 각 클래스로더는 하나의 부모 클래스로더를 갖는다.(bootstrap 클래스로더 제외)
    * bootstrap classloader
      * <JAVA_HOME>/jre/lib 디렉토리에 위치한 핵심 자바 라이브러리들을 불러들인다.
      * 핵심 JVM의 일부분인 bootstrap classloader는 네이티브로 구현되어 있다.
    * extension classloader
      * bootstrap loading 후, 기본적으로 로딩되는 클래스로 <JAVA_HOME>/jre/lib/ext에 있는 클래스들이 로딩된다. 이 클래스들은 별도로 classpath에 잡혀
      있지 않아도 로딩된다.
    * system classloader
      * CLASSPATH에 정의되어 있거나, JVM option에서 -cp, -classpath에 있는 클래스들이 로딩된다.
    * User-defined class
      * 사용자가 직접 생성해서 사용하는 클래스 로더이다.
      
클래스 로더 계층구조에서 delegation에 의하여, 부모 클래스 로더가 로딩한 클래스를 찾을 수 있지만, 반대는 불가능 하다. 또한, 로드된 클래스 로더에 의해 로드된 클래스는
클래스 로더가 삭제될 때까지 유지된다.
