import {
  request
} from "../../network/request"

export function getTools() {
  return request({
    url: "/item/tool/cards",
    method: "get"
  })
}
