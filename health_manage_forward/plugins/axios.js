export default function ({
  $axios,
  redirect
}) {
  $axios.interceptors.request.use(
    config => {
      return config
    },
    error => {
      return Promise.reject(error)
    }
  )

  $axios.interceptors.response.use(
    response => {
      const res = response.data
      if (res.status === 200) {
        console.log(res)
        return res;
      } else {
        redirect("/error")
      }
    },
    err => {
      if (err && err.response) {
        switch (err.response.status) {
          case 400:
            err.message = '请求错误(400)';
            redirect('/login');
            break;
          case 401:
            redirect('/login');
            break;
          case 403:
            err.message = '拒绝访问(403)';
            redirect('/login');
            break;
          case 404:
            err.message = '请求出错(404)';
            break;
          case 500:
            err.message = '服务器错误(500)';
            break;
          case 501:
            err.message = '服务未实现(501)';
            break;
          case 502:
            err.message = '网络错误(502)';
            break;
          case 503:
            err.message = '服务不可用(503)';
            break;
          case 504:
            err.message = '网络超时(504)';
            break;
          case 505:
            err.message = 'HTTP版本不受支持(505)';
            break;
          default:
            err.message = `连接出错(${err.response.status})!`;
        }
      } else {
        err.message = '连接服务器失败!'
      }
      console.log(err);
      return Promise.reject(err);
    })
}
