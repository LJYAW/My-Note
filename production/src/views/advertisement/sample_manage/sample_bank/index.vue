<!--
 * @Author: your name
 * @Date: 2021-05-10 15:16:20
 * @LastEditTime: 2021-06-17 15:37:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/SampleBank.vue
-->
<template>
  <div class="sample-bank">
    <el-form :inline="true">

      <el-form-item label="广告名称">
        <el-input v-model="form.ad_title" placeholder="请输入广告名称" clearable />
      </el-form-item>

      <el-form-item label="频道">
        <el-select v-model="form.channel_id" filterable clearable placeholder="请选择频道" style="width:100%">
          <el-option
            v-for="item in channelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="分类">
        <el-select v-model="form.tag" placeholder="请选择分类" clearable>
          <el-option v-for="item in ad_category" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="样本ID">
        <el-input v-model="form.sample_ids" placeholder="请输入样本ID" clearable />
      </el-form-item>

      <el-form-item label="广告ID">
        <el-input v-model="form.ad_id" placeholder="请输入广告ID" clearable />
      </el-form-item>

      <el-form-item label="更新时间">
        <el-date-picker
          v-model="form.update_time"
          clearable
          type="datetimerange"
          value-format="yyyy-MM-dd HH:mm:ss"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>

      <div class="btn-wrap">
        <div class="btn-wrap-left">
          <base-btn @click="search">查询</base-btn>
          <base-btn @click="clear">清除</base-btn>
          <base-btn @click="exportData">导出</base-btn>
        </div>
      </div>
    </el-form>

    <SampleTable
      v-loading="loading"
      :table-data="tableData"
      :pag="pag"
      @currentChange="handleCurrentChange"
      @sizeChange="handleSizeChange"
      @update="getData()"
    />
  </div>
</template>

<script>
import SampleTable from './SampleTable'
import { mapGetters } from 'vuex'
import axios from '@/axios/request.js'
export default {
  components: {
    SampleTable
  },
  props: {

  },
  data() {
    return {
      form: {
        ad_title: '',
        tag: '',
        ad_id: '',
        category: '',
        sample_ids: '',
        update_time: [],
        channel_id: null
      },
      pag: {
        page: 1,
        limit: 10,
        total: 0
      },
      tableData: [],
      loading: true,
      ad_category: ['公益广告', '商业广告', '购物广告', '植入广告', '赞助广告']
    }
  },
  computed: {
    ...mapGetters(['channelData', 'token'])
  },
  watch: {

  },
  created() {
    this.getData()
    if (!this.channelData.length) this.$store.dispatch('channel/getChannelData')
  },
  mounted() {

  },
  methods: {
    handleCurrentChange(val) {
      this.pag.page = val
      this.getData()
    },
    handleSizeChange(val) {
      this.pag.limit = val
      this.getData()
    },
    // 搜索
    search() {
      this.pag.page = 1
      this.getData()
    },
    // 清除搜索
    clear() {
      Object.keys(this.form).forEach((item) => {
        this.form[item] = ''
      })
      this.form.update_time = []
      this.getData()
    },
    exportData() {
      axios({
        method: 'get',
        url: '/ad-sample/export',
        headers: {
          token: this.token
        },
        params: {
          sample_ids: this.form.sample_ids,
          tag: this.form.tag,
          channel_id: this.form.channel_id,
          ad_id: this.form.ad_id,
          ad_title: this.form.ad_title,
          updated_at_start: this.form.update_time ? this.form.update_time[0] : null,
          updated_at_end: this.form.update_time ? this.form.update_time[1] : null
        },
        responseType: 'blob'
      })
        .then(res => {
          if (res.data.type) {
            // 文件下载
            const blob = new Blob([res.data], {
              type: 'application/vnd.ms-excel'
            })
            let link = document.createElement('a')
            link.href = URL.createObjectURL(blob)
            link.setAttribute('download', '样本库.xlsx')
            link.click()
            link = null
            this.$message.success('导出成功')
          } else {
            // 返回json
            this.$message.warning(res.data.msg)
          }
        }).catch((error) => {
          console.log(error)
        })
    },
    getData() {
      this.loading = true
      const params = {
        ad_title: this.form.ad_title,
        ad_id: this.form.ad_id,
        sample_ids: this.form.sample_ids,
        updated_at_start: this.form.update_time ? this.form.update_time[0] : null,
        updated_at_end: this.form.update_time ? this.form.update_time[1] : null,
        page: this.pag.page,
        limit: this.pag.limit,
        show_delete: 0,
        tag: this.form.tag,
        channel_id: this.form.channel_id
      }
      this.$get('/ad-sample/search', params).then((res) => {
        this.tableData = res.data.list
        this.pag.total = res.data.total
      }).catch((error) => {
        console.log(error.msg)
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style scoped lang="scss">
.sample-bank{
    padding: 20px;
    display: flex;
    flex-direction: column;
    .el-form{
      .el-form-item{
        margin-right: 20px;
      }
      .btn-wrap{
        display: flex;
        justify-content: space-between;
      }
    }

}
</style>
