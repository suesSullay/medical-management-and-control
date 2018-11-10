<template>
  <div>
    <el-button @click="create">{{home}}</el-button>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      home: '新增'
    }
  },
  methods: {
    ...mapActions(['createUser']),
    create () {
      this.createUser()
    }
  }

}
</script>
<style>

</style>
