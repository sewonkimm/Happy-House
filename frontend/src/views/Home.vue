<template>
  <div class="home">
    <HomeMain />
  </div>
</template>

<script>
// @ is an alias to /src
import HomeMain from "@/components/home/Main.vue";

export default {
  name: "Home",
  components: {
    HomeMain
  }
};
</script>

<style scoped src="@/assets/css/home/Home.css" />
