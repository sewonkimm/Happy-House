<template>
  <div class="selectContatiner" data-app>
    <div class="selectors">
      <v-select
        id="option"
        v-model="sido"
        :items="sidoList"
        placeholder="시도"
        item-text="name"
        item-value="code"
        prepend-icon="mdi-map-marker"
        background-color="#fedccc"
        color="#203eac"
        outlined
        return-object
        @change="selectSido"
      />

      <v-select
        v-model="gugun"
        :items="gugunList"
        placeholder="구군"
        item-text="name"
        item-value="code"
        background-color="#fedccc"
        color="#203eac"
        outlined
        return-object
        @change="selectGugun"
      />

      <v-select
        v-model="dong"
        :items="dongList"
        placeholder="읍면동"
        item-text="name"
        item-value="code"
        background-color="#fedccc"
        color="#203eac"
        outlined
        return-object
        @change="selectDong"
      />
    </div>

    <!-- <div class="searchInput">
      <input type="text" v-model="searchWord" />
      <v-icon large color="#203eac" @click="searchDeal">{{ svgPath }}</v-icon>
    </div>-->

    <v-app style="overflow: hidden; height: 80px;">
      <div class="chartButtonContainer">
        <v-btn
          elevation="2"
          large
          depressed
          color="accent"
          @click="openSales"
          class="salesButton"
        >{{ this.tableList[0].gugun }} 매물건수</v-btn>

        <div v-if="showCoronaButton">
          <v-btn elevation="2" large depressed color="info" @click="openCorona">구군별 코로나 비율</v-btn>
        </div>
      </div>

      <v-row>
        <v-dialog v-model="showCorona" persistent max-width="1200">
          <v-card>
            <v-card-title class="headline">서울특별시 구군별 코로나 확진자 수</v-card-title>
            <v-card-text>
              <GChart type="ColumnChart" :data="coronaChartData" />
            </v-card-text>

            <v-card-actions class="buttonContainer">
              <v-app style="overflow: hidden; height: 80px">
                <v-btn elevation="2" large depressed color="secondary" @click="closeCorona">닫기</v-btn>
              </v-app>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>

      <v-row>
        <v-dialog v-model="showSales" persistent max-width="1200">
          <v-card>
            <v-card-title class="headline">동별 매물 건수</v-card-title>
            <v-card-text>
              <GChart type="ColumnChart" :data="salesChartData" />
            </v-card-text>

            <v-card-actions class="buttonContainer">
              <v-app style="overflow: hidden; height: 80px">
                <v-btn elevation="2" large depressed color="secondary" @click="closeSales">닫기</v-btn>
              </v-app>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
    </v-app>
  </div>
</template>

<script>
import sidoCode from "@/assets/data/sidoCode.json";
import gugunCode from "@/assets/data/gugunCode.json";
import dongCode from "@/assets/data/dongCode.json";
import { GChart } from "vue-google-charts";
import { mdiMagnify } from "@mdi/js";

export default {
  name: "Select",
  props: {
    tableList: Array
  },
  components: {
    GChart
  },
  data() {
    return {
      sidoList: [],
      gugunList: [],
      dongList: [],
      sido: 0,
      gugun: 0,
      dong: 0,
      svgPath: mdiMagnify,
      searchWord: "",
      // google chart
      showSales: false,
      salesChartData: [],
      salesChartOptions: {
        title: "House Sales"
      },
      showCoronaButton: true,
      showCorona: false,
      coronaChartData: [["corona", "확진자수"]],
      coronaChartOptions: {
        title: "Corona Proportion",
        subtitle: "서울특별시 구군별 코로나 확진자 수"
      }
    };
  },
  created() {
    sidoCode.forEach(item => {
      this.sidoList.push({
        code: item.sidoCode.substring(0, 2),
        name: item.sidoName
      });
    });
    this.sido = this.sidoList[0].code;
    this.getGugun();

    this.setCoronaChartData();
  },
  methods: {
    getGugun: function() {
      this.gugunList = [];
      gugunCode.forEach(item => {
        if (item.gugunCode.substring(0, 2) === this.sido) {
          this.gugunList.push({
            code: item.gugunCode.substring(0, 5),
            name: item.gugunName
          });
        }
      });
      this.gugun = this.gugunList[0].code;
      this.getDong();
    },
    getDong: function() {
      this.dongList = [];
      dongCode.forEach(item => {
        if (item.dongCode.substring(0, 5) === this.gugun) {
          this.dongList.push({
            code: item.dongCode.substring(5, 10),
            name: item.dong
          });
        }
      });
      this.dong = this.dongList[0].code;
      this.$emit("send-code", {
        sido: this.sido,
        gugun: this.gugun,
        dong: this.dong
      });
    },
    selectSido: function() {
      this.sido = this.sido.code;
      this.setCoronaButton();
      this.getGugun();
    },
    selectGugun: function() {
      this.gugun = this.gugun.code;
      this.getDong();
    },
    selectDong: function() {
      this.dong = this.dong.code;
      this.$emit("send-code", {
        sido: this.sido,
        gugun: this.gugun,
        dong: this.dong
      });
    },
    // charts
    openSales: function() {
      this.setSalesChartData();
      this.showSales = true;
    },
    closeSales: function() {
      this.showSales = false;
    },
    setSalesChartData: function() {
      this.salesChartData = [["Dong", "매물"]];
      this.dongList.forEach((dong, index) => {
        let count = 0;

        this.tableList.forEach((table, index) => {
          if (dong.name === table.dong.trim()) {
            ++count;
          }
        });

        if (count != 0) {
          this.salesChartData.push([dong.name, count]);
        }
      });
    },
    setCoronaButton: function() {
      this.sido === "11"
        ? (this.showCoronaButton = true)
        : (this.showCoronaButton = false);
    },
    openCorona: function() {
      this.showCorona = true;
    },
    closeCorona: function() {
      this.showCorona = false;
    },
    setCoronaChartData: function() {
      gugunCode.forEach(item => {
        const count = this.getCoronaCount(item.gugunName);

        if (count !== 0) {
          this.coronaChartData.push([item.gugunName, count]);
        }
      });
    },
    getCoronaCount: function(gugun) {
      let count = 0;
      this.$store.getters.getCoronaList.forEach(value => {
        if (value.CORONA19_AREA === gugun) {
          ++count;
        }
      });
      return count;
    }
  }
};
</script>

<style scoped src="@/assets/css/house/Select.css" />
