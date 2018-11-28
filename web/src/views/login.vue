<template>
  <div class="login">
    <el-form :model="formInfo">
      <h1>四川省中医医疗质量控制管理系统</h1>
      <el-form-item>
        <el-input v-model="formInfo.userName" placeholder="请输入账号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-input v-model="formInfo.passWord" placeholder="请输入密码"  @keyup.enter.native="login2"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="login2" type="primary">登录</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      formInfo: {
        userName: '',
        passWord: ''
      }
    }
  },
  methods: {
    ...mapActions(['login']),
    login2 () {
      if (!this.formInfo.userName || !this.formInfo.passWord) {
        this.$message({
          showClose: true,
          message: '请输入账号密码',
          type: 'error'
        })
      } else {
        this.login(this.formInfo).then(res => {
          console.log(res)
          if (res.data.code === 200) {
            this.$message({
              showClose: true,
              message: '密码错误',
              type: 'error'
            })
          } else {
            sessionStorage.setItem('name', res.data.data.user.name)
            sessionStorage.setItem('rule', res.data.data.user.rule)
            this.$router.push('/task')
          }
        })
      }
    }
  }

}
</script>
<style>
  .login{
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 100%;
  }
  .login .el-form{
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 35%;
    height: 43.4%;
    border: 1px;
    margin-top: 100px;
  }
  .login .el-form h1{
    font-size: 30px;
    color: #4A4A4A;
    letter-spacing: 2.4px;
    margin-top: 70px;
    margin-bottom: 50px;
  }
  .login .el-form-item{
    width: 300px;
  }
  .login .el-button{
    width: 300px;
  }
</style>
