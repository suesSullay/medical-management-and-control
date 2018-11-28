<template>
  <div class="expert">
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="createDialog"
      :show-close="true"
      width="80%"
      top="30px">
      <el-menu :default-active="step" mode="horizontal" @select="stepSelect">
        <el-menu-item index="1">基本信息</el-menu-item>
        <el-menu-item index="2">工作记录</el-menu-item>
      </el-menu>
      <el-form :model="newExpert" style="margin-top:10px;height: 600px" label-width="80px"  label-position ="left" >
        <div  v-show="step=='1'" >
          <div style="display:flex;flex-direction:row">
            <div style="flex:1 ">
              <el-form-item class="el-form-item" label="专家姓名">
                <el-input v-model="newExpert.name" placeholder="必填" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item class="el-form-item" label="性别">
                <el-select v-model="newExpert.sex" placeholder="必填" style="width:80%; margin-left:18px;">
                  <el-option :value="0" label="男"></el-option>
                  <el-option :value="1" label="女"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item class="el-form-item" label="出生年月" >
                <el-input v-model="newExpert.birthday" placeholder="必填" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item class="el-form-item" label="单位" >
                <el-input v-model="newExpert.company" placeholder="必填" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item class="el-form-item" label="职务/职称" >
                <el-input v-model="newExpert.post" placeholder="必填" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item class="el-form-item" label="专业" >
                <el-input v-model="newExpert.major" placeholder="必填" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item class="el-form-item" label="联系电话" >
                <el-input v-model="newExpert.tel" placeholder="必填" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item class="el-form-item" label="电子邮箱" >
                <el-input v-model="newExpert.mail" placeholder="必填" style="width:80%"></el-input>
              </el-form-item>
              <el-form-item class="el-form-item" label="证件号码" >
                <el-input v-model="newExpert.idNo" placeholder="必填" style="width:80%"></el-input>
              </el-form-item>
            </div>
            <div style="flex:1">
              <el-form-item class="el-form-item" label="专家简介">
                <el-input
                  type="textarea"
                  :autosize="{ minRows: 25, maxRows: 36}"
                  placeholder="选填"
                  v-model="newExpert.context">
                </el-input>
              </el-form-item>
            </div>
          </div>
          <el-button  type="primary" @click="createNewExpert(false)">完成创建</el-button>
        </div>
        <div v-show="step=='2'" class="top" style="display:flex;flex-direction:column">
          <div style="width: 100%; text-align:left;">
            <el-button class="create" type="primary" @click="createRecord" style="border-radius:5px; margin-left:15px;">新增出行记录</el-button>
          </div>
          <div style="width: 100%">
            <el-table
              :data="newExpert.records"
              style="width: 100%">
              <el-table-column
                :formatter="timeFormatter"
                prop="createTime"
                label="开始时间"
                width="300">
              </el-table-column>'
              <el-table-column
                :formatter="timeFormatter"
                prop="endTime"
                label="结束时间"
                width="300">
              </el-table-column>'
              <el-table-column
                prop="hospital"
                label="到访医院"
                width="150">
              </el-table-column>
              <el-table-column
                prop="type"
                label="记录类型"
                width="220">
              </el-table-column>
              <el-table-column
                fixed="right"
                label="操作">
                <template slot-scope="scope">
                  <el-button @click="handleRecordDetails(scope.row)" type="text" size="small">查看详情</el-button>
                  <el-button type="text" size="small" @click="handleDeleteRecord(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-form>
    </el-dialog>

    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="createRecordDialog"
      :show-close="true"
      width="60%"
      top="30px">
      <h1 style="width:100%">添加或修改出行记录</h1>
      <el-form :model="newRecord" style="margin-top:10px"  label-width="80px">
        <div>
          <el-form-item class="el-form-item" label="出行医生">
            <el-input v-model="newExpert.name" placeholder="必填" style="width:91%" disabled></el-input>
          </el-form-item>
          <el-form-item label="开始时间" style="display:inline-block; width:50%">
            <el-date-picker
              v-model="newRecord.createTime"
              type="datetime"
              placeholder="必填"
              style="width:80%">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="完成时间" style="display:inline-block; width:50%">
            <el-date-picker
              v-model="newRecord.endTime"
              type="datetime"
              placeholder="必填"
              style="width:80%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item class="el-form-item" label="出行类型" style="display:inline-block;width:50%">
            <el-input v-model="newRecord.type" placeholder="必填" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item class="el-form-item" label="走访医院" style="display:inline-block;width:50%">
            <el-input v-model="newRecord.hospital" placeholder="必填" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item class="el-form-item" >
            <el-input
              type="textarea"
              :autosize="{ minRows: 10, maxRows: 12}"
              placeholder="备注"
              v-model="newRecord.context"
              style="width:91%">
            </el-input>
          </el-form-item>
          <div  style="margin-top:10px;">
            <el-button @click="createRecordDialog=false">取消</el-button>
            <el-button type="primary" @click="createNewRecord">完成</el-button>
          </div>
        </div>
      </el-form>
    </el-dialog>

    <div class="top">
      <el-button class="create" type="primary" @click="create">添加专家</el-button>
      <el-input v-model="keyword" placeholder="输入关键字" class="keyword"></el-input>
      <el-button type="primary" @click="queryKeyWord">搜索</el-button>
      <uploadExcel @loaded="handleImport"></uploadExcel>
      <el-button type="primary" @click="exportInfo">导出</el-button>
    </div>
    <el-table
      :data="expertList"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="姓名"
        width="200">
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        width="200"
        :formatter="sexFormatter">
      </el-table-column>
      <el-table-column
        prop="birthday"
        label="出生年月"
        width="250">
      </el-table-column>
      <el-table-column
        prop="company"
        label="单位"
        width="300">
      </el-table-column>
      <el-table-column
        prop="recordNum"
        label="出行记录"
        width="200">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作">
        <template slot-scope="scope">
          <el-button @click="handleExpertDetails(scope.row)" type="text" size="small">查看详情</el-button>
          <el-button type="text" size="small" @click="handleDeleteExpert(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top:10px;"
      background
      layout="prev, pager, next"
      :total="expertListNum"
      :page-size="7"
      @current-change="currentChange"
      :current-page.sync="page">
    </el-pagination>
  </div>
