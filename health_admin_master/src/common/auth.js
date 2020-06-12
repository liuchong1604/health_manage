import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

const in30Minutes = 1 / 48

export function setToken(token) {
  return Cookies.set(TokenKey, token, {
    expires: in30Minutes
  })
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
