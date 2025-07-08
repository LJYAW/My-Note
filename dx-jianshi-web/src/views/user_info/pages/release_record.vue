<!--
 * @Author: zzx
 * @Date: 2020-07-22 19:25:22
 * @LastEditTime: 2020-11-19 14:58:09
 * @FilePath: /weijian_web/src/views/user_info/pages/release_record.vue
-->
<template>
  <div class="publish-history h-100">
    <!-- <div class='publish-header mb-20px pt-15px px-32px pb-35px'>
      <el-form :model="form" size="mini">
        <div class='mb-20px text-right'>
          <el-input v-model='keywords' placeholder="搜索视频关键词"></el-input>
          <vsvg icon='iconsousuoicon-01'></vsvg>
        </div>
        <el-form-item label='分类:' label-width="50px">
          <el-select v-model='form.category' placeholder="请选择视频分类">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label='发布平台:' label-width="80px">
          <el-select v-model='form.publish_platform' placeholder="请选择发布平台">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label='发布账号:' label-width="80px">
          <el-select v-model='form.publish_account' placeholder="请选择发布账号">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label='发布时间:' label-width="80px">
          <el-date-picker
            v-model="form.publish_time"
            type="datetimerange"
            :picker-options="pickerOptions"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right">
          </el-date-picker>
        </el-form-item>
      </el-form>
    </div> -->
    <tableCard :data="hository_list" :title="tableData" :operate="true" operate_name="查看" @routerGo="goToBaijiahao">
      <div slot="noData" class="no-data">
        <img :src="src" alt="" />
      </div>
    </tableCard>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="limit"
      class="mt-30px mr-40px fl-right mb-20px"
      layout=" prev, pager, next, jumper"
      :total="total"
      v-if="total > limit"
    ></el-pagination>
  </div>
</template>
<script>
import tableCard from '@/components/table'
export default {
  name: 'history',
  props: {},
  data() {
    return {
      video_status: 1,
      orderby_type: 'asc', // asc/desc
      keywords: '',
      hository_list: [],
      page: 1,
      limit: 10,
      total: 0,
      tableData: { cover_pic: '视频', title: '视频标题', category_name: '分类', publish_time: '发布时间', platform_name: '发布者账号', status: '发布状态' },
      form: {
        category: '',
        publish_time: '',
        publish_platform: '',
        publish_account: ''
      },
      options: [
        {
          value: '选项1',
          label: '黄金糕'
        },
        {
          value: '选项2',
          label: '双皮奶'
        },
        {
          value: '选项3',
          label: '蚵仔煎'
        },
        {
          value: '选项4',
          label: '龙须面'
        },
        {
          value: '选项5',
          label: '北京烤鸭'
        }
      ],
      pickerOptions: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      src: require('../../../assets/images/user_info/no_publish.png')
    }
  },
  computed: {},
  watch: {},
  methods: {
    handleSizeChange(val) {
      this.limit = val
      this.getHoistoryList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
      this.page = val
      this.getHoistoryList()
    },
    getHoistoryList() {
      let params = {
        status: this.video_status,
        page: this.page,
        limit: this.limit
      }
      this.$get('/short-video/list', { params: params }).then(res => {
        if (res.data.code == '0000') {
          this.hository_list = res.data.data.list
          this.total = res.data.data.total
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    goToBaijiahao(item) {
      this.$actions.openWin(item.baijiahao_url)
    }
  },
  components: { tableCard },
  created() {
    this.getHoistoryList()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.publish-history {
  background: #fff;
  // min-height: 750px;
  /deep/ .el-form {
    input {
      font-size: 14px;
      background: #f7f7f7;
    }
    & > div:first-child {
      position: relative;
      .el-input {
        width: 250px;
        input {
          border-radius: 20px;
          padding-left: 30px;
          border: none;
        }
      }
      svg {
        position: absolute;
        top: 13px;
        right: 25px;
      }
    }
    .el-form-item {
      width: 44%;
      margin-right: 0;
      display: inline-block;
      margin-right: 6%;

      .el-select,
      .el-date-editor {
        width: 100%;
      }
      .el-form-item__label {
        line-height: 40px;
      }
      .el-range-separator {
        padding: 6px 0;
      }
      .el-input__inner {
        height: 40px;
        line-height: 40px;
        background: #f7f7f7;
      }
    }
  }

  .publish-header {
    background: #fff;
    margin-bottom: 20px;
  }
  /deep/ .table {
    font-size: 12px;
    .img-wrap {
      width: 80px;
      height: 45px;
      background: #e5e5e5;
    }
    th {
      background: #fff;
      color: #999;
      border-bottom: none;
      padding: 32px 15px;
      &:first-child {
        padding-left: 32px;
      }
    }
    td {
      color: #333;
    }
    tbody {
      td:last-child {
        color: $c;
      }
      td:first-child {
        padding-left: 32px;
      }
      td:nth-child(6) {
        color: #49bf5d;
        .fail {
          color: $c;
        }
      }
    }
  }
}
</style>