</template>

<script>
import moment from 'moment'
import { mapActions } from 'vuex'
import fileSaver from 'file-saver'
import uploadExcel from '@/components/UploadExcel'
import xlsxUtil from '../util/xlsxUtil.js'
import _ from 'lodash'
export default {
  components: {
    uploadExcel
  },
  data () {
    return {
      // 添加专家
      expertListNum: 0,
      newExpert: {
        records: []
      },
      // 添加行为记录
      newRecord: {
      },
      createDialog: false,
      createRecordDialog: false,
      // 步骤
      step: '1',
      // 查询关键字
      keyword: '',
      page: 1,
      rule: '',
      name: '',
      expertList: []
    }
  },
  mounted () {
    this.rule = sessionStorage.getItem('rule')
    this.name = sessionStorage.getItem('name')
    this.init()
  },
  methods: {
    ...mapActions(['createMessage', 'createExpert', 'deleteExpert', 'findExpertList', 'findUserByName']),

    init () {
      this.getExperList(this.name)
      this.page = 1
    },
    async exportInfo () {
      let info = _.cloneDeep(this.expertList)
      info.forEach(i => {
        delete i.user
        delete i.id
        delete i.createTime
        delete i.records
        delete i.recordNum
        i['姓名'] = i.name
        i['生日'] = i.birthday
        i['单位'] = i.company
        i['职务/职称'] = i.post
        i['专业'] = i.major
        i['联系电话'] = i.tel
        i['电子邮箱'] = i.mail
        i['证件号码'] = i.idNo
        i['专家简介'] = i.context
        if (i.sex === 1) {
          i['性别'] = '女'
        } else {
          i['性别'] = '男'
        }
        delete i.name
        delete i.birthday
        delete i.company
        delete i.post
        delete i.major
        delete i.tel
        delete i.mail
        delete i.idNo
        delete i.context
        delete i.sex
      })
      const xlsx = xlsxUtil.jsonToExcel(
        info
      )
      const blob = new Blob([xlsx], {
        type: 'application/octet-stream'
      })
      fileSaver.saveAs(blob, '专家信息.xlsx')
    },
    async handleImport (arrayBuffer) {
      const tempExpertList = await xlsxUtil.excelToJson(arrayBuffer)
      let user = ''
      this.findUserByName({ name: this.name }).then(async res => {
        user = res.data.data.user
        await tempExpertList.forEach(r => {
          r['user'] = user
          r.name = r['姓名']
          r.birthday = r['生日']
          r.company = r['单位']
          r.post = r['职务/职称']
          r.major = r['专业']
          r.tel = r['联系电话']
          r.mail = r['电子邮箱']
          r.idNo = r['证件号码']
          r.context = r['专家简介']
          if (r['性别'] === '女') {
            r.sex = 1
          } else {
            r.sex = 0
          }
          delete r['姓名']
          delete r['生日']
          delete r['单位']
          delete r['职务/职称']
          delete r['专业']
          delete r['联系电话']
          delete r['电子邮箱']
          delete r['证件号码']
          delete r['专家简介']
          delete r['性别']

          r.createTime = new Date()
          r.records = []
          try {
            this.createExpert(r)
          } catch (error) {
            console.log(error)
          }
        })
      }).then(() => {
        this.init()
      })
    },
    queryKeyWord () {
      this.getExperList(this.name, this.keyword)
    },
    handleRecordDetails (row) {
      this.newRecord = row
      this.createRecordDialog = true
    },
    handleExpertDetails (row) {
      this.newExpert = row
      this.createDialog = true
    },
    handleDeleteRecord (row) {
      this.$confirm('此操作将永久删除该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.newExpert.records = this.newExpert.records.filter(r => {
          return r.id !== row.id
        })
        this.createNewExpert(true)
        let message = {}
        message['createTime'] = new Date()
        message['messageType'] = 'EXPERT'
        message['content'] = this.name + '删除了专家' + row.name
        this.createMessage(message)
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleDeleteExpert (row) {
      this.$confirm('此操作将永久删除该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteExpert(row).then(() => {
          this.init()
          let message = {}
          message['createTime'] = new Date()
          message['messageType'] = 'EXPERT'
          message['content'] = this.name + '删除了专家' + row.name
          this.createMessage(message)
        })
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    getExperList (userName, keyName, page) {
      this.findExpertList({ userName, keyName, page }).then(res => {
        this.expertList = res.data.data.expertPage.content
        this.expertListNum = res.data.data.expertPage.totalElements
        this.expertList.map(expert => {
          expert.recordNum = expert.records.length
        })
      })
    },
    currentChange () {
      this.getExperList(this.name, this.keyword, this.page - 1)
    },
    timeFormatter (row, column, cellValue, index) {
      console.log(this.newExpert)
      console.log(cellValue)
      if (cellValue) {
        return moment(cellValue).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    sexFormatter (row, column, cellValue, index) {
      if (cellValue === 1) {
        return '女'
      } else {
        return '男'
      }
    },
    create () {
      this.newExpert = {
        records: []
      }
      this.step = '1'
      this.createDialog = true
    },

    createRecord () {
      this.newRecord = {}
      this.createRecordDialog = true
    },

    stepSelect (key) {
      this.step = key
    },

    createNewRecord () {
      if (this.newRecord.createTime && this.newRecord.endTime &&
            this.newRecord.type && this.newRecord.hospital) {
        console.log(this.newRecord)
        let newR = true
        this.newExpert.records.forEach(r => {
          if (r.id === this.newRecord.id) {
            newR = false
            r = this.newRecord
          }
        })
        if (newR) {
          this.newExpert.records.push(this.newRecord)
        }
        this.createRecordDialog = false
      } else {
        this.$message({
          showClose: true,
          message: '请检查出行记录信息',
          type: 'error'
        })
      }
    },

    createNewExpert (isDeleteRecord) {
      console.log(isDeleteRecord)
      if (this.newExpert.name && this.newExpert.sex != null && this.newExpert.birthday &&
            this.newExpert.company && this.newExpert.post &&
            this.newExpert.major && this.newExpert.tel && this.newExpert.mail && this.newExpert.idNo) {
        if (!this.newExpert.createTime) {
          this.newExpert.createTime = new Date()
          let message = {}
          message['createTime'] = new Date()
          message['messageType'] = 'EXPERT'
          message['content'] = this.name + '创建了新专家' + this.newExpert.name
          this.createMessage(message)
        } else {
          let message = {}
          message['createTime'] = new Date()
          message['messageType'] = 'EXPERT'
          message['content'] = this.name + '修改了专家' + this.newExpert.name
          this.createMessage(message)
        }
        this.findUserByName({ name: this.name }).then(res => {
          this.newExpert.user = res.data.data.user
          this.createExpert(this.newExpert).then(res => {
            if (res.data.code !== 100) {
              this.$message({
                showClose: true,
                message: '专家创建失败',
                type: 'error'
              })
              if (!isDeleteRecord) {
                this.createDialog = false
                this.createRecordDialog = false
              }
              this.newExpert = {
                records: []
              }
            } else {
              this.newExpert = {
                records: []
              }
              if (!isDeleteRecord) {
                this.createDialog = false
                this.createRecordDialog = false
              }
              this.init()
            }
          })
        })
      } else {
        this.$message({
          showClose: true,
          message: '请检查专家信息',
          type: 'error'
        })
      }
    }
  }
}
</script>
<style>
  .expert .top{
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 75px;
  }
  .expert .top .create {
    border-radius: 30px;
    width: 150px;
  }
  .expert .keyword{
    width: 200px;
    margin-left: 748px;
  }
  .expert .task-type{
    margin-left: 300px;
  }
  .expert .el-form-item{
    margin-bottom: 15px;
     width:100%;
  }
  .expert .el-select{
    margin-right: 20px;
  }
  .expert .el-menu-item{
    width: 50%;
  }
  .expert .el-dialog__header{
    height: 0px;
    padding: 0px;
  }
  .expert .el-textarea{
    width: 91%;
  }
</style>
