/*
 * @Author: zzx
 * @Date: 2020-10-27 15:33:14
 * @LastEditTime: 2020-12-05 16:44:38
 * @FilePath: /zhijian_web/src/views/intellect_create/modal_m/js/search_img_ai.js
 */
import { imgUrlTofile, dataURLtoFile } from '@/utils/canvas'
const PAGE = 1
const LIMIT = 100

export const searchImgAi = {
  data() {
    return {
      // is_done: true,
      status_loading: true,
      similar_img_list: [],
      search_tab_img: [
        {
          name: '人脸图片',
          key: 'face-query',
        },
        {
          name: '物体图片',
          key: 'object-query',
        },
        // {
        //   name: '相似图片',
        //   key: 'query'
        // }
      ],
      // search_img_list: []
    }
  },
  methods: {
    // 将图片URL 转成 File
    urlByfile(url) {
      // 搜索方式 query,object-query,face-query
      this.similar_img_list = []
      this.search_img_list = []
      this.search_img_loading = true
      this.is_done = false

      imgUrlTofile(url)
        .then(dataURL => {
          let file = dataURLtoFile(dataURL)
          this.asyncGetImgList(file).then(res => {
            let list = []
            this.search_img_list.forEach(item => {
              item.forEach(arr => {
                list.push(arr.image_url)
              })
            })
            this.search_img_loading = false

            this.is_done = true
            this.similar_img_list = list.splice(0, 5)
            // return this.similar_img_list
          })
        })
        .catch(err => {
          this.$alertMsg(err.message)
        })
    },
    async asyncGetImgList(file) {
      let arr = this.search_tab_img
      this.status_loading = true

      for (let i = 0; i < arr.length; i++) {
        const type = arr[i].key
        const name = arr[i].name

        let data = await this.getSearchImgByFile(file, type)
        this.search_img_list.push(data.items)

        for (let i = 0; i < this.search_img_list.length; i++) {
          let arr = this.search_img_list[i]
          if (arr.length > 0) {
            this.search_tab_index = i
            break
          }
        }
        this.status_loading = false
      }
    },
    // 获取搜索图片
    getSearchImgByFile(file, type) {
      return new Promise((resolve, reject) => {
        var formData = new FormData()
        formData.append('page', PAGE)
        formData.append('limit', LIMIT)
        formData.append('photo', file)
        formData.append('type', type)

        this.$post('/intelligent-creation/image-search-by-file', formData).then(res => {
          resolve(res.data.data)
        })
      })
    },
  },
}
