import api from '../../service'
const actions = {
  async login ({ commit }, { userName, passWord }) {
    const result = await api.user.login({ userName: userName, passWord: passWord })
    return result
  }
}

export default {
  actions
}
