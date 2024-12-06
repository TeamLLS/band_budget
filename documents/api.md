# API 

 
| API | 설명 | DB | 이벤트 | 
|-----|------|----|--------|
|[예산 조회](#예산-조회)|모임의 예산 상태 조회|||   
|[예산 갱신](#예산-갱신)|모임의 예산 상태 갱신||BudgetUpdated|   
|[예산 기록 조회](#예산-기록-조회)|모임의 예산 갱신 기록 조회|||   
|[장부 생성](#장부-생성)|모임에 장부 추가|PayBook 생성|PayBookCreated|   
|[장부 취소](#장부-취소)|장부 등록 취소|PayBook 변경|PayBookCanceled|   
|[장부 만료](#장부-만료)|장부 만료|PayBook 변경|PayBookClosed, PayMemberConfirmed, BudgetUpdated|   
|[장부 목록 조회](#장부-목록-조회)|모임의 장부 목록 조회|||   
|[장부 조회](#장부-조회)|해당 장부 조회|||   
|[납부 대상 등록-전체](#납부-대상-등록-전체)|해당 Club의 모든 회원을 해당 PayBook의 납부대상으로 등록|PayMember 생성|PayMemberCreated|   
|[납부 대상 등록-선택](#납부-대상-등록-전체)|선택된 Member들을 해당 PayBook의 납부대상으로 등록|PayMember 생성|PayMemberCreated|   
|[회원 납부](#회원-납부)|해당 회원의 납부 상태를 "납부"로 변경|PayMember 변경|PayMemberStatusChanged|   
|[회원 미납](#회원-미납)|해당 회원의 납부 상태를 "미납"으로 변경|PayMember 변경|PayMemberStatusChanged|     
|[회원 연체 납부](#회원-연체-납부)|해당 회원의 납부 상태를 "연체 납부"로 변경|PayMember 변경|PayMemberStatusChanged|   
|[납부 대상 제외](#납부-대상-제외)|해당 회원 PayBook의 납부 대상에서 제외|PayMember 변경|PayMemberStatusChanged|     
|[납부 대상 목록 조회](#납부-대상-목록-조회)|해당 장부의 납부 대상 목록 조회|    ||   
|[내 장부 목록 조회](#내-장부-목록-조회)|내가 등록된 장부 목록 조회|    || 


## ▶예산 조회
### GET /budget/{clubId}?time={원하는 시점} 

- time은 선택사항이며, 포함하지 않을시 현재 시점의 예산을 반환 
- time의 시간 형식은 ISO 8601 (UTC 시간)

```
header: {  
  Authorization: Bearer ${accessToken value},
} 
```

### 응답
```
body: {
  id: 예산 id, (Long)
  clubId: 클럽 Id, (Long)
  amount: 원하는 시점의 예산 (Integer)
}
```


## ▶예산 갱신
### POST /budget

```
header: {  
  Authorization: Bearer ${accessToken value},
}

body: {
  clubId: 대상 클럽 Id, (Long)
  description: 설명, (String)
  amount: 갱신 금액 (Integer)
}
```

### 응답
```
```


## ▶예산 기록 조회
### GET /budget/{clubId}/records?pageNo={페이지 번호}&time={원하는 시점} 

- time은 선택사항이며, 포함하지 않을시 현재 시점까지의 기록을 반환 
- time의 시간 형식은 ISO 8601 (UTC 시간)

```
header: {  
  Authorization: Bearer ${accessToken value},
} 
```

### 응답
```
body: {
  list: [
    {
      clubId: 클럽 Id, (Long)
      description: 설명, (String)
      amount: 갱신 액수, (Integer)
      username: 갱신자, (String)
      time: 갱신 시간 (Instant)
    },
    ...
  ]
}
```


## ▶장부 생성
### POST /paybook

```
header: {  
  Authorization: Bearer ${accessToken value},
}

body: {
  clubId: 대상 클럽 Id, (Long)
  amount: 납부 금액, (Integer)
  name: 장부 이름, (String)
  description: 설명 (String)
  deadline: 모금 종료 시간 (Instnat, ISO 8601)
}
```

### 응답
```
```


## ▶장부 취소
### PATCH /paybook/{클럽 Id}/{장부 Id}/cancel

```
header: {  
  Authorization: Bearer ${accessToken value},
}
```

### 응답
```
```

## ▶장부 만료
### PATCH /paybook/{클럽 Id}/{장부 Id}/close

```
header: {  
  Authorization: Bearer ${accessToken value},
}
```

### 응답
```
```


## ▶장부 목록 조회
### GET /paybook/{클럽 Id}/list?pageNo={페이지 번호}

```
header: {  
  Authorization: Bearer ${accessToken value},
} 
```

### 응답
```
body: {
  list: [
    {
      id: 장부 Id, (Long)
      name: 이름, (String)
      status: 상태, (String)
      createdAt: 생성 시간 (Instant)
      deadline: 모금 종료 시간 (Instnat, ISO 8601)
    },
    ...
  ]
}
```


## ▶장부 조회
### GET /paybook/{장부 Id}

```
header: {  
  Authorization: Bearer ${accessToken value},
} 
```

### 응답
```
body: {
  id: 장부 id, (Long)
  clubId: 클럽 Id, (Long)
  createdBy: 생성자 이름, (String)
  name: 이름, (String)
  description: 설명, (String)
  status: 상태, (String)
  amount: 납부 금액, (Integer)
  createdAt: 생성 시간, (Instant)
  closedAt: 만료 시간, (Instant)
  deadline: 모금 종료 시간 (Instnat, ISO 8601)
}
```


## ▶납부 대상 등록-전체
### POST /paymember/{클럽 Id}/{장부 Id}/all

```
header: {  
  Authorization: Bearer ${accessToken value},
}
```

### 응답
```
```



## ▶납부 대상 등록-선택
### POST /paymember/list

```
header: {  
  Authorization: Bearer ${accessToken value},
}

body: {
  clubId: 대상 클럽 Id, (Long)
  payBookId: 대상 장부 Id, (Long)
  list: [Member Id 리스트] (Long[] List)
}
```

### 응답
```
```



## ▶회원 납부
### PATCH /paymember/{장부 Id}/{회원 ID}/pay

```
header: {  
  Authorization: Bearer ${accessToken value},
}
body: {
 {납부 시간} (Instnat, ISO 8601)
}
```

### 응답
```
```

## ▶회원 미납
### PATCH /paymember/{장부 Id}/{회원 ID}/unpay

```
header: {  
  Authorization: Bearer ${accessToken value},
}
```

### 응답
```
```

## ▶회원 연체 납부
### PATCH /paymember/{장부 Id}/{회원 ID}/late-pay

```
header: {  
  Authorization: Bearer ${accessToken value},
}
body: {
 {납부 시간} (Instnat, ISO 8601)
}
```

### 응답
```
```

## ▶납부 대상 제외
### PATCH /paymember/{장부 Id}/{회원 ID}/exclude

```
header: {  
  Authorization: Bearer ${accessToken value},
}
```

### 응답
```
```


## ▶납부 대상 목록 조회
### GET /paymember/{장부 Id}/list?unPay={true|false}&pageNo={페이지 번호}

unPay=true일 경우, 미납자만 조회 (포함하지 않을 경우 자동 false)

```
header: {  
  Authorization: Bearer ${accessToken value},
} 
```

### 응답
```
body: {
  list: [
    {
      id: 납부 대상 Id, (Long)
      payBookId: 장부 Id, (Long)
      username: username, (String)
      memberId: 회원 Id, (Long)
      memberName: 회원 이름, (String)
      status: 상태, (String)
      paidAt: 납부 일자 (Instnat, ISO 8601)
    },
    ...
  ]
}
```


## ▶내 장부 목록 조회
### GET /paymember/{클럽 Id}/paybook/list?unPay={true|false}&pageNo={페이지 번호}

unPay=true일 경우, 미납 장부만 조회 (포함하지 않을 경우 자동 false)

```
header: {  
  Authorization: Bearer ${accessToken value},
} 
```

### 응답
```
body: {
  list: [
    {
      payBookId: 장부 Id, (Long)
      memberName: 이름, (String)
      amount: 납부 금액, (Integer)
      status: 납부 상태 (String)
      time: 장부 생성 시간 (Instant)
      paidAt: 납부 일자 (Instnat, ISO 8601)
    },
    ...
  ]
}
```
