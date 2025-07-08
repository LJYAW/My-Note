<!--
 * @Author: your name
 * @Date: 2021-05-27 18:01:42
 * @LastEditTime: 2021-07-14 17:39:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/components/taskTable.vue
-->
<template>
  <div class="task-table">
    <!-- 任务列表 -->
    <p class="total">当前条件下共{{ pag.total }}条数据</p>
    <el-table :data="taskData" border>
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="resourcetype" label="资源类型">
        <template slot-scope="scope">
          {{ resourcetype[scope.row.resourcetype-1] }}
        </template>
      </el-table-column>
      <el-table-column prop="channel_name" label="频道名称" width="300" />
      <el-table-column prop="program_name" label="栏目名称" width="200" />
      <el-table-column label="状态">
        <template slot-scope="scope">
          {{ baseData.statusData[scope.row.doType] }}
        </template>
      </el-table-column>
      <el-table-column prop="source" label="路径来源" />
      <el-table-column prop="business" label="业务" width="180" />
      <el-table-column prop="restrict" label="数据限制" width="200">
        <template slot-scope="scope">
          {{ scope.row.programTimeStart }} - {{ scope.row.programTimeEnd }}
        </template>
      </el-table-column>
      <el-table-column prop="successnums" label="成功数" />
      <el-table-column prop="lastfinishtime" label="爬取时间" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.lastfinishtime">
            {{ (scope.row.lastfinishtime)*1000|unixToHms }}
          </span>
          <span v-else> - </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <base-btn v-has="['superman','任务管理']" type="text" @click="edit(scope.row.id)">编辑</base-btn>
          <base-btn
            v-if="scope.row.doType!==-3&&scope.row.doType!==-4&&scope.row.doType!==-5"
            v-has="['superman','任务管理','任务删除']"
            type="text"
            @click="confirm('取消任务',-4,scope.row.id)"
          >取消任务</base-btn>
          <base-btn v-if="scope.row.doType==-2" type="text" @click="changeStatus('无法解析',-3,scope.row.id)">无法解析</base-btn>
          <base-btn
            v-if="scope.row.doType==2&&scope.row.resourcetype==1"
            type="text"
            @click="confirm('标记完结',3,scope.row.id)"
          >标记完结</base-btn>
          <base-btn
            v-if="scope.row.doType==3&&scope.row.resourcetype==1"
            type="text"
            @click="confirm('标记在播',2,scope.row.id)"
          >标记在播</base-btn>
          <base-btn
            v-if="scope.row.doType==-4"
            type="text"
            @click="changeStatus('开启爬取',2,scope.row.id)"
          >开启爬取</base-btn>
          <base-btn type="text" @click="work(scope.row.id)">执行</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <base-pag
      :total="pag.total"
      :limit="pag.pageSize"
      :page="pag.currentPage"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    />

    <base-dialog :show.sync="dialogVisible" :title="dialogTitle">
      <compontents
        :is="dialogCompomtentsName"
        :title="dialogTitle"
        @close="close"
        @updateData="updateData"
      />
    </base-dialog>
  </div>
</template>

<script>
import addAnalyzerDialog from '../model/addAnalyzerDialog'
import analysisDialog from '../model/analysisDialog'
import baseData from '../js/baseData'
export default {
  components: {
    addAnalyzerDialog,
    analysisDialog
  },
  props: {
    taskData: {
      type: Array,
      default() {
        return []
      }
    },
    pag: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      // 弹窗标题
      dialogTitle: '',
      // 是否显示弹窗
      dialogVisible: false,
      // 弹窗里展示的组件
      dialogCompomtentsName: '',
      baseData: baseData,
      resourcetype: ['整档', '单集', '短视频'],
      roles: localStorage.getItem('roles')
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    handleCurrentChange(val) {
      this.$emit('currentChange', val)
    },
    handleSizeChange(val) {
      this.$emit('sizeChange', val)
    },
    close() {
      this.dialogVisible = false
    },
    openDialog(title, name) {
      this.dialogVisible = true
      this.dialogCompomtentsName = name
      this.dialogTitle = title
    },
    updateData() {
      this.$emit('updateData')
    },
    edit(id) {
      this.$router.push({
        path: '/editTask',
        query: {
          id: id
        }
      })
    },
    confirm(text, status, id) {
      this.$confirm(`确定${text}？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.changeStatus(text, status, id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async changeStatus(text, status, id) {
      const { data, err } = await this.$put(`/jobs/${id}`, { doType: status })
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: `${text}成功`
        })
        this.updateData()
      }
    },
    deleteTask(id) {
      this.$confirm('确定取消任务？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.delete(id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async delete(id) {
      const { data, err } = await this.$deleteRun(`/jobs/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '取消任务成功'
        })
        this.updateData()
      }
    },
    async work(id) {
      const { data, err } = await this.$get(`/jobs/sendjobs?id=${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '执行成功'
        })
      }
    }
  }
}
</script>

<style scoped lang="scss">
.task-table{
    .total{
        margin-bottom: 20px;
    }
    .el-button + .el-button{
        margin-left: 0px;
    }
    .el-button--text{
      margin-right: 5px;
    }
}
</style>
