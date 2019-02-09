빌드 툴 사용에 미숙해서 공부한 내용을 간단하게 적어보려 한다.

# maven ?
 - Making the build process easy
 - Providing a uniform build system
 - Providing quality project information
 - Providing guidelines for best practices development
 - Allowing transparent migration to new features

 -> 소프트웨어가 어떻게 빌드되고, 어떤 디펜던시들을 가지고 있는지를 기술한다. (build tool + project management 역할 가능)

 ## maven 특징
 - 프로젝트들이 일관된 디렉토리 구조와 빌드 프로세스를 유지할 수 있다.
 - maven은 repository로부터 java 라이브러리나 플러그인 등을 내려받고, local cache(.m2)에 저장한다. 다운로드 된 artifacts의 local cache들은 local project에
 의해서 업데이트 될 수도 있고, 물론 remote repository역시 업데이트 될 수 있다.

 ## maven 설정 파일
 1. settings.xml
 - The settings element in the settings.xml file contains elements used to define values which configure Maven execution in various ways,
 like the pom.xml, but should not be bundled to any specific project, or distributed to an audience.
 These include values such as the local repository location, alternate remote repository servers, and authentication information.

- maven global settings: ${maven.home}/conf/settings.xml
- A user’s settings: ${user.home}/.m2/settings.xml

maven build tool과 관련된 설정이다.

 2. pom.xml
 - Project Object Model, 프로젝트를 위한 모든 설정을 제공한다. 프로젝트 이름, 버전 등 기본 설정과 각 phase에 대한 빌드 프로세스 등도 정의할 수 있다. 
 - 규모가 있는 프로젝트면, 각 모듈마다 하나의 pom.xml를 가지고 있고 root pom.xml에 모듈로 추가하면 된다. 공통적인 디펜던시들도 root pom.xml에 추가할 수 있다.
  - 프로젝트 정보
	  - name, packaging, version, properties, profile 등을 정의할 수 있다.
  - 디펜던시 정보
	  -  groupId, artifactId, version 정보를 통하여 디펜던시를 내려 받는다.
	  -  scope 
		  - 해당 dependency가 포함되는 범위에 대한 타입  
			  - compile
				  - 기본 scope
			  - provided
				  - 기본 제공되는 API인 경우, provided로 지정 시 마지막 패키징에 포함 되지 않는다. (ex) tomcat에서는 servlet api를 기본으로 제공하기 때문에 패키징시에 제외됨.
			  - runtime
				  - 컴파일 시에는 불필요한 경우
				  - 런타임 및 테스트 시에는 classpath에 추가되지만, 컴파일 시에는 그렇지 않음.
			  - test
				  - 테스트 시에만 사용
			  - system
				  - system의 특정 path를 참조하도록 지정
				  - provided와 비슷
			  - import
				  - dependencyManagement 섹션에서 pom의 의존 관계에 사용
 
```xml
<project>
    <groupId>com.navercorp.line</groupId>
    <artifactId>new-project</artifactId>
    <modelVersion>4.0.0</modelVersion>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
</project>
```
* groupId : 프로젝트의 그룹명, 일반적으로 회사 도메인명을 거꾸로 쓴다.
* artifactId : 프로젝트 이름, groupId에서 유니크 해야한다.
* modelVersion : maven xml 버전, 4.0을 쓴다
* version : 해당 artifact의 버전, SNAPSHOT은 개발 중
* packaging : 어떤 파일 형식으로 패키징 할 것인가를 정의

* 빌드 정보
	* maven life cycle, 세부적으로 phase 존재
		* default
			* compile
			* test
			* package
			* install
			* deploy
		* site: 
			* site
			* site-deploy
		* clean
		
		-   _validate:_  check if all information necessary for the build is available
-   _compile:_  compile the source code
-   _test-compile:_  compile the test source code
-   _test:_  run unit tests
-   _package:_  package compiled source code into the distributable format (jar, war, …)
-   _integration-test:_  process and deploy the package if needed to run integration tests
-   _install:_  install the package to a local repository
-   _deploy:_  copy the package to the remote repository
(출처 : https://www.baeldung.com/maven-goals-phases )

1. mvn process-resources: resources의 실행으로 resources 폴더에 있는 내용을 target/classes로 복사
2. mvn compile: compile의 실행으로 src/java 밑에 있는 모든 java 파일을 컴파일 하여 target/classes로 복사
3. mvn process-testResources, mvn test-compile: test/java의 내용을 target/test-classes
4. mvn test: target/-test-classes에 있는 테스트케이스의 단위 테스트를 진행한다. 결과를 target/surefire-reports에 생성한다.
5. mvn package : target 디렉토리 하위에 jar, war, ear 등 패키지 파일을 생성
6. mvn install: 로컬 저장소로 배포
7. mvn deploy: 원격 저장소로 배포 (회사 repo에 배포, nexus 같은..)
8. mvn clean: 빌드 과정에서 생성된 target 디렉토리 삭제
9. mvn site: target/site에 문서 사이트 생성
10. mvn site-deploy: 문서 사이트를 서버로 배포  

* Phase와 Goal
	* Phase
		* phase는 build life-cycle의 각각의 단계를 의미한다.
		* phase는 특정 순서에 따라서 goal 실행되도록 구조를 제공한다.
		* phase의 순서는 의존관계가 있어서, 이전 phase가 실행된 다음에 순서대로 실행되어야 한다.
	* Goal
		* Each phase is a sequence of goals and each goal is responsible for a specific task.
		* phase를 실행하면, 관련된 모든 goal들이 같이 실행되어진다.
		 
	* Phase와 Goal의 관계
		* maven에서 제공하는 모든 기능은 plugin 기반으로 동작
		* maven 기본 phase에는 해당 phase와 연결된 plugin의 goal들이 실행된다.
		* 각 phase는 0 또는 1개 이상의 goal들과 바인딩 된다.
		* Maven에서 플러그인을 실행할 때 '플러그인이름:플러그인지원골'의 형식으로 실행 할 기능을 선택 할 수 있다.
		* 예를들어 mvn compiler:compile은 'compiler' 플러그인에서 'compile' 기능(goal)을 실행한다는 것을 뜻 한다.

		* phase and default goals
			* compiler:compile - compile phase
			* compiler:testCompile - test-compile phase
			* surefire:test - test phase 
			* install:install - install phase
			* jar:jar and war:war - package phase

### 출처
- https://jeong-pro.tistory.com/168
- https://goddaehee.tistory.com/48
- https://en.wikipedia.org/wiki/Apache_Maven#cite_note-maven2repo-3
- https://maven.apache.org/settings.html
- https://wikidocs.net/18338
- http://wiki.gurubee.net/display/SWDEV/Maven+Lifecycle
- https://www.baeldung.com/maven-goals-phases
