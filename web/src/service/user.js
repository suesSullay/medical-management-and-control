import axios from './util/axios'

const createUser = () => axios.setAxiosPostPromise('user/')

export default{
  createUser
}
