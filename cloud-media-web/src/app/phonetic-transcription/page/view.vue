<!--
 * @Author: your name
 * @Date: 2021-08-31 12:11:19
 * @LastEditTime: 2021-09-08 09:51:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/phonetic-transcription/index.vue
-->
<template>
  <div class="container wrap">
    <title-bar title="语音转写" />
    <div class="operation">
      <el-input
        v-model="searchWord"
        class="search"
        placeholder=""
        size="small"
        clearable
        suffix-icon="el-icon-search"
        @change="initList"
      />
      <el-button type="primary" size="small" @click="addProject">新建项目</el-button>
    </div>

    <el-table v-loading="loading" :data="tableData" border stripe>
      <el-table-column prop="title" label="文件名称" />
      <el-table-column prop="types" label="文件类型">
        <template slot-scope="scoped">
          <div>{{ scoped.row.types === 1?'音频':'字幕' }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="create_time" label="创建时间" />
      <el-table-column prop="status" label="识别类型">
        <template slot-scope="scoped">
          <div :style="`color:${scoped.row.status===2?'#5675E8':'#3DD400'}`">{{ initStatus(scoped.row.status) }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="操作">
        <template slot-scope="scoped">
          <el-button v-if="scoped.row.status===2" type="text" @click="goToDetail(scoped.row.id)">查看</el-button>
          <el-button type="text" style="color: #f16b6b" @click="detele(scoped.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <base-pag
      :total="total"
      :limit="limit"
      :page="page"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
    />
    <base-dialog
      :show.sync="chooseVisible"
      title="选择视频"
      width="588px"
      class="upload-dialog"
      :close-on-click-modal="false"
    >
      <AddProject
        file-type="audio/mpeg, audio/wav,audio/aac, video/mp4"
        upload-type="voice"
        @addProject="add"
      >
        <div class="btn-wrap" solt>
          <div
            v-for="(i,index) in btn"
            :key="index"
            :class="['btn',activeIndex===index?'active':'']"
            @click="activeIndex=index"
          >
            {{ i }}
          </div>
        </div>
      </AddProject>
    </base-dialog>
  </div>
</template>

<script>
import AddProject from '@/components/AddProject'
import setQueryParams from '@/utils/setQueryParams'

export default {
  components: {
    AddProject
  },
  props: {

  },
  data() {
    return {
      searchWord: '',
      chooseVisible: false,
      loading: false,
      tableData: [],
      btn: [
        '视频语音识别',
        '视频字幕识别'
      ],
      activeIndex: 0,

      total: 0,
      page: 1,
      limit: 10
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.initList()
  },
  mounted() {

  },
  methods: {

    async add(item) {
      const key = item.key || item.videoData.video_bos_key
      const title = item.title
      const params = {
        title: title,
        url: key,
        types: this.activeIndex + 1
      }
      const { err, res } = await this.$post('/voice_txt/', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.chooseVisible = false
      this.initList()
    },
    async initList() {
      this.loading = true
      const query = {
        title__contains: this.searchWord
      }
      const params = {
        query: setQueryParams(query),
        page: this.page,
        limit: this.limit
      }
      const { err, res } = await this.$get('/voice_txt', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.loading = false
      this.tableData = res.data
      this.total = res.count
    },

    goToDetail(id) {
      this.$router.push({
        path: '/phonetic-transcription/details',
        query: {
          id: id
        }
      })
    },
    detele(id) {
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteDirect(id)
      }).catch(() => {
      })
    },
    async deleteDirect(id) {
      const { err, res } = await this.$deleteRun(`/voice_txt/${id}`)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('删除成功')
      this.initList()
    },
    handleCurrentChange(val) {
      this.page = val
      this.initList()
    },
    addProject() {
      this.chooseVisible = true
    },
    initStatus(val) {
      let str = ''
      switch (val) {
        case 1:
          str = '待识别'
          break
        case -1:
          str = '识别失败'
          break
        case 2:
          str = '识别成功'
          break
        case 3:
          str = '识别中'
          break
        default:
          str = '识别中'
          break
      }
      return str
    }
  }
}
</script>

<style scoped lang="scss">
.wrap {
  padding-top: 30px;
  padding-bottom: 30px;

  .operation {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 30px;
    margin-bottom: 30px;

    .search {
      width: 365px;
    }

  }

}

</style>
