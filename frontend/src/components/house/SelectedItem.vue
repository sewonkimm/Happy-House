<template>
  <div class="tableContainer">
    <v-data-table
      :headers="headers"
      :items="tableList"
      :items-per-page="10"
      height="600"
      class="elevation-1 selectedTable"
    />

    <div id="map"></div>
  </div>
</template>

<script>
import baseAddress from '@/assets/data/baseAddress.json';

export default {
  name: 'Table',
  props: {
    tableList: Array,
    regionCode: String,
    type: String,
  },
  data() {
    return {
      headers: [],
      map: '',
      positions: [], // marker list
      regionLat: 0,
      regionLng: 0,
      markerImageSrc: "",
      markerImageSize: new window.kakao.maps.Size(64, 69),
      markerImageOption: { offset: new window.kakao.maps.Point(38, 69) },
      markerImage: ""
    };
  },
  mounted() {
    this.setMarkerImage();
    window.kakao && window.kakao.maps ? this.initMap() : '';

    if (this.type === 'aptDeal') {
      this.headers = [
        { text: '번호', value: 'num' },
        { text: '아파트', value: 'apt' },
        { text: '법정동', value: 'dong' },
        { text: '거래일시', value: 'dealDate' },
        { text: '거래금액', value: 'price' }
      ];
    } else if (this.type === 'houseDeal') {
      this.headers = [
        { text: '번호', value: 'num' },
        { text: '연립다세대', value: 'house' },
        { text: '층', value: 'floor' },
        { text: '법정동', value: 'dong' },
        { text: '거래일시', value: 'dealDate' },
        { text: '거래금액', value: 'price' }
      ];
    } else if (this.type === 'aptInfo') {
      this.headers = [
        { text: '번호', value: 'num' },
        { text: '아파트', value: 'apt' },
        { text: '층', value: 'floor' },
        { text: '법정동', value: 'dong' },
        { text: '거래일시', value: 'dealDate' },
        { text: '보증금액', value: 'price' },
        { text: '월세금액', value: 'monthPrice' }
      ];
    } else if (this.type === 'houseInfo') {
      this.headers = [
        { text: '번호', value: 'num' },
        { text: '연립다세대', value: 'house' },
        { text: '층', value: 'floor' },
        { text: '법정동', value: 'dong' },
        { text: '거래일시', value: 'dealDate' },
        { text: '거래금액', value: 'price' }
      ];
    }
  },
  watch: {
    regionCode: function() {
      for (let index = 0; index < baseAddress.length; index++) {
        const value = baseAddress[index];
        if (value.code === this.regionCode) {
          this.regionLat = value.lat;
          this.regionLng = value.lng;
          this.initMap();
          return;
        }
      }
    },
    tableList: function() {
      this.positions = [];
      this.tableList.forEach(value => {
        for (let index = 0; index < baseAddress.length; index++) {
          const address = baseAddress[index];
          if (address.code === value.code) {
            this.positions.push({
              title: address.code,
              latlng: new window.kakao.maps.LatLng(address.lat - 0, address.lng - 0),
              gugun: value.gugun
            });
            break;
          }
        }
      });
      this.initMap();
    },
  },
  methods: {
    initMap: function() {
      const mapContainer = document.getElementById('map');
      const mapOption = {
        center: new window.kakao.maps.LatLng(this.regionLat, this.regionLng),
        level: 4
      };
      const map = new window.kakao.maps.Map(mapContainer, mapOption);

      this.positions.forEach(position => {
        const marker = new window.kakao.maps.Marker({
          map: map,
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: this.markerImage,
          clickable: true
        });

        const infowindow = new window.kakao.maps.InfoWindow({
          position: position.latlng,
          removable: true,
          content: `<div style="padding:5px;">코로나지수: ${this.getCoronaInfo(position.gugun)}</div>`
        });
        
        window.kakao.maps.event.addListener(marker, 'click', function() {
          infowindow.open(map, marker);  
        });
      });

      this.map = map;
    },
    setMarkerImage: function() {
      this.markerImageSrc = this.getImgSrc("marker.png"); 
      this.markerImage = new window.kakao.maps.MarkerImage(this.markerImageSrc, this.markerImageSize, this.markerImageOption);
    },
    getImgSrc: function(fileName) {
      return require("@/assets/images/"+fileName);
    },
    getCoronaInfo: function(gugun) {
      let count = 0;
      this.$store.getters.getCoronaList.forEach(value => {
        if(value.CORONA19_AREA === gugun){ 
          ++count; 
        };
      });
      
      const coronaInfo = count/this.$store.getters.getCoronaList.length*100;
      return coronaInfo.toFixed(2);
    }
  }
};
</script>

<style scoped src="@/assets/css/house/Select.css" />
