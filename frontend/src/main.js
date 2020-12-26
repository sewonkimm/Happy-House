import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import vuetify from './plugins/vuetify';
import VueGoogleCharts from 'vue-google-charts'

Vue.config.productionTip = false;
Vue.use(vuetify);
Vue.use(VueGoogleCharts);

new Vue({
  router,
  store,
  vuetify,
  VueGoogleCharts,
  render: (h) => h(App),
}).$mount('#app');
