import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './router/filter';
import 'font-awesome/css/font-awesome.min.css';
import { postKeyValueRequest,postRequest,putRequest,getRequest,deleteRequest } from './utils/api';

Vue.use(ElementUI);
Vue.config.productionTip = false;
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.deleteRequest = deleteRequest;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
