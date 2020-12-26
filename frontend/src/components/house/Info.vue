<template>
  <div class="infoContainer">
    <h1 class="title">{{ title }}</h1>

    <Select @send-code="getInfoList" :tableList="infos" />
    <SelectedItem :type="type" :tableList="infos" :regionCode="regionCode" />
  </div>
</template>

<script>
import axios from "axios";
import Select from "./Select.vue";
import SelectedItem from "./SelectedItem.vue";
import baseAddress from "@/assets/data/baseAddress.json";

const API_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;

export default {
  name: "Info",
  props: {
    title: String,
    API_URL: String,
    type: String
  },
  components: {
    Select,
    SelectedItem
  },
  data() {
    return {
      infoList: [],
      infos: [],
      regionCode: "1111011500"
    };
  },
  methods: {
    getInfoList: function(code) {
      this.regionCode = code.gugun + code.dong;
      const params = {
        LAWD_CD: code.gugun,
        DEAL_YMD: "202011",
        serviceKey: decodeURIComponent(API_KEY)
      };

      axios
        .get(this.API_URL, {
          params
        })
        .then(response => {
          this.infoList = response.data.response.body.items.item;
          this.initData();
        })
        .catch(error => {
          console.log(error);
        });
    },
    initData: function() {
      this.infos = [];
      this.infoList.forEach((info, index) => {
        this.type === "aptInfo"
          ? this.infos.push({
              num: index,
              apt: info.아파트,
              floor: info.층,
              gugun: `${this.getGugun(info.지역코드)}`,
              dong: info.법정동,
              dealDate: `${info.년}-${info.월}-${info.일}`,
              price: info.보증금액,
              monthPrice: info.월세금액,
              code: `${this.getCode(info.법정동)}`
            })
          : this.infos.push({
              num: index,
              house: info.연립다세대,
              floor: info.층,
              gugun: `${this.getGugun(info.지역코드)}`,
              dong: info.법정동,
              dealDate: `${info.년}-${info.월}-${info.일}`,
              price: info.보증금액,
              monthPrice: info.월세금액,
              code: `${this.getCode(info.법정동)}`
            });
      });
    },
    getGugun: function(gugunCode) {
      for (let index = 0; index < baseAddress.length; index++) {
        const address = baseAddress[index];
        if (address.code.substring(0, 5) === gugunCode.toString()) {
          return address.gugun;
        }
      }
    },
    getCode: function(dongName) {
      for (let index = 0; index < baseAddress.length; index++) {
        const address = baseAddress[index];
        if (address.dong === dongName.trim()) {
          return address.code;
        }
      }
    }
  }
};
</script>

<style scoped src="@/assets/css/house/Info.css" />
