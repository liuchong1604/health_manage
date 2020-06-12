import {
  request
} from '../../network/request'

export function findByUid (id, uid) {
  return request({
    url: `item/exam/${id}`,
    method: 'get',
    params: {
      uid
    }
  })
}

export function findAllByUid (uid) {
  return request({
    url: 'item/exam/all',
    method: 'get',
    params: {
      uid
    }
  })
}

export function addFromExcel (exams) {
  return request({
    url: 'item/exam/add',
    method: 'post',
    data: exams,
    headers: {
      'Content-Type': 'application/json'
    }
  })
}

export function deleteExcelData (examedtimes) {
  return request({
    url: 'item/exam/delete',
    method: 'get',
    params: {
      examedtimes
    }
  })
}
