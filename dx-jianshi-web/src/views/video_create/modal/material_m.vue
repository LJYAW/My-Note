<template>
  <div class="material">
    <model
      :id="'materialM'"
      ref="materialM"
      @close="close"
      :class="['model-ll',{'video_editor_show d-block' : video_editor_show}]">
      <div slot="title">
        <div class="d-flex">
          <a
            @click="video_editor_show = false"
            v-if="video_editor_show"
            class="mr-20px fz-14px hove-c">
            <i class="iconfont iconicon d-inline-block" style="transform: rotateY(180deg)"></i>返回
          </a>
          <a v-else-if="search_show" @click="search_show = false" class="mr-20px fz-14px hove-c">
            <i class="iconfont iconicon d-inline-block" style="transform: rotateY(180deg)"></i>返回
          </a>
          <p class="modal-title fz-14px">浏览视频内容，从中剪选所需镜头</p>
        </div>

        <quesTion
          v-if="video_editor_show"
          video_title="视频剪辑帮助"
          video_url="https://cdn-magic.weijian.video/storage/mnt/online/intelligent_writing/system/FinalVideo_1594183093.200344.mp4" />
      </div>
      <div slot="body">
        <div v-if="!video_editor_show">
          <div class="pt-26px w-50 m-auto search-header">
            <!-- <upload
              file_type=" image/jpeg"
              class="upload"
              ref="upload"
              button_name
              @getFileUrl="getFileImg"
            >
              <i class="iconfont iconcamera fz-28px cp hove-c"></i>
            </upload>-->

            <el-input v-model="search_key" @keyup.enter.native="searchAll" placeholder="输入关键词查找素材">
              <el-button
                slot="append"
                @click="searchAll"
                type="primary"
                icon="iconfont iconsearch fz-16px fc-white"></el-button>
            </el-input>

            <div class="fz-12px mt-10px d-flex mb-20px">
              <div v-if="keyword_lsit.length > 0">
                <div class="d-flex flex-wrap">
                  <span style="min-width: 80px">推荐关键字：</span>
                  <a
                    v-for="(key,j) in keyword_lsit"
                    @click="searchKeyWord(key.item)"
                    class="mr-10px mb-6px hove-c fz-12px"
                    :key="j">{{key.item}}</a>
                </div>
              </div>
            </div>
          </div>

          <!-- <loading v-if="status_loading" /> -->
          <div style="min-height: 400px">
            <!-- 原文素材 -->
            <div v-if="!search_show && src_list.image_list" class="source-wrap border-top">
              <div v-if=" src_list.image_list.length > 0">
                <p class="my-12px">原文图片</p>

                <div class="list">
                  <div class="imgas-list d-flex">
                    <div
                      :class="['box mr-10px d-flex justify-content-center source-img-wrap',{'active': image_index == index},{'imgError': item.imgError}]"
                      @click="addItem(item,index,1)"
                      v-for="(item,index) in this.src_list.image_list"
                      :key="index">
                      <img :src="item.proxy_url" @error="imgError(item,index)" />

                      <label class="el-upload-list__item-status-label">
                        <i class="iconfont iconqueding d-block"></i>
                      </label>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 相似图片 -->
              <!-- <div class="list ai-img-wrap" v-if="src_list.image_list.length > 0">
                <div class="d-flex justify-content-between">
                  <p class="mb-12px mr-auto">相似图片</p>
                  <el-button type="text" class="more-btn" @click="getMoreAiImg">更多</el-button>
                </div>

                <div class="text-loading" v-if="search_img_loading" style="min-height: 100px">
                  <img width="50" height="35" src="@/assets/images/text_loading.svg" />
                </div>

                <div v-else class="video-list d-flex" style="min-height: 100px">
                  <p class="fc-999" v-if="!search_img_list_temp.length > 0">未找到相似图片</p>

                  <div
                    v-else
                    v-for="(image_url,j) in this.search_img_list_temp"
                    :key="j"
                    class="d-flex"
                    :class="['box mr-10px d-flex justify-content-center source-img-wrap',{'active': ai_image_index == j}]"
                    @click="addItem(image_url, j ,3)"
                  >
                    <img :src="image_url" />

                    <label class="el-upload-list__item-status-label">
                      <i class="iconfont iconqueding d-block"></i>
                    </label>
                  </div>
                </div>
              </div>-->
            </div>

            <!-- 搜索素材 -->
            <div v-else-if="search_show == 'video'">
              <div class="d-flex search-nav">
                <ul class="mini-nav d-flex justify-content-start" v-if="!status_loading">
                  <li
                    v-for="(link,j) in search_tab"
                    :key="j"
                    :class="[{'active' : j == search_tab_index}]"
                    @click="searchTab(link,j)">
                    <a v-if="search_list[j].length > 0">{{link.name}}</a>
                  </li>
                </ul>
                <div class="text-loading" v-if="!is_done">
                  <img width="50" height="35" src="@/assets/images/text_loading.svg" />
                </div>
              </div>

              <scroll class="wrapper search-list" ref="scroll" @scrollDown="scrollDown">
                <div style="min-height: 600px" v-if="!is_done"></div>

                <div class="content" v-else>
                  <div
                    class="text-center w-100 py-100px"
                    v-if="search_list[search_tab_index].length < 1 ">
                    <div>
                      <img src="@/assets/images/is_empty.png" style="264" height="205" />
                      <p class="fz-12px fc-999 mt-25px">未找到您所搜索的内容，换个词试试？</p>
                    </div>
                  </div>
                  <el-row :gutter="20" style="margin-left: 10px">
                    <!-- <keep-alive> -->
                    <el-col
                      :span="5"
                      v-for="(video,v) in search_list[search_tab_index]"
                      class="video-box"
                      style="padding: 0;"
                      :key="v"
                      @click.native="videoEditorShow(video)"
                      @mouseleave.self.native="rmVideo($event)"
                      @mouseenter.native="getVideo($event,video,v)">
                      <el-tooltip
                        v-if="video_show_index !== v"
                        :content="video.title + video.publish_time"
                        :visible-arrow="true"
                        placement="bottom-end"
                        :offset="100">
                        <div class="img-wrap">
                          <img :src="video.image" />
                        </div>
                      </el-tooltip>

                      <videoPalyer
                        v-else-if="video_show_index == v"
                        :video_options="video_options"
                        :start_time_s="start_time_s"
                        :controls_show="false"
                        :crossOrigin="false" />

                      <div class="time-box">
                        <span v-if="video.time_start">{{video.time_start *1000 | msToTime}} /</span>
                        <span v-if="video.duration_str">{{video.duration_str}}</span>
                      </div>
                    </el-col>
                  </el-row>
                  <!-- </keep-alive> -->
                </div>
              </scroll>
            </div>

            <!-- 搜索图片素材 -->
            <div v-else-if="search_show == 'img'">
              <div class="d-flex search-nav">
                <ul class="mini-nav d-flex justify-content-start" v-if="!status_loading">
                  <li
                    v-for="(link,j) in search_tab_img"
                    :key="j"
                    :class="[{'active' : j == search_tab_index}]"
                    @click="searchImgTab(link,j)">
                    <a v-if="search_img_list[j] && search_img_list[j].length > 0">{{link.name}}</a>
                  </li>
                </ul>
                <div class="text-loading" v-if="!is_done">
                  <img width="50" height="35" src="@/assets/images/text_loading.svg" />
                </div>
              </div>

              <scroll
                v-if="is_done"
                class="wrapper search-list img-list"
                ref="scroll"
                style="min-height: 400px"
                @scrollDown="scrollDown">
                <div class="content">
                  <div class="text-center w-100 py-100px" v-if="!search_img_list[search_tab_index]">
                    <img src="@/assets/images/is_empty.png" style="264" height="205" />
                    <p class="fz-12px fc-999 mt-25px">未找到您所搜索的内容，换个词试试？</p>
                  </div>
                  <el-row :gutter="20" style="margin-left: 10px">
                    <el-col
                      @click.native="addSearchItem(img.image_url,v)"
                      :span="5"
                      v-for="(img,v) in search_img_list[search_tab_index]"
                      :class="['video-box',{ 'isactive': select_img_index == v}]"
                      style="padding: 0;"
                      :key="v">
                      <div class="card-img">
                        <img class="w-100 h-100" :src="img.image_url" />
                        <label class="el-upload-list__item-status-label">
                          <i class="iconfont iconqueding"></i>
                        </label>
                      </div>
                    </el-col>
                  </el-row>

                  <div v-if="search_img_list[search_tab_index] && is_done">
                    <div
                      class="text-center w-100 py-100px"
                      v-if="search_img_list[search_tab_index].length < 1">
                      <img src="@/assets/images/is_empty.png" style="264" height="205" />
                      <p class="fz-12px fc-999 mt-25px">未找到您所搜索的内容，换个词试试？</p>
                    </div>
                  </div>
                </div>
              </scroll>
              <div class="text-center py-20px" v-if="is_done">
                <el-button
                  round
                  size="mini"
                  type="primary"
                  style="width: 100px"
                  @click="submitAddImg">完成</el-button>
              </div>
            </div>

            <!-- 我上传的 -->
            <div v-if="!search_show">
              <div class="d-flex align-items-center my-12px">
                <p class>本地素材</p>
                <upload
                  button_name="上传本地素材"
                  file_type="image/png, image/jpeg, image/gif, image/jpg, video/mp4"
                  class="ml-10px"
                  @getFileUrl="getFileUrl" />
              </div>
              <scroll class="list" scrollX @scrollXDown="scrollXDown">
                <div class="my-list d-flex">
                  <div
                    :class="['box mr-10px',{'active': my_image_index == index}]"
                    @click="addItem(item,index,2)"
                    v-for="(item,index) in img_video_list"
                    :key="index">
                    <div v-if="item.type == '上传中'" style="margin-top: 37px" class="px-10px">
                      <!-- <p>上传中...</p> -->
                      <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
                    </div>

                    <div v-else class="h-100">
                      <img :src="item.resource_url" v-if="item.type == 'image'" />
                      <video
                        v-if="item.type == 'video'"
                        controls="controls"
                        style="width:100%;height:100%"
                        :src="item.resource_url"></video>

                      <label :class="['el-upload-list__item-status-label']">
                        <i class="iconfont iconqueding d-block"></i>
                      </label>

                      <a class="delete-icon" @click="deleteRsource(item,index)">
                        <i class="iconfont fc-white iconshanchuicon hove-c"></i>
                      </a>
                    </div>
                  </div>
                </div>
              </scroll>
              <div class="text-center mb-30px">
                <el-button
                  type="primary"
                  round
                  style="width: 100px"
                  size="small"
                  @click="submit()">完成</el-button>
              </div>
            </div>
          </div>
        </div>

        <!-- 视频编辑 -->
        <div v-else class="video-wrap">
          <videoEditor />
        </div>
      </div>
    </model>
  </div>
