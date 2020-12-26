<template>
  <div class="main">
    <div class="bg"></div>
    <div class="bg"></div>
    <div class="bg"></div>

    <div class="mainLeft">
      <div class="mainTitle" v-html="text"></div>

      <router-link to="/house">
        <button>{{ buttonText }}</button>
      </router-link>
    </div>

    <div class="mainRight">
      <img
        src="../../assets/images/main_house.jpg"
        alt="mainImage"
        class="mainImage"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeMain",
  data() {
    return {
      text: "Welcome<br />Happy<br /> House",
      buttonText: "실거래가 보러가기"
    };
  }
};
</script>

<style scoped src="@/assets/css/home/Main.css" />
