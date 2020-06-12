import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import getters from './getters'
Vue.use(Vuex)

//vuex教程:
// gettres: 获取$store的计算属性(里面的函数一定要带state, 同时getters也可以作为参数传入)
//         moreAgeStu(state){ 自定义参数用法
//           return function (18){

//           }
//         }
// muation: 可以直接加自定义参数 addStudent(state,stu(负载playload)),里面不能使用异步方法
const store = new Vuex.Store({
  getters,
  modules: {
    user
  },
});
//params 动态路由  用name
//query  get请求拼接路径后参数 前用path
export default store;
