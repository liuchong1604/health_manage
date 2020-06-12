import {
  request
} from '../../network/request'

export function getResult(data) {
  return request({
    url: "user/health/evaluate",
    method: "post",
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
