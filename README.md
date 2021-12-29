# JWT 토큰 사용하여 로그인 API 구현


##### 테스트 환경

```
스프링 부트 2.6.2 sts
h2-console
```



# JWT

#### Header

> 시그니처를 해싱하기 위한 알고리즘 정보가 들어가있음

#### payload

> 서버와 클라이언트가 주고받는, 시스템에서 실제로 사용될 정보에 대한 내용

#### signature

> 토큰의 유효성 검증을 위한 문자열, 이 문자열을 통해 서버에서는 이 토큰이 유요한 토큰인지를 검증할 수 있음



장점

> 중앙의 인증서버, 데이터 스토어에 대한 의존성이 없다. 시스템 수평 확장이 유리

> Base64 URL Safe Encoding 방식으로 URL, Cookie, Header 등 어디에서 사용할 수 있는 범용성

단점

> payload의 정보가 많아지면 네트워크 사용량 증가, 데이터 설계 고려 필요

> 토큰이 클라이언트에 저장, 서버에서 클라이언트의 토큰을 조작할 수 없다.



임시 jwt secret key 생성 명령어

```
echo 'yoon-tech-spring-boot-jwt-tutorial-secret-yoon-tect-spring-boot-jwt-tutorial-secret'|base64
```



postman에서의 토큰 테스트

```
var jsonData = JSON.parse(responseBody)

pm.globals.set("jwt_tutorial_token",jsonData.token);

```

