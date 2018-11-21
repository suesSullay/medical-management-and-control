import api from '../../service'
const actions = {
  async downloadFile ({ commit }, options) {
    const result = await api.file.downloadFile(options)
    let content = result.data
    let fileName = options.fileName
    const blob = new Blob([content])
    if (window.navigator.msSaveOrOpenBlob) {
      // 兼容IE10
      navigator.msSaveBlob(blob, fileName)
    } else {
      //  chrome/firefox
      let aTag = document.createElement('a')
      aTag.download = fileName
      aTag.href = window.URL.createObjectURL(blob)
      aTag.click()
      URL.revokeObjectURL(aTag.href)
    }
    return result
  }
}

export default {
  actions
}
