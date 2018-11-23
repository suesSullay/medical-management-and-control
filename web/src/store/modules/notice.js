import api from '../../service'
const actions = {
  async createNotice ({ commit }, notice) {
    const result = await api.notice.createNotice(notice)
    return result
  },
  async deleteNotice ({ commit }, notice) {
    const result = await api.notice.deleteNotice(notice)
    return result
  },
  async findNoticeList ({ commit }, { userName, keyName, page }) {
    const result = await api.notice.findNoticeList({ userName, keyName, page })
    return result
  }
}

export default {
  actions
}
