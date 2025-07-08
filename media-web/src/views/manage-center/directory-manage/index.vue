<!--
 * @Author: your name
 * @Date: 2021-08-03 14:40:19
 * @LastEditTime: 2021-08-26 16:14:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-mange/index.vue
-->
<template>
  <div class="directory-manage">
    <title-bar title="目录管理" />
    <div class="search-top">
      <el-form ref="form" :model="form" :inline="true" class="form">
        <div class="top">
          <el-form-item class="item-input">
            <el-input
              v-model="form.keyWord"
              placeholder="请输入目录名称"
              size="small"
            >
              <i slot="suffix" class="el-input__icon el-icon-search icon-search" @click="search()" />
            </el-input>
          </el-form-item>
          <!-- <el-form-item class="btns">
            <el-button type="primary">完成</el-button>
            <el-button>取消</el-button>
          </el-form-item> -->
        </div>
        <el-form-item class="add">
          <el-button type="primary" size="mini" @click="addCatalogue()">创建目录</el-button>
        </el-form-item>
      </el-form>
    </div>
    <FileLIst v-loading="loading" :file-data="directoryData">
      <div slot="page">
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
    </FileLIst>
    <!-- 弹窗 -->
    <BaseDialog
      title="创建目录"
      :show.sync="dialogVisible"
      width="294px"
      class="dialog"
    >
      <AddCatalogue @close="close" />
    </BaseDialog>
  </div>
</template>

<script>
import FileLIst from './components/fileLIst'
import AddCatalogue from './model/addCatalogue'
import setQueryParams from '@/utils/setQueryParams'
import { mapGetters } from 'vuex'

export default {
  name: 'DirectoryManage',
  components: {
    FileLIst,
    AddCatalogue
  },
  props: {

  },
  data() {
    return {
      loading: false,
      dialogVisible: false,
      form: {
        keyWord: ''
      },
      page: 1,
      limit: 20,
      total: null
    }
  },
  computed: {
    ...mapGetters(['directoryData'])
  },
  watch: {

  },
  created() {
    this.getDirectoryData()
  },
  mounted() {
    this.$bus.$on('resetData', this.updateData)
  },
  beforeDestroy() {
    this.$bus.$off('resetData', this.updateData)
  },
  methods: {
    // 更新数据
    async updateData() {
      this.loading = true
      await this.getDirectoryData()
      this.$store.dispatch('user/getDirInfo')
      this.loading = false
    },
    async getDirectoryData() {
      this.loading = true
      const query = {
        names: this.form.keyWord
      }
      const params = {
        limit: this.limit,
        page: this.page,
        query: setQueryParams(query),
        sortby: 'id',
        order: 'desc'
      }
      await this.$store.dispatch('directoryData/getDierctoryData', params).then((res) => {
        this.loading = false
        this.total = res.count
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.getDirectoryData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getDirectoryData()
    },
    search() {
      this.getDirectoryData()
    },
    addCatalogue() {
      this.dialogVisible = true
    },
    close() {
      this.dialogVisible = false
    }
  }
}
</script>

<style lang="scss">
@import "./index.scss"
</style>
