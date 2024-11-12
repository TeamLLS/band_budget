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


## 2-2. BudgetSnapshot
| 도메인 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|BudgetSnapshot|      |      |      |      |
|              |id|Long|Budget Id|Primary Key|
|              |clubId|Long|Club Id|Club 추적키, NotNull|
|              |payload|String|스냅샷 데이터||
|              |time|Instant|생성일||


## 2-3. PayBook 
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


## 2-4. PayMember 
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


# 4. 예외

| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|BudgetNotActiveException|      |      |해당 Budget이 만료되었음을 표시|RuntimeException|
|                        |budgetId|Long|Budget Id||


| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|PayBookNotInClubException|      |      |해당 Club에 포함된 PayBook이 아님을 표시|RuntimeException|
|                         |clubId|Long|Club Id||
|                         |payBookId|Long|PayBook Id||

| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|PayBookNotOpenedException|      |      |해당 PayBook이 만료되었음을 표시|RuntimeException|
|                         |payBookId|Long|PayBook Id||
|                         |status|String|현재 상태||

| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|PayBookNotClosedException|      |      |해당 PayBook이 활성 상태임을 표시|RuntimeException|
|                         |payBookId|Long|PayBook Id||
|                         |status|String|현재 상태||


# 5. 주요 컴포넌트

| 컴포넌트 | 설명 | 비고 |  
|----------|------|------|
|KafkaConsumerService|kafka 메시지 소비용 컴포넌트||
|BudgetController|Budget 관련 엔드포인트||
|BudgetService|Budget 관련 비즈니스 로직 수행||
|BudgetStore|Budget 관련 DB 접근||
|PayBookController|PayBook 관련 엔드포인트||
|PayBookService|PayBook 관련 비즈니스 로직 수행||
|PayBookStore|PayBook 관련 DB 접근||
|PayMemberController|PayMember 관련 엔드포인트||
|PayMemberService|PayMember 관련 비즈니스 로직 수행||
|PayMemberStore|PayMember 관련 DB 접근||


# 6. ERD
![erd](https://github.com/user-attachments/assets/01335612-a9bc-4f61-8628-8357f262afda)
