<template>
  <div class="notice">
    <el-dialog
      :visible.sync="createDialog"
      :show-close="false"
      width="80%"
      top="50px">
      <el-form :model="newNotice" style="margin-top:10px;">
        <el-form-item label="任务名称" style="display:inline-block; width:50%">
          <el-input v-model="newNotice.name" placeholder="必填" style="width:80%" v-if="rule!=='ROOT'"></el-input>
          <el-input v-model="newNotice.name" placeholder="必填" style="width:80%" v-else disabled></el-input>
        </el-form-item>
        <el-form-item label="备选内容">
          <el-input
            v-if="rule!=='ROOT'"
            type="textarea"
            :autosize="{ minRows: 5, maxRows: 8}"
            placeholder="请输入内容"
            v-model="newNotice.context">
          </el-input>
          <el-input
            v-else
            disabled
            type="textarea"
            :autosize="{ minRows: 5, maxRows: 8}"
            placeholder="请输入内容"
            v-model="newNotice.context">
          </el-input>
        </el-form-item>
        <el-form-item style="text-align:left;">
          <el-upload
            v-if="rule!=='ROOT'"
            ref="upload"
            :action="FileBase"
            :on-success="uploadSuccess">
            <el-button size="small" type="primary">添加附件</el-button>
          </el-upload>
        </el-form-item>
        <div v-for="file in newNotice.files" :key="file.value" class="file"><span>{{file.fileName}}</span>
          <el-button v-if="rule!=='ROOT'" type="text" @click="removeFile(file)"><i class="el-icon-close"></i></el-button>
          <el-button v-else type="text" @click="downloadFile(file)">下载</el-button>
        </div>
      </el-form>
      <el-button v-if="rule!=='ROOT'" type="primary" @click="createNewNotice">完成创建</el-button>
    </el-dialog>
    <div class="top">
      <el-button class="create" type="primary" @click="create" v-if="rule!=='ROOT'">添加素材</el-button>
      <el-select v-model="commitUser" placeholder="提交机构" class="task-type" @change="selectCommitUser" v-if="rule==='ROOT'" style="margin-left:694px;">
        <el-option
          v-for="item in userList"
          :key="item.id"
          :label="item.name"
          :value="item.name">
        </el-option>
      </el-select>
      <el-input v-model="keyword" placeholder="输入关键字" class="keyword" v-if="rule==='ROOT'"></el-input>
      <el-input v-model="keyword" placeholder="输入关键字" class="keyword" v-else style="margin-left:748px;"></el-input>
      <el-button type="primary" @click="selectCommitUser">查询</el-button>
    </div>
    <el-table
      :data="noticeList"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="任务名"
        width="350">
      </el-table-column>
      <el-table-column
        :formatter="timeFormatter"
        prop="createTime"
        label="创建时间"
        width="400">
      </el-table-column>
      <el-table-column
        prop="user.name"
        label="创建机构"
        width="350">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作">
        <template slot-scope="scope">
          <el-button @click="handleNoticeDetails(scope.row)" type="text" size="small">查看详情</el-button>
          <el-button type="text" size="small" @click="handleDeleteNotice(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top:10px;"
      background
      layout="prev, pager, next"
      :total="noticeListNum"
      :page-size="7"
      @current-change="currentChange"
      :current-page.sync="page">
    </el-pagination>
  </div>
</template>
<script>
import moment from 'moment'
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      name: '',
      userList: [],
      commitUser: '',
      keyword: '',
      rule: '',
      newNotice: {
        files: []
      },
      createDialog: false,
      detailsDialog: false,
      noticeList: [],
      page: 0,
      noticeListNum: 0
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    ...mapActions(['deleteNotice', 'createMessage', 'findUserList', 'downloadFile', 'createNotice', 'findNoticeList', 'findUserByName']),
    init () {
      this.findUserList().then(res => {
        this.userList = res.data.data.userList
      })
      this.rule = sessionStorage.getItem('rule')
      this.name = sessionStorage.getItem('name')
      this.getNoticeList(this.name)
      this.page = 1
    },
    handleNoticeDetails (row) {
      this.newNotice = row
      try {
        this.newNotice['files'] = JSON.parse(row.files)
      } catch (error) {

      }
      this.createDialog = true
    },
    handleDeleteNotice (row) {
      this.$confirm('此操作将永久删除该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteNotice(row).then(() => {
          this.init()
          let message = {}
          message['createTime'] = new Date()
          message['messageType'] = 'NOTICE'
          message['content'] = this.name + '删除了新闻' + row.name
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
    getNoticeList (userName, keyName, page) {
      this.findNoticeList({ userName, keyName, page }).then(res => {
        this.noticeList = res.data.data.noticePage.content
        this.noticeListNum = res.data.data.noticePage.totalElements
      })
    },
    currentChange () {
      if (!this.commitUser) {
        this.getNoticeList(this.name, this.keyword, this.page - 1)
      } else {
        this.getNoticeList(this.commitUser, this.keyword, this.page - 1)
      }
    },
    createNewNotice () {
      if (this.newNotice.name) {
        if (!this.newNotice.createTime) {
          this.newNotice.createTime = new Date()
          let message = {}
          message['createTime'] = new Date()
          message['messageType'] = 'NOTICE'
          message['content'] = this.name + '创建了新闻' + this.newNotice.name
          this.createMessage(message)
        } else {
          if (this.rule !== 'ROOT') {
            let message = {}
            message['createTime'] = new Date()
            message['messageType'] = 'NOTICE'
            message['content'] = this.name + '修改了新闻' + this.newNotice.name
            this.createMessage(message)
          }
        }
        this.newNotice.files = JSON.stringify(this.newNotice.files)
        this.findUserByName({ name: this.name }).then(res => {
          this.newNotice.user = res.data.data.user
          this.createNotice(this.newNotice).then(res => {
            if (res.data.code !== 100) {
              this.$message({
                showClose: true,
                message: '新闻创建失败',
                type: 'error'
              })
              this.$refs.upload.clearFiles()
              this.createDialog = false
              this.detailsDialog = false
              this.newNotice = { files: [] }
            } else {
              this.$refs.upload.clearFiles()
              this.newNotice = { files: [] }
              this.createDialog = false
              this.detailsDialog = false
              this.init()
            }
          })
        })
      } else {
        this.$message({
          showClose: true,
          message: '请检查新任务信息',
          type: 'error'
        })
      }
    },
    create () {
      this.newNotice = {
        files: []
      }
      this.createDialog = true
    },
    uploadSuccess (res, file) {
      this.newNotice['files'].push(res.data)
    },
    selectCommitUser () {
      if (!this.commitUser) {
        this.getNoticeList(this.name, this.keyword)
      } else {
        this.getNoticeList(this.commitUser, this.keyword)
      }
    },
    removeFile (file) {
      this.newNotice['files'].map((f, index, arr) => {
        if (f.value === file.value) {
          this.newNotice['files'].splice(index, 1)
        }
      })
    },
    timeFormatter (row, column, cellValue, index) {
      if (cellValue) {
        return moment(cellValue).format('YYYY-MM-DD hh:mm:ss')
      }
    }
  }
}
</script>

<style>
  .notice .top{
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 75px;
  }
  .notice .top .create {
    border-radius: 30px;
    width: 150px;
  }
  .notice .keyword{
    width: 200px;
  }
  .notice .task-type{
    margin-left: 544px;
  }
  .notice .el-upload-list__item{
    display: none;
  }
  .notice .el-textarea{
    width: 91%;
  }
  .notice .file{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
</style>
