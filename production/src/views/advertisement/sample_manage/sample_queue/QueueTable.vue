<!--
 * @Author: your name
 * @Date: 2021-05-11 16:27:28
 * @LastEditTime: 2021-07-08 11:23:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/sample_queue/QueueTable.vue
-->
<template>
  <div class="queue-table">
    <p class="table-total">当前条件下共{{ pag.total }}条数据</p>
    <el-table :data="tableData" border>
      <el-table-column prop="status" label="状态" width="200">
        <template slot-scope="scope">
          {{ scope.row.status }}
          <span v-if="scope.row.status=='失败'">:{{ scope.row.reason }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="channel_name" label="频道" width="200" />
      <el-table-column prop="ad_id" label="广告 ID" />
      <el-table-column prop="title" label="标题" width="300" />
      <el-table-column prop="tag" label="分类" width="180">
        <template slot-scope="scope">
          {{ scope.row.tags }}
        </template>
      </el-table-column>
      <el-table-column prop="brand" label="品牌" />
      <el-table-column prop="updated_at" label="更新时间" width="180" />
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <base-btn
            v-if="(scope.row.status!=='视频转码中'&&scope.row.reason!=='视频问题')||scope.row.status=='入库中'"
            type="text"
            @click="playSample(scope.row)"
          >播放</base-btn>

          <base-btn
            v-if="scope.row.reason==='视频问题'&&scope.row.status!=='入库中'"
            type="text"
            @click="reSubmit(scope.row)"
          >重新提交</base-btn>
          <base-btn
            v-if="scope.row.reason==='已存在'&&scope.row.status!=='待编辑'"
            type="text"
            @click="toAdd(scope.row)"
          >继续添加</base-btn>

          <base-btn
            v-if="scope.row.status==='待编辑'"
            type="text"
            @click="updateSample(scope.row)"
          >编辑</base-btn>

          <base-btn
            v-if="showDelBtn(scope.row.status)"
            type="text"
            class="del-btn"
            @click="deleteData(scope.row)"
          >删除</base-btn>
        </template>
      </el-table-column>
    </el-table>
    <base-pag
      :total="pag.total"
      :limit="pag.limit"
      :page="pag.page"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    />
    <PlaySample v-if="showPlayModel" :show="showPlayModel" :video-url="modelData.mp4_url" @close="closePlayModel">
      <div class="model-text">
        <p>样本 ID ：{{ modelData.sample_ids }}</p>
        <p>标题 ：{{ modelData.title }}</p>
        <p>分类 ：{{ modelData.tags }}</p>
        <p>品牌 ：{{ modelData.brand }}</p>
        <p>样本来源 ：{{ modelData.channel_name }} {{ modelData.start_time|unixToHms }} - {{ modelData.end_time|unixToHms }}</p>
      </div>
    </PlaySample>

    <UpdateSample
      v-if="showUpdateModel"
      :show="showUpdateModel"
      :video-url="modelData.mp4_url"
      @close="closeUpdateModel"
    >
      <UpdateForm
        :model-data="modelData"
        :video-url="modelData.mp4_url"
        @close="closeUpdateModel"
        @update="updateTable"
      />
    </UpdateSample>

    <ContinueAdd
      v-if="showAddModel"
      :show="showAddModel"
      :model-data="modelData"
      @close="closeAddModel"
      @update="updateTable"
    />
  </div>
</template>

<script>
import PlaySample from '../components/PlaySample'
import UpdateSample from './components/UpdateSample'
import UpdateForm from './components/UpdateForm'
import ContinueAdd from './components/ContinueAdd'
export default {
  components: {
    PlaySample,
    UpdateSample,
    UpdateForm,
    ContinueAdd
  },
  props: {
    tableData: {
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
      showPlayModel: false,
      showUpdateModel: false,
      showAddModel: false,
      modelData: {},
      repeat_id: null
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
    updateTable() {
      this.$emit('update')
    },
    deleteData(obj) {
      this.$confirm('删除不可逆，请确定！', '提示', {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.modelData = obj
        this.deleteSample()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    showDelBtn(status) {
      if (status === '入库中' || status === '视频转码中' || status === '入库成功') return false
      return true
    },
    deleteSample() {
      const params = {
        data_id: this.modelData.id,
        action: 'delete',
        seq: {
          title: this.modelData.title,
          brand: this.modelData.brand,
          tags: this.modelData.tags
        }
      }
      this.$post('/ad-sample/queue-modify', params).then((res) => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.$emit('update')
      }).catch((error) => {
        this.$message({
          type: 'warning',
          message: error.msg
        })
      })
    },
    // 播放
    playSample(obj) {
      this.modelData = obj
      this.showPlayModel = true
    },
    // 修改
    updateSample(obj) {
      this.showUpdateModel = true
      this.modelData = obj
    },
    // 关闭播放弹窗
    closePlayModel() {
      this.showPlayModel = false
    },
    // 关闭修改弹窗
    closeUpdateModel() {
      this.showUpdateModel = false
    },
    // 关闭继续添加弹窗
    closeAddModel() {
      this.showAddModel = false
    },
    // 重新提交
    reSubmit(obj) {
      this.$confirm('确定重新提交！视频问题导致失败重新提交也可能失败。', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.modelData = obj
        this.submit()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    submit() {
      const params = {
        data_id: this.modelData.id,
        action: 'resubmit',
        seq: {
          title: this.modelData.title,
          brand: this.modelData.brand,
          tags: this.modelData.tags
        }
      }
      this.$post('/ad-sample/queue-modify', params).then((res) => {
        this.$message({
          type: 'success',
          message: '提交成功'
        })
        this.$emit('update')
      }).catch((error) => {
        this.$message({
          type: 'warning',
          message: error.msg
        })
      })
    },
    // 继续添加
    toAdd(obj) {
      this.modelData = obj
      this.showAddModel = true
    }
  }
}
</script>

<style scoped lang="scss">
.queue-table{
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    flex: 1;
    .table-total{
        margin-bottom: 20px;
    }
    .el-table{
        height: 0;
    }
    ::v-deep .el-table__body-wrapper{
        height: calc(100% - 40px);
    }
    .del-btn{
        color: #F71D1D;
    }
    .model-text{
        margin-top: 20px;
        p{
            color: #333;
            margin-bottom: 10px;
        }
    }
}
</style>
