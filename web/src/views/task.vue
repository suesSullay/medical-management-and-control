<template>
  <div class="task">
    <el-dialog
      :visible.sync="createDialog"
      :show-close="false"
      width="80%"
      top="50px">
      <el-menu default-active="1" mode="horizontal" @select="stepSelect">
        <el-menu-item index="1">基本信息</el-menu-item>
        <el-menu-item index="2">指派给</el-menu-item>
      </el-menu>
      <el-form :model="newTask" style="margin-top:10px;">
        <div v-show="step=='1'">
          <el-form-item label="任务类型" style="display:inline-block; width:50%">
            <el-select v-model="newTask.taskType.id" placeholder="必填" style="width:80%">
              <el-option
                v-for="item in taskTypes"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
              <el-input placeholder="新增类型" style="margin-top:3px;" @keyup.enter.native="addTaskType" v-model="addTaskTypeName"></el-input>
            </el-select>
          </el-form-item>
          <el-form-item label="任务名称" style="display:inline-block; width:50%">
            <el-input v-model="newTask.name" placeholder="必填" style="width:80%"></el-input>
          </el-form-item>
          <el-form-item label="开始时间" style="display:inline-block; width:50%">
            <el-date-picker
              v-model="newTask.beginTime"
              type="datetime"
              placeholder="选择日期时间"
              style="width:80%; margin-left:-20px;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" style="display:inline-block; width:50%">
            <el-date-picker
              v-model="newTask.endTime"
              type="datetime"
              placeholder="选择日期时间"
              style="width:80%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="备选内容">
            <el-input
              type="textarea"
              :autosize="{ minRows: 5, maxRows: 8}"
              placeholder="请输入内容"
              v-model="newTask.context">
            </el-input>
          </el-form-item>
          <el-form-item style="text-align:left;">
            <el-upload
              ref="upload"
              :action="FileBase"
              :before-remove="fileBeforeRemove"
              :on-success="uploadSuccess">
              <el-button size="small" type="primary">添加附件</el-button>
            </el-upload>
          </el-form-item>
        </div>
        <el-table
          max-height="450"
          v-show="step=='2'"
          ref="userListTable"
          :data="userList"
          style="width: 100%"
          @selection-change="userListSelectionChange">
          <el-table-column
            type="selection"
            width="80">
          </el-table-column>
          <el-table-column
            property="name"
            label="名称">
          </el-table-column>
        </el-table>
        <div v-show="step==='2'" style="margin-top:10px;">
          <el-button @click="createDialog=false">取消</el-button>
          <el-button type="primary" @click="createNewTask">完成创建</el-button>
        </div>
      </el-form>
    </el-dialog>
    <div class="top">
      <el-button class="create" type="primary" @click="create">新建安排</el-button>
      <el-select v-model="taskType" placeholder="所有类型" class="task-type" @change="selectTaskType">
        <el-option label="全部" value="-1"></el-option>
        <el-option
          v-for="item in taskTypes"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="state" placeholder="提交情况" @change="selectState">
        <el-option label="全部" value="全部"></el-option>
        <el-option label="完成" value="完成"></el-option>
        <el-option label="未完成" value="未完成"></el-option>
      </el-select>
      <el-input v-model="keyword" placeholder="输入关键字" class="keyword"></el-input>
      <el-button type="primary" @click="getTaskList(keyword, taskType, state)">查询</el-button>
    </div>
    <el-table
      :data="taskList"
      style="width: 100%">
      <el-table-column
        prop="name"
        label="任务名"
        width="220">
      </el-table-column>
      <el-table-column
        :formatter="timeFormatter"
        prop="updateTime"
        label="修改时间"
        width="400">
      </el-table-column>
      <el-table-column
        prop="taskType.name"
        label="任务类型"
        width="220">
      </el-table-column>
      <el-table-column
        label="提交情况"
        width="220">
        <template slot-scope="scope">
          <span style="color: #409EFF;">{{ scope.row.endNum }}</span>/<span>{{ scope.row.userNum }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">查看详情</el-button>
          <el-button type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import moment from 'moment'
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      // 查询任务类型
      taskType: '',
      // 任务类型列表
      taskTypes: [],
      // 创建任务
      newTask: {
        taskType: {
          id: null
        },
        files: []
      },
      createDialog: false,
      // 步骤
      step: '1',
      // 新增任务类型名
      addTaskTypeName: '',
      // 查询关键字
      keyword: '',
      userList: [],
      taskList: [],
      state: ''
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    ...mapActions(['taskTypeList', 'createTaskType', 'commonUserList', 'createTask', 'findTaskList']),
    init () {
      this.getTaskTypeList()
      this.getCommonUserList()
      this.getTaskList()
    },

    selectState (val) {
      this.getTaskList(this.keyword, this.taskType, this.state)
    },
    selectTaskType (val) {
      this.getTaskList(this.keyword, this.taskType, this.state)
    },
    timeFormatter (row, column, cellValue, index) {
      console.log(cellValue)
      return moment(cellValue).format('YYYY-MM-DD hh:mm:ss')
    },
    getTaskList (name, tid, state) {
      this.findTaskList({ name, tid, state }).then(res => {
        this.taskList = res.data.data.taskPage.content
      })
    },
    getTaskTypeList () {
      this.taskTypeList().then(res => {
        this.taskTypes = res.data.data.taskTypes
      })
    },
    create () {
      this.addTaskTypeName = ''
      this.step = '1'
      this.createDialog = true
    },
    stepSelect (key) {
      this.step = key
    },
    addTaskType () {
      if (this.addTaskTypeName) {
        this.createTaskType({ name: this.addTaskTypeName }).then(res => {
          this.getTaskTypeList()
        })
      }
    },
    uploadSuccess (res, file) {
      this.newTask['files'].push(res.data)
    },
    fileBeforeRemove (file, files) {
      this.newTask['files'].map((f, index, arr) => {
        if (f.value === file.response.data.value) {
          this.newTask['files'].splice(index, 1)
        }
      })
    },
    getCommonUserList () {
      this.commonUserList().then(res => {
        this.userList = res.data.data.commonUsers
        console.log(this.userList)
      })
    },
    userListSelectionChange (val) {
      this.newTask['users'] = val
    },
    createNewTask () {
      if (this.newTask.name && this.newTask.taskType && this.newTask.beginTime && this.newTask.endTime) {
        this.newTask.createTime = new Date()
        this.newTask.updateTime = new Date()
        if (this.newTask.users) {
          this.newTask.userNum = this.newTask.users.length
        } else {
          this.newTask.userNum = 0
        }
        this.newTask.endNum = 0
        this.newTask.users = JSON.stringify(this.newTask.users)
        this.newTask.files = JSON.stringify(this.newTask.files)
        this.createTask(this.newTask).then(res => {
          if (res.data.code !== 100) {
            this.$message({
              showClose: true,
              message: '新任务创建失败',
              type: 'error'
            })
            this.$refs.upload.clearFiles()
            this.createDialog = false
            this.newTask = { taskType: { id: null }, files: [] }
          } else {
            this.$refs.upload.clearFiles()
            this.newTask = { taskType: { id: null }, files: [] }
            this.createDialog = false
          }
        })
      } else {
        this.$message({
          showClose: true,
          message: '请检查新任务信息',
          type: 'error'
        })
      }
    }
  }
}
</script>
<style>
  .task .top{
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 75px;
  }
  .task .top .create {
    border-radius: 30px;
    width: 150px;
  }
  .task .keyword{
    width: 200px;
  }
  .task .task-type{
    margin-left: 300px;
  }
  .task .el-select{
    margin-right: 20px;
  }
  .task .el-menu-item{
    width: 50%;
  }
  .task .el-dialog__header{
    height: 0px;
    padding: 0px;
  }
  .task .el-textarea{
    width: 91%;
  }
</style>
