<!--
 * @Author: your name
 * @Date: 2021-12-13 15:44:01
 * @LastEditTime: 2021-12-23 17:49:22
 * @LastEditors: Please set LastEditors
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /production_web/src/views/EPG/new-system-pad/index.vue
-->
<template>
  <div class="new-system-pad">
    <div v-loading />
    <div v-if="!loading">
      <div class="pad-header">
        <span>{{ epgData.channel_name }}</span>
        <span>任务ID:{{ epgData.task_id }}</span>
        <span style="color: red">时效:{{ epgData.processing_efficiency_seconds / 60 }}分钟</span>
        <span>时间段: {{ epgData.start_time }} - {{ epgData.end_time }}</span>
      </div>
      <div class="content">
        <div class="left-pad">
          <LeftPad :epg-data="epgData" @setTime="setTime" />
        </div>
        <div class="right-pad">
          <RightPad :epg-data="epgData" />
        </div>
      </div>
      <div>
        <el-button type="primary" size="default" @click="cancelFn">挂起</el-button>
        <el-button type="primary" size="default" @click="submit">完成</el-button>
      </div>
    </div>

  </div>
</template>

<script>
import to from '@/utils/to-promise.js'
import LeftPad from './left-pad.vue'
import RightPad from './right-pad'

export default {
  components: {
    LeftPad,
    RightPad
  },
  props: {},
  data() {
    return {
      epgData: {},
      cutTimeList: [],
      loading: true
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getEpgData()
  },
  mounted() {},
  methods: {
    cancelFn() {

    },
    submit() {

    },
    // /epg-task/get-generate-status
    async getEpgData() {
      this.loading = true
      const id = this.$route.query.task_id
      const res = await this.$get(`epg-task/${id}/for-edit?from=h5`)
      // const res = await this.$get(`epg-task/364451/for-edit`)
      console.log(res)
      this.epgData = res.data
      this.loading = false
    },
    setTime(time) {
      console.log('🚀 ~ file: index.vue ~ line 55 ~ setTime ~ time', time)
    }
  }
}
</script>

<style scoped lang="scss">
.new-system-pad {
  height: 100%;
  position: relative;
  .pad-header {
    position: absolute;
    top: -15px;
    display: flex;
    span {
      margin: 0 10px;
    }
  }
  .content {
    width: 100%;
    height: 100%;
    display: flex;
    position: absolute;
    left: 0;
    top: 0;
    .right-pad {
      max-width: 400px;
      min-width: 300px;
      border-left: 1px solid #ccc;
      height: 100%;
    }
    .left-pad {
      flex: 1;
    }
  }
}
</style>
