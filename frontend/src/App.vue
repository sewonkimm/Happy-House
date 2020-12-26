<template>
  <div id="app">
    <Header />
    <router-view />
  </div>
</template>

<script>
// @ is an alias to /src
import Header from "@/components/Header.vue";

export default {
  components: {
    Header
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

* {
  box-sizing: border-box;
  margin: 0;
}

.hidden {
  display: none !important;
}

.invisible {
  visibility: hidden;
}

.clearfix::before,
.clearfix::after {
  content: "";
  display: table;
}

.clearfix::after {
  clear: both;
}


/* 
 * 반응형 웹을 위한 media query (mobile first)
 * IE9부터 지원
 */

@media screen and (min-width: 768px) and (max-width: 959px) {
  /* Tablet */
}

@media screen and (min-width: 960px) {
  /* Desktop */
}

</style>
