<!--
 * @Author: your name
 * @Date: 2021-08-03 14:42:51
 * @LastEditTime: 2021-10-13 10:18:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/order-info/index.vue
-->
<template>
  <div class="opton-record">
    <title-bar title="操作记录" />
    <div class="opton-top">
      <div class="item-list">
        <span class="title-top">操作人员</span>
        <el-input v-model="form.name" clearable />
      </div>
      <div class="item-list">
        <span class="title-top">视频名称</span>
        <el-input v-model="form.videoName" clearable />
      </div>
      <div class="item-list item-date">
        <el-date-picker
          v-model="form.date"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd hh:mm:ss"
        />
      </div>
      <div class="btns">
        <el-button type="primary" size="mini" @click="search">查询</el-button>
      </div>
    </div>
    <div class="table-list">
      <el-table
        :data="tableData"
        border
      >
        <el-table-column
          prop="admin_phone"
          label="操作人手机号"
        />
        <el-table-column
          prop="admin_names"
          label="操作人姓名"
        />
        <el-table-column
          prop="dotype"
          label="操作类别"
        />
        <el-table-column
          prop="titles"
          label="视频名称"
        />
        <el-table-column
          prop="create_time"
          label="订单创建时间"
        />
      </el-table>
    </div>
    <div class="page-list">
      <base-pag
        :total="total"
        :limit="limit"
        :page="page"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<script>
import setQueryParams from '@/utils/setQueryParams'

export default {
  name: 'OperationLog',
  components: {

  },
  props: {

  },
  data() {
    return {
      total: null,
      limit: 10,
      page: 1,
      tableData: [
        { date: '2021-08-09' }
      ],
      form: {
        name: '',
        videoName: '',
        date: []
      }
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getData()
  },
  mounted() {

  },
  methods: {
    async getData() {
      const query = {
        admin_names: this.form.name,
        create_time__gt: this.form.date ? this.form.date[0] : '',
        create_time__lt: this.form.date ? this.form.date[1] : '',
        titles: this.form.videoName
      }
      const params = {
        limit: this.limit,
        page: this.page,
        query: setQueryParams(query),
        sortby: 'id',
        order: 'desc'
      }
      const { err, res } = await this.$get('/logs/', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.tableData = res.data
      this.total = res.count
    },
    search() {
      this.getData()
    },
    handleCurrentChange(val) {
      this.page = val
      this.getData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getData()
    }
  }
}
</script>

<style scoped lang="scss">
.opton-record {
  width: 100%;
  height: 100%;

  .opton-top {
    margin-top: 20px;
    display: flex;

    .item-date {
      width: 400px  !important;

      ::v-deep .el-range-separator {
        height: auto;
        padding: 0;
      }

      .el-date-editor {

        ::v-deep .el-range__icon {
          height: auto;
        }
      }
    }

    .item-list {
      width: 260px;
      height: 30px;
      line-height: 30px;
      padding: 0 10px;
      box-sizing: border-box;
      border: 1px solid rgba(216, 216, 216, 1);
      color: rgba(193, 193, 193, 1);
      font-size: 12px;
      display: flex;
      border-radius: 4px;
      margin-right: 10px;
      cursor: pointer;

      ::v-deep .el-icon-circle-close {
        height: auto;
      }

      ::v-deep .el-input__suffix {
        top: -3px;
      }

      &:hover {
        border: 1px solid #5675e8  !important;
        color: rgba(64, 64, 64, 1) !important;
      }

      &:last-child {
        margin-right: 0;
      }

      .title-top {
        position: relative;
        min-width: 70px;
        text-align: center;
        padding-right: 10px;
        font-size: 12px;

        &::after {
          content: "";
          position: absolute;
          top: 7px;
          right: 0;
          width: 1px;
          height: 14px;
          background: #d8d8d8;
        }
      }

      ::v-deep .el-input__inner {
        height: 100%;
        border: 0;
        background: transparent;
      }
    }
  }

  .table-list {
    width: 100%;
    margin-top: 20px;
  }

  .page-list {
    text-align: right;
    margin-top: 20px;
  }
}
</style>
