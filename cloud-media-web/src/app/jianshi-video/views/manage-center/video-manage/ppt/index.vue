<!--
 * @Author: your name
 * @Date: 2021-10-21 18:49:32
 * @LastEditTime: 2021-10-25 16:22:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/manage-center/video-manage/ppt/index.vue
-->
<template>
  <div v-loading="loading">
    <ul v-if="initData.length" v-infinite-scroll="load" class="infinite-list transition-wrap">
      <base-card
        v-for="(item) in initData"
        :key="item.id"
        :show-mark="false"
        class="video-item infinite-list-item"
        @click.native="routeTo(item)"
      >
        <div slot="img">
          <base-image v-if="item.status==1" :src="getSrc(item.pic_bos_key)" />
          <img v-else src="@/assets/images/jianshi/ppt_create.png" alt="">
          <div class="btn-wrap">
            <p><span v-if="item.duration">视频时长 {{ item.duration|timesToHMS }}</span></p>
            <svg-icon icon-class="delete" @click.stop.native="deletePPT(item.id)" />
          </div>
        </div>
        <div slot="des">
          <p class="desc">{{ item.titles }}</p>
          <div class="video-status">
            <p class="date">{{ item.create_time }}</p>
            <p :class="setStyleClass(item.status)">{{ statusData[item.status] }}</p>
          </div>
        </div>
      </base-card>
    </ul>
    <svg-icon v-else icon-class="empty-img" class="empty-icon" />
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      initData: [],
      page: 1,
      limit: 20,
      count: 0,
      loading: false,
      statusData: { 0: '待转码', 1: '成功', '-1': '失败', '-2': '转码中' }
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getPPTData()
  },
  mounted() {

  },
  methods: {
    routeTo(item) {
      if (item.status !== 1) return
      const { href } = this.$router.resolve({
        path: '/jianshi-video/script-create',
        query: {
          pptId: item.id
        }
      })
      window.open(href, '_blank')
    },
    load() {
      if (this.initData.length < this.count) {
        this.page++
        this.getPPTData()
      }
    },
    async getPPTData() {
      const { err, res } = await this.$get('/transppt')
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.initData = this.initData.concat(res.data)
    },
    setStyleClass(status) {
      switch (status) {
        case -2:
          return 'is-building'
        case -1:
          return 'build-fail'
        case 1:
          return 'build-success'
        default:
          return 'to-build'
      }
    },
    getSrc(srcKey) {
      const keyData = srcKey.split(',')[0]
      return keyData.split('|||')[0] || ''
    },
    deletePPT(id) {
      this.$confirm('是否确认删除视频?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmDeletePPT(id)
      }).catch(() => {

      })
    },
    async confirmDeletePPT(id) {
      const { err, res } = await this.$deleteRun(`/transppt/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.resetData()
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      }
    },
    resetData() {
      this.page = 1
      this.initData = []
      this.getPPTData()
    }
  }
}
</script>

<style scoped lang="scss">
.video-item {
  cursor : pointer;
}

</style>
