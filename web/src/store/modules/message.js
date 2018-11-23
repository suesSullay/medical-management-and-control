import api from '../../service'
const actions = {
  async createMessage ({ commit }, message) {
    const result = await api.message.createMessage(message)
    return result
  },
  async findMessageList ({ commit }, { messageType, page }) {
    const result = await api.message.findMessageList({ messageType, page })
    return result
  }
}

export default {
  actions
}
