<template>
  <div class="message">
    <div class="message-top">
      <el-radio-group v-model="messageType" style="margin-bottom: 20px;">
        <el-radio-button label="all">全部通知</el-radio-button>
        <el-radio-button label="TASK">任务通知</el-radio-button>
        <el-radio-button label="NOTICE">新闻通知</el-radio-button>
        <el-radio-button label="EXPERT">专家通知</el-radio-button>
        <el-radio-button label="SYSTEM">系统通知</el-radio-button>
      </el-radio-group>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      messageType: 'all'
    }
  }
}
</script>
<style>
  .message .message-top{
    text-align: left;
    margin-left: 40px;
  }
</style>
