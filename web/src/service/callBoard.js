import axios from './util/axios'

const createCallBoard = (options) => axios.setAxiosPostPromise('callBoard/', options)

const findCallBoardList = (options) => axios.setAxiosGetPromise('callBoard/all', options)

const deleteCallBoard = (options) => axios.setAxiosDeletePromise('callBoard/', options)

export default{
  createCallBoard,
  findCallBoardList,
  deleteCallBoard
}
