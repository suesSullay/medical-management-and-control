import axios from 'axios'
const baseUrl = 'http://localhost:7000/'
// const baseUrl = 'http://47.96.16.1:7000'
axios.defaults.baseURL = baseUrl
export default {
  baseUrl
}
