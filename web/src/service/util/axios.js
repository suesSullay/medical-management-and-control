import axios from 'axios'

axios.defaults.timeout = 10000000
// axios.defaults.withCredentials = true
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
// axios.defaults.headers['Cache-Control'] = 'no-cache,no-store,must-revalidate,max-age=-1,private'
// axios.defaults.fileUploadUrl = localStorage.getItem('fileUploadUrl')
// axios.defaults.fileLoadUrl = localStorage.getItem('fileLoadUrl')

export default {
  setAxiosGetPromise: (url, params = {}) => {
    return axios.get(url, { params: params }).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  setAxiosPostPromise: (url, data, config) => {
    return axios.post(url, data, config).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  // 更新全部
  setAxiosPutPromise: (url, data) => {
    return axios.put(url, data).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  },
  setAxiosDeletePromise: (url, data) => {
    return axios.delete(url, { data: data }).then(response => {
      return response
    }).catch(err => {
      throw err
    })
  }
}
