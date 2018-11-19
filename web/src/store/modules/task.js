import api from '../../service'
const actions = {
  async createTask ({ commit }, task) {
    const result = await api.task.createTask(task)
    return result
  }
}

export default {
  actions
}
