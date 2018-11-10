import api from '../../service'
const actions = {
  async createUser ({ commit }) {
    await api.create_user()
  }
}

export default {
  actions
}
