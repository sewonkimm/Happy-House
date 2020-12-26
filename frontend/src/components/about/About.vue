<template>
  <div class="about">
    <img
      v-if="left"
      :src="require(`@/assets/images/${imgName}`)"
      alt="aboutImage"
      class="aboutImage"
    />

    <div class="aboutContent">
      <h2>{{ aboutTitle }}</h2>
      <p v-html="aboutContnet"></p>
    </div>

    <img
      v-if="right"
      :src="require(`@/assets/images/${imgName}`)"
      alt="aboutImage"
      class="aboutImage"
    />
  </div>
</template>

<script>
export default {
  name: "About",
  props: {
    imgName: String,
    aboutTitle: String,
    aboutContnet: String,
    left: Boolean,
    right: Boolean
  }
};
</script>

<style scoped src="@/assets/css/about/About.css" />
