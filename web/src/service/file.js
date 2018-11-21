import axios from './util/axios'

const downloadFile = (options) => axios.setAxiosGetPromise('/download', options, 'arraybuffer')

export default{
  downloadFile
}
