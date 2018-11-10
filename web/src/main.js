import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from 'axios'
import config from '../config'
import ElementUI from 'element-ui' // 饿了么插件
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/base.css'
import mainCss from './assets/css/main.css'

Vue.config.productionTip = false
Vue.use(config)
Vue.use(ElementUI)
Vue.use(mainCss)
Vue.use(axios)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
