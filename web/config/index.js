import axiosConfig from './axiosConf'
import wsConf from './wsConf'

export default {
  install (Vue, options) {
    Vue.prototype.wsConf = wsConf
    Vue.prototype.BaseUrl = axiosConfig.baseUrl
    Vue.prototype.FileBase = axiosConfig.baseUrl + 'uploadFile/'
  }
}
