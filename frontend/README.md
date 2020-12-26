# HappyHouse Web Vue서울 5반 김동준, 김세원

## HappyHouse Vue 관통 프로젝트

👩‍👦팀원 : 김동준, 김세원

&nbsp;
&nbsp;
&nbsp;

## 🚩목표

1. 웹 아키텍처를 이해하고 활용할 수 있다.
2. Vue.js를 활용하여 SPA 웹 서버를 구축할 수 있다.
3. MVC 아키텍처를 활용하여 확장성 및 유지보수성이 좋은 웹 서버를 구축할 수 있다.

&nbsp;
&nbsp;
&nbsp;

## 💻작동화면

- Vue cli를 이용하여 최종 관통 프로젝트 Frontend 일부를 구현
- 각 페이지는 router를 통해 전환이 가능하게 설계

### (1) Main

![main](/uploads/74406e6b1d1ab663e78a746b5298bfc8/main.png)

### (2) About, House (미완성)

![about](/uploads/6fbfe2ecf4b248eecc36492a3ac1723b/about.png)

![house](/uploads/14a3dea235123b6596f77285976c9dbd/house.png)

### (3) Notice

Axios를 사용해 api 호출이 가능한 것을 확인했으나 DB 수정 후 연결 예정

![notice](/uploads/2da1a165a0a13bcd4844b1efcb60772c/notice.png)

&nbsp;
&nbsp;
&nbsp;

## 🗂폴더 설계

```
├─ README.md
├─ package.json
└─ src
   ├─ main.js
   ├─ App.vue
   ├─ components        컴포넌트
   │  ├─ home
   │  └─ ...
   ├─ routes            라우터
   │  └─ index.js
   ├─ views             라우터 페이지
   │  ├─ Home.vue
   │  └─ ...
   └─ assets            리소스
      ├─ css
      └─ images
```

- components 폴더는 view에 사용되는 page들끼리 폴더로 나누어 관리
