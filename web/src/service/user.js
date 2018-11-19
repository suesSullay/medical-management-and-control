import axios from './util/axios'

const login = (options) => axios.setAxiosPostPromise('user/login/', options)
const commonUserList = () => axios.setAxiosGetPromise('user/common/')

export default{
  login,
  commonUserList
}
