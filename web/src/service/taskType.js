import axios from './util/axios'

const findTaskTypeList = () => axios.setAxiosGetPromise('taskType/all/')
const createTaskType = (options) => axios.setAxiosPostPromise('taskType/', options)

export default{
  findTaskTypeList,
  createTaskType
}
