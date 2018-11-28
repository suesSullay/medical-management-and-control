import api from '../../service'
const actions = {
  async createCallBoard ({ commit }, callBoard) {
    const result = await api.callBoard.createCallBoard(callBoard)
    return result
  },
  async deleteCallBoard ({ commit }, callBoard) {
    const result = await api.callBoard.deleteCallBoard(callBoard)
    return result
  },
  async findCallBoardList ({ commit }, { keyName, page }) {
    const result = await api.callBoard.findCallBoardList({ keyName, page })
    return result
  }
}

export default {
  actions
}
