<template>
  <div class="task">
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="taskUserDetailsDialog"
      :show-close="true"
      width="40%"
      top="200px">
      <h1 style="margin-bottom:30px;">{{taskUserDetails.name}}提交详情</h1>
      <el-input
        style="margin-bottom:10px;"
        disabled
        type="textarea"
        :autosize="{ minRows: 5, maxRows: 8}"
        placeholder="请输入内容"
        v-model="taskUserDetails.context">
      </el-input>
      <div v-for="file in taskUserDetails.files" :key="file.value" class="file"><span>{{file.fileName}}</span>
        <el-button type="text" @click="downloadFile(file)">下载</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="detailsDialog"
      :show-close="true"
      width="80%"
      top="50px">
      <el-menu :default-active="step" mode="horizontal" @select="stepSelect">
        <el-menu-item index="1">基本信息</el-menu-item>
        <el-menu-item index="2" v-show="rule=='ROOT'">提交记录</el-menu-item>
      </el-menu>
      <el-form :model="newTask" style="margin-top:10px;">
        <div v-show="step=='1'">
          <el-form-item label="任务类型" style="display:inline-block; width:50%">
            <el-select v-model="newTask.taskType.id" placeholder="必填" style="width:80%" v-if="rule==='ROOT'">
              <el-option
                v-for="item in taskTypes"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
              <el-input placeholder="新增类型" style="margin-top:3px;" @keyup.enter.native="addTaskType" v-model="addTaskTypeName"></el-input>
            </el-select>
            <el-select v-model="newTask.taskType.id" placeholder="必填" style="width:80%" disabled v-else>
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
            <el-input v-model="newTask.name" placeholder="必填" style="width:80%" v-if="rule==='ROOT'"></el-input>
            <el-input v-model="newTask.name" placeholder="必填" style="width:80%" disabled v-else></el-input>
          </el-form-item>
          <el-form-item label="开始时间" style="display:inline-block; width:50%">
            <el-date-picker
              v-if="rule==='ROOT'"
              v-model="newTask.beginTime"
              type="datetime"
              placeholder="选择日期时间"
              style="width:80%; margin-left:-20px;">
            </el-date-picker>
            <el-date-picker
              v-else
              disabled
              v-model="newTask.beginTime"
              type="datetime"
              placeholder="选择日期时间"
              style="width:80%; margin-left:-20px;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" style="display:inline-block; width:50%">
            <el-date-picker
              v-if="rule==='ROOT'"
              v-model="newTask.endTime"
              type="datetime"
              placeholder="选择日期时间"
              style="width:80%;">
            </el-date-picker>
            <el-date-picker
              v-else
              disabled
              v-model="newTask.endTime"
              type="datetime"
              placeholder="选择日期时间"
              style="width:80%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="备选内容">
            <el-input
              v-if="rule==='ROOT'"
              type="textarea"
              :autosize="{ minRows: 5, maxRows: 8}"
              placeholder="请输入内容"
              v-model="newTask.context">
            </el-input>
            <el-input
              v-else
              disabled
              type="textarea"
              :autosize="{ minRows: 5, maxRows: 8}"
              placeholder="请输入内容"
              v-model="newTask.context">
            </el-input>
          </el-form-item>
          <el-form-item style="text-align:left;" label="附件列表">
            <el-upload
              class="upload"
              v-if="rule==='ROOT'"
              ref="upload"
              :action="FileBase"
              :before-remove="fileBeforeRemove"
              :on-success="uploadSuccess">
              <el-button size="small" type="primary">添加附件</el-button>
            </el-upload>
            <el-upload
              disabled
              class="upload"
              v-else
              ref="upload"
              :action="FileBase"
              :before-remove="fileBeforeRemove"
              :on-success="uploadSuccess">
              <el-button size="small" type="primary" disabled>添加附件</el-button>
            </el-upload>
          </el-form-item>
          <div v-for="file in newTask.files" :key="file.value" class="file" style="margin-left:88px;"><span>{{file.fileName}}</span>
            <el-button v-if="rule==='ROOT'" type="text" @click="removeFile(file)"><i class="el-icon-close"></i></el-button>
            <el-button v-else type="text" @click="downloadFile(file)">下载</el-button>
          </div>
          <div v-if="rule!=='ROOT'" style="text-align:left; margin:10px;">
            <div class="underLine"></div>
            <el-input
              style="margin-left:80px; margin-bottom:10px;"
              type="textarea"
              :autosize="{ minRows: 5, maxRows: 8}"
              placeholder="请输入内容"
              v-model="userContext">
            </el-input>
            <el-upload
              style="margin-bottom:20px; margin-left:80px;"
              ref="upload"
              :action="FileBase"
              :before-remove="fileBeforeRemoveSub"
              :on-success="uploadSuccessSub">
              <el-button size="small" type="primary">添加附件</el-button>
            </el-upload>
            <div v-for="file in userFileList" :key="file.value" class="file" style="margin-left:88px;"><span>{{file.fileName}}</span>
              <el-button  type="text" @click="removeFileSub(file)"><i class="el-icon-close"></i></el-button>
            </div>
          </div>

          <el-button v-if="rule!=='ROOT'" type="primary" @click="createNewTask">确认</el-button>
        </div>
        <div v-show="step=='2'&&!reassign" style="text-align: left; margin:10px;"><el-button type="primary" @click="reassignClick">重新指派</el-button></div>
        <el-table
          max-height="450"
          v-show="step=='2'&&reassign"
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
        <el-button v-show="step=='2'&&reassign" type="primary" @click="confirmReassign">确定</el-button>
        <el-table
          max-height="450"
          v-show="step=='2'&&!reassign"
          :data="newTask.users"
          style="width: 100%">
          <el-table-column
            property="name"
            label="分中心"
            width="300">
          </el-table-column>
          <el-table-column
            property="updateTime"
            label="更新时间"
            :formatter="timeFormatter"
            width="300">
          </el-table-column>
          <el-table-column
            :formatter="isConfirmFormatter"
            property="isConfirm"
            label="是否查看"
            width="200">
          </el-table-column>
          <el-table-column
            property="state"
            label="提交情况"
            width="200">
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作">
            <template slot-scope="scope">
              <el-button @click="handleTaskUserDetails(scope.row)" type="text" size="small">详情</el-button>
              <el-button @click="removeTaskUserDetails(scope.row)" type="text" size="small">取消指派</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div v-show="step=='2'&&!reassign" style="margin-top:10px;">
          <el-button @click="detailsDialog=false">取消</el-button>
          <el-button type="primary" @click="createNewTask">确认</el-button>
        </div>
      </el-form>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="createDialog"
      :show-close="true"
      width="80%"
      top="50px">
      <el-menu :default-active="step" mode="horizontal" @select="stepSelect">
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
          <el-form-item style="text-align:left;" label="附件列表">
            <el-upload
              class="upload"
              ref="upload"
              :action="FileBase"
              :before-remove="fileBeforeRemove"
              :on-success="uploadSuccess">
              <el-button size="small" type="primary">添加附件</el-button>
            </el-upload>
          </el-form-item>
          <div v-for="file in newTask.files" :key="file.value" class="file" style="margin-left:88px;"><span>{{file.fileName}}</span>
            <el-button v-if="rule==='ROOT'" type="text" @click="removeFile(file)"><i class="el-icon-close"></i></el-button>
            <el-button v-else type="text" @click="downloadFile(file)">下载</el-button>
          </div>
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
        <div v-show="step=='2'" style="margin-top:10px;">
          <el-button @click="createDialog=false">取消</el-button>
          <el-button type="primary" @click="createNewTask">完成创建</el-button>
        </div>
      </el-form>
    </el-dialog>
    <div class="top">
      <el-button class="create" type="primary" @click="create" v-if="rule==='ROOT'">新建安排</el-button>
      <el-select v-model="taskType" placeholder="所有类型" class="task-type" @change="selectTaskType" v-if="rule==='ROOT'">
        <el-option label="全部" value="-1"></el-option>
        <el-option
          v-for="item in taskTypes"
          :key="item.id"
          :label="item.name"
          :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="taskType" placeholder="所有类型" class="task-type" @change="selectTaskType" v-else style="margin-left:450px;">
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
        v-if="rule=='ROOT'"
        prop="name"
        label="任务名"
        width="300">
      </el-table-column>
      <el-table-column
        v-else
        prop="name"
        label="任务名"
        width="500">
      </el-table-column>
      <el-table-column
        v-if="rule=='ROOT'"
        :formatter="timeFormatter"
        prop="updateTime"
        label="修改时间"
        width="300">
      </el-table-column>
      <el-table-column
        v-else
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
        v-if="rule=='ROOT'"
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
          <el-button @click="handleTaskDetails(scope.row)" type="text" size="small">查看详情</el-button>
          <el-button type="text" size="small" @click="handleDeleteTask(scope.row)" v-if="rule==='ROOT'">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top:10px;"
      background
      layout="prev, pager, next"
      :total="taskListNum"
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
      reassign: false,
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
      detailsDialog: false,
      taskUserDetailsDialog: false,
      // 步骤
      step: '1',
      // 新增任务类型名
      addTaskTypeName: '',
      // 查询关键字
      keyword: '',
      userList: [],
      taskList: [],
      state: '',
      page: 1,
      rule: '',
      user: '',
      taskListNum: 0,
      taskUserDetails: '',
      userFileList: [],
      userContext: ''
    }
  },
  computed: {
    // userFileList: function () {
    //   let temp = []
    //   if (this.newTask['users']) {
    //     this.newTask['users'].forEach(user => {
    //       if (user.name === this.user) {
    //         if (user.files) {
    //           temp = user.files
    //         } else {
    //           temp = []
    //         }
    //       }
    //     })
    //   }
    //   return temp
    // }
  },
  mounted () {
    this.rule = sessionStorage.getItem('rule')
    this.user = sessionStorage.getItem('name')
    console.log(this.rule)
    this.init()
  },
  methods: {
    ...mapActions(['findTaskById', 'createMessage', 'taskTypeList', 'createTaskType', 'commonUserList', 'createTask', 'findTaskList', 'deleteTask', 'downloadFile']),
    init () {
      this.getTaskTypeList()
      this.getCommonUserList()
      this.getTaskList()
      this.page = 1
    },
    confirmReassign () {
      this.reassign = false
    },
    reassignClick () {
      this.reassign = true
      let users = this.userList.filter((user) => {
        let s = false
        this.newTask.users.forEach(u => {
          if (u.id === user.id) {
            s = true
          }
        })
        return s
      })
      this.toggleSelection(users)
    },
    handleTaskUserDetails (row) {
      this.taskUserDetailsDialog = true
      this.taskUserDetails = row
    },
    removeTaskUserDetails (row) {
      this.$confirm('此操作将取消该指派, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.newTask.users.map((u, index) => {
          if (u.id === row.id) {
            this.newTask.users.splice(index, 1)
          }
        })
        this.$message({
          type: 'success',
          message: '取消指派成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '未取消'
        })
      })
    },
    currentChange (page) {
      this.getTaskList(this.keyword, this.taskType, this.state, null, page - 1)
    },
    handleTaskDetails (row) {
      this.findTaskById(row.id).then(res => {
        this.newTask = res.data.data.task
        this.reassign = false
        this.addTaskTypeName = ''
        this.step = '1'
        this.detailsDialog = true
        try {
          this.newTask['files'] = JSON.parse(res.data.data.task.files)
          this.newTask['users'] = JSON.parse(res.data.data.task.users)
        } catch (error) {

        }
        let temp = []
        if (this.newTask['users']) {
          this.newTask['users'].forEach(user => {
            if (user.name === this.user) {
              if (user.files) {
                temp = user.files
              } else {
                temp = []
              }
              this.userContext = user.context
              // 是否查看
              if (user.isConfirm !== '是') {
                user.isConfirm = '是'
                this.newTask.users = JSON.stringify(this.newTask.users)
                this.newTask.files = JSON.stringify(this.newTask.files)
                this.createTask(this.newTask).then(res => {
                  this.newTask.users = JSON.parse(this.newTask.users)
                  this.newTask.files = JSON.parse(this.newTask.files)
                })
              }
            }
          })
          this.userFileList = temp
        }
      })
    },
    handleDeleteTask (row) {
      this.$confirm('此操作将永久删除该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteTask(row).then(() => {
          this.init()
          let message = {}
          message['createTime'] = new Date()
          message['messageType'] = 'TASK'
          message['content'] = '管控中心删除了任务' + row.name
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
    selectState (val) {
      this.getTaskList(this.keyword, this.taskType, this.state)
    },
    selectTaskType (val) {
      this.getTaskList(this.keyword, this.taskType, this.state)
    },
    timeFormatter (row, column, cellValue, index) {
      if (cellValue) {
        return moment(cellValue).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    getTaskList (name, tid, state, user, page) {
      if (user == null && this.rule !== 'ROOT') {
        user = this.user
      }
      this.findTaskList({ name, tid, state, user, page }).then(res => {
        console.log(res)
        this.taskList = res.data.data.taskPage.content
        this.taskListNum = res.data.data.taskPage.totalElements
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
      this.newTask = {
        taskType: {
          id: null
        },
        files: []
      }
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
    uploadSuccessSub (res, file) {
      this.newTask['users'].forEach(user => {
        if (user.name === this.user) {
          console.log(user.files)
          if (user.files && user.files.length > 0) {
            user.files.push(res.data)
            console.log(user.files)
          } else {
            user.files = [res.data]
            console.log(user.files)
            this.newTask['endNum'] = this.newTask['endNum'] + 1
            console.log(this.newTask['endNum'])
            user.state = '完成'
          }
          user.updateTime = new Date()
        }
      })
      let temp = []
      if (this.newTask['users']) {
        this.newTask['users'].forEach(user => {
          if (user.name === this.user) {
            if (user.files) {
              temp = user.files
            } else {
              temp = []
            }
          }
        })
        this.userFileList = temp
      }
      this.newTask['updateTime'] = new Date()
    },
    fileBeforeRemoveSub (file, files) {
      this.newTask['users'].forEach(user => {
        if (user.name === this.user) {
          user.files.map((f, index, arr) => {
            if (f.value === file.response.data.value) {
              user.files.splice(index, 1)
            }
          })
          user.updateTime = new Date()
          if (!user.files || user.files.length <= 0) {
            user.state = ''
            this.newTask['endNum'] = this.newTask['endNum'] - 1
          }
        }
      })
      this.newTask['updateTime'] = new Date()
    },
    fileBeforeRemove (file, files) {
      this.newTask['files'].map((f, index, arr) => {
        if (f.value === file.response.data.value) {
          this.newTask['files'].splice(index, 1)
        }
      })
    },
    removeFile (file) {
      this.newTask['files'].map((f, index, arr) => {
        if (f.value === file.value) {
          this.newTask['files'].splice(index, 1)
        }
      })
    },
    removeFileSub (file) {
      this.newTask['users'].forEach(user => {
        if (user.name === this.user) {
          user.files.map((f, index, arr) => {
            if (f.value === file.value) {
              user.files.splice(index, 1)
            }
          })
          user.updateTime = new Date()
          if (!user.files || user.files.length <= 0) {
            user.state = ''
            this.newTask['endNum'] = this.newTask['endNum'] - 1
          }
        }
      })
      this.newTask['updateTime'] = new Date()
    },
    getCommonUserList () {
      this.commonUserList().then(res => {
        this.userList = res.data.data.commonUsers
        console.log(this.userList)
      })
    },
    userListSelectionChange (val) {
      this.newTask['users'] = val
      this.newTask['users'].forEach(user => {
        if (!user.isConfirm) {
          user.isConfirm = '否'
        }
      })
    },
    toggleSelection (rows) {
      if (rows) {
        this.$refs.userListTable.clearSelection()
        rows.forEach(row => {
          this.$refs.userListTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.userListTable.clearSelection()
      }
    },
    isConfirmFormatter (row, column, cellValue, index) {
      if (!cellValue) {
        return '否'
      } else {
        return cellValue
      }
    },
    createNewTask () {
      if (this.newTask.name && this.newTask.taskType && this.newTask.beginTime && this.newTask.endTime) {
        if (!this.newTask.createTime) {
          this.newTask.createTime = new Date()
          if (this.rule === 'ROOT') {
            let message = {}
            message['createTime'] = new Date()
            message['messageType'] = 'TASK'
            message['content'] = '管控中心创建了新任务' + this.newTask.name
            this.createMessage(message)
          }
        } else {
          if (this.rule === 'ROOT') {
            let message = {}
            message['createTime'] = new Date()
            message['messageType'] = 'TASK'
            message['content'] = '管控中心修改了任务' + this.newTask.name
            this.createMessage(message)
          } else {
            let message = {}
            message['createTime'] = new Date()
            message['messageType'] = 'TASK'
            message['content'] = this.user + '提交了' + this.newTask.name + '任务'
            this.createMessage(message)
          }
        }
        this.newTask.updateTime = new Date()
        if (this.newTask.users) {
          this.newTask.userNum = this.newTask.users.length
        } else {
          this.newTask.userNum = 0
        }
        if (!this.newTask.endNum) {
          this.newTask.endNum = 0
        }
        // 分中心备注
        if (this.newTask['users'] && this.userContext) {
          this.newTask['users'].forEach(user => {
            if (user.name === this.user) {
              user.context = this.userContext
            }
          })
        }
        let temp = _.cloneDeep(this.newTask)
        // this.newTask.users = JSON.stringify(this.newTask.users)
        // this.newTask.files = JSON.stringify(this.newTask.files)
        temp.users = JSON.stringify(temp.users)
        temp.files = JSON.stringify(temp.files)
        this.createTask(temp).then(res => {
          if (res.data.code !== 100) {
            this.$message({
              showClose: true,
              message: '新任务创建失败',
              type: 'error'
            })
            this.$refs.upload.clearFiles()
            this.createDialog = false
            this.detailsDialog = false
            this.newTask = { taskType: { id: null }, files: [] }
          } else {
            this.$refs.upload.clearFiles()
            this.newTask = { taskType: { id: null }, files: [] }
            this.createDialog = false
            this.detailsDialog = false
            this.init()
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
  .task .file{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
  .task .el-upload-list__item{
    display: none;
  }
  .task .underLine{
    margin: 10px auto;
    width:100%;
    height:3px;
    background:linear-gradient(270deg,rgba(166,187,200,0.2) 0%,rgba(166,187,200,1) 52%,rgba(166,187,200,0.2) 100%);
  }
  .task .upload{
    margin-left: 88px;
  }
</style>
