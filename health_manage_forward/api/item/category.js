import {
  request
} from '../../network/request'

export function getCategoryByPid(pid) {
  return request({
    url: '/item/category/list',
    method: 'get',
    params: {
      pid
    }
  })
}
