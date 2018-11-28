import axios from './util/axios'

const login = (options) => axios.setAxiosPostPromise('user/login/', options)
const commonUserList = () => axios.setAxiosGetPromise('user/common/')
const userList = () => axios.setAxiosGetPromise('user/all')
const userByName = (options) => axios.setAxiosGetPromise('user/', options)
const createUser = (options) => axios.setAxiosPostPromise('user/', options)

export default{
  login,
  commonUserList,
  userList,
  userByName,
  createUser
}
