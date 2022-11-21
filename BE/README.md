### <img height="25px" src="https://user-images.githubusercontent.com/78953393/202858325-2895d601-1043-4921-91b6-6238eefa7f84.png" /> BE 구성원

<table>
  <tr>
    <td>
        <a href="https://github.com/crispindeity">
            <img src="https://user-images.githubusercontent.com/78953393/202717705-6905f508-5153-454e-b623-5af3e279e3b5.jpeg" width="100" height="100" />
        </a>
    </td>
    <td>
        <a href="https://github.com/juni8453">
            <img src="https://user-images.githubusercontent.com/78953393/202717589-416e8ada-2cf1-49ed-8bc4-86f6a0258e9b.jpeg" width="100" height="100" />
        </a>
    </td>
  </tr>
  <tr>
    <td><b>Crispin</b></td>
    <td><b>Tany</b></td>
  </tr>
  <tr>
    <td><b>Back-End</b></td>
    <td><b>Back-End</b></td>
  </tr>

</table>

<br>

### <img height="20px" src="https://user-images.githubusercontent.com/78953393/202724902-5c0e2787-eb17-45e0-9faa-a119d4eeaa5b.png" /> 프로젝트의 목적
BE 에서는 아래 3가지 키워드에 최대한 초점을 맞추고 프로젝트를 진행했습니다.
#### `JPA` 활용
```text
자바 ORM 인 JPA 를 Spring Data JPA 라이브러리를 통해 활용했습니다. 학습하면서 간단한 예제가 주를 이루었고, 
설명을 듣고 글을 읽는 것 보다는 직접 작은 프로젝트라도 진행하는 것이 좋을 것 같아 DB 접근 기술로 JPA 를 채택해 사용했습니다. 
팀원 모두 처음 사용하는 기술이라 작업 속도가 많이 더뎠지만, JPA 를 사용하면 꼭 마주치게 되는 N+1 문제들을 해결하며 사용 경험을 쌓을 수 있었습니다.
```

#### `Junt5` 테스트 코드 작성
```text
TDD 로 프로젝트를 진행하지는 않았으나 Junit5 라이브러리를 통해 통합 테스트와 단위 테스트 코드를 작성했습니다.
테스트를 작성하며 프로젝트를 진행하며 미처 발견하지 못했던 부분들 (잘못된 변수 전달, 네이밍 등) 을 바로 잡을 수 있었습니다.
최초 테스트 코드를 작성했을 때는 상당한 시간이 소요되었지만, 각 API 가 통과하는 것을 한번에 깔끔하게 확인하며 API 의 견고함을 확신할 수 있었고
매번 Postman 과 같은 클라이언트로 수동 테스트를 하는 것 보다 더욱 빠르게 문제점을 파악할 수 있었습니다. 
통합 테스트에서 외부 라이브러리를 테스트하는 것에 대해서는 더욱 학습이 필요한 것 같아 이 부분은 서로 학습해 공유 및 작업 진행 예정에 있습니다.
```

#### `GitHub Action` 자동배포
```text
GitHub 에서 제공하는 GitHub Action 을 활용해 자동으로 서버에 코드를 적용하도록 했습니다.
기존에는 수동으로 빌드 -> (테스트) -> 배포 과정을 진행해 서버에 코드를 적용시켰는데, 이에 자동화 구축이 필수라고 느꼈고 학습 후 적용하였습니다.
프로젝트 시작부터 제일 먼저 자동화를 구축했는데, 기존 작업이 모두 자동화가 되었기 때문에 그 만큼 프로젝트 진행 속도가 빨라진걸 몸소 체험할 수 있었습니다.
Jenkins 등 다양한 툴이 있었지만 GitHub 자체에서 제공하는 툴을 사용해보고 싶어서 Action 을 적용했습니다.
```

<br>

### <img height="20px" src="https://user-images.githubusercontent.com/78953393/202724902-5c0e2787-eb17-45e0-9faa-a119d4eeaa5b.png" /> 주요 트러블슈팅 해결 및 리팩토링 내용

#### 전체 Issue 조회 시 JPA N + 1 문제 발생
- [Tany 포스팅](https://jeonboard.tistory.com/129)
- [Crispin 포스팅) (채워주세요!) 

#### 이슈 내 연관된 Entity 수정 시 N + 1 문제 발생
- [Tany 포스팅] (채워주세요!)
- [Crispin 포스팅) (채워주세요!) 

#### OAuth 관련 단위 테스트 작성 시 외부 라이브러리 테스트
- [Crispin 포스팅) (채워주세요!) 

#### Swith - Case -> 인터페이스 분리 리팩토링
- [Tany 포스팅](https://jeonboard.tistory.com/131)

<br>

### <img height="20px" src="https://user-images.githubusercontent.com/78953393/202724902-5c0e2787-eb17-45e0-9faa-a119d4eeaa5b.png" /> 프로젝트 소감

> Tany <br>
- 채워주세요 !

> Crispin <br>
- 채워주세요 !
