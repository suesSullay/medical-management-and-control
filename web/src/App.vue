<template>
  <div id="app" class="app">
    <router-view v-if="isRouterAlive&&this.$route.path=='/'"></router-view>
    <el-container v-else-if="isRouterAlive&&this.$route.path!='/'">
      <el-header height="80px"><Headerx></Headerx></el-header>
      <el-container>
        <el-aside width="180px"><div><SideBar></SideBar></div></el-aside>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Headerx from './components/header'
import SideBar from './components/sideBar'
export default {
  components: {
    Headerx,
    SideBar
  },
  provide () {
    return {
      reload: this.reload
    }
  },
  data () {
    return {
      isRouterAlive: true
    }
  },
  created () {
    if (!sessionStorage.getItem('name') || !sessionStorage.getItem('rule')) {
      this.$router.push('/')
    }
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    }
  }
}
</script>

<style>
  .app .el-header{
    padding: 0 0;
  }
</style>
