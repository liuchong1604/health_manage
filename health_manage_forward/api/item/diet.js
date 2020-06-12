import {
  request
} from '../../network/request'

export function generateDiet(heat) {
  return request({
    url: "food/food/recommend",
    method: 'post',
    data: heat,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
