<!--
 * @Author: your name
 * @Date: 2021-07-02 16:13:11
 * @LastEditTime: 2021-07-16 16:27:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/src/views/channel/index.vue
-->
<template>
  <div class="channel-wrap">
    <p class="title">频道管理</p>
    <div class="search-wrap">
      <base-btn class="export-btn btn" @click="exportLive">导入直播流</base-btn>
      <el-input v-model="keywords" @keyup.enter.native="search">
        <template slot="prepend">频道名称</template>
      </el-input>
      <base-btn class="search-btn btn" @click="search">搜索</base-btn>
    </div>
    <ChannelTable :table-data="channelData" />
    <div class="pageList">
      <base-pag
        :total="channelCount"
        :limit="limit"
        :page="page"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      />
    </div>
  </div>
</template>

<script>
import ChannelTable from './pages/channelTable.vue'
import setQueryParams from '@/utils/setQueryParams'
import { mapGetters } from 'vuex'
export default {
  components: {
    ChannelTable
  },
  props: {

  },
  data() {
    return {
      keywords: '',
      page: 1,
      limit: 10,
      tableData: []
    }
  },
  computed: {
    ...mapGetters(['channelData', 'channelCount'])
  },
  watch: {

  },
  created() {
    this.getData()
  },
  mounted() {

  },
  methods: {
    exportLive() {
      this.$router.push({
        path: 'export-live'
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.getData()
    },
    handleSizeChange(val) {
      this.limit = val
      this.getData()
    },
    async getData() {
      const query = {
        channelsname__icontains: this.keywords
      }
      const params = {
        limit: this.limit,
        page: this.page,
        query: setQueryParams(query)
      }
      await this.$store.dispatch('channel/getChannelData', params)
      // const { err, data } = await this.$get('/v1/channels', params)
      // if (err) {
      //   this.$message.error(err.msg)
      //   return
      // }
      // this.tableData = data.data
      // this.total = data.count
    },
    search() {
      this.page = 1
      this.getData()
    }
  }
}
</script>

<style scoped lang="scss">
.channel-wrap {
  background: #fff;
  padding: 30px;

  .title {
    font-size: 32px;
    line-height: 32px;
    font-weight: 600;
  }

  .search-wrap {
    margin: 28px 0;
    display: flex;

    .btn {
      font-size: 24px;
      border-radius: 4px;
    }

    .export-btn {
      padding: 17px 83px;
      margin-right: 40px;
    }

    .search-btn {
      padding: 18px 51px;
    }

    ::v-deep  .el-input-group {
      width: 600px;

      .el-input-group__prepend {
        font-size: 18px;
        padding: 18px 44px 17px 29px;
        background: #eaf1fe;
        border: none;
        color: #888887;
      }

      .el-input__inner {
        height: 60px;
      }
    }
  }
}
</style>