</template>

<script>
import myElupload from '@/components/elupload'
import videoEditor from '../../video_editor/index'
import quesTion from '@/components/question'
import { imgUrlTofile, dataURLtoFile } from '@/utils/canvas'

export default {
  props: ['src_list', 'edtor_total_ms', 'keyword_lsit'],
  data() {
    return {
      complete: 0, // 文件上传进度
      is_done: true,
      status_loading: true,
      pulldown: true,
      add_loading: false,
      search_key: '',
      dialogImageUrl: '',
      dialogVisible: false,
      data: {},
      img_video_list: [],
      search_tab_index: -1,
      search_list: [],
      search_show: '',
      search_page: 1,
      search_limit: 20,
      image_index: null,
      select_item: {},
      my_image_index: null,
      ai_image_index: null,
      search_img_loading: true,
      video_options: {
        width: '175',
        height: '94',
        poster: '',
        autoplay: true,
        src: ''
      },
      select_img_index: -1,
      search_img_list: [],
      search_img_list_temp: [],
      // 有排序 人物 物体 帧搜索
      search_tab: [
        {
          name: '实时资讯',
          url: '/intelligent-creation/cut-video-search'
        },
        {
          name: '人物搜索',
          url: '/intelligent-creation/video-search-person'
        },
        {
          name: '物体搜索',
          url: '/intelligent-creation/video-search-object'
        },
        {
          name: '帧搜索',
          url: '/intelligent-creation/video-search'
        }
      ],
      search_tab_img: [
        {
          name: '人脸图片',
          key: 'face-query'
        },
        {
          name: '物体图片',
          key: 'object-query'
        },
        {
          name: '相似图片',
          key: 'query'
        }
      ],
      onelyUUID: '',
      video_show: false,
      video_show_index: null,
      video_editor_show: false,
      time: null,
      page: 1,
      limit: 6
    }
  },
  components: { myElupload, videoEditor, quesTion },
  computed: {
    per_px_ms() {
      return this.$store.state.videoM.per_px_ms
    },
    edtortotalms() {
      return this.$store.state.edtor_total_ms
    }
  },
  watch: {},
  methods: {
    submitAddImg() {
      this.submit()
    },
    addSearchItem(url, index) {
      this.select_img_index = index
      this.select_item = {
        resource_url: url,
        is_ai_image: true,
        type: 'image'
      }
    },
    async getFileImg(url, file) {
      this.search_show = 'img'
      this.search_img_list = []
      this.keyword = ''
      this.is_done = false
      await this.asyncGetImgList(file)
      this.$refs.upload.clearFile()
      this.is_done = true
    },
    getMoreAiImg() {
      this.search_show = 'img'
    },
    // 将图片URL 转成 File
    urlByfile(url) {
      // 搜索方式 query,object-query,face-query
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
            this.search_img_list_temp = list.splice(0, 5)
            this.search_img_loading = false
            this.is_done = true
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
    searchImgTab(link, index) {
      this.search_tab_index = index
      this.$refs.scroll.scrollTop()
    },
    // 获取搜索图片
    getSearchImgByFile(file, type) {
      return new Promise((resolve, reject) => {
        var formData = new FormData()
        formData.append('page', 1)
        formData.append('limit', 100)
        formData.append('photo', file)
        formData.append('type', type)

        this.$post('/intelligent-creation/image-search-by-file', formData).then(res => {
          console.log('getSearchImgByFile -> res')
          resolve(res.data.data)
        })
      })
    },
    imgError(item, index) {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/img_error.svg')
      img.src = img_error_url
      img.onerror = null // 防止闪图
      this.$set(this.src_list.image_list[index], 'imgError', true)
    },
    close() {
      this.$emit('Modalclose')
      this.search_show = null
      this.$store.commit('setCutTrackList', [])
    },
    getFileUrl(url, file) {
      var fileType = file.type

      var type = fileType.split('/')[0]

      var obj = {
        type: type,
        url: url
      }
      // this.img_video_list.unshift(obj)
      this.uploadMyFile(obj, file)
    },
    uploadMyFile(obj, file) {
      this.complete = 0
      var formData = new FormData()
      formData.append('type', obj.type)
      formData.append('resource', file)
      formData.append('duration', '123')

      this.img_video_list.unshift({
        type: '上传中'
      })

      this.$axios
        .post('/intelligent-creation/upload-user-resource', formData, {
          onUploadProgress: progressEvent => {
            this.complete = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          }
        })
        .then(res => {
          if (res.data.code === '0000') {
            this.img_video_list = []
            this.page = 1
            this.getMyFileList()
          } else {
            this.$alertMsg('上传失败，请重新上传')
          }
        })
        .catch(err => {
          this.$alertMsg('上传失败，请重新上传')
          console.error(err)
        })
    },
    // 获取用户本地素材列表
    getMyFileList() {
      this.status_loading = true

      this.$get('/intelligent-creation/user-resource-list', { params: { page: this.page, limit: this.limit } }).then(res => {
        if (res.data.code === '0000') {
          this.img_video_list = this.img_video_list.concat(res.data.data.list)
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.status_loading = false
      })
    },
    searchTab(item, index) {
      this.search_page = 1
      this.search_tab_index = index
      this.$refs.scroll.scrollTop()
    },
    getSearchList(url, index) {
      return new Promise((resolve, reject) => {
        var key = this.search_key
        if (!key) {
          return
        }

        var params = {
          q: key,
          page: this.search_page,
          limit: this.search_limit
        }

        this.$get(url, { params: params }).then(res => {
          if (res.data.code == '0000') {
            resolve(res.data.data.items)
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
      })
    },
    async searchAll() {
      if (!this.search_key) {
        this.$alertMsg('请输入关键词')
        return
      }
      this.search_tab_index = -1
      this.status_loading = true
      this.search_show = 'video'
      this.search_page = 1
      this.is_done = false

      for (let i = 0; i < this.search_tab.length; i++) {
        let item = this.search_tab[i]
        let arr = await this.getSearchList(item.url, i)
        // this.search_list[i] = arr
        this.$set(this.search_list, i, arr)
        this.setTabIndex(this.search_list)
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
      // console.log('setTabIndex -> this.search_tab_index', this.search_tab_index)
    },
    getVideo(e, item, index) {
      this.time = setTimeout(() => {
        this.video_show = true
        this.video_show_index = index

        this.video_options = {
          width: '175',
          height: '96',
          poster: item.image,
          autoplay: true,
          src: item.video_url
        }
        this.start_time_s = item.time_start || 0
        this.onelyUUID = item.uuid
      }, 2000)
    },
    rmVideo(e) {
      this.onelyUUID = ''
      this.video_options.src = ''
      this.start_time_s = 0
      this.video_show = false
      this.video_show_index = null
      clearTimeout(this.time)
      this.time = null
    },
    videoEditorShow(item, e) {
      var duration = item.duration_ms

      this.video_editor_show = true

      let left = item.time_start * (1000 / this.per_px_ms)

      let video_item = {
        type: 'video',
        video_url: item.video_url,
        duration: duration,
        uuid: item.uuid,
        start_time: item.time_start,
        end_time: duration,
        preview_img: item.photo,
        video_hd_str: item.video_hd_str,
        video_origin: item.video_origin
      }

      var arr = [video_item]
      this.$store.commit('setCutTrackList', arr)
    },
    addItem(item, index, type) {
      console.log('addItem -> item', item)
      if (item.imgError || item.type == '上传中') {
        return
      }

      this.image_index = null
      this.my_image_index = null
      this.ai_image_index = null

      // 原文图片才搜索
      if (item.proxy_url) {
        this.urlByfile(item.proxy_url)
      }

      // 选择原文图片
      if (type == 1) {
        this.image_index = index
        this.select_item = {
          resource_url: this.src_list.image_list[index].proxy_url,
          origin_url: this.src_list.image_list[index].origin_url,
          type: 'image'
        }
      }
      // 选择用户素材
      if (type == 2) {
        this.my_image_index = index
        this.select_item = this.img_video_list[index]
      }
      // 选择 相似的素材
      if (type == 3) {
        this.ai_image_index = index
        this.select_item = {
          resource_url: item,
          is_ai_image: true,
          type: 'image'
        }
      }
    },
    submit() {
      if (this.select_item.type) {
        console.log(this.select_item)

        this.$emit('addItem', this.select_item)
      }
      this.$refs.materialM.close()
    },
    // 删除本地素材
    deleteRsource(item) {
      this.$deleteRun('/intelligent-creation/delete-user-resource', { data: { id: item.id } }).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg('删除成功')
          this.page = 1
          this.img_video_list = []
          this.getMyFileList()
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    // 关键词搜索
    searchKeyWord(key) {
      this.search_key = key
      this.searchAll()
    },
    // 滚动加载
    scrollDown() {
      let index = this.search_tab_index
      let url = this.search_tab[index].url
      this.search_page++

      this.getSearchList(url, index).then(res => {
        let arr = this.search_list[index].concat(res)
        this.$set(this.search_list, index, arr)
      })
    },
    // 原文图片加载
    scrollXDown() {
      console.log('scrollXDown -> scrollXDown')
      this.page++
      this.getMyFileList()
    }
  },
  created() {
    this.getMyFileList()
    this.search_tab.forEach(() => {
      this.search_list.push([])
    })
  },
  mounted() {
    let img_list = this.src_list.image_list || []
    if (img_list.length > 0) {
      this.urlByfile(img_list[0].proxy_url)
    }
  }
}
</script>

<style lang='scss'>
@import '../css/material_m.scss';
</style>
