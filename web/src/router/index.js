import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/home.vue'
import File from '../views/file.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/file',
      name: 'file',
      component: File
    }
  ]
})
