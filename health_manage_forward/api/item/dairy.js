import {
  request
} from '../../network/request'

export function getAllByUid (uid) {
  return request({
    url: "dairy/dairy/all",
    method: 'get',
    params: {
      uid
    }
  })
}

export function getDairy (createdtime) {
  return request({
    url: "dairy/dairy/getByTime",
    method: 'get',
    params: {
      createdtime
    }
  })
}

export function getFoods (did) {
  return request({
    url: "dairy/dairy/getFoods",
    method: 'get',
    params: {
      did
    }
  })
}

export function deleteDairy (createdtime) {
  return request({
    url: "/dairy/dairy/delete",
    method: "get",
    params: {
      createdtime
    }
  })
}

export function saveDairy (data) {
  return request({
    url: "/dairy/dairy/saveDairy",
    method: "post",
    data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function updateDairy (data) {
  return request({
    url: "/dairy/dairy/updateDairy",
    method: "post",
    data,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function getByTimes (createdtime) {
  return request({
    url: "/dairy/dairy/getByTimes",
    method: "get",
    params: {
      createdtime
    }
  })
}

export function getAllFoods () {
  return request({
    url: "/food/food/all",
    method: "get"
  })
}

export function getFoodsByFid (ids) {
  return request({
    url: "/food/food/getByIds",
    method: "get",
    params: {
      ids
    }
  })
}
