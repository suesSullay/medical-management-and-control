import axios from './util/axios'

const createExpert = (options) => axios.setAxiosPostPromise('expert/', options)

const findExpertList = (options) => axios.setAxiosGetPromise('expert/all', options)

const deleteExpert = (options) => axios.setAxiosDeletePromise('expert/', options)

export default{
  createExpert,
  findExpertList,
  deleteExpert
}
