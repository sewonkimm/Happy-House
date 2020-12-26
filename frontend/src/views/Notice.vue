<template>
  <div class="notice">
    <NoticeMain />
  </div>
</template>

<script>
// @ is an alias to /src
import NoticeMain from "@/components/notice/Main.vue";

export default {
  name: "Notice",
  components: {
    NoticeMain
  }
};
</script>
