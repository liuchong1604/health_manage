import user from './modules/user'
import getters from './getters'
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// const store = new Vuex.Store({
//   getters,
//   modules: {
//     user
//   }
// })

const store = () => {
  return new Vuex.Store({
    getters,
    modules: {
      user
    }
  })
}
export default store
