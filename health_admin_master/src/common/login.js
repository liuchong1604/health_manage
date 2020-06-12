import {
  request
} from '../network/request'

export function login(params) {
  return request({
    url: '/user/admin/adminlogin',
    method: 'post',
    data: params,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
