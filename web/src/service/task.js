import axios from './util/axios'

const createTask = (options) => axios.setAxiosPostPromise('task/', options)

export default{
  createTask
}
