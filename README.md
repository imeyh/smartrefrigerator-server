# 장비서 Back-end API Spec

## Format

### 기본 응답 포맷

* Success(2XX)

```
// Success
{
		"count" : 1 // 반환 데이터 개수
		"data" : { ... } // 요청한 데이터 타입 (Food,List<Food>, Shelf, UserInfo, ... )
}

```

* Failure(4XX)

```
// Failure
{
		"errorCode" : 404 // error code
		"errorMessage" : "존재하지 않는 정보입니다." // error message

}
```



### API 명세 확인 방법

* 로컬에서 서버를 돌리고 확인하는 경우
  * http://localhost:8080/swagger-ui.html#/
* AWS에서 서버를 돌리고 확인하는 경우
  * [ec2 주소 또는 IP]/swagger-ui.html#/



* 확인하고 싶은 메서드를 `try it out` 
  * 파라미터를 직접 입력하면 응답 결과를 확인할 수 있습니다.

## Status Code

* `2XX`
  * 200 : 성공
  * 201 : 생성 성공
  * 204 : 수정, 삭제 성공
* `4XX`
  * 400 : 파라미터 오류, 잘못된 요청
  * 401 : 인증 실패
  * 403 : 권한 없음
  * 404 : 리소스를 찾을 수 없음

* `5XX`
  * 500 : 서버 에러



## Methods

`GET` : 조회

`POST` : 생성

`PATCH` : 수정

`DELETE` : 삭제

## Headers

* Content-Type : `application/json`