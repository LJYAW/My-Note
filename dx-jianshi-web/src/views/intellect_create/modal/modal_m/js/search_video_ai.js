/*
 * @Author: zzx
 * @Date: 2020-10-27 15:33:14
 * @LastEditTime: 2021-08-09 15:59:15
 * @FilePath: /zhijian_web/src/views/intellect_create/modal_m/js/search_video_ai.js
 */
export const searchVideoAi = {
  data() {
    return {
      search_page: 0,
      search_limit: 20,
      search_tab_index: 0,
      // 有排序 人物 物体 帧搜索
      search_tab: [
        {
          name: '素材库',
          url: '/material-library/material-list'
        }
        // {
        //   name: '实时资讯',
        //   url: '/intelligent-creation/cut-video-search'
        // },
        // {
        //   name: '人物搜索',
        //   url: '/intelligent-creation/video-search-person'
        // },
        // {
        //   name: '物体搜索',
        //   url: '/intelligent-creation/video-search-object'
        // },
        // {
        //   name: '帧搜索',
        //   url: '/intelligent-creation/video-search'
        // }
      ]
    }
  },
  computed: {
    // search_list() {
    //   return this.$parent['search_list']
    // }
  },
  methods: {
    async searchAll(search_key) {
      this.search_key = search_key
      this.search_tab_index = 0
      this.status_loading = true
      this.search_show = 'video'
      this.search_page = 1
      this.is_done = false
      // console.log(this.search_list)

      for (let i = 0; i < this.search_tab.length; i++) {
        let item = this.search_tab[i]
        let arr = await this.getSearchList(item.url, i)
        // this.search_list[i] = arr
        this.$set(this.search_list, i, arr)
        // this.setTabIndex(this.search_list)
        this.status_loading = false
      }
      this.is_done = true
    },
    setTabIndex(list) {
      console.log(this.search_list)

      for (let i = 0; i < list.length; i++) {
        let arr = list[i]
        if (arr.length > 0) {
          this.search_tab_index = i
          break
        }
      }
    },
    getSearchList(url, index) {
      return new Promise((resolve, reject) => {
        var key = this.search_key
        if (!key) {
          return
        }

        // var params = {
        //   q: key,
        //   page: this.search_page,
        //   limit: this.search_limit
        // }
        var params = {
          keywords: key,
          page: this.search_page,
          limit: this.search_limit
        }

        this.$get(url, { params: params }).then(res => {
          if (res.data.code == '0000') {
            // resolve(res.data.data.items)
            resolve(res.data.data.list)
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
      })
    }
  }
}
