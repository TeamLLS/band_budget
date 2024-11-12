# 1. 패키지

```
band_budget
    ├─budget
    │  ├─command
    │  ├─event
    │  ├─exception
    │  ├─form
    │  └─policy
    ├─core
    ├─external
    │  └─kafka
    ├─PayBook
    │  ├─command
    │  ├─event
    │  ├─exception
    │  ├─form
    │  └─policy
    └─PayMember
        ├─command
        ├─event
        └─form
```

# 2. 도메인

## 2-1. Budget 
| 도메인 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|Budget  |      |      |      |      |
|        |id|Long|Budget Id|Primary Key|
|        |clubId|Long|Club Id|Club 추적키, NotNull|
|        |amount|Intger|예산 총액||
|        |createdAt|Instant|생성일||
|        |expireddAt|Instant|만료일||


## 2-2. PayBook 
| 도메인 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayBook |      |      |      |      |
|        |id|Long|PayBook Id|Primary Key|
|        |clubId|Long|Club Id|Club 추적키, NotNull|
|        |amount|Intger|납부 금액|NotNull|
|        |name|String|이름||
|        |description|String|설명||
|        |status|PayBookStatus|상태|NotNull|
|        |createdAt|Instant|생성일||
|        |expireddAt|Instant|만료일||

| ENUM | 값 | 설명 | 비고 |  
|------|----|------|------|
|PayBookStatus|    |     ||
|            |OPENEDE|모금중||
|            |CANCELED|취소됨||
|            |CLOSED|모금종료||


## 2-3. PayMember 
| 도메인 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayMember|     |      |      |      |
|        |id|Long|PayMember Id|Primary Key|
|        |payBook|PayBook|대상 PayBook|Foreginer Key, NotNull|
|        |username|String|username|User 추적키, NotNull|
|        |memberId|Long|Member Id|Member 추적키, NotNull|
|        |memberName|String|이름||
|        |status|Paytatus|상태|NotNull|

| ENUM | 값 | 설명 | 비고 |  
|------|----|------|------|
|PaStatus|  |     |       |
|            |PAID|납부||
|            |UNPAID|미납||
|            |LATE_PAID|연체 납부||
|            |EXCLUDED|제외||


# 3. 이벤트

## 3-1. Budget 이벤트

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|BudgetEvent|      |      |      |      |
|         |eventId|String|event Id|Event 추적키|
|         |triggerdBy|String|생성자||
|         |clubId|Long|Club Id|Club 추적키|
|         |time|Instnat|발생 시간||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|BudgetCreated|      |      |발생 API: Club 생성|BudgetEvent 상속|

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|BudgetUpdated|      |      |발생 API: Budget 갱신, PayBook 만료|BudgetEvent 상속|
|             |description|String|설명||
|             |amount|Integer|갱신 액수||

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|BudgetClosed|      |      |발생 API: Club 폐쇄|BudgetEvent 상속|


## 3-2. PayBook 이벤트

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayBookEvent|      |      |      |      |
|            |eventId|String|event Id|Event 추적키|
|            |triggerdBy|String|생성자||
|            |payBookId|Long|PayBook Id|PayBook 추적키|
|            |clubId|Long|Club Id|Club 추적키|
|            |time|Instnat|발생 시간||

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayBookCreated|      |      |발생 API: PayBook 생성|PayBookEvent 상속|
|              |name|String|이름||
|              |description|String|설명||

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayBookCanceled|      |      |발생 API: PayBook 취소|PayBookEvent 상속|
|               |status|String|변경 상태||

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayBookClosed|      |      |발생 API: PayBook 만료|PayBookEvent 상속|
|             |status|String|변경 상태||



## 3-3. PayMember 이벤트

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayMemberEvent|      |      |      |      |
|              |eventId|String|event Id|Event 추적키|
|              |triggerdBy|String|생성자||
|              |payBookId|Long|PayBook Id|PayBook 추적키|
|              |memberbId|Long|Member Id|Member 추적키|
|              |time|Instnat|발생 시간||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayMemberCreated|      |      |발생 API: PayMember 등록|PayMemberEvent 상속|
|                |username|String|username|User 추적키|
|                |memberName|String|Member 이름||

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayMemberStatusChanged|      |      |발생 API: 납부 관련 API|PayMemberEvent 상속|
|                      |status|String|변경 상태||

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|PayMemberConfirmed|      |      |발생 API: PayBook 만료|PayMemberEvent 상속|
|                  |status|String|변경 상태||
