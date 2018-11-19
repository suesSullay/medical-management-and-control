import api from '../../service'
const actions = {
  async login ({ commit }, { userName, passWord }) {
    const result = await api.user.login({ userName: userName, passWord: passWord })
    return result
  },
  async commonUserList ({ commit }) {
    const result = await api.user.commonUserList()
    return result
  }
}

export default {
  actions
}
