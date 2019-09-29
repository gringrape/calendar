# 2019-09-28

## 1. 진행 계획

-   [x] 개발 환경 설정
-   [x] 깃 저장소 생성, 빌드 테스트
-   [ ] 요구 사항 분석 및 아웃라인 설정

## 2. 개발 환경 설정

1.  개발 환경 설정 다운로드 - Spring Boot -> **o.k.**
2.  readme 파일 읽기 -> **o.k.**
    1.  과제 실행 시스템 환경 체크 -> 요구 환경
3.  요구 환경 체크 -> **o.k.**
    1.  mysql 8.0.17 -> 현재 사용하는 버전: 8.0.16 -> 새로운 버전으로 업데이트 -> **o.k.**
        1.  유저 생성 - 'root', 'password' -> **o.k.**
        2.  데이터 베이스 생성 - my_database -> **o.k.**
        3.  데이터 베이스 연동 확인 -> **o.k.**
            1.  spring data jpa 의존성 추가
                1.  환경변수 변경 -> hostname ->                                                                                                          업로드 할 경우** mysql-server 로 변경하여 업로드**
            2.  데이터베이스 my_database 생
    2.  java jdk 1.8.0_201 -> 현재 사용하는 버전: 1.8.0_211 -> **버전이 달라도 괜찮은지 확인**
4.  의존성 설정 ->** o.k.**
    1.  jpa(hibernate) 
    2.  jdbc connector
    3.  junit
    4.  lombok
5.  데이터 베이스 연동 확인 -> **o.k.**
6.  vue, spring boot 연동 설정 -> **o.k.**
    -   개요
        -   vue webpack의 output위치가 spring boot 의 output 위치가 되도록 설정해준다. 
    -   작업내용
        -   [x] 스프링으로 index.html, hello world 띄우기
        -   [x] vue webpack 설치
        -   [x] vue webpack output 설정 
        -   [x] 스프링으로 해당 output 바라보기 설정
        -   [x] 스프링 실행하여 헬로월드 출력

  \* 작업 중 문제 발생 내용

-   index.html 을 찾지 못하는 문제 -> thymeleaf 를 의존성에 추가하여 해결.    
-   javascript relative path 지정 문제 -> ../ means one level up
-   static resource 의 경로 설정 문제 -> html 과 같은 폴더에 css와 js를 담은 static 폴더가 위치하여야 한다. 


-   개발 환경 설정의 결과 
    -   spring boot를 사용하여 main을 띄워준다. -> index.html
    -   index.html의 자바스크립트 내용은 vue webpack으로 작업하여 build 한 결과 물이다. 
    -   index.html에서 spa 개념으로 axios를 활용해 spring의 다른 controller에 요청을 전달할 수 있다.   

## 3. 깃 저장소 생성, 빌드 테스트

저장소 주소: <https://github.com/gringrape/calendar>

private repo 생성

local 과 연동

-    빌드 테스트
    -   lombok annotation processor -> build.gradle에 추가
    -   application.properties ->  driverclass, mysql url 수정

## 4. 요구 사항 분석 및 아웃라인 설정 - 다음날 수행

# 2019-09-29

## 1. 계획

-   [ ] 요구 사항 분석
-   [ ] 아웃라인
-   [ ] 1차 목표 설정

## 2. 요구 사항 분석 

-   **어플리케이션의 목적**
    -   **일정**을 **달력**에 표시한다.
-   **달력**
    -   Monthly view
        -   달력 보기
            -   이전, 다음을 클릭하면 해당월의 달력을 표시
            -   기본은 현재날짜가 속한 달을 표시 
        -   일정 보기
            -   여러날에 걸쳐진 일정은 선으로 표
        -   일정 추가
            -   날짜를 클릭하면 해당일의 일정을 추
        -   일정 상세보기
            -   일정을 클릭하면 상세한 내용이 팝업으로 표시
    -   Daily view
        -   일정 표시
            -   해당 일의 일정들을 표시
        -   일정 상세보기
            -   일정을 클릭하면 일정의 상세내용이 팝업으로 표시
        -   일정 추가
            -   빈 칸을 누르면 일정을 추가 가능
