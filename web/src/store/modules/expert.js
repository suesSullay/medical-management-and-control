import api from '../../service'
const actions = {
  async createExpert ({ commit }, expert) {
    const result = await api.expert2.createExpert(expert)
    return result
  },
  async deleteExpert ({ commit }, expert) {
    const result = await api.expert2.deleteExpert(expert)
    return result
  },
  async findExpertList ({ commit }, { userName, keyName, page }) {
    const result = await api.expert2.findExpertList({ userName, keyName, page })
    return result
  }
}

export default {
  actions
}
