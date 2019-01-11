<template>
  <div class="message">
    <div class="message-top">
      <el-radio-group v-model="messageType" style="margin-bottom: 20px;" @change="selectMessageType">
        <el-radio-button label="all">全部通知</el-radio-button>
        <el-radio-button label="TASK">任务通知</el-radio-button>
        <el-radio-button label="NOTICE">新闻通知</el-radio-button>
        <el-radio-button label="EXPERT">专家通知</el-radio-button>
        <el-radio-button label="SYSTEM">系统通知</el-radio-button>
      </el-radio-group>
    </div>
    <el-table
    :data="messageList"
    style="width: 100%">
      <el-table-column
        prop="content"
        label="通知名"
        width="1000">
      </el-table-column>
      <el-table-column
        :formatter="timeFormatter"
        prop="createTime"
        label="修改时间"
        width="200">
      </el-table-column>
    </el-table>
    <el-pagination
      style="margin-top:10px;"
      background
      layout="prev, pager, next"
      :total="messageListNum"
      :page-size="7"
      @current-change="currentChange"
      :current-page.sync="page">
    </el-pagination>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
import moment from 'moment'
export default {
  data () {
    return {
      messageType: 'all',
      page: 1,
      messageListNum: 0,
      messageList: []
    }
  },
  mounted () {
    this.getMessageList()
  },
  methods: {
    ...mapActions(['findMessageList']),
    selectMessageType () {
      if (this.messageType === 'all') {
        this.getMessageList()
      } else {
        this.getMessageList(this.messageType)
      }
    },
    timeFormatter (row, column, cellValue, index) {
      if (cellValue) {
        return moment(cellValue).format('YYYY-MM-DD hh:mm:ss')
      }
    },
    currentChange (page) {
      if (this.messageType === 'all') {
        this.getMessageList(null, this.page - 1)
      } else {
        this.getMessageList(this.messageType, this.page - 1)
      }
    },
    getMessageList (messageType, page) {
      this.findMessageList({ messageType, page }).then(res => {
        this.messageList = res.data.data.messagePage.content
        this.messageListNum = res.data.data.messagePage.totalElements
      })
    }
  }
}
</script>
<style>
  .message .message-top{
    text-align: left;
    margin-left: 40px;
  }
  .message{
    text-align: center;
  }
</style>
