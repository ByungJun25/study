# docker - 외부 주소를 찾아야하는 경우, 도커 내부 네트워크를 잊지말자

- 문제 - spring을 이용한 간단한 smtp 메일 서비스를 만들어 도커로 배포하였지만 host를 찾을 수 없는 문제가 발생

- 해결방법  
docker는 자체 내부 네트워크를 구성하고 이를 통해 호스트 네트워크와 통신한다. 따라서 호스트 네트워크와 연결되지 않고, 내부 네트워크만 쓰면 smtp 전송을 위한 host를 찾을수가 없다. 왜냐하면 내부에서만 찾고 있기때문. 따라서 host 네트워크에 바로 연결해서 사용하던가, 아니면 내부 네트워크에서 외부 네트워크를 통해 호스트를 찾을 수 있도록 설정해야한다.