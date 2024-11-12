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
|PayBookStatus|    |     ||
|            |OPENEDE|모금중||
|            |CANCELED|취소됨||
|            |CLOSED|모금종료||


| ENUM | 값 | 설명 | 비고 |  
|------|----|------|------|
|PaStatus|  |     |       |
|            |PAID|납부||
|            |UNPAID|미납||
|            |LATE_PAID|연체 납부||
|            |EXCLUDED|제외||
