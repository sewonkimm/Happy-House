<template>
  <div class="dealContainer">
    <h1 class="title">{{ title }}</h1>

    <Select @send-code="getDealList" :tableList="deals" />
    <SelectedItem :type="type" :tableList="deals" :regionCode="regionCode" />
  </div>
</template>

<script>
import axios from "axios";

import Select from "./Select.vue";
import SelectedItem from "./SelectedItem.vue";
import baseAddress from "@/assets/data/baseAddress.json";

const API_KEY = process.env.VUE_APP_APT_DEAL_API_KEY;

export default {
  name: "Deal",
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
      dealList: [],
      deals: [],
      regionCode: "1111011500"
    };
  },
  methods: {
    getDealList: function(code) {
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
          this.dealList = response.data.response.body.items.item;
          this.initData();
        })
        .catch(error => {
          console.log(error);
        });
    },
    initData: function() {
      this.deals = [];
      this.dealList.forEach((deal, index) => {
        this.type === "aptDeal"
          ? this.deals.push({
              num: index,
              apt: deal.아파트,
              gugun: `${this.getGugun(deal.지역코드)}`,
              dong: deal.법정동,
              price: deal.거래금액,
              dealDate: `${deal.년}-${deal.월}-${deal.일}`,
              code: `${this.getCode(deal.법정동)}`
            })
          : this.deals.push({
              num: index,
              house: deal.연립다세대,
              floor: deal.층,
              gugun: `${this.getGugun(deal.지역코드)}`,
              dong: deal.법정동,
              price: deal.거래금액,
              dealDate: `${deal.년}-${deal.월}-${deal.일}`,
              code: `${this.getCode(deal.법정동)}`
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

<style scoped src="@/assets/css/house/Deal.css" />
