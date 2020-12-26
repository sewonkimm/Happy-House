<template>
  <div class="header">
    <router-link to="/">
      <img src="../assets/images/logo.png" alt="logo" class="logo" />
    </router-link>

    <div class="nav">
      <router-link to="/">Home</router-link>
      <router-link to="/about">소개</router-link>
      <router-link to="/notice">공지사항</router-link>
      <router-link to="/house">서비스</router-link>
    </div>

    <div class="login">
      <div class="userInfo" v-if="getAccessToken">
        <v-icon class="noprofile" large>{{ svgPath }}</v-icon>
        <p class="userName">{{ getUserName }}</p>
        <a @click="logout">로그아웃</a>
      </div>
      <router-link v-else to="/login">login</router-link>
    </div>
  </div>
</template>

<script>
import { mdiAccountCircle } from "@mdi/js";
import { mapGetters } from "vuex";

export default {
  name: "Header",
  data: () => ({
    svgPath: mdiAccountCircle,
  }),
  computed: {
    ...mapGetters(["getAccessToken", "getUserEmail", "getUserName"])
  },
  methods: {
    logout() {
      this.$store
        .dispatch("LOGOUT")
        .then(() => this.$router.replace("/").catch(() => {}));
    }
  }
};
</script>

<style scoped src="@/assets/css/Header.css" />
