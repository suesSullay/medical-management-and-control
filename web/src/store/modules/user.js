import api from '../../service'
const actions = {
  async test ({ commit }, data) {
    await api.test(data)
  }
}

export default {
  actions
}
