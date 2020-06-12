import {
  request
} from '../../network/request'

export function getUsers(key, page, rows, sortBy, desc) {
  return request({
    url: "user/user/page",
    method: "get",
    params: {
      key,
      page,
      rows,
      sortBy,
      desc
    }
  })
}

export function deleteUser(id) {
  return request({
    url: "user/user/delete",
    method: "get",
    params: {
      id
    }
  })
}
