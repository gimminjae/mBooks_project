# mBooks_project
글 작성 및 도서 등록, 구매가 가능한 ebook마켓앱과 reader앱으로 이루어진 ebook서비스

## 프로젝트 기간
- 2022.10.17 ~ 2022.11.10
- 2022.11.10 ~ 유지보수 및 고도화

## 📚 Stacks
<div align=center> 
  <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
  <img src="https://img.shields.io/badge/java17-007396?style=for-the-badge&logo=java17&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">
<img src="https://img.shields.io/badge/SPRING SECURITY-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
</div>

### 기술 스택 상세
- 개발 언어 : Java 17
- 개발 환경 : InteliJ IDEA
- front : html5, css3, Javascript, JQuery, Ajax, 
- back : Spring Boot 2.7.2, Gradle, Spring Data JPA, Spring Security, Spring Batch
- DB : mariaDB, MySQLWorkbench
- API : 토스페이먼츠 결제 모듈 API, Gmail API

## Git Convention

### Branch

| 이름          | 설명              |
|-------------|-----------------|
| `master`    | `마스터 브랜치`       |
| `devbranch` | `개발 브랜치`        |
| `Nweek`     | `N주차 과제 수행 브랜치` |

### Commit

- 예시
> 타입(Type): 제목(Title)
- 타입(Type)

|Type             |설명                          |
|----------------|-------------------------------|
|`FEAT`      |`기능개발`           |
|`FIX`       |`버그수정`             |
|`DOCS`      |`문서수정`               |
|`STYLE`     |`스타일수정 (들여쓰기, 세미콜론 등)`  |
|`REFACTOR`     |`리팩토링`  |
|`TEST`     |`테스트코드`  |
|`CHORE`     |`빌드, 패키지매니저 수정 (gitignore 등)`  |



## ERD

## 핵심 기능

### 1. 회원
- 로그인 & 회원가입
- 마이페이지
  - 개인 정보, 글 목록, 등록한 도서 목록, 구매한 도서 목록, 주문 & 결제 내역 등 확인 가능
  - 개인 정보(작가명, 이메일) 수정 가능
- 이메일로 아이디 찾기
- 비밀번호 분실시 이메일로 임시비밀번호 발급 및 전송
- 회원가입 축하 메일 발송
- 사용자는 자신의 잔여 예치금을 관리자에게 출금 신청할 수 있다.
- 출금 신청 취소 가능

### 2. 글
- 제목, 해시태그, 내용을 입력하고 글 작성 가능
- 해시태그로 글 찾기
- 토스트 에디터로 글 작성

### 3. 도서
- 여러개의 글을 모아 하나의 도서로 등록
- 도서 등록시 제목, 가격, 해시태그, 들어갈 글 목록 입력
- 해시태그로 도서 찾기

### 4. 장바구니 & 주문 & 결제
- 장바구니에 도서 담기
- 본인이 등록한 도서, 이미 구매한 도서는 담을 수 없다.
- 도서를 중복하여 담을 수 없다.
- 장바구니 목록을 주문, 결제 가능
  - 토스페이먼츠 결제 모듈
- 결제 취소 기능
- 구매 후 10분 이내 환불 가능, 10분 이후에는 환불 불가

### 5. 관리자 페이지 & 정산
- 관리자 권한을 가진 admin계정만 관리자 페이지에 접속 가능
- 관리자는 사용자가 도서를 사고 판 내역에 대해서 정산 처리 가능
- 정산 데이터는 매 달 15일 새벽 4시에 생성된다.
- 정산 처리는 예치금 입금으로 이루어진다.
- 사용자의 출금 신청을 받아 송금 후 완료처리
- 정산 비율은 5:5이다.

# 추가로 구현할 사항
- 소셜 로그인
- 도서에 썸네일 이미지 등록
- 개인정보에 프로필 사진 등록
- 도서 키워드 검색 기능
- 관리자에게도 출금 신청 취소 기능 부여 O
- 출금 신청 취소 사유 추가 O
