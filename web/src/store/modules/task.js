import api from '../../service'
const actions = {
  async createTask ({ commit }, task) {
    const result = await api.task.createTask(task)
    return result
  },
  async deleteTask ({ commit }, task) {
    const result = await api.task.deleteTask(task)
    return result
  },
  async findTaskById ({ commit }, id) {
    const result = await api.task.findTaskById({ 'id': id })
    console.log(result)
    return result
  },
  async findTaskList ({ commit }, { name, tid, state, user, page, size }) {
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
    if (user) {
      options['user'] = user
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
