<!--
 * @Author: your name
 * @Date: 2021-07-30 09:56:57
 * @LastEditTime: 2021-08-02 11:44:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/examine_log/index.vue
-->
<template>
  <div class="examine-log">
    <el-form :inline="true">
      <el-form-item label="任务日期">
        <el-date-picker
          v-model="form.date"
          value-format="yyyy-MM-dd"
          type="date"
          :clearable="false"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="频道">
        <el-select v-model="form.channel_id" filterable clearable placeholder="请选择频道" style="width:100%">
          <el-option
            v-for="item in filterChannelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <base-btn size="big" @click="search">查询</base-btn>

    </el-form>
    <LogTable
      v-loading="loading"
      :table-data="tableData"
      :pag="pag"
      @currentChange="handleCurrentChange"
      @sizeChange="handleSizeChange"
    />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import LogTable from './LogTable'
import dateFun from '@/utils/time'
export default {
  components: {
    LogTable
  },
  props: {

  },
  data() {
    return {
      form: {
        date: '',
        channel_id: null
      },
      loading: true,
      pag: {
        page: 1,
        limit: 10,
        total: 0
      },
      tableData: []
    }
  },
  computed: {
    ...mapGetters([
      'channelData', 'userInfo'
    ]),
    filterChannelData() {
      if (this.userInfo.work === '广告审核' && this.channelData.length) {
        const channelIds = this.userInfo.ad_channel_ids.split(',')
        return this.channelData.filter(item => channelIds.includes(String(item.id)))
      }
      return this.channelData
    }
  },
  watch: {

  },
  created() {
    if (!this.channelData.length) {
      this.$store.dispatch('channel/getChannelData')
    }
    this.form.date = dateFun.convert(new Date(), 'y-m-d')
    this.getData()
  },
  mounted() {

  },
  methods: {
    search() {
      this.pag.page = 1
      this.getData()
    },
    getData() {
      this.loading = true
      const params = {
        date: this.form.date || null,
        channel_id: this.form.channel_id,
        page: this.pag.page,
        limit: this.pag.limit
      }
      this.$get('/examine/ads-logs', params).then((res) => {
        this.tableData = res.data.list
        this.pag.total = res.data.total
        this.loading = false
      }).catch((error) => {
        this.$message.error(error.msg)
      })
    },
    handleCurrentChange(val) {
      this.pag.page = val
      this.getData()
    },
    handleSizeChange(val) {
      this.pag.limit = val
      this.getData()
    }
  }
}
</script>

<style scoped lang="scss">
.examine-log{
    padding: 20px;
    display: flex;
    flex-direction: column;
    .el-form{
        .el-form-item{
            margin-right: 20px;
        }
    }
}
</style>
