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
#### 1. `JPA` 활용
```text
자바 ORM 인 JPA 를 Spring Data JPA 라이브러리를 통해 활용했습니다. 학습하면서 본 Entity 는 대부분 간단한 예제가 주를 이루었고, 
설명을 듣고 글을 읽는 것 보다는 직접 작은 프로젝트라도 진행하는 것이 좋을 것 같아 DB 접근 기술로 JPA 를 채택해 사용했습니다. 
팀원 모두 처음 사용하는 기술이라 작업 속도가 많이 더뎠지만, JPA 를 사용하면 꼭 마주치게 되는 N+1 문제들을 해결하며 사용 경험을 쌓을 수 있었습니다.
```

#### 2. `Junt5` 테스트 코드 작성
```text
TDD 로 프로젝트를 진행하지는 않았으나 Junit5 라이브러리를 통해 통합 테스트와 단위 테스트 코드를 작성했습니다.
테스트를 작성하며 프로젝트를 진행하며 미처 발견하지 못했던 부분들 (잘못된 변수 전달, 네이밍 등) 을 바로 잡을 수 있었습니다.
최초 테스트 코드를 작성했을 때는 상당한 시간이 소요되었지만, 각 API 가 통과하는 것을 한번에 깔끔하게 확인하며 API 의 견고함을 확신할 수 있었고
매번 Postman 과 같은 클라이언트로 수동 테스트를 하는 것 보다 더욱 빠르게 문제점을 파악할 수 있었습니다. 
현재 IntellJ 내장 테스트 커버리지를 통해 Class 88%, Method 78%, Line 78% 커버리지가 달성되었고, 통합 테스트에서 외부 라이브러리를 테스트하는 것에 
대해서는 더욱 학습이 필요한 것 같아 이 부분은 서로 학습해 공유 및 작업 진행 예정에 있습니다.
```

#### 2-1. `테스트 커버리지`
<img width="855" alt="스크린샷 2022-11-21 오후 5 51 07" src="https://user-images.githubusercontent.com/79444040/203006397-653d658f-3292-4112-8b52-2d97db9681c1.png">



#### 3. `GitHub Action` 자동배포
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
- [Crispin 포스팅](https://crispindeity.github.io/posts/(PROJECT)API-최적화/)

#### 이슈 내 연관된 Entity 수정 시 N + 1 문제 발생
- [Tany 포스팅] (채워주세요!)
- [Crispin 포스팅) (채워주세요!) 

#### OAuth 관련 단위 테스트 작성 시 외부 라이브러리 테스트
- [Crispin 포스팅](https://crispindeity.github.io/posts/(TEST)외부-API-테스트/)

#### 페이징 처리 시 Count Query를 따로 작성하지 않아 발생했던 문제 해결
- [Crispin 포스팅](https://crispindeity.github.io/posts/(PROJECT)페이징-처리-시-발생한-문제/)

#### Controller 와 Request DTO 에서 Enum 을 사용했을때 발생했던 문제 해결
- [Crispin 포스팅](https://crispindeity.github.io/posts/(PROJETC)Enum-Converter/)

#### Swith - Case -> 인터페이스 분리 리팩토링
- [Tany 포스팅](https://jeonboard.tistory.com/131)

<br>

### <img height="20px" src="https://user-images.githubusercontent.com/78953393/202724902-5c0e2787-eb17-45e0-9faa-a119d4eeaa5b.png" /> 프로젝트 소감

> Tany
```text
6월 중순에 시작해 8월 ~ 9월 안으로 끝내자고 다짐한 프로젝트가 12월이 다되서야 끝이 났습니다.

코드스쿼드를 수료했을 때 제대로 마무리한 프로젝트가 하나도 없어서 마지막 프로젝트는 꼭 한 번 끝까지 해보고 싶었고,

다른 팀원 분들의 도움 끝에 무사히 마친 것 같습니다. 최초 목표로 잡은 JPA 활용, Test 코드 작성, 배포 자동화를 모두 경험해서

자신감을 얻을 수 있었고, 프로젝트를 진행하면서 모르는 것, 모르는지도 모르는 그런 키워드들이 아직 굉장히 많다는 것도 새삼 깨닫게 되었습니다.

지금 껏 기능개선을 해본 경험이 없었는데, N + 1 문제를 해결하며 1500개 가량의 쿼리가 20개 내외로 줄고 속도가 10배 빨라진 경험을 해보며

이래서 기능개선을 하는구나 ~ 라는 느낌을 처음 받았는데, 엄청난 성취감을 통해 개발자의 뿌듯함? 이 뭔지도 알게 되었네요.

하지만 트러블 슈팅이 일어나서 어떻게 해결했는지에 대한 과정을 모두 기록하지 못한 점이 정말 아쉽습니다. 이럴 떄 블로그에 포스팅하고 두고두고

상기시키면 좋을 텐데 .. 프로젝트를 새로 시작하게 된다면 보다 꼼꼼히 기록해야겠다! 라는 깨달음도 얻었습니다. 

이슈 트래커 프로젝트 진행 경험으로 다른 프로젝트를 어떤 식으로 진행해야하는지에 대한 틀이 잡힌 것 같아 사이드 프로젝트를 통해 체화하려고 합니다.

정말 많은 것을 배웠 문제에 대한 크고 작은 경험들이 저를 더욱 성장시키는 훌륭한 밑거름이 되었다고 확신합니다 !
```

<br>

> Crispin <br>
```text
채워주세요 !
```
