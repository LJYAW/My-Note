<!--
 * @Author: your name
 * @Date: 2021-05-10 15:51:54
 * @LastEditTime: 2021-06-08 15:21:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/sample_bank/SampleTable.vue
-->
<template>
  <div class="sample-table">
    <p class="table-total">当前条件下共{{ pag.total }}条数据</p>
    <el-table :data="tableData" border>
      <el-table-column prop="id" label="样本 ID " width="80" />
      <el-table-column prop="channel_name" label="频道" width="200" />
      <el-table-column prop="ad_id" label="广告 ID" />
      <el-table-column prop="title" label="标题" width="300" />
      <el-table-column prop="tags" label="分类" width="180" />
      <el-table-column prop="brand" label="品牌" width="200" />
      <el-table-column prop="hash_id" label="HASH ID" width="350" />
      <el-table-column prop="updated_at" label="更新时间" width="180" />
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <base-btn type="text" @click="playSample(scope.row)">播放</base-btn>
          <base-btn
            v-if="$store.state.user.userInfo.is_superman||
              $store.state.user.userInfo.work.indexOf('广告审核管理员')!=-1"
            type="text"
            @click="updateSample(scope.row)"
          >修改</base-btn>
          <base-btn
            v-if="$store.state.user.userInfo.is_superman||
              $store.state.user.userInfo.work.indexOf('广告审核管理员')!=-1"
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
        <p>样本 ID ：{{ modelData.id }}</p>
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
      <UpdateForm :model-data="modelData" @close="closeUpdateModel" @update="updateTable" />
    </UpdateSample>
  </div>
</template>

<script>
import PlaySample from '../components/PlaySample'
import UpdateSample from '../components/UpdateSample'
import UpdateForm from './components/UpdateForm'
export default {
  components: {
    PlaySample,
    UpdateSample,
    UpdateForm
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
      modelData: {}
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
    deleteSample() {
      const params = {
        data_id: this.modelData.id,
        action: 'delete',
        seq: {
          title: this.modelData.title,
          tags: this.modelData.tags
        }
      }
      this.$post('/ad-sample/modify', params).then((res) => {
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
    updateTable() {
      this.$emit('update')
    },
    // 关闭播放弹窗
    closePlayModel() {
      this.showPlayModel = false
    },
    // 关闭修改弹窗
    closeUpdateModel() {
      this.showUpdateModel = false
    }
  }
}
</script>

<style scoped lang="scss">
.sample-table{
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
