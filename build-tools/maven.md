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
 
