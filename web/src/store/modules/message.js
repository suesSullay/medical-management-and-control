import api from '../../service'
const actions = {
  async createMessage ({ commit }, message) {
    const result = await api.message.createMessage(message)
    return result
  }
}

export default {
  actions
}
