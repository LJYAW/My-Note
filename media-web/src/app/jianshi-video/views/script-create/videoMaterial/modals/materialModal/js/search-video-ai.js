/*
 * @Author: zzx
 * @Date: 2020-10-27 15:33:14
 * @LastEditTime: 2021-09-15 14:39:09
 * @FilePath: /zhijian_web/src/views/intellect_create/modal_m/js/search_video_ai.js
 */
export const searchVideoAi = {
  data() {
    return {
      search_page: 1,
      search_limit: 20
    }
  },
  computed: {

  },
  methods: {
    async searchAll() {
      this.status_loading = true
      this.search_page = 1
      this.search_list = await this.getSearchList(this.search_url)
      console.log('sjbsjhvb')
      this.status_loading = false
    },
    async getSearchList(url) {
      var params = {
        keywords: this.searchKey,
        page: this.search_page,
        limit: this.search_limit
      }
      const { err, res } = await this.$get(url, { params: params })
      if (err) {
        this.$message.error(err.msg)
        return
      }
      // this
      console.log(err, res)
      // return new Promise((resolve, reject) => {
      //   var params = {
      //     keywords: this.searchKey,
      //     page: this.search_page,
      //     limit: this.search_limit
      //   }
      //   this.$get(url, { params: params }).then(res => {
      //     console.log(res)
      //     if (res.data.code === '0000') {
      //       resolve(res.data.data.list)
      //     } else {
      //       this.$alertMsg(res.data.msg)
      //     }
      //   })
      // })
    }
  }
}
