<!--
 * @Author: your name
 * @Date: 2021-04-08 18:45:50
 * @LastEditTime: 2021-07-20 17:01:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/views/homePage/index.vue
-->
<template>
  <div class="homePage-wrap">
    <div class="main-wrap">
      <p class="title"> 广电规划院品控表下载</p>
      <p class="desc">请选择下载日期，默认选择前一天</p>
      <el-date-picker
        v-model="date"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="选择日期"
      />
      <div class="btn-wrap">
        <base-btn class="btn" size="big" @click="exportData('EPG')">
          <span class="i-span"><i class="el-icon-bottom" /></span>EPG 品控表
        </base-btn>
        <base-btn class="btn" size="big" @click="exportData('广告')">
          <span class="i-span"><i class="el-icon-bottom" /></span>广告品控表
        </base-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { formatDate } from '@/utils/date.js'
import axios from '@/axios/request.js'
import { mapGetters } from 'vuex'
export default {
  name: 'Home',
  components: {

  },
  props: {

  },
  data() {
    return {
      date: ''
    }
  },
  computed: {
    timeDefault() {
      var date = new Date()
      var defaultDate = new Date(date.getTime()) - 24 * 60 * 60 * 1000
      return formatDate(defaultDate)
    },
    ...mapGetters([
      'token'
    ])
  },
  watch: {

  },
  created() {
    this.date = this.timeDefault
  },
  mounted() {
  },
  methods: {
    exportData(type) {
      const url = type === 'EPG' ? '/epg/epg-export' : '/epg-ad/export'
      axios({
        method: 'get',
        url: url,
        headers: {
          token: this.token
        },
        params: {
          day: this.date
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
            link.setAttribute('download', type + '品控表' + '-' + this.date + '.xlsx')
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
    }
  }
}
</script>

<style lang="scss">
.app-main .homePage-wrap.contain{
  width: 100vw;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  .main-wrap{
    width: 800px;
    margin-top: -100px;
    text-align: center;
    .title{
      font-size: 34px;
      margin-bottom: 100px;
    }
    .desc{
      color: #999;
      font-size: 14px;
      margin-bottom: 10px;
    }
    .el-input {
      input{
        background: #fff;
        border: 1px solid #ccc;
      }
    }
    .btn-wrap{
      margin-top: 100px;
      .btn{
        margin: 0 40px;
        width: 200px;
        .i-span{
          background-color: rgba(244, 249, 255, 94);
          display: inline-block;
          width: 30px;
          height: 30px;
          border-radius: 100%;
          margin-right: 10px;
          i{
            color: #409EFF;
            font-size: 20px;
            font-weight: 600;
            line-height: 28px;
          }
        }
      }

    }
  }
}
</style>
