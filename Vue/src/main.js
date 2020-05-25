import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Element from 'element-ui'

import router from './plugins/router.js'
import App from './App.vue'

Vue.config.productionTip = false
Vue.use(VueAxios,axios)
Vue.use(Element)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
