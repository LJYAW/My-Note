<!--
 * @Author: your name
 * @Date: 2021-08-03 14:40:19
 * @LastEditTime: 2021-09-16 10:14:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-mange/index.vue
-->
<template>
  <div class="video-manage">
    <title-bar title="视频管理" />
    <div class="operation">
      <div class="search">
        <el-input
          v-model="searchWord"
          placeholder=""
          size="normal"
          clearable
          suffix-icon="el-icon-search"
          @change="search()"
        />

        <Select :option="option" :default-value="selectVal" class="select" @onChange="handlerSelect" />

        <div class="tab-bar">
          <TabBar
            v-for="(str,index) in tabList"
            :key="index"
            :val="str"
            :class="index === tabIndex?'active':''"
            @click.native="tabChange(index)"
          />
        </div>
      </div>

      <div v-show="!batch" class="btn">
        <el-button v-show="selectVal.name !='全部目录'" type="primary" size="small" @click="handlebatchVca(true)">批量分析</el-button>
        <el-button v-show="selectVal.name !='全部目录'" type="danger" size="small" @click="handlebatchDel">批量删除</el-button>
        <el-button type="primary" size="small" @click="pathLink">
          上传视频
        </el-button>
      </div>
      <div v-show="batch" class="btn">
        <el-button type="primary" size="small" @click="chooseAll">全部选择</el-button>
        <el-button v-if="vca" type="primary" size="small" @click="goVca">开始分析</el-button>
        <el-button v-if="del" type="danger" size="small" @click="confirmDelete">删除</el-button>
        <el-button type="info" size="small" @click="handlCancel">取消</el-button>
      </div>
    </div>

    <div ref="wrapper" class="list">
      <VideoList v-loading="loading" :video-data="videoData" />
      <div v-if="page>=pagesize&&page>1" class="noMore">
        {{ page>=pagesize?' 没有更多数据了~':'加载中...' }}
      </div>
    </div>
  </div>
</template>

<script>
import TabBar from '@/views/home/components/select/tabItem'
import Select from '@/components/VideoSelect/selectItem.vue'

import VideoList from './components/videoList'

import throttle from '@/utils/debounce.js'
import setQueryParams from '@/utils/setQueryParams'

