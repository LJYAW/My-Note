<!--
 * @Author: your name
 * @Date: 2021-01-13 18:08:14
 * @LastEditTime: 2021-03-16 14:15:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/edit_task/task/task_statistics.vue
-->
<template>
  <div class="edit-task-table">
    <div class="search-top">
      <div class="item-search">
        <span class="item-label">日期:</span>
        <el-date-picker
          v-model="form.date"
          type="daterange"
          value-format="yyyy-MM-dd"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />

      </div>
      <div class="item-search btns">
        <base-btn size="big" @click="getStatisticsNum()">确定</base-btn>
      </div>
    </div>
    <numsList :num-data="numData" />
  </div>
</template>

<script>
import numsList from '../../../components/NumsLIst/index'
import { formatDate } from '../js/times'
export default {
  components: { numsList },
  data() {
    return {
      numData: [],
      form: {
        date: [formatDate(new Date()), formatDate(new Date())]
      },
      list: [
        { key: 'complete_num',
          value: '已完成广告数'
        },
        { key: 'hang_up_num',
          value: '挂起任务数'
        },
        { key: 'incomplete_num',
          value: '未完成任务数'
        },
        { key: 'no_ads_complete_num',
          value: '已完成非广告数' }
      ]
    }
  },
  computed: {},
  watch: {},
  created() {
    this.getStatisticsNum()
  },
  mounted() {

  },
  methods: {
    getStatisticsNum() {
      this.numData = []
      var params = {
        start_date: this.form.date ? this.form.date[0] : '',
        end_date: this.form.date ? this.form.date[1] : ''
      }
      this.numData = []
      this.$get('/ads-task/editor-tj', params).then((res) => {
        this.list.forEach((item, index) => {
          this.numData.push({
            label: item.value,
            value: res.data[item.key]
          })
        })
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    }
  }
}
</script>

<style lang='scss' scoped>
  .nums-list{
     ::v-deep.item-nums{
       margin-left: 0 !important;
     }
  }
</style>
