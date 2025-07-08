<template>
  <div class="">
    <loading v-if="status_loading" />
    <div v-else>
      <videoList :list='list' :total='total' @page='handleCurrentChange' :currentPage='currentPage' />
    </div>

  </div>
</template>

<script>
import videoList from '../../components/video_list'
export default {
  data() {
    return {
      keyword: '',
      page: 1,
      limit: 12,
      total: 0,
      currentPage: 0,
      list: [],
      timeout: null,
      status_loading: true,
      finished_status: true,
      refresh_time: null
    }
  },
  components: { videoList },
  computed: {},
  watch: {
    finished_status: {
      handler(val) {
        if (!val) {
          this.refresh_time = setTimeout(() => {
            this.getSearch()
          }, 60000)
        } else {
          clearTimeout(this.refresh_time)
          this.refresh_time = null
        }
      },
      immediate: true
    }
  },

  methods: {
    getSearch() {
      let params = {
        keyword: this.keyword,
        page: this.page,
        limit: this.limit
      }
      if (this.timeout !== null) {
        clearTimeout(this.timeout)
      }
      this.status_loading = true
      this.finished_status = true

      this.$get('/intelligent-creation/intelligent-video-list', { params: params }).then(res => {
        if (res.data.code == '0000') {
          var data = res.data.data
          let list = data.list
          list.forEach(item => {
            if (item.finished_time) {
              item.finished_time = item.finished_time.substring(5)
            } else {
              this.finished_status = false
            }

            if (item.estimate_finish_time) {
              let current_date = new Date().getTime()
              let date = new Date(item.estimate_finish_time).getTime()
              let time_remaining = date - current_date
              item.finished_time_by_now = time_remaining
            }
          })
          this.list = data.list
          this.total = data.total
          this.currentPage = Number(data.page)
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.status_loading = false
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.currentPage = val
      this.getSearch()
    }
  },

  created() {
    this.getSearch()
  },
  destroyed() {
    clearInterval(this.refresh_time)
    this.refresh_time = null
  },

  mounted() {}
}
</script>