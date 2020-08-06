<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-%3E%3D%205.5.0-blue">
  <img alt="node" src="https://img.shields.io/badge/node-%3E%3D%209.3.0-blue">
  <img alt="license" src="https://img.shields.io/github/license/woowacourse/atdd-subway-2020">
</p>

<br>

# 레벨2 최종 미션 - 지하철 노선도 애플리케이션

## 🎯 요구사항
- [프론트엔드 미션](https://github.com/woowacourse/atdd-subway-2020/blob/master/frontend-mission.md)
- [백엔드 미션](https://github.com/woowacourse/atdd-subway-2020/blob/master/backend-mission.md)

## 😌 레벨2 최종 미션을 임하는 자세
레벨2 과정을 스스로의 힘으로 구현했다는 것을 증명하는데 집중해라
- [ ] 기능 목록을 잘 작성한다.  
- [ ] 자신이 구현한 기능에 대해 인수 테스트를 작성한다.
- [ ] 자신이 구현한 코드에 대해 단위 테스트를 작성한다.
- [ ] TDD 사이클 이력을 볼 수 있도록 커밋 로그를 잘 작성한다.
- [ ] 사용자 친화적인 예외처리를 고민한다.
- [ ] 읽기 좋은 코드를 만든다.

## 기능 목록
### 백엔드
- [X] 로그인 하지 않은 사용자
    - [x] 인수 테스트 업데이트 - 경로를 조회하면 총 거리, 소요 시간과 함께 요금 정보를 받을 수 있다.
    - [X] 거리에 따른 요금을 계산하는 도메인 로직 추가 및 단위 테스트
    - [X] 인수 테스트 업데이트 - 추가 요금이 있는 노선을 이용하면 추가 요금이 발생한다.
    - [X] 노선별 추가 요금을 계산하는 도메인 로직 추가 및 단위 테스트
- [ ] 로그인 한 사용자
    - [x] 인수 테스트 업데이트 - 어린이, 청소년 계정으로 경로를 조회하면 총 거리, 소요 시간과 함께 할인된 요금 정보를 받을 수 있다.
    - [ ] Controller 구현 및 단위 테스트
- [ ] 경로 조회에 대한 문서화
### 프론트엔드
- [X] 백엔드 요금 조회 api를 프론트엔드에서 사용할 수 있게 연동
- [ ] 템플릿 리터럴을 이용해 현재 시간을 사용자가 보기 편한 형식으로 문자열 렌더링
- [ ] validator를 구현해, form의 유효성을 검사
- [ ] 길찾기를 위해 사용자가 입력한 값을 이용해 검색결과를 불러오는 핸들러를 구현