<template>
  <div class="main">
    <v-btn
      v-if="showBack"
      @click="goBack"
      elevation="2"
      color="#203eac"
      fab
      large
      dark
      class="backButton"
    >
      <v-icon dark>{{backIcon}}</v-icon>
    </v-btn>

    <h1 class="title">{{ text }}</h1>

    <div class="serviceContainer" v-if="isService">
      <ServiceCard
        v-for="(name, index) in service"
        :key="index"
        :serviceName="name"
        :type="index"
        @go-page="goPage"
      />
    </div>

    <Deal v-if="isDeal" :type="type" :title="title" :API_URL="apiUrl" />
    <Info v-if="isInfo" :type="type" :title="title" :API_URL="apiUrl" />
  </div>
</template>

<script>
import ServiceCard from "./Service.vue";
import Deal from "./Deal.vue";
import Info from "./Info.vue";
import { mdiArrowLeft } from "@mdi/js";

export default {
  name: "HouseMain",
  components: {
    ServiceCard,
    Deal,
    Info
  },
  data() {
    return {
      text: "Happy House Service",
      service: ["aptDeal", "houseDeal", "aptInfo", "houseInfo"],
      isService: true,
      isDeal: false,
      isInfo: false,
      title: "",
      type: "",
      apiUrl: "",
      showBack: false,
      backIcon: mdiArrowLeft
    };
  },
  created() {
    this.$store
        .dispatch("GETCORONA")
        .then(() => console.log(this.$store.getters.getCoronaList));
  },
  methods: {
    goDeal: function(pageName, apiUrl) {
      this.apiUrl = apiUrl;
      this.isDeal = true;
      this.isService = false;
      this.isInfo = false;
      pageName.includes("apt") ? this.setAptDeal() : this.setHouseDeal();
    },
    goInfo: function(pageName, apiUrl) {
      this.apiUrl = apiUrl;
      this.isInfo = true;
      this.isService = false;
      this.isDeal = false;
      pageName.includes("apt") ? this.setAptInfo() : this.setHouseInfo();
    },
    goPage: function(pageName, apiUrl) {
      this.showBack = true;
      pageName.includes("Deal") ? this.goDeal(pageName, apiUrl) : this.goInfo(pageName, apiUrl);
    },
    goBack: function() {
      this.isService = true;
      this.isDeal = false;
      this.isInfo = false;
      this.showBack = false;
    },
    setAptDeal: function() {
      this.title = "아파트 실거래가";
      this.type = "aptDeal";
    },
    setHouseDeal: function() {
      this.title = "연립주택 실거래가";
      this.type = "houseDeal";
    },
    setAptInfo: function() {
      this.title = "아파트 전월세가";
      this.type = "aptInfo";
    },
    setHouseInfo: function() {
      this.title = "연립주택 전월세가";
      this.type = "houseInfo";
    }
  }
};
</script>

<style scoped src="@/assets/css/house/Main.css" />
