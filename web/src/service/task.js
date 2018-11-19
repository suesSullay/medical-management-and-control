import axios from './util/axios'

const createTask = (options) => axios.setAxiosPostPromise('task/', options)
const findTaskList = (options) => axios.setAxiosGetPromise('task/all', options)

export default{
  createTask,
  findTaskList
}
