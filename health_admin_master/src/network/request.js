import axios from 'axios'
import store from '../store';
import {
  getToken
} from '../common/auth';

export function request(config) {
  //1创建axios的实例
  const instance = axios.create({
    baseURL: 'http://api.health.com/api',
    timeout: 5000
  })

  //axios的拦截器
  //请求拦截的作用
  instance.interceptors.request.use(
    config => {
      // if (store.getters.token) {
      //   config.headers['X-Token'] = getToken
      // }
      return config;
    }, err => {
      console.log(err);
      Promise.reject(err);
    }
  )

  //响应拦截
  instance.interceptors.response.use(res => {
    return res;
  }, err => {
    return Promise.reject(err)
  })

  //发送真正的网络请求
  return instance(config)
}
