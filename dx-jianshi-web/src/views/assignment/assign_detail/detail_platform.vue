<!--
 * @Author: wxh
 * @Date: 2020-11-13 14:24:27
 * @LastEditTime: 2020-11-18 16:31:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/assignment/assign_detail/detail_my.vue
-->
<template>
  <div>
    <assign-detail>
      <!-- 任务详情图 -->
      <div slot="detail-bg" class="assign-detail-bg">
        <img :src="assignInfo.banner_url" alt="任务详情图" @error="imgError()" />
      </div>

      <!-- 领取任务按钮 -->
      <template slot="assign-get-btn">
        <el-button
          type="primary"
          round
          style="width: 100px;height:30px;margin:34px auto;"
          size="small"
          :disabled="assignInfo.claimed"
          @click="recieveAssign">{{ assignInfo.claimed ? '已领取' : '领取任务'}}</el-button>
      </template>
    </assign-detail>
  </div>
</template>

<script>
import AssignDetail from './../components/assign_detail.vue'
export default {
  provide() {
    return {
      detail: this //展开收起使用
    }
  },
  inject: ['assignDetail'],
  props: {},
  data() {
    return {
      url: 'task/claim-task'
    }
  },
  computed: {
    assignInfo() {
      return this.assignDetail.assignInfo
    }
  },
  watch: {},
  methods: {
    recieveAssign() {
      const { task_id } = this.assignInfo
      this.$post(this.url, { task_id }).then(res => {
        if (res.data.code == '0000') {
          // 领取成功
          this.assignInfo.claimed = true
          window.sessionStorage.setItem('assignDetail', JSON.stringify(this.assignInfo))
        }
        this.$alertMsg(res.data.msg)
      })
    }
  },
  components: { AssignDetail },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
