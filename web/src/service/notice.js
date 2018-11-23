import axios from './util/axios'

const createNotice = (options) => axios.setAxiosPostPromise('notice/', options)

const findNoticeList = (options) => axios.setAxiosGetPromise('notice/all', options)

const deleteNotice = (options) => axios.setAxiosDeletePromise('notice/', options)

export default{
  createNotice,
  findNoticeList,
  deleteNotice
}
