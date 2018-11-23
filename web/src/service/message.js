import axios from './util/axios'

const createMessage = (options) => axios.setAxiosPostPromise('message/', options)

const findMessageList = (options) => axios.setAxiosGetPromise('message/all', options)
export default{
  createMessage,
  findMessageList
}
