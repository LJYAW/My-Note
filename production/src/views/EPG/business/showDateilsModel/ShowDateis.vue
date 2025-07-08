<!--
 * @Author: your name
 * @Date: 2021-04-21 11:48:12
 * @LastEditTime: 2021-04-25 15:00:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/business/showDateilsModel/ShowDateis.vue
-->
<template>

  <div class="show-dateils">
    <table style="border-collapse:separate; border-spacing:0px 10px;">
      <tr>
        <td>TID:</td>
        <td>{{ activeDate.task_id }}</td>
      </tr>
      <tr>
        <td>栏目:</td>
        <td v-if="activeDate.item_name_list">
          {{ activeDate.item_name_list.join(',') }}
        </td>
      </tr>
      <tr>
        <td>标题:</td>
        <td v-if="activeDate.title_list">
          {{ activeDate.title_list.join(',') }}
        </td>
      </tr>
      <tr>
        <td>编辑人员:</td>
        <td>{{ showDateisItem.executor_name }}</td>
      </tr>
      <tr>
        <td>提取时间:</td>
        <td>{{ activeDate.update_time }}</td>
      </tr>
      <tr>
        <td>提交时间:</td>
        <td>{{ activeDate.update_time }}</td>
      </tr>
      <tr>
        <td style="min-width: 100px">任务时间段:</td>
        <td>
          {{ showDateisItem.start_time }} ~ {{ showDateisItem.end_time }}
        </td>
      </tr>
      <tr>
        <td>任务执行团队:</td>
        <td>{{ showDateisItem.department_name?showDateisItem.department_name:' 无' }}</td>
      </tr>
      <tr>
        <td>任务状态:</td>
        <td>{{ showDateisItem.status }}</td>
      </tr>
    </table>
    <div class="btns">
      <el-button type="primary" size="mini" @click="playFlow(activeDate)">播放流</el-button>
    </div>
  </div>
  <!-- </BaseTip> -->
</template>

<script>
export default {
  name: 'ShowDateis',
  components: {

  },
  props: {
    show: {
      type: Boolean,
      default: false
    },
    showPosition: {
      type: Object,
      default: () => {
        return {}
      }
    },
    showDateisItem: {
      type: Object,
      default: () => {
        return {}
      }
    },
    activeDate: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      // showDetails: false
    }
  },
  computed: {},
  watch: {
  },
  mounted() {},
  created() {},
  methods: {
    // close() {
    //   this.$emit('close')
    //   this.$emit('update:show', false)
    // },
    playFlow(item) {
      this.$emit('playFlow', item)
    }
  }
}
</script>

<style lang='scss' scoped>
    .show-dateils{
      .btns{
        text-align: center;
      }
    }
</style>
