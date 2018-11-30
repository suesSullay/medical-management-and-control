<template>
  <div class="account">
    <div class="top">
      <el-button type="primary" @click="handleAdd" class="create">添加账号</el-button>
    </div>

    <!--列表-->
    <el-table :data="userList" stripe highlight-current-row v-loading="listLoading" style="width: 100%;" height="480">
      <el-table-column prop="name" label="单位" min-width="200">
        <template slot-scope="scope">
          <strong v-if="scope.row.rule === 'ROOT'">{{scope.row.name}}</strong>
          <span v-else>{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="userName" label="用户名" min-width="200">
        <template slot-scope="scope">
          <strong v-if="scope.row.rule === 'ROOT'">{{scope.row.userName}}</strong>
          <span v-else>{{scope.row.userName}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="rule" label="角色" min-width="120">
        <template slot-scope="scope">
          <span v-if="scope.row.rule === 'COMMON'">普通用户</span>
          <strong v-if="scope.row.rule === 'ROOT'">管理员</strong>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="地址" min-width="250">
        <template slot-scope="scope">
          <strong v-if="scope.row.rule === 'ROOT'">{{scope.row.address}}</strong>
          <span v-else>{{scope.row.address}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="tel" label="电话" min-width="200"></el-table-column>
      <el-table-column label="操作" min-width="150">
        <template slot-scope="scope">
          <el-button size="small" type="text" @click="handleEdit(scope.$index, scope.row)" style="cursor: pointer;">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--编辑界面-->
    <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false" top="80px;" style="padding:0px 20px;">
      <el-form :model="editForm" label-width="80px" ref="editForm">
        <el-form-item label="ID" hidden>
          <el-input v-model="editForm.id"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="rule">
          <el-radio-group v-model="editForm.rule" disabled>
            <el-radio label="COMMON">普通用户</el-radio>
            <el-radio label="ROOT">管理员</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="单位" prop="name">
          <el-input v-model="editForm.name" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="editForm.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="passWord" style="text-align:left;">
          <el-input type="password" v-model="editForm.passWord" :disabled='!changePassWordState' style="width:90%;"></el-input>
          <el-button type="text" style="margin-left:5%;" @click="changePassWordState=true">修改</el-button>
        </el-form-item>
        <el-form-item label="确认密码" prop="passWord2" v-if="changePassWordState">
          <el-input type="password" v-model="editForm.passWord2"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="editForm.address"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="editForm.tel"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="context">
          <el-input v-model="editForm.context" type="textarea"
              :autosize="{ minRows: 5, maxRows: 8}"
              placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" style="margin-top:10px;">
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
        <el-button @click.native="editFormVisible = false">取消</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false"  top="80px;" style="padding:0px 20px;">
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="ID" hidden>
          <el-input v-model="addForm.id"></el-input>
        </el-form-item>
        <el-form-item label="单位" prop="name">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="rule">
          <el-radio-group v-model="addForm.rule">
            <el-radio label="COMMON">普通用户</el-radio>
            <el-radio label="ROOT" disabled>管理员</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="addForm.userName"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="passWord">
          <el-input type="passWord" v-model="addForm.passWord"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="passWord2">
          <el-input type="passWord" v-model="addForm.passWord2"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="addForm.address"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="addForm.tel"></el-input>
        </el-form-item>
        <el-form-item label="简介" prop="context">
          <el-input v-model="addForm.context" type="textarea"
              :autosize="{ minRows: 5, maxRows: 8}"
              placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
        <el-button @click.native="addFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/* import moment from 'moment' */
import { mapActions } from 'vuex'
export default {
  data () {
    var validateAddPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.addForm.passWord2 !== '') {
          this.$refs.addForm.validateField('passWord2')
        }
        callback()
      }
    }
    var validateAddPass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.addForm.passWord) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      changePassWordState: false,
      filters: {
        name: ''
      },
      name: '',
      rule: '',
      userList: [],
      listLoading: false,
      sels: [], // 列表选中列
      addFormRules: {
        name: [
          { required: true, message: '请输入单位', trigger: 'blur' }
        ],
        rule: [
          { required: true, message: '请选择用户角色', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        passWord: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { validator: validateAddPass, trigger: 'blur' }
        ],
        passWord2: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validateAddPass2, trigger: 'blur' }
        ]
      },

      editFormVisible: false, // 编辑界面是否显示
      editLoading: false,
      // 编辑界面数据
      editForm: {
        id: 0,
        name: '',
        userName: '',
        passWord: '',
        rule: '',
        address: '',
        tel: '',
        context: ''
      },
      addFormVisible: false, // 新增界面是否显示
      addLoading: false,
      // 新增界面数据
      addForm: {
        id: 0,
        name: '',
        userName: '',
        passWord: '',
        rule: 'COMMON',
        address: '',
        tel: '',
        context: ''
      }
    }
  },
  mounted () {
    this.getUsers()
  },
  methods: {
    ...mapActions(['createUser', 'createMessage', 'findUserList', 'findUserByName']),
    refresh () {
      this.listLoading = true
      this.userList = []
      setTimeout(() => {
        this.getUsers()
      }, 500)
    },
    getUsers () {
      this.listLoading = true
      this.tableData = []
      this.findUserList().then(res => {
        this.userList = res.data.data.userList
        this.listLoading = false
      })
      this.rule = sessionStorage.getItem('rule')
      this.name = sessionStorage.getItem('name')
    },
    handleAdd: function () {
      this.addFormVisible = true
      this.addForm = {
        name: '',
        userName: '',
        passWord: '',
        rule: 'COMMON',
        address: '',
        tel: '',
        context: ''
      }
    },
    handleEdit: function (index, row) {
      this.editFormVisible = true
      this.changePassWordState = false
      this.findUserByName({ name: row.name }).then((response) => {
        if (response.status === 200) {
          console.log('==========')
          console.log(response.data.data.user)
          console.log('===========')
          this.editForm = response.data.data.user
          this.editForm.passWord2 = ''
        } else {
          this.$message({
            message: '内部错误！',
            type: 'error'
          })
        }
      }).catch(() => {
        this.$message({
          message: '内部错误！',
          type: 'error'
        })
      })
    },
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          if (this.editForm.passWord2 && (this.editForm.passWord !== this.editForm.passWord2)) {
            this.$message({
              message: '两次密码不相同',
              type: 'error'
            })
          } else {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.editLoading = true
              this.createUser(this.editForm).then((response) => {
                this.editLoading = false
                if (response && response.status === 200) {
                  this.$message({
                    message: '修改用户成功',
                    type: 'success'
                  })
                  this.$refs['editForm'].resetFields()
                  this.editFormVisible = false
                  this.refresh()
                } else {
                  this.$message({
                    message: '内部错误！',
                    type: 'error'
                  })
                }
              }).catch(() => {
                this.editLoading = false
                this.$message({
                  message: '内部错误！',
                  type: 'error'
                })
              })
            }).catch(() => {
            })
          }
        }
      })
    },
    addSubmit: function () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addLoading = true
            this.createUser(this.addForm).then((response) => {
              this.addLoading = false
              if (response && response.status === 200) {
                this.$message({
                  message: '新建用户成功',
                  type: 'success'
                })
                this.$refs['addForm'].resetFields()
                this.addFormVisible = false
                this.refresh()
              } else {
                console.log(1111111111111)
                this.$message({
                  message: '内部错误！',
                  type: 'error'
                })
              }
            }).catch(() => {
              this.addLoading = false
              this.$message({
                message: '内部错误！',
                type: 'error'
              })
            })
          }).catch(() => {
          })
        }
      })
    }
  }
}
</script>

<style>
  .account .top{
    display: flex;
    flex-direction: row;
    align-items: center;
    height: 75px;
  }
  .account .top .create {
    border-radius: 30px;
    width: 150px;
  }
</style>
