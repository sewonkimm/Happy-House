<template>
  <div class="main">
    <h1 class="title">{{ text }}</h1>

    <About
      imgName="face.jpg"
      :aboutTitle="aboutTitle"
      :aboutContnet="aboutContent"
      :left="true"
    />

    <About
      imgName="about_img.jpg"
      :aboutTitle="aboutTitle"
      :aboutContnet="aboutContent"
      :right="true"
    />
  </div>
</template>

<script>
import About from "./About.vue";

export default {
  name: "AboutMain",
  components: {
    About
  },
  data() {
    return {
      text: "Make Your Happy House",
      aboutTitle: "행복한 내집!",
      aboutContent: `I'm a paragraph. Click here to add your own text and edit me. It’s easy. Just click “Edit Text” or double click me to add your own content and make changes to the font. Feel free to drag and drop me anywhere you like on your page. I’m a great place for you to tell a story and let your users know a little more about you.<br/><br/>This is a great space to write long text about your company and your services. You can use this space to go into a little more detail about your company. Talk about your team and what services you provide. Tell your visitors the story of how you came up with the idea for your business and what makes you different from your competitors.`
    };
  }
};
</script>

<style scoped src="@/assets/css/about/Main.css" />
