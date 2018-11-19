import api from '../../service'
const actions = {
  async taskTypeList ({ commit }) {
    const result = await api.taskType.findTaskTypeList()
    return result
  },
  async createTaskType ({ commit }, { name }) {
    const result = await api.taskType.createTaskType({ name })
    return result
  }
}

export default {
  actions
}
