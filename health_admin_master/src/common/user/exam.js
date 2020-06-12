import {
  request
} from "../../network/request"

export function deleteExam(id) {
  return request({
    url: "item/exam/delete",
    method: "get",
    params: {
      id
    }
  })
}

export function findAll() {
  return request({
    url: "item/exam/findAll",
    method: "get"
  })
}
