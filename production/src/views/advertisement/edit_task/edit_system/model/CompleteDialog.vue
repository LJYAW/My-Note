<!--
 * @Author: your name
 * @Date: 2021-02-24 14:04:29
 * @LastEditTime: 2021-03-09 18:47:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/model/CompleteDialog.vue
-->
<template>
  <base-dialog :show="show" title="完成任务" width="320px" @close="close">
    <div class="dialog-btn">
      <base-btn size="mini" type="info" @click="out">退出任务</base-btn>
      <base-btn size="mini" @click="startNewTask">开始新任务</base-btn>
    </div>
  </base-dialog>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      show: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    close() {
      this.show = false
    },
    open() {
      this.show = true
    },
    out() {
      this.$router.push({
        path: '/advertisement/edit-task'
      })
    },
    startNewTask() {
      this.$parent.$parent.getTaskId()
      this.close()
    }
  }
}
</script>

<style scoped lang="scss">
.dialog-btn{
    text-align: center;
    button{
        margin: 10px 20px 20px 20px;
    }
}
</style>
