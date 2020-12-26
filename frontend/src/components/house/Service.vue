<template>
  <div class="container">
    <img :src="require(`@/assets/images/${imgName}`)" alt="serviceImg" />
    <h2 class="title">{{ content.title }}</h2>
    <p class="description">{{ content.description }}</p>

    <button @click="goPage(content.nextLink, content.apiUrl)">보러가기</button>
  </div>
</template>

<script>
const contents = [
  {
    title: "아파트 실거래가",
    description: "아파트 실거래가를 확인해보세요",
    nextLink: "aptDeal",
    apiUrl: "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev"
  },
  {
    title: "주택 실거래가",
    description: "주택 실거래가를 확인해보세요",
    nextLink: "houseDeal",
    apiUrl: "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHTrade"
  },
  {
    title: "아파트 전월세가",
    description: "아파트 전세, 월세 가격을 확인해보세요",
    nextLink: "aptInfo",
    apiUrl: "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent"
  },
  {
    title: "주택 전월세가",
    description: "주택 전세, 월세 가격을 확인해보세요",
    nextLink: "houseInfo",
    apiUrl: "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHRent"
  }
];

export default {
  name: "ServiceCard",
  props: {
    serviceName: String,
    type: Number
  },
  data() {
    let imgName = "";
    if (this.serviceName.includes("apt")) imgName = "apt.jpg";
    else if (this.serviceName.includes("house")) imgName = "house.jpg";

    return {
      imgName,
      content: contents[this.type]
    };
  },
  methods: {
    goPage: function(nextLink, apiUrl) {
      this.$emit("go-page", nextLink, apiUrl);
    }
  }
};
</script>

<style scoped src="@/assets/css/house/Service.css" />
