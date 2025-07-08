<template>
  <div class="edit-table-wrap">
    <div style="position:absolute;right:20px;top:20px">
      <base-btn size="big" @click.native="edit()">进入编辑系统</base-btn>
    </div>
    <taskStatistics />
    <taskDeatils />
  </div>
</template>

<script>
import taskStatistics from './task/task_statistics'
import taskDeatils from './task/task_deatils'
export default {
  name: 'Home',
  components: { taskStatistics, taskDeatils },
  data() {
    return {

    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    edit() {
      this.$router.push({ path: '/full-business/task-choice' })
    }
  }
}
</script>

<style lang='scss'>
.edit-table-wrap{
  position: relative;
  background-color: #f3f3f3!important;
  .pagination-list{
    width: 100%;
    text-align: right;
    margin-top: 30px;
  }
  .edit-task-table{
    background: #fff;
    padding: 20px;
    .search-top{
      display: flex;
      .btns{
        margin-left: 10px;
      }
      .item-search{
        display: flex;
        margin-right: 10px;
        .item-label{
          min-width: 60px;
          font-size: 14px;
          margin-top: 10px;
          // text-align: center;
        }
      }
    }
    .task-table{
      margin-top: 26px;
    }
  }
  .task-details{
    background: #fff;
    padding: 20px;
    margin-top: 20px;
    .top-title{
      font-size: 16px;
      font-weight: 700;
      color: #333333;
      padding-bottom: 15px;
      border-bottom: 1px solid #ddd;
    }
    .top-details{
      margin-top: 30px;
      .el-form-item{
        margin-right: 20px;
      }
    }
    .check-list{
      margin-top: 10px;
    }
    .item-search{
        display: flex;
        flex-grow: 1;
        margin-right: 10px;
        .item-label{
          min-width: 70px;
          font-size: 14px;
          margin-top: 10px;
          // text-align: center;
        }
    }
    .table-list{
      .pri-logo{
        background: #7D7D7D 10000% 10000%;
        color: #fff;
        padding: 2px;
        &.pub-logo{
          background: #167CFF 10000%;
          color: #fff;
          padding: 2px;
        }
      }
    }
    .details-table{
      .el-tabs__nav-wrap::after{
        position: static !important;
      }
    }
  }
}
</style>
