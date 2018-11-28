import api from '../../service'
const actions = {
  async login ({ commit }, { userName, passWord }) {
    const result = await api.user.login({ userName: userName, passWord: passWord })
    return result
  },
  async commonUserList ({ commit }) {
    const result = await api.user.commonUserList()
    return result
  },
  async findUserList ({ commit }) {
    const result = await api.user.userList()
    return result
  },
  async findUserByName ({ commit }, { name }) {
    const result = await api.user.userByName({ name })
    return result
  },
  async createUser ({ commit }, user) {
    const result = await api.user.creareUser(user)
    return result
  }
}

export default {
  actions
}
