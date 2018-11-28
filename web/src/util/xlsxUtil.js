const XLSX = require('xlsx')
export function excelToJson (arrayBuffer) {
  const data = new Uint8Array(arrayBuffer)
  const workbook = XLSX.read(data, {
    type: 'array'
  })
  let result = XLSX.utils.sheet_to_json(workbook.Sheets[workbook.SheetNames[0]], { defval: '' })
  return result
}

export function jsonToExcel (jsonArray) {
  const workbook = XLSX.utils.book_new()
  // Object.keys(jsonArray).forEach(function (key) {
  //   let worksheet = XLSX.utils.json_to_sheet(jsonArray[key])
  //   XLSX.utils.book_append_sheet(workbook, worksheet, key)
  // })
  let worksheet = XLSX.utils.json_to_sheet(jsonArray)
  XLSX.utils.book_append_sheet(workbook, worksheet, '专家信息')
  const wopts = { bookType: 'xlsx', bookSST: false, type: 'array' }
  const wbout = XLSX.write(workbook, wopts)
  return wbout
}

export default {
  excelToJson,
  jsonToExcel
}
