import api from '../../service'
const actions = {
  async createTask ({ commit }, task) {
    const result = await api.task.createTask(task)
    return result
  },
  async findTaskList ({ commit }, { name, tid, state, page, size }) {
    let options = {}
    if (name) {
      options['name'] = name
    }
    if (tid) {
      options['tid'] = tid
    }
    if (state) {
      options['state'] = state
    }
    if (page) {
      options['page'] = page
    }
    if (size) {
      options['size'] = size
    }
    const result = await api.task.findTaskList(options)
    return result
  }
}

export default {
  actions
}
