<!--
 * @Author: your name
 * @Date: 2021-01-13 18:07:27
 * @LastEditTime: 2021-04-09 10:37:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/edit_task/task/task_deatils.vue
-->
<template>
  <div>
    <div class="task-details">
      <!-- <div class="top-title">
        任务监控
      </div> -->
      <el-tabs v-model="activeName">
        <el-tab-pane label="任务监控" name="monitor">
          <Monitor />
        </el-tab-pane>
        <el-tab-pane label="编辑记录" name="record">
          <Record />
        </el-tab-pane>
      </el-tabs>

    </div>
  </div>
</template>

<script>

// import { formatDate } from '../js/times'
export default {
  components: {
    Monitor: () => import('./monitor.vue'),
    Record: () => import('./record.vue')
  },
  data() {
    return {
      activeName: 'monitor'
    }
  },
  computed: {

  },
  watch: {
  },
  created() {
    this.$store.dispatch('channel/getChannelData')
  },
  mounted() {
  },
  methods: {

  }
}
</script>

<style lang='scss' scoped>

</style>
