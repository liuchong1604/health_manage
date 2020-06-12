import {
  login,
  getInfoByToken
} from '../../api/user'
import {
  getToken,
  setToken,
  removeToken
} from '../../api/auth'
import qs from 'qs'

export default {
  state: {
    token: getToken(),
    username: '',
    role: ''
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, username) => {
      state.username = username
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
          const formData = qs.parse(params);
          const data = formData.expire;
          let time = 0;
          if (data === 1) {
            time = 7;
          } else {
            time = 1 / 48;
          }
          setToken(res.data.token, time)
          commit('SET_TOKEN', res.data.token)
          commit('SET_ROLE', res.data.role)
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    GetUserInfo() {
      return new Promise((resolve, reject) => {
        const token = getToken();
        getInfoByToken(token)
          .then(res => {
            resolve(res)
          })
          .catch(err => {
            reject(err)
          })
      })
    },
    Logout({
      commit
    }) {
      return new Promise((resolve) => {
        commit('SET_TOKEN', '')
        commit('SET_NAME', '')
        commit('SET_ROLE', '')
        removeToken()
        resolve()
      })
    }
  }
}
