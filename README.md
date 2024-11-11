# TODO: 프로젝트 이름

## 프로젝트 모듈 구조

```text
root
│
├── evawova-adapters/
│   ├── adapter-http            # 외부와 통신을 담당하는 모듈
│   ├── adapter-persistence     # DB 와의 통신을 담당하는 모듈
│   └── adapter-redis           # 레디스와 통신을 담당하는 모듈
│
├── evawova-apps/
│   ├── app-api                 # REST API 를 모아둔 모듈
│   └── app-batch               # 배치잡을 모아둔 모듈
│
├── evawova-commons/            # 공통 모듈
│
└── evawova-core/
    ├── core-domain/            # 도메인 모델을 담당하는 모듈
    ├── core-port/              # 외부와의 통신을 위한 인터페이스를 모아둔 모듈
    ├── core-service/           # 비즈니스 로직을 구현하는 모듈
    └── core-usecase/           # 클라이언트에서 호출할 수 있는 인터페이스를 모아둔 모듈
```
