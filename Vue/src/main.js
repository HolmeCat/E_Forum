import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Element from 'element-ui'
import VueSession from 'vue-session'

import 'element-ui/lib/theme-chalk/index.css'
import router from './plugins/router.js'
import App from './App.vue'

Vue.config.productionTip = false
Vue.use(VueAxios,axios)
Vue.use(Element)
Vue.use(VueSession)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
