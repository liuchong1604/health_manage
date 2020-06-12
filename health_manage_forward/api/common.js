import {
  request
} from '../network/request'

export function formatData (data) { // 转换日期函数
  const dateee = new Date(data).toJSON();
  const time = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
  return time;
}

export function switchTime (numb, format) {
  if (numb !== undefined) {
    const time = new Date((numb - 1) * 24 * 3600000 + 1)
    time.setFullYear(time.getFullYear() - 70)
    const year = time.getFullYear() + ''
    const month = time.getMonth() + 1 + ''
    const date = time.getDate() + ''
    if (format && format.length === 1) {
      return year + format + month + format + date
    }
    return year + (month < 10 ? '0' + month : month) + (date < 10 ? '0' + date : date)
  } else {
    return undefined;
  }
}

export function uploadImage (uploadUrl, data) { // 上传图片
  return request({
    url: uploadUrl,
    method: "post",
    data
  })
}

export function sendSms (phone) {
  return request({
    url: '/user/user/code',
    method: 'post',
    data: phone,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}
