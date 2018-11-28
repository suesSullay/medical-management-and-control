<template>
  <div class="sider">
    <el-menu>
      <el-menu-item index="0" v-if="rule=='ROOT'"><router-link to="/manage">管理中心</router-link></el-menu-item>
      <el-menu-item index="1"><router-link to="/task">任务管理</router-link></el-menu-item>
      <el-menu-item index="2"><router-link to="/message">通知管理</router-link></el-menu-item>
      <el-menu-item index="3"><router-link to="/notice">过程管理</router-link></el-menu-item>
      <el-menu-item index="4"><router-link to="/expert">专家管理</router-link></el-menu-item>
      <el-menu-item index="5"><router-link to="/account">账号管理</router-link></el-menu-item>
    </el-menu>
  </div>
</template>
<script>
export default {
  computed: {
    rule: function () {
      return sessionStorage.getItem('rule')
    }
  }
}
</script>
<style>
  .sider{
    margin-top: 10px;
  }
  .sider .el-menu{
    border: solid 1px #e6e6e6;
  }
  .sider .el-menu .el-menu-item{
    margin-top: 15px;
  }
  .sider .el-menu .el-menu-item:first-child{
    margin-top: 0px;
  }
  .sider a {
    font-size: 20px;
    display: block;
    text-decoration: none;
    color: #4A4A4A;
  }
  .sider .router-link-active {
    display: block;
    text-decoration: none;
    color: #4A90E2;
  }
</style>
