import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './router'
import store from './store/index'
import qs from 'qs'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
// import echarts from 'echarts'

Vue.config.productionTip = false
Vue.prototype.$qs = qs;
Vue.use(ElementUI);
// Vue.prototype.$echarts = echarts;

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
