import axios from './util/axios'

const createMessage = (options) => axios.setAxiosPostPromise('message/', options)

export default{
  createMessage
}
