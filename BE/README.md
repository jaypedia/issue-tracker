# BE 구성원

- [@타니](https://github.com/juni8453)
- [@검봉](https://github.com/geombong) 

# DB 요구사항 

# ERD

# API 분석

<details>
<summary>📌로그인/회원가입 API</summary>

## 로그인/회원가입 필요 API

- 아이디/비번 로그인 API
- GitHub 로그인 API
- 회원가입 API

### 아이디/비번 로그인 API
#### 로그인 시 Client -> Server Request
> POST `/signin`

```json
{
  "userId": "TestID", //(NOT NULL)
  "password": "1234" //(NOT NULL)
}
```
### 회원가입 API
#### 회원가입 시 Client -> Server Request
> POST `/signup`

```json
{
  "userId": "TestID", //(NOT NULL)
  "password": "1234", //(NOT NULL)
  "email": "TestID@gmail.com" //(NOT NULL)
}
```

</details>

<details>
<summary>📌이슈 리스트 API</summary>

## 이슈 리스트 필요 API

- 모든 이슈 리스트 API
- 열린 이슈 리스트 API(default)
- 닫힌 이슈 리스트 API

### 모든 이슈 리스트 API
#### 모든 이슈 리스트 확인 시 Server -> Client Response
> GET `/issues`

```json
[
  {
    "Id": 1,
    "issueTitle": "이슈1 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "issueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  },
  {
    "Id": 2,
    "issueTitle": "이슈2 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": false //(CLOSE 상태)
  },
  {
    "Id": 3,
    "issueTitle": "이슈3 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  }
]

/*
List 형태로 각 이슈가 담긴다.
모든 이슈가 보이기 때문에 Open, Close 이슈 모두가 담겨야 한다. 
각 이슈 객체는 댓글을 남긴 사용자들의 리스트를 가지고 있다.
댓글을 있을 수도 있고, 없을 수도 있다.
만약 댓글이 없다면 빈 리스트가 Response 된다.
*/
```

### 열린 이슈 리스트 API(default)
#### 열린 이슈 리스트 확인 시 Server -> Client Response
> GET `/issues/{status}`

```json
[
  {
    "Id": 1,
    "issueTitle": "이슈1 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "issueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  },
  {
    "Id": 3,
    "issueTitle": "이슈3 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  }
]

/*
1 번, 3 번 이슈는 Open 상태, 2 번 이슈는 Close 상태이기 때문에 
1 번, 3 번 이슈만 리스트에 담겨 Response 된다.
*/
```

### 닫힌 이슈 리스트 API
#### 닫힌 이슈 리스트 확인 시 Server -> Client Response
> GET `/issues/{status}`

```json
[
  {
    "Id": 2,
    "issueTitle": "이슈2 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": false //(CLOSE 상태)
  }
]

/*
1,2,3 중 닫힌 이슈는 2번이기 때문에 현재 2번만 리스트에 담긴 모습
*/
```

</details>

<details>
<summary>📌이슈 필터 API</summary>

```text
필터는 현재 3가지로 나눌 수 있다.

내가 작성한 이슈, 나에게 할당된 이슈, 내가 댓글을 남긴 이슈

클릭하는 필터는 한번에 하나만 할 수 있도록 하고, 검색으로 필터링을 할 때, 여러 조건을 걸 수 있도록 한다.
```

## 이슈 필터 필요 API

- 내가 작성한 이슈 API
- 자신에게 할당된 이슈 API
- 내가 댓글을 남긴 이슈 API

### 내가 작성한 이슈 API
#### 자신이 작성한 이슈 중 닫힌 이슈일 때 Server -> Client Response 여기서 자신이라 함은 TestID 를 뜻한다.
> GET `/issues/created_by?status=close&id=myID`
> myID: 현재 로그인 되어있는 계정의 아이디

```json
[
  {
    "Id": 2,
    "issueTitle": "이슈2 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": false //(CLOSE 상태)
  }
] 
```

#### 자신이 작성한 이슈 중 열린 이슈일 때 Server -> Client Response
> GET `/issues/created_by?status=open&id=myID`
> myID: 현재 로그인 되어있는 계정의 아이디

```json
[
  {
    "Id": 1,
    "issueTitle": "이슈1 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  },
  {
    "Id": 3,
    "issueTitle": "이슈3 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  }
] 
```
### 자신에게 할당된 이슈 API
#### 닫힌 이슈 중 자신에게 할당된 이슈일 때, Server -> Client Response
> GET `/issues/managed_by?status=close&id=myID`
> myID: 현재 로그인 되어있는 계정의 아이디

```json
[
  {
    "Id": 2,
    "issueTitle": "이슈2 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": false //(CLOSE 상태)
  }
] 
```

#### 열린 이슈 중 자신에게 할당된 이슈일 때, Server -> Client Response
> GET `/issues/managed_by?status=open&id=myID`
> myID: 현재 로그인 되어있는 계정의 아이디

```json
[
  {
    "Id": 1,
    "issueTitle": "이슈1 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  },
  {
    "Id": 3,
    "issueTitle": "이슈3 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  }
] 
```

### 내가 댓글을 남긴 이슈 API
#### 내가 댓글을 남긴 이슈 중 닫힌 이슈일 때 Server -> Client Response
> GET `/issues/commented_by?status=close&id=myID`
> myID: 현재 로그인 되어있는 계정의 아이디

```json
[
  {
    "Id": 2,
    "issueTitle": "이슈2 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": false //(CLOSE 상태)
  }
] 
```

#### 내가 댓글을 남긴 이슈 중 열린 이슈일 때 Server -> Client Response
> GET `/issues/commented_by?status=open&id=myID`
> myID: 현재 로그인 되어있는 계정의 아이디

```json
[
  {
    "Id": 1,
    "issueTitle": "이슈1 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  },
  {
    "Id": 3,
    "issueTitle": "이슈3 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  }
]

```

</details>


<details>
<summary>📌이슈 검색 API</summary>

## 이슈 검색 필요 API
- 제목 검색 API (클릭이 아닌 직접 제목을 검색해서 필터링 하는 것, 제목을 이슈1 이라고 검색했다고 가정한다.)

### 제목 검색 API
#### 이슈 제목으로 검색 시 Server -> Client Response
> GET `/issues?title=이슈타이틀`

```json
[
  {
    "Id": 1,
    "issueTitle": "이슈1 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  }
]
```
#### 닫힌 이슈 제목으로 검색 시 Server -> Client Response
> GET `/issues?title=이슈타이틀&status=close`

```json
[
  {
    "Id": 1,
    "issueTitle": "이슈1 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  }
]
```

#### 열린 이슈 제목으로 검색 시 Server -> Client Response
> GET `/issues?title=이슈타이틀&status=open`

```json
[
  {
    "Id": 1,
    "issueTitle": "이슈1 타이틀", //(NOT NULL)
    "issueWriter": "TestID", //(NOT NULL)
    "issueCreateTime": "yyyy-mm-dd", //(NOT NULL)
    "LabelName": "레이블 이름",
    "MileStoneName": "마일스톤 이름",
    "IssueWriterImage": "이슈 작성자 프로필 이미지", //(NOT NULL)
    "issueStatus": true //(OPEN 상태)
  }
]
```

</details>

<details>
<summary>📌이슈 목록 API</summary>

> Server -> Client 로 Response 하는 JSON 데이터는 위와 동일하다.

- 담당자로 조회 API

1. 닫힌 이슈 API
> GET `/issues?menager=담담자아이디&status=close`
2. 열린 이슈 API
> GET `/issues?menager=담담자아이디&status=open`

- 레이블로 조회 API

1. 닫힌 이슈 API
> GET `/issues?label=레이블이름&status=close`
2. 열린 이슈 API
> GET `/issues?label=레이블이름&status=open`

- 마일스톤으로 조회 API

1. 닫힌 이슈 API
> GET `/issues?millstone=마일스톤이름&status=close`
2. 열린 이슈 API
> GET `/issues?millstone=마일스톤이름&status=open`

- 작성자로 조회 API

1. 닫힌 이슈 API
> GET `/issues?writer=작성자이름&status=close`
2. 열린 이슈 API
> GET `/issues?writer=작성자이름&status=open`

</details>

<details>
<summary>📌이슈 탭 API</summary>

## 이슈 탭 필요 API
- 레이블 목록 API
> GET `/labels`
- 마일스톤 목록 API
> GET `/milestones`
 
>Server -> Client 로 Response 하는 JSON 데이터는 위와 동일하다.

</details>

<details>
<summary>📌이슈 상태 수정 API</summary>

## 이슈 상태 수정 필요 API

- 단일선택 이슈 상태 수정 API
- 다중선택 이슈 상태 수정 API

### 단일선택 이슈 상태 수정 API
#### 하나의 이슈 상태를 수정할 때 Client -> Server Request
> PATCH `/issues/{id}/{status}
> 이슈의 상태는 현재 상태의 반대를 요청한다. 예를 들어 `open`인 이슈는 `close`를 보내고, `close`이슈는 `open`을 보낸다.

```json
{
  "issueId": "1", //(NOT NULL)
  "requestIssueStatus": false //(NOT NULL)
}

/*
현재 1 번 이슈의 상태는 true (OPEN) 상태이기 때문에, 반대 boolean 타입을 Request 한다.
*/
```

### 다중선택 이슈 상태 수정 API
#### 여러 개의 이슈 상태를 수정할 때 Client -> Server Request
> PATCH `/issues/{issueId}/{requestIssueStatus}`
> 이슈의 상태는 현재 상태의 반대를 요청한다. 예를 들어 `open`인 이슈는 `close`를 보내고, `close`이슈는 `open`을 보낸다.
> 예시: `/issues/1,2,3/open`

```json
{
  "issues" : 
  [
    {
      "issueId": "1", //(NOT NULL)
      "requestIssueStatus": open //(NOT NULL)
    },
    {
      "issueId": "2", //(NOT NULL)
      "requestIssueStatus": open //(NOT NULL)
    },
    {
      "issueId": "3", //(NOT NULL)
      "requestIssueStatus": open //(NOT NULL)
    }
  ]
}

/*
리스트 형태로 선택한 이슈의 번호와 원하는 상태를 Request 한다.
현재 1,3 번 이슈는 true (OPEN) 상태라 false를 Request 한다.
*/
```

</details>

<details>
<summary>📌새로운 이슈 작성 API</summary>

## 새로운 이슈 작성 필요 API
- 이슈 작성 API

### 작성 API
#### 이슈 작성 시 Client -> Server Request
> POST `/issues/write`

```json
{
  "issueTitle": "이슈 제목", //(NOT NULL)
  "issueContent": "이슈 내용",
  "issueWriteDate" : "이슈 작성 날짜",
  "issueWriterName" : "이슈 작성자 이름",
  "files": [
    {
      //"파일과 관련된 Key, Value"
    }
  ],
  "managerName" : "담당자 이름",
  "labelName" : "라벨 이름",
  "mileStoneName" : "마일스톤 이름"
}
```

</details>

<details>
<summary>📌이슈 상세 조회 API</summary>

## 이슈 상세 조회 필요 API
- 이슈 상세 페이지 API
- 제목편집 API
- 코멘트 목록 API
- 코멘트 작성 API
- 코멘트 편집 API

### 이슈 상세 페이지 API
#### 이슈 상세 페이지 접속 시, Server -> Client Response
> GET `/issues/{id}`

```json
{
  "issueTitle": "이슈1 타이틀",
  "issueCount": 1,
  "issueStatus": true,
  "issueCreateTime": "yyyy-mm-dd",
  "commentCount": 1,
  "managers": [
    {
      "managerName": "담당자1",
      "managerImage": "담당자1 이미지 사진"
    },
    {
      "managerName": "담당자2",
      "managerImage": "담당자2 이미지 사진"
    }
  ],
  "labels": [
    {
      "labelName": "레이블1"
    },
    {
      "labelName": "레이블2"
    }
  ],
  "mileStoneName" : "마일스톤 이름",
  "muleStoneDescription" : "마일스톤 설명"
}

/*
managers, labels, mileStoneName 빈 값이 Response 될 때도 있다.
*/
```
### 제목편집 API
#### 제목 편집 시 Client -> Server Request
> PATCH `/issues/{id}`
 
```json
{
  "issueId": 1,
  "issueTitle": "변경할 제목"
}
```

### 코멘트 목록 API
#### 코멘트 목록 조회 시 Server -> Client Response
> GET `/issues/{id}/comments`
```json
[
  {
    "commentWriter" : "작성자 아이디",
    "commentContent" : "코멘트 내용",
    "commentWriteDate" : "코멘트 작성 날짜"
  }
]
```

### 코멘트 작성 API
#### 코멘트 작성 시 Client -> Server Request
> POST `/issues/{id}/comments`
```json
[
  {
    "commentWriter" : "작성자 아이디",
    "commentContent" : "코멘트 내용",
    "commentWriteDate" : "코멘트 작성 날짜",
    "files": [
      {
        //"파일과 관련된 Key, Value"
      }
    ]
  }
]
```

### 코멘트 편집 API
#### 코멘트 편집 시 Client -> Server Request
> PATCH `/issues/{id}/comments`
```json
[
  {
    "commentWriter" : "작성자 아이디",
    "commentContent" : "코멘트 내용",
    "commentWriteDate" : "코멘트 작성 날짜",
    "files": [
      {
        //"파일과 관련된 Key, Value"
      }
    ]
  }
]
```

</details>

<details>
<summary>📌레이블 API</summary>

## 레이블 필요 API
- 레이블 목록 조회 API
- 레이블 추가 API
- 레이블 편집 API
- 레이블 삭제 API

### 레이블 목록 조회 API
#### 레이블 목록에 접속했을 때, Server -> Client Response
> GET `/labels`

```json
{
  "labelCount": 3,
  "labels" : 
  [
    {
      "labelTitle": "레이블 제목", //(NOT NULL)
      "labelDescription": "레이블 설명",
      "labelBackgroundColor": "배경색", //(NOT NULL)
      "labelTestColor": "글자색"//(NOT NULL)
    },
    {
      "labelTitle": "레이블 제목", //(NOT NULL)
      "labelDescription": "레이블 설명",
      "labelBackgroundColor": "배경색", //(NOT NULL)
      "labelTestColor": "글자색"//(NOT NULL)
    },
    {
      "labelTitle": "레이블 제목", //(NOT NULL)
      "labelDescription": "레이블 설명",
      "labelBackgroundColor": "배경색", //(NOT NULL)
      "labelTestColor": "글자색"//(NOT NULL)
    }
  ]
}
```
### 레이블 추가 API
#### 레이블 추가 시 Client -> Server Request
> POST `/labels`

```json
{
  "labelTitle": "레이블 제목", //(NOT NULL)
  "labelDescription": "레이블 설명",
  "labelBackgroundColor": "배경색", //(NOT NULL)
  "labelTestColor": "글자색" //(NOT NULL)
}
```
### 레이블 편집 API
#### 레이블 편집 시 Client -> Server Request
> PATCH `/labels/{id}`

```json
{
  "labelTitle": "레이블 제목", //(NOT NULL)
  "labelDescription": "레이블 설명",
  "labelBackgroundColor": "배경색", //(NOT NULL)
  "labelTestColor": "글자색" //(NOT NULL)
}
```

### 레이블 삭제 API
#### 레이블 삭제 시 Client -> Server Request
> DELETE `/labels/{id}`

```json
{
  "labelId": 1
}
```

</details>

<details>
<summary>📌마일스톤 API</summary>

## 마일스톤 필요 API

- 마일스톤 목록 API
- 마일스톤 추가 API
- 마일스톤 편집 API
- 마일스톤 삭제 API

### 마일스톤 목록 API
#### 마일스톤 목록 접속 시 Server -> Client Response
> GET `/milestones`

```json
{
  "allMileStonesCount": 2,
  "openMileStonesCount": 2,
  "closeMileStonesCount": 0,
  
  "mileStones" :
  [
    {
      "mileStoneTitle": "마일스톤 제목", //(NOT NULL)
      "mileStoneEndDate": "yyyy-mm-dd",
      "mileStoneDescription": "마일스톤 설명",
      "openIssueCount": 1, //(NOT NULL)
      "closeIssueCount": 1 //(NOT NULL)
    },
    {
      "mileStoneTitle": "마일스톤 제목", //(NOT NULL)
      "mileStoneEndDate": "yyyy-mm-dd",
      "mileStoneDescription": "마일스톤 설명",
      "openIssueCount": 1, //(NOT NULL)
      "closeIssueCount": 1 //(NOT NULL)
    }
  ]
}
```

### 마일스톤 추가 API
#### 마일스톤 추가 시 Client -> Server Request
> POST `/milestones`

```json
{
  "mileStoneTitle": "마일스톤 제목", //(NOT NULL)
  "mileStoneEndDate": "yyyy-mm-dd",
  "mileStoneDescription": "마일스톤 설명"
}
```

### 마일스톤 편집 API
#### 마일스톤 편집 시 Client -> Server Request
> PATCH `/milestones/{id}`

```json
{
  "mileStoneTitle": "마일스톤 제목", //(NOT NULL)
  "mileStoneEndDate": "yyyy-mm-dd",
  "mileStoneDescription": "마일스톤 설명"
}
```

### 마일스톤 삭제 API
#### 마일스톤 삭제 시 Client -> Server Request
> DELETE `/milestones/{id}`

```json
{
  "mileStoneId": 1 //(NOT NULL)
}
```

</details>

<details>
<summary>📌담당자 API</summary>

## 담당자 필요 API

- 담당자 목록 API
- 담당자 추가 API

### 담당자 목록 API
#### 담당자 목록 (+ 버튼 클릭) 클릭 시, Sever -> Client Response
> GET `/manager`

```json
{
  "managers" : 
  [
    {
      "managerName": "담당자 이름", //(NOT NULL)
      "managerImage": "담당자 이미지" //(NOT NULL)
    },
    {
      "managerName": "담당자 이름", //(NOT NULL)
      "managerImage": "담당자 이미지" //(NOT NULL)
    }
  ]
}

/*
담당자가 없는 경우 빈 리스트 [] 를 반환한다.
*/
```

### 담당자 추가 API
#### 담당자 추가 시 Client -> Server Request (현재 요구사항에 페이지는 존재하지 않음)
> POST `/manager`

```json
{
  "managerName": "추가할 담당자 이름", //(NOT NULL)
  "managerImage": "추가할 담당자 이미지" //(NOT NULL)
}
```

</details>

# 인프라

# PR 리뷰
