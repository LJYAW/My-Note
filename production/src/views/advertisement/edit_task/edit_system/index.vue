<!--
 * @Author: your name
 * @Date: 2021-02-23 17:55:16
 * @LastEditTime: 2021-04-13 21:22:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/EditSystem.vue
-->
<template>
  <div
    v-loading="loading"
    class="edit-system"
  >
    <div class="header">
      <p><span class="type">广告</span>{{ this.$store.state.settings.loginTitle }}</p>
      <p><span class="type type-time">时效</span><span>{{ getTime(taskMessage.processing_efficiency_seconds*1000) }}</span></p>
      <p>{{ taskMessage.channel_name }}({{ taskMessage.task_id }}){{ taskMessage.start_date_time }} - {{ taskMessage.end_date_time }}</p>
      <base-btn type="text" class="out-btn" @click="goOut">退出任务</base-btn>
    </div>

    <div v-if="!loading" class="edit-system-content">
      <TaskKeyFrame
        class="task-frame"
        :task-message="taskMessage.ad_files"
        :start-time="taskMessage.start_date_time"
        :end-time="taskMessage.end_date_time"
      />
      <EditSystemForm class="edit-system-form" :task-message="taskMessage" />
    </div>
    <NpTaskModel v-if="showNoTaskModel" :show="showNoTaskModel" @close="close" />

  </div>
</template>

<script>
import TaskKeyFrame from './TaskKeyFrame'
import EditSystemForm from './EditSystemForm'
import { timesToHMS } from '../../js/times'
export default {
  components: {
    TaskKeyFrame,
    EditSystemForm,
    NpTaskModel: () => import('./model/noTask')
  },
  props: {

  },
  data() {
    return {
      taskMessage: {},
      loading: true,
      task_id: null,
      showNoTaskModel: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getInfo()
  },
  mounted() {

  },
  methods: {
    getInfo() {
      this.loading = true
      this.task_id = this.task_id || this.$route.query.tid
      const action = this.$route.query.status ? 'view' : 'edit'
      this.$get(`/epg-ad/${this.task_id}/task-info/${action}`).then((res) => {
        this.taskMessage = res.data
        this.loading = false
      }).catch((error) => {
        console.log(error)
        this.$message(error.msg)
      })
    },
    getTime(val) {
      return timesToHMS(val)
    },
    goOut() {
      // this.$router.push({
      //   path: '/advertisement/edit-task'
      // })
      this.$router.go(-1)
    },
    close() {
      this.showNoTaskModel = false
    },
    getTaskId() {
      this.$get('/ads-task/get-task').then((res) => {
        this.task_id = res.data.id
        this.$router.push({
          path: '/advertisement/edit-system',
          query: {
            tid: this.task_id
          }
        })
        this.getInfo()
      }).catch((error) => {
        if (error.msg === '暂时没有任务') {
          this.showNoTaskModel = true
          return
        }
        this.$message(error.msg)
      })
    }
  }
}
</script>

<style scoped lang="scss">
.edit-system{
    padding: 0px;
    display: flex;
    flex-direction: column;
    height: calc(100vh - 103px)!important;
    position: relative;
    .header{
        display: flex;
        justify-content: space-between;
        position: absolute;
        top: -15px;
        width: 100%;

        .type{
            color: #fff;
            background: darkviolet;
            padding: 3px 6px;
            border-radius: 3px;
            margin-right: 5px;
        }
        .type-time{
            background:#eee;
            color: limegreen;
        }
        .out-btn{
          padding: 0;
        }
    }
    .edit-system-content{
        flex: 1;
        display: flex;
        // margin-top: 10px;
        height: 100%;
        .task-frame {
          flex: 1;
        }
        .edit-system-form {
          max-width: 400px;
          min-width: 300px;
        }
    }
}
</style>
