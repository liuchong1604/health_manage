import Vue from 'vue'
import VueRouter from 'vue-router'
import {
  getToken
} from 'common/auth'

Vue.use(VueRouter)

const Layout = () => import('views/Layout')

const routes = [{
  path: '/login',
  component: () => import('views/Login')
},
{
  path: '/404',
  component: () => import('views/Error'),
},
{
  path: '/',
  component: () => import("views/Layout"),
  redirect: '/index/dashboard',
  children: [{
    path: '/index/dashboard',
    component: () => import('views/Dashboard')
  },
  {
    path: "/user/usermanagement",
    name: "userManagement",
    component: () => import('views/user/UserManagement')
  }, {
    path: "/user/exam",
    name: "exam",
    component: () => import('views/user/Exam')
  },
  {
    path: "/item/news",
    name: "news",
    component: () => import('views/item/News')
  },
  {
    path: "/item/tool",
    name: "tool",
    component: () => import('views/item/Tool')
  },
  {
    path: "/item/category",
    name: "category",
    component: () => import('views/item/Category')
  }
  ]
},
{
  path: '*',
  redirect: '/404'
}
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

const whiteList = ['/login'] //不重定向名单
router.beforeEach((to, from, next) => {
  if (getToken()) {
    if (to.path === '/login') {
      next({
        path: '/'
      })
    } else {
      next()
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) { //在白名单中
      next()
    } else {
      next(`/login?redirect=${to.path}`) //否则全部重定向到登录页
    }
  }
})

export default router;
