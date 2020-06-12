import {
  request
} from '../../network/request'

export function getArticlesByCid(cid) {
  return request({
    url: 'item/article/getarticle',
    method: 'get',
    params: {
      cid
    }
  })
}

export function getArticles(key, page, rows, sortBy, desc) {
  return request({
    url: 'item/article/page',
    method: 'get',
    params: {
      key,
      page,
      rows,
      sortBy,
      desc
    }
  })
}

export function getArticleById(id) {
  return request({
    url: `item/article/${id}`, // 模板表达式
    method: 'get'
  })
}
