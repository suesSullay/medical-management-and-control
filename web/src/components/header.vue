<template>
  <div class="header">
    <div class="type">
      <div>四川省中医医疗管理控制系统</div>
      <div>{{type}}</div>
    </div>
    <div class="name">{{name}}</div>
  </div>
</template>
<script>
export default {
  data () {
    return {
    }
  },
  computed: {
    name: function () {
      return sessionStorage.getItem('name')
    },
    type: function () {
      return sessionStorage.getItem('rule') === 'ROOT' ? '管控中心版本' : '分中心版本'
    }
  }
}
</script>

<style>
  .header{
    height: 80px;
    width: 100%;
    background-color: #4A90E2;
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content:space-between;
  }
  .header .name{
    font-size: 30px;
    color: #FFFFFF;
    letter-spacing: 3px;
    margin-left: 8.4%;
    padding: 40px;
  }
  .header .type{
    color: #FFFFFF;
    padding: 40px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: space-between;
  }
  .header .type div:first-child{
    font-size: 20px;
  }
</style>
