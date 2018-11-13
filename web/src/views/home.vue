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
      home: 'test'
    }
  },
  methods: {
    ...mapActions(['test']),
    create () {
      let users = {
        'user1': {
          'state': true,
          'files': ['C:/file/123.txt']
        },
        'user2': {
          'state': true,
          'files': ['C:/file/123.txt', 'C:/31231/223.txt']
        }
      }
      let data = {
        name: '123',
        createTime: new Date(),
        updateTime: new Date(),
        taskType: {
          id: 1
        },
        users: JSON.stringify(users)
      }
      this.test(data)
    }
  }

}
</script>
<style>

</style>
