# TLS, Transport Layer Security(= SSL)
컴퓨터 네트워크 상에서 통신 보안을 제공하기 위하여 고안된 암호 프로토콜이다. 웹 사이트들은 서버와 웹 브라우저 간의 통신을 보안하기 위해서 TLS를 사용할 수 있다.

* TLS protocol의 목적
  * 두 개 이상의 컴퓨터 애플리케이션이 통신하는 것에 있어서, 프라이버시와 데이터 무결성을 제공하는 것이 목적이다.
  * data integrity : 데이터의 일관성(consistency)과 정확성(accuracy)을 보장하는 것. <b>추후에 data integrity에 대하여 자세히 공부하고 링크 걸기.</b>
  
* TLS의 속성
  * The connection is private because symmetric cryptography is used to encrypt the data transmitted.
    * 대칭키 암호화에 쓰이는 키들은 각 연결에서 세션이 시작할 때 유니크하게 생성된다.
    * TLS Handshake
      1. 클라이언트 -> 서버 : ClientHello, 클라이언트에서 가능한 TLS versions, 서버 도메인, 세션 식별자, 암호 설정 등의 정보가 담겨있다.
      2. 서버 -> 클라이언트 : ServerHello, ClientHello의 정보 중 서버에서 사용하기로 선택한 TLS version, 세션 식별자, 암호 설정 정보 등을 보냄.
      3. 서버 -> 클라이언트 : Certificate 메시지를 보낸다. 여기에는 서버의 인증서가 들어간다. 이 인증서는 제 3자의 기관에서 발급받은 것이며, 
      서버가 신뢰할 수 있음을 알린다.
      4. 서버 -> 클라이언트 : ServerKeyExchange를 보낸다. 
        * ServerKeyExchange : 서버 Certificate 메시지가 충분한 데이터를 포함하지 않을 경우 서버에 의해서 보내진다. 
      5. 서버 -> 클라이언트 : ServerHelloDone 메시지를 보낸다. (negotitation이 끝났음을 알리기 위해)
      6. 클라이언트는 전달받은 인증서를 검증한다. 유효 기간, 인증서가 해당 서버에 발급된 것인지 등..
      7. 클라이언트 -> 서버 : ClientKeyExchange를 보낸다.
        * ClientKeyExchange : may contain a PreMasterSecret, public key, or nothing. (Again, this depends on the selected cipher.) 
        * PreMasterSecret : is encrypted using the public key of the server certificate.
        
      8. 서버는 전송받은 정보를 복호화하여 pre-master secret을 알아낸 뒤, 이 정보를 사용해 master secret을 생성한다. 그 뒤 master secret에서 세션 키를 생성해내며, 이 세션 키는 앞으로 서버와 클라이언트 간의 통신을 암호화하는데 사용할 것이다. 물론 클라이언트 역시 자신이 만들어낸 pre-master secret을 알고 있으므로, 같은 과정을 거쳐 세션 키를 스스로 만들 수 있다.

      9. 서버와 클라이언트는 각자 동일한 세션 키를 가지고 있으며, 이를 사용해 대칭 키 암호를 사용하는 통신을 할 수 있다. 따라서 우선 서로에게 ChangeCipherSpec 메시지를 보내 앞으로의 모든 통신 내용은 세션 키를 사용해 암호화해 보낼 것을 알려준 뒤, Finished 메시지를 보내 각자의 핸드셰이킹 과정이 끝났음을 알린다.

      10. 서버와 클라이언트 간에 보안 통신이 구성된다.
