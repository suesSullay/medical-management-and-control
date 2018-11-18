import axios from './util/axios'

const login = (options) => axios.setAxiosPostPromise('user/login/', options)

export default{
  login
}
