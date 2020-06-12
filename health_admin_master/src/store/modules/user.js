import {
  login
} from 'common/login'
import {
  getToken,
  setToken,
  removeToken
} from 'common/auth'


export default {
  state: {
    token: getToken(),
    name: '',
    role: '',
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_ROLE: (state, role) => {
      state.role = role
    }
  },
  actions: {
    Login({
      commit
    }, params) {
      return new Promise((resolve, reject) => {
        login(params).then(res => {
          setToken(res.data.token);
          commit('SET_TOKEN', res.data.token);
          commit('SET_ROLE', res.data.role)
          resolve();
        }).catch(err => {
          reject(err);
        })
      })
    },
    Logout({
      commit
    }) {
      return new Promise((resolve, reject) => {
        commit('SET_TOKEN', '');
        commit('SET_NAME', '');
        commit('SET_NAME', '')
        removeToken();
        resolve()
      })
    }
  }
}
