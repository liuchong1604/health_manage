import {
  request
} from '../../network/request'

export function getCards() {
  return request({
    url: 'item/tool/cards',
    method: 'get'
  })
}

export function getCardById(id) {
  return request({
    url: `/item/tool/${id}`,
    method: 'get'
  })
}
