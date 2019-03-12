# Network 
- Loop back (lo0)
	- 전기 신호의 라우팅이 원래의 장치나 장비로 돌아가는 것. 주로 전송 테스트에 사용

- 가상 루프백 인터페이스
	- 인터넷 프로토콜 스위트의 구현은 가상 루프백 인터페이스를 포함한다.
		- 인터넷 프로토콜 스위트 : 인터넷에서 컴퓨터들이 서로 정보를 주고 받는데, 쓰이는 통신 규약 모음
	- 같은 기기에서 작동하는 네트워크 응용 프로그램 클라이언트와 서버는 이것을 통해 통신할 수 있다.
	- 루프백 인터페이스는 OS의 네트워킹 소프트웨어 내에서 완전히 구현되며, 네트워크 인터페이스 컨트롤러에 패킷을 보내지 않는다.
		- NIC (Network Interface Controller, == LAN Card)
			- 컴퓨터를 네트워크에 연결하여 통신하기 위해 사용하는 하드웨어 장치
			- OSI 1,2 layer 장치를 가지는데, 맥 주소를 사용하여 낮은 수준의 주소 할당 시스템을 제공하고, 네트워크 매개체로 물리적인 접근을 가능하게 한다.


* 가상 루프백 인터페이스는 Localhost (ipv4), 127.0.0.1 (::1, ipv6)의 이름을 주로 사용한다.
* 127.0.0.1 vs 0.0.0.0
	* 127.0.0.1
		*  is IP address (loopback or local-only interface)
		* face network adapter로써, same host하고만 통신 가능
	* 0.0.0.0
		* Listen on every available network interface.
		* 서브넷 마스크 0.0.0.0과 같이 사용되며 모든 Ip를 의미.
		* is non-routable address
		* 0.0.0.0 can be used to mean anything from accept all ip address or block ip address to the default route.
		* IPv4 패킷을 전송하고자 하는 컴퓨터가 자신의 ip를 모르는 경우, 통신을 하기 위해 사용한다.
		* 보통 자신의 ip 주소를 모르는 컴퓨터는 부트스트랩이 진행되는 도중에 0.0.0.0을 사용한다. 
			* ex) 발신지: 0.0.0.0, 목적지: 255.255.255으로 pc -> DHCP 서버로.. 
			* 부트스트랩 : 전원을 켜거나 재부팅을 할 때 적재되는 프로그램. 시스템을 모든 측면에서 초기화하며, 운영 체제 커널을 적재하고 실행시킨다.


* DHCP 서버 (Dynamic Host Configuration Protocol)
	* IP router는 인터페이스 및 호스트에 ip 주소를 할당해 줄 수 있다.
		* 예전에는 각 pc 고정 ip 설정 -> 충돌, 오타 위험
	* DHCP는 ip를 필요로 하는 컴퓨터에 자동으로 할당해서 사용할 수 있도록 함
		* 사용하지 않으면, 반환 받아 다른 컴퓨터가 사용할 수 있도록.

* Broadcast address (255.255.255.255)
	* Network address at which all devices connected to a multiple-access communications networks are enabled to receive datagrams.


* Hostname (node name)
	* 네트워크에 연결된 장치 (컴퓨터, 파일 서버, 복사기 등) 들에게 부여되는 고유한 이름
* 인터넷 hostname
	* 인터넷에서 호스트 이름은 연결된 호스트의 이름으로, 보통 호스트의 지역 이름에 도메인 이름을 붙인 것이다. ex) kr.wikipedia.org -> host name, wikipedia.org -> domain name
	* host name은 DNS를 통해 계층적으로 IP주소로 변환되거나, 사용자의 컴퓨터에 있는 host 파일에서 ip 주소를 검색하여 사용 
	
