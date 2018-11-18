<template>
  <div class="header">
    <span>{{name}}</span>
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
    justify-content: flex-start;
  }
  .header span{
    font-size: 30px;
    color: #FFFFFF;
    letter-spacing: 3px;
    margin-left: 8.4%;
  }
</style>
