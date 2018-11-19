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
      <el-button>删除</el-button>
      <el-button>移动到</el-button>
      <el-select v-model="taskType" placeholder="所有类型" class="task-type">
        <el-option label="全部" value="全部"></el-option>
        <el-option
          v-for="item in taskTypes"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="taskType" placeholder="提交情况">
        <el-option label="全部" value="全部"></el-option>
        <el-option label="完成" value="完成"></el-option>
        <el-option label="未完成" value="未完成"></el-option>
      </el-select>
      <el-input v-model="keyword" placeholder="输入关键字" class="keyword"></el-input>
      <el-button type="primary">查询</el-button>
    </div>
  </div>
</template>

<script>
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
      step: 1,
      // 新增任务类型名
      addTaskTypeName: '',
      // 查询关键字
      keyword: '',
      userList: []
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    ...mapActions(['taskTypeList', 'createTaskType', 'commonUserList', 'createTask']),
    init () {
      this.getTaskTypeList()
      this.getCommonUserList()
    },
    getTaskTypeList () {
      this.taskTypeList().then(res => {
        this.taskTypes = res.data.data.taskTypes
      })
    },
    create () {
      this.addTaskTypeName = ''
      this.step = 1
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
        this.newTask.userNum = this.newTask.users.length
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