export default {

  components: {
    TabBar,
    Select,
    VideoList
  },
  props: {

  },
  data() {
    return {
      dom: null,
      searchWord: '',

      selectVal: {
        name: '全部目录',
        id: 0
      },

      vca: false,
      del: false,

      dir_id: 0,

      tabList: [
        { name: '全部视频' },
        { name: '已分析' },
        { name: '未分析' },
        { name: '分析中' }
      ],
      tabIndex: 0,
      batch: false,

      videoData: [],
      loading: true,

      page: 1,
      limit: 24,
      pagesize: 1
    }
  },
  computed: {
    option() {
      const arr = [{ name: '全部目录', id: 0 }]
      this.$store.state.user.dirInfo.forEach(item => {
        const obj = {}
        obj.name = item.dir_name
        obj.id = item.dir_id
        arr.push(obj)
      })
      return arr
    }
  },
  watch: {

  },
  beforeDestroy() {

  },
  created() {
    this.selectVal = {
      name: this.$route.query.names || '全部目录',
      id: Number(this.$route.query.id) || 0
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.dom = this.$refs.wrapper
      this.dom.addEventListener('scroll', this.betterScroll, true)
    })

    this.getVideoList()
  },
  methods: {
    search() {
      this.page = 1
      this.videoData = []
      this.getVideoList()
    },
    handlerSelect(e) {
      this.selectVal = {
        name: e.name || '全部目录',
        id: e.id || 0
      }
      this.dir_id = e.id
      this.page = 1
      this.videoData = []
      this.getVideoList()
    },
    // 批量操作
    handlebatchVca(flag) {
      this.$bus.$emit('batchFlag', true, flag)
      this.batch = true
      this.vca = true
    },
    handlebatchDel(flag) {
      this.$bus.$emit('batchFlag', true)
      this.batch = true
      this.del = true
    },
    handlCancel() {
      this.$bus.$emit('batchFlag', false)
      this.batch = false
      this.vca = false
      this.del = false
      const arr = this.$store.state.videoStatus.batchVideo
      arr.splice(0)
      this.$store.commit('videoStatus/BATCH_VIDEO_CHANGE', arr)
    },
    chooseAll() {
      const arr = this.$store.state.videoStatus.batchVideo
      this.videoData.forEach(ele => {
        if (ele.vca_status === 2 && this.vca) {
          !arr.includes(ele.uuid) && arr.push(ele.uuid)
        } else if (!this.vca) {
          !arr.includes(ele.uuid) && arr.push(ele.uuid)
        }
      })
      this.$store.commit('videoStatus/BATCH_VIDEO_CHANGE', arr)
    },
    pathLink() {
      this.$router.push({
        path: '/manage-center/videos-manage/add-videos'
      })
    },
    // 批量操作 End
    async goVca() {
      const params = this.$store.state.videoStatus.batchVideo
      if (!params.length) {
        this.$message.warning('请选择视频后操作')
        return
      }
      const { err, res } = await this.$post(`/videos/vca`, params, {
        DirId: this.dir_id
      })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.videoData = []
      this.page = 1
      this.getVideoList()
      this.$message.success('开始分析成功')
      this.handlCancel()
    },
    // 确认删除
    confirmDelete() {
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.handleDelete()
      }).catch(() => {
      })
    },
    // 全部删除队列
    handleDelete() {
      const pArr = []
      const arr = this.$store.state.videoStatus.batchVideo
      if (!arr.length) {
        this.$message.warning('请选择视频后操作')
        return
      }
      for (let i = 0; i < arr.length; i++) {
        const p = this.delete(arr[i])
        pArr.push(p)
      }
      Promise.all(pArr).then((res) => {
        if (res.includes(false)) {
          return
        }
        this.page = 1
        this.videoData = []
        this.getVideoList()
        this.$message.success('删除成功')
        this.handlCancel()
      })
    },
    // 删除
    async delete(id) {
      const { err, res } = await this.$deleteRun(`/videos/${id}`, {
        DirId: this.dir_id
      })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return false
      }
    },

    tabChange(index) {
      this.tabIndex = index
      const val = this.tabList[index]
      // this.$emit('onChange', { tab: val })
      this.page = 1
      this.videoData = []
      this.getVideoList()
    },

    betterScroll() {
      var scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      // 变量windowHeight是可视区的高度
      var windowHeight = document.documentElement.clientHeight || document.body.clientHeight
      // 变量scrollHeight是滚动条的总高度
      var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight
      // 把距离顶部的距离加上可视区域的高度 等于或者大于滚动条的总高度就是到达底部
      if (scrollTop + windowHeight === scrollHeight) {
        this.pagesize > this.page && this.getVideoList(this.page++)
      }
    },

    // 获取视频列表
    async getVideoList() {
      this.handlCancel()
      const query = {
        titles__contains: this.searchWord,
        dir_id: this.selectVal.id,
        vca_status: this.tabIndex
      }
      const params = {
        query: setQueryParams(query),
        page: this.page,
        limit: this.limit
      }
      // 整理参数发送请求
      this.loading = true
      const { err, res } = await this.$get('/videos', params)
      this.loading = false
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.videoData = this.videoData.concat(res.data)
      this.count = res.count
      this.pagesize = res.pagesize
    }

  }
}
</script>

<style scoped lang="scss">
.video-manage {
  // max-width: 1440px;
  // margin: 0 auto;
  display: flex;
  flex-direction: column;
  height: 100%;

  .operation {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: nowrap;
    margin-top: 20px;

    .search {
      display: flex;
      flex: 1;

      .tab-bar {
        display: flex;
        justify-content: flex-start;
        border-radius: 4px;
        overflow: hidden;
        margin-left: 30px;

        .active {
          font-weight: 600;
          color: #5675e8
        }

        ::v-deep.tab-item {
          position: relative;
          z-index: 1;

          &::after {
            content: "";
            position: absolute;
            right: 0;
            top: 8px;
            height: 15px;
            width: 1px;
            background: #d8d8d8;
          }

          &:last-child {

            &::after {
              display: none;
            }
          }

        }
      }
    }

  }

  .list {
    flex: 1;
    height: 0;
    padding-top: 20px;
    width: 100%;
    padding-bottom: 30px;
    overflow: hidden;
    overflow-y: auto;
    padding-bottom: 50px;

    .noMore {
      margin-top: 30px;
      font-size: 10px;
      text-align: center;
    }
  }
}

.el-input {
  width: auto;
  margin-right: 25px;
  max-width: 365px;
  width: 30%;

  ::v-deep.el-input__inner {
    width: 100%;
    height: 30px;
    line-height: 30px;
  }

  ::v-deep.el-input__icon {
    line-height: 30px;
  }

}

.select {

  ::v-deep.el-select,
  ::v-deep.el-select>.el-input ,
  ::v-deep.el-select .el-input__inner {
    width: auto;
    max-width: 120px;
    border-radius: 4px;
  }
}

</style>
