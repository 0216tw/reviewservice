# 📚 ReviewService

**Spring 수업을 위한 간단한 리뷰 웹서비스입니다. (꾸준히 업데이트됩니다)**

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-green)
![JPA](https://img.shields.io/badge/JPA-Oracle-blue)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.0.11-blueviolet)

---

## 📅 커밋 현황

- **2024-08-29**: 스케줄링 기능 반영
- **2024-08-28**: flask 연동 기능 추가 
- **2024-08-27**: 리뷰 이미지 등록 기능 추가 , 사용자 로그 기능 추가 
- **2024-08-26**: 리뷰 이미지 조회 기능 추가 
- **2024-08-25**: 🔧 프로젝트 설정 , README.md 세팅 , 회원가입 및 로그인, session 기능 구현완료

---

## 🌟 Features

### 👤 사용자 관련

- ✅ **회원가입**: 사용자는 웹 인터페이스를 통해 새로운 계정을 생성할 수 있습니다.
- ✅ **로그인 (세션 활용)**: 세션을 활용하여 사용자가 로그인하고, 로그인 상태를 유지할 수 있습니다.
- ✅ **아이디 중복확인**: 동일한 사용자ID 가 있을경우 이를 중복 검사하도록 합니다. 
- **마이페이지 수정**: 사용자는 자신의 프로필 정보를 수정할 수 있습니다.
- **회원삭제**: 사용자는 자신의 계정을 삭제할 수 있으며, 이 과정에서 모든 개인 데이터가 삭제됩니다.

### 📝 리뷰 관련

- **리뷰 등록**: 사용자는 웹 인터페이스를 통해 새로운 리뷰를 이미지 포함 작성할 수 있습니다.
- **리뷰 삭제**: 본인과 관리자만 리뷰를 삭제할 수 있습니다.
- **리뷰 수정**: 리뷰 작성자 본인만 해당 리뷰를 수정할 수 있습니다.
- **리뷰 조회**: 작성된 리뷰를 리스트로 확인하고, 상세 정보를 볼 수 있습니다.

### 📊 분석 관련

- **검색시 사용자 로깅**: 사용자가 리뷰를 검색할 때 검색 로그를 기록하여 분석에 활용합니다.
- **리뷰 분석** : flask를 통해 특정 리뷰 분석을 요청할 수 있습니다. (연동만 합니다) 
- **스케줄링으로 주기적 리뷰 분석** : 스케줄링을 활용해 주기적으로 리뷰를 처리합니다.


---

## 🛠️ Tech Stack

- **Backend**: SpringBoot 3.3.3
- **Database**: JPA + Oracle
- **Frontend**: Thymeleaf

---

## 🚀 Getting Started

### Prerequisites

- **Java 17** 이상
- **Oracle Database** (설치 및 실행 필요)

### Installation & Run

1. **프로젝트 클론**:
    ```bash
    git clone https://github.com/0216tw/reviewservice.git
    ```
   
2. **Spring Boot에서 프로젝트 불러오기**:
    - IntelliJ IDEA 또는 STS(Spring Tool Suite)와 같은 IDE를 사용하여 프로젝트를 불러옵니다.
    - 프로젝트의 `build.gradle` 파일이 자동으로 인식되어 의존성이 설치됩니다.

3. **데이터베이스 설정**:
    - `src/main/resources/application.properties` 파일에서 데이터베이스 설정을 확인하고 수정합니다.
    - 우리는 이미 세팅된 경로를 그대로 사용할 겁니다.
      

4. **애플리케이션 실행**:
    - IDE에서 `ReviewServiceApplication.java` 파일을 실행하거나, 다음 명령어를 사용하여 애플리케이션을 실행합니다.
    ```bash
    ./gradlew bootRun
    ```

5. **애플리케이션 접속**:
    - 브라우저에서 `http://localhost:8088`으로 접속하여 애플리케이션을 확인합니다.
    - 현재 application.properties에 server.port = 8088 로 세팅되어 있습니다. 

---

