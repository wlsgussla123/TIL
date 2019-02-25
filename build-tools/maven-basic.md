## maven 정리
* maven은 기본적으로 mvn [options] [goals] [phases] 의 형태로 조합하여 실행하는 것이 가능.
	* 예로, maven은 기본적으로 실행할 때 단위 테스트를 진행하는데, mvn -Dmaven.test.skip=true [phases]와 같이 실행할 수 있다.
	* -D는 maven 설정 파일에 인자를 전달할 때 사용.
	* 여러 개의 phases와 goal도 조합 가능
		* mvn clean compiler:compile: clean 페이즈를 실행하고, compiler 플러그인의 compile goal을 실행한다.

	* -f : pom.xml 파일이 아닌 다른 파일을 읽고자 할 때.
		* mvn -f mypom.xml test
* pom.xml
	* groupId : 프로젝트 생성하는 조직의 고유 id
	* artifactId : 프로젝트를 식별하는 고유 id
		* maven은 중앙 저장소를 가지고, 모든 라이브러리를 관리해야 하기 때문에 프로젝트 이름이 고유해야 함. 따라서, groupId + artifactId로 관리
	* build : build와 관련한 디렉토리 구조, 산출물 위치 등을 관리
		* sourceDirectory : 실제 서비스를 담당하는 자바 소스 코드를 관리하는 디렉토리 
			* src/main/java
		* testSourceDirectory : 테스트 소스를 관리하기 위한 디렉토리. 
			* src/test/java
		* outputDirectory : sourceDirectory 소스를 컴파일한 결과물이 위치하는 디렉토리
			* target/classes
		* testOutputDirectory : testSourceDirectory의 소스를 컴파일한 결과물이 위치하는 디렉토리
			* targat/test-classes
		* resources : 서비스에 사용되는 자원 관리 
			* src/main/resource
	* maven의 기본 디렉토리를 변경하고 싶으면, 최상위 POM에서 정의한 내용을  재정의 하면 된다. 
		

### maven lifecycle
* 기본 라이프 사이클 : 컴파일, 테스트, 압축, 배포를 담당하는 기본 라이프 사이클
* clean : 빌드한 결과물을 제거하기 위한 clean 라이프 사이클
* site : 프로젝트 문서 사이트를 생성하는 라이프 사이클

* 기본 라이프 사이클
	* compile : 소스 코드를 컴파일 한다.
	* test : JUnit과 같은 단위 테스트 프레임워크로 단위 테스트를 한다. 기본 설정은 단위 테스트가 실패해도 빌드 실패로 간주한다.
	* package : 단위 테스트가 성공하면 <packaging />에 따라 압축한다.
	* install : 로컬 저장소에 압축한 파일을 배포한다.
	* deploy : 원격 저장소에 압축한 파일을 배포한다.
	* 패키지 간에 의존 관계가 있기 때문에 의존 관계에 앞서 있는 phase가 실행된다. 


* clean 라이프 사이클
	* maven 빌드를 통해 생성된 산출물을 삭제한다. (기본적으로 target 디렉토리의 하위에 존재)

* site 라이프 사이클
	* maven 설정 정보를 통하여, 프로젝트에 대한 문서 사이트를 만들 수 있도록 제공


### maven phases and plugins
* maven에서 제공하는 기능은 모두 플러그인 기반으로 동작	
	* maven phases 또한 실질적으로 플러그인을 기반으로 동작한다.
		* ex) <artifactId>maven-compiler-plugin</artifactId> 
	* maven plugin은 하나의 플러그인에서 다양한 goal을 실행할 수 있도록 제공한다.
		* compiler 플러그인은 compile, testCompile, help goal을 가진다.
* 플러그인 규칙
	* maven은 중앙 저장소에 위치한 플러그인을 찾기 위한 groupId 목록을 settings.xml 의 pluginGroups 태그로 관리할 수 있다.
		* 이 플러그인 그룹 목록에 포함되면, name:goal과 같이 실행 가능하다.
			* ex) compiler:compile
			* 그렇지 않으면, mvn org.apache.maven.plugins:compiler:compile



* maven phases와 플러그인의 관계
	* maven 빌드 라이프 사이클에서 phases는 빌드 단계와 각 단계의 순서만을 정의한 개념이다.
		* phases가 실질적인 작업을 하지않고, phases와 연결된 goal이 실질적인 빌드 작업을 한다.
			* ex) mvn compile는 compile에 연결된 compiler의 compile 골이 실행되면서 빌드 작업
			* 기본 페이즈에 대한 작업은 별도의 설정 없이도 알아서 플러그인을 다운 받아서 실행해준다.
	* mvn package
		* compile, test-compile, test, package 순으로 실행된 다음, war, jar 파일이 target 디렉토리 하위에 생성된다.
		* 이름 규칙 : ${finalName}.${packaging}
			* finalName 미 설정시 : ${artifactId}-${version}.${packaging}
	* mvn clean
		* clean phase는 다른 phase와 의존 관계가 없다. 
		* 다른 phase를 실행할 때, 충돌 방지를 할 수 있어서 하면 좋음.
