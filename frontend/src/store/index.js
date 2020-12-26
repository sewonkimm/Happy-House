import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";

Vue.use(Vuex);

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

const CORONA_API_KEY = process.env.VUE_APP_CORONA_API_KEY;
const CORONA_API_URL = "http://openapi.seoul.go.kr:8088/";

const STORE_API_URL = "http://apis.data.go.kr/B553077/api/open/sdsc/storeListInDong?divId=ctprvnCd&key=11&ServiceKey="
const STORE_API_KEY = process.env.VUE_APP_STORE_API_KEY;

export default new Vuex.Store({
  state: {
    accessToken: null,
    userEmail: "",
    userName: "",
    userSeq: "",
    coronaList: [],
    storeList : [],
  },
  getters: {
    getAccessToken(state) {
      return state.accessToken;
    },
    getUserEmail(state) {
      return state.userEmail;
    },
    getUserName(state) {
      return state.userName;
    },
    getUserSeq(state) {
      return state.userSeq;
    },
    getCoronaList(state) {
      return state.coronaList;
    },
    getStoreList(state) {
      return state.storeList;
    }
  },
  mutations: {
    LOGIN(state, payload) {
      state.accessToken = payload["auth-token"];
      state.userEmail = payload["user-email"];
      state.userName = payload["user-name"];
      state.userSeq = payload["user-seq"];
    },
    LOGOUT(state) {
      state.accessToken = null;
      state.userEmail = "";
      state.userName = "";
      state.userSeq = "";
    },
    CORONALIST(state, payload) {
      state.coronaList = payload;
    },
    STORELIST(state, payload) {
      state.storeList = payload;
    }
  },
  actions: {
    LOGIN(context, user) {
      return axios
        .post(`${SERVER_URL}/login`, user)
        .then(response => {
          context.commit("LOGIN", response.data);
          axios.defaults.headers.common["auth-token"] = `${response.data["auth-token"]}`;
        });
    },
    LOGOUT(context) {
      context.commit("LOGOUT");
      axios.defaults.headers.common["auth-token"] = undefined;
    },
    GETCORONA(context, coronaList) {
      return axios
        .get(CORONA_API_URL + CORONA_API_KEY+"/json/Corona19Status/1/1000")
        .then(response => {
          context.commit("CORONALIST", response.data.Corona19Status.row);
        })
        .catch(error => {
          console.log(error);
        });
    },
    GETSTORE(context, storeList) {
      return axios
        .get(STORE_API_URL + STORE_API_KEY)
        .then(response => {
          context.commit("STORELIST", response.data);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  modules: {}
});
