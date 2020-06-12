import Vue from 'vue'
import axios from 'axios'
import qs from 'qs'

Vue.prototype.$qs = qs

export function request (config) {
  const instance = axios.create({
    baseURL: 'http://api.health.com/api',
    withCredentials: true, // send cookies when cross-domain requests
    timeout: 30000
  })

  instance.interceptors.request.use(
    config => {
      return config;
    },
    err => {
      // console.log(err)
      return Promise.reject(err)
    }
  )

  instance.interceptors.response.use(
    response => {
      if (response.status === 200 || response.status === 201) {
        return response;
      } else {
        return Promise.reject(new Error("出现错误"));
      }
    },
    error => {
      // console.log('err: ' + error)
      return Promise.reject(error)
    }
  )

  return instance(config)
}
