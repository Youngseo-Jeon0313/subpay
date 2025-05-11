# 정기 구독 결제 서비스

- 사용자가 정기 구독을 신청하면, 결제일에 맞춰 결제를 진행하는 서비스
- 정기 구독을 신청한 사용자는 결제일에 맞춰 결제 진행
  - 결제 실패(카드 한도 초과 또는 카드 만료, 재고 부족, 결제 시스템 장애) 시, 재시도 및 알림 기능 제공
  - 결제 성공 시, 알림 기능 제공

## Tool

- Airflow
- Kotlin/SpringBoot
- SpringBatch
- React

## Plan

- DB 구축
- Batch Pipeline 구축
- API 및 간단한 UI 구축
- Test code 작성 및 Test

## 프로젝트 실행 방법

- docker 이미지 전체 삭제
  - 기존의 Docker 이미지와 컨테이너를 삭제하려면 아래 명령어를 실행
    `docker-compose down --rmi all --volumes --remove-orphans`
- docker compose 로 서비스 실행
  `docker compose up`
- docker-compose.yml 파일에 정의된 서비스
  - mysql: DB
  - airflow: Airflow
  - springboot: Spring Boot API 서버

## API 명세

- API 명세는 swagger[localhost:8080/swagger-ui/index.html] 를 통해 확인 가능
