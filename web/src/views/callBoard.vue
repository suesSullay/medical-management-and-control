<template>
  <div class="callBoard">
    <el-dialog
      :visible.sync="createDialog"
      :show-close="false"
      width="80%"
      top="50px">
      <el-form :model="newCallBoard" style="margin-top:10px;">
        <el-form-item label="任务名称" style="display:inline-block; width:100%">
          <el-input v-model="newCallBoard.name" placeholder="必填" style="width:91%" v-if="rule==='ROOT'"></el-input>
          <el-input v-model="newCallBoard.name" placeholder="必填" style="width:91%" v-else disabled></el-input>
        </el-form-item>
        <el-form-item label="备选内容">
          <el-input
            v-if="rule==='ROOT'"
            type="textarea"
            :autosize="{ minRows: 5, maxRows: 8}"
            placeholder="请输入内容"
            v-model="newCallBoard.context">
          </el-input>
          <el-input
            v-else
            disabled
            type="textarea"
            :autosize="{ minRows: 5, maxRows: 8}"
            placeholder="请输入内容"
            v-model="newCallBoard.context">
          </el-input>
        </el-form-item>
        <el-form-item style="text-align:left;">
          <el-upload
            v-if="rule==='ROOT'"
            ref="upload"
            :action="FileBase"
            :on-success="uploadSuccess">
            <el-button size="small" type="primary">添加附件</el-button>
          </el-upload>
        </el-form-item>
        <div v-for="file in newCallBoard.files" :key="file.value" class="file"><span>{{file.fileName}}</span>
          <el-button v-if="rule==='ROOT'" type="text" @click="removeFile(file)"><i class="el-icon-close"></i></el-button>
          <el-button v-else type="text" @click="downloadFile(file)">下载</el-button>
        </div>
      </el-form>
      <el-button v-if="rule==='ROOT'" type="primary" @click="createNewCallBoard">完成创建</el-button>
    </el-dialog>
    <div class="top">
      <el-button class="create" type="primary" @click="create" v-if="rule==='ROOT'">添加素材</el-button>
      <el-input v-model="keyword" placeholder="输入关键字" class="keyword" v-if="rule!=='ROOT'" style="margin-left:898px;"></el-input>
      <el-input v-model="keyword" placeholder="输入关键字" class="keyword" v-else style="margin-left:748px;"></el-input>
      <el-button type="primary" @click="selectCommitUser">查询</el-button>
    </div>
    <el-table
      :data="callBoardList"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="任务名"
        width="520">
      </el-table-column>
      <el-table-column
        :formatter="timeFormatter"
        prop="createTime"
        label="创建时间"
        width="520">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作">
        <template slot-scope="scope">
          <el-button @click="handleCallBoardDetails(scope.row)" type="text" size="small">查看详情</el-button>
          <el-button type="text" size="small" @click="handleDeleteCallBoard(scope.row)" v-if="rule==='ROOT'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top:10px;"
      background
      layout="prev, pager, next"
      :total="callBoardListNum"
      :page-size="7"
      @current-change="currentChange"
      :current-page.sync="page">
    </el-pagination>
  </div>
</template>
<script>
import moment from 'moment'
import { mapActions } from 'vuex'
import _ from 'lodash'
export default {
  data () {
    return {
      name: '',
      keyword: '',
      rule: '',
      newCallBoard: {
        files: []
      },
      createDialog: false,
      callBoardList: [],
      page: 0,
      callBoardListNum: 0
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    ...mapActions(['deleteCallBoard', 'createMessage', 'downloadFile', 'createCallBoard', 'findCallBoardList']),
    init () {
      this.rule = sessionStorage.getItem('rule')
      this.name = sessionStorage.getItem('name')
      this.getCallBoardList()
      this.page = 1
    },
    handleCallBoardDetails (row) {
      this.newCallBoard = row
      try {
        this.newCallBoard['files'] = JSON.parse(row.files)
      } catch (error) {

      }
      this.createDialog = true
    },
    handleDeleteCallBoard (row) {
      this.$confirm('此操作将永久删除该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteCallBoard(row).then(() => {
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
    getCallBoardList (keyName, page) {
      this.findCallBoardList({ keyName, page }).then(res => {
        this.callBoardList = res.data.data.callBoardPage.content
        this.callBoardListNum = res.data.data.callBoardPage.totalElements
      })
    },
    currentChange () {
      this.getCallBoardList(this.keyword, this.page - 1)
    },
    createNewCallBoard () {
      if (this.newCallBoard.name) {
        if (!this.newCallBoard.createTime) {
          this.newCallBoard.createTime = new Date()
          let message = {}
          message['createTime'] = new Date()
          message['messageType'] = 'NOTICE'
          message['content'] = this.name + '创建了新闻' + this.newCallBoard.name
          this.createMessage(message)
        } else {
          if (this.rule === 'ROOT') {
            let message = {}
            message['createTime'] = new Date()
            message['messageType'] = 'NOTICE'
            message['content'] = this.name + '修改了新闻' + this.newCallBoard.name
            this.createMessage(message)
          }
        }
        let temp = _.cloneDeep(this.newCallBoard)
        temp.files = JSON.stringify(temp.files)
        this.createCallBoard(temp).then(res => {
          if (res.data.code !== 100) {
            this.$message({
              showClose: true,
              message: '新闻创建失败',
              type: 'error'
            })
            this.$refs.upload.clearFiles()
            this.createDialog = false
            this.newCallBoard = { files: [] }
          } else {
            this.$refs.upload.clearFiles()
            this.newCallBoard = { files: [] }
            this.createDialog = false
            this.init()
          }
        })
      } else {
        this.$message({
          showClose: true,
          message: '请检查新闻信息',
          type: 'error'
        })
      }
    },
    create () {
      this.newCallBoard = {
        files: []
      }
      this.createDialog = true
    },
    uploadSuccess (res, file) {
      this.newCallBoard['files'].push(res.data)
    },
    selectCommitUser () {
      this.getCallBoardList(this.keyword)
    },
    removeFile (file) {
      this.newCallBoard['files'].map((f, index, arr) => {
        if (f.value === file.value) {
          this.newCallBoard['files'].splice(index, 1)
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
  .callBoard .top{
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 75px;
  }
  .callBoard .top .create {
    border-radius: 30px;
    width: 150px;
  }
  .callBoard .keyword{
    width: 200px;
  }
  .callBoard .task-type{
    margin-left: 544px;
  }
  .callBoard .el-upload-list__item{
    display: none;
  }
  .callBoard .el-textarea{
    width: 91%;
  }
  .callBoard .file{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
</style>
