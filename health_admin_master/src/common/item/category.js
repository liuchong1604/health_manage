import {
  request
} from "../../network/request"

export function getCategoryByPid(pid) {
  return request({
    url: '/item/category/list',
    method: 'get',
    params: {
      pid
    }
  })
}

export function deleteCategory(id) {
  return request({
    url: "/item/category/delete",
    method: 'get',
    params: {
      id
    }
  })
}
