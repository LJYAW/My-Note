<!--
 * @Author: your name
 * @Date: 2021-01-13 17:26:54
 * @LastEditTime: 2021-01-29 15:31:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/full_business/edit_task/task-models/task-models.vue
-->
<template>
  <div class="edit-table-wrap">
    <!-- <div class="top">
      <div class="task-time">
        <span>当前任务失效:</span>
        <span>00:00:00</span>
      </div>
      <div class="btns">
        <el-button type="primary" round>任务管理</el-button>
        <el-button type="danger" round>任务挂起</el-button>
        <el-button type="success" round>任务占用</el-button>
      </div>
    </div> -->
    <div class="tab-list">

      <!-- <base-btn @click="openQt">测试打开客户端 测试缓存 测试方法</base-btn> -->

      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="指派任务" name="指派任务">
          <assign />
        </el-tab-pane>
        <el-tab-pane label="公共任务" name="公共任务">
          <vPublic />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import assign from './tab/assign.vue'
import vPublic from './tab/public.vue'
import { getToken } from '@/utils/auth' // get token from cookie

export default {
  components: { assign, vPublic },
  data() {
    return {
      activeName: '指派任务'
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    openQt() {
      const token = getToken()

      /* eslint-disable no-unused-vars */
      /* eslint-disable no-undef */
      var webChannel = new QWebChannel(
        qt.webChannelTransport, // 这里的webChannel是全局的变量，可以在其它位置访问
        function(channel) {
          window.TaskEditor = channel.objects.TaskEditor
        }
      )
      setTimeout(() => {
        if (window.TaskEditor) {
          window.TaskEditor.onEditTask(373, token)
        }
      })
    },
    handleClick() {}
  }
}
</script>

<style lang='scss' scoped>
.edit-table-wrap {
  background-color: #fff!important;
  padding: 20px;
  border-radius: 3px;
  // .top{
  //   height: 80px;
  //   line-height: 80px;
  //   display: flex;
  //   justify-content: space-between;
  //   border-bottom: 1px solid #ddd;
  //   .btns{
  //     button{
  //       height: 36px;
  //       line-height: 10px;
  //     }
  //   }
  //   .task-time{
  //     font-size: 18px;
  //     font-weight: 500;
  //     color: #333333;
  //     letter-spacing: 1px;
  //     span{
  //       margin-right: 10px;
  //     }
  //   }
  // }
  .tab-list{
    ::v-deep.el-tabs__nav-wrap::after{
       position: static !important;
    }
  }
}
</style>
