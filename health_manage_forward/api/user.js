import {
  request
} from '../network/request'

export function sendSms (phone) {
  return request({
    url: '/user/user/code',
    method: 'post',
    data: phone,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export function register (data) {
  return request({
    url: '/user/user/register',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export function login (data) {
  return request({
    url: '/user/user/userlogin',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export function getInfoByToken (token) {
  return request({
    url: '/user/user/getUserInfo',
    method: 'get',
    params: {
      token
    }
  })
}

export function verify (phone, code) {
  return request({
    url: "/user/user/verify",
    method: 'get',
    params: {
      phone, code
    }
  })
}

export function forgetPwd (data) {
  return request({
    url: "/user/user/forgetPwd",
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export function modifyPwd (data) {
  return request({
    url: "/user/user/modifyPwd",
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export function findByUid (id) {
  return request({
    url: "/user/user/getById",
    method: "get",
    params: {
      id
    }
  })
}

export function modifyUser (data) {
  return request({
    url: "/user/user/modify",
    method: "post",
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
