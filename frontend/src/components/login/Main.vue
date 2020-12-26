<template>
  <div class="main">
    <h1 class="title">{{ title }}</h1>
    <Login v-if="showLogin" @open-join-page="goJoinPage" />
    <Join v-else />
  </div>
</template>

<script>
import Login from "./Login.vue";
import Join from "./Join.vue";

export default {
  name: "LoginMain",
  components: {
    Login,
    Join
  },
  data() {
    return {
      title: "Login",
      showLogin: true
    };
  },
  methods: {
    goJoinPage: function() {
      this.title = "Join";
      this.showLogin = false;
    }
  }
};
</script>

<style scoped src="@/assets/css/login/Main.css" />
