import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Select from '../views/Select.vue'
import Product from '../views/Product.vue'
import User from '../views/User.vue'
Vue.use(VueRouter)
const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/Select',
    name: 'Select',
    component: Select
  },
  {
    path: '/Product',
    name: 'Product',
    component: Product
  },
  {
    path: '/User',
    name: 'User',
    component: User
  }
]
const router = new VueRouter({ routes })
export default router