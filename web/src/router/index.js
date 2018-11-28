import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/login'
import Task from '../views/task.vue'
import Manage from '../views/manage.vue'
import Message from '../views/message.vue'
import Notice from '../views/notice.vue'
import Expert from '../views/expert.vue'
import Account from '../views/account.vue'
import CallBoard from '../views/callBoard.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/manage',
      name: 'manage',
      component: Manage
    },
    {
      path: '/task',
      name: 'task',
      component: Task
    },
    {
      path: '/message',
      name: 'message',
      component: Message
    },
    {
      path: '/notice',
      name: 'notice',
      component: Notice
    },
    {
      path: '/expert',
      name: 'expert',
      component: Expert
    },
    {
      path: '/account',
      name: 'account',
      component: Account
    },
    {
      path: '/callBoard',
      name: 'callBoard',
      component: CallBoard
    }
  ]
})
