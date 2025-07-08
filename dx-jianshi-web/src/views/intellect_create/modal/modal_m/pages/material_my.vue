<!--
 * @Author: zzx
 * @Date: 2020-10-27 16:28:22
 * @LastEditTime: 2021-07-28 14:36:20
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/material_my.vue
-->
<template>
  <div>
    <div class="d-flex align-items-center my-12px">
      <p class>本地素材</p>
      <upload
        button_name="上传本地素材"
        file_type="image/png, image/jpeg, image/gif, image/jpg, video/mp4"
        class="ml-10px"
        @getFileUrl="getFileUrl" />
    </div>
    <scroll class="list" scrollX @scrollXDown="scrollXDown">
      <videoToggle>
        <div v-for="(item,index) in img_video_list"
          :key="index" class=''>
          <div v-if="item.type == 'image'" class='toggle-content'>
            <div
              :class="['toggle-item box mr-15px',{'active': my_image_index == index}]"
              @click="addItem(item,index,2)">
              <img :src="item.resource_url" />
              <label :class="['el-upload-list__item-status-label']">
                <i class="iconfont iconqueding d-block"></i>
              </label>
              <a class="delete-icon" @click="deleteRsource(item,index)">
                <i class="iconfont fc-white icondelect hove-c"></i>
              </a>
            </div>
          </div>

          <videoToggleItem v-else
            :class="['box mr-7px mb-10px',{'active': my_image_index == index}]"
            @click.native="addItem(item,index,2)">
            <div
              slot='progress'
              v-if="item.type == '上传中'"
              class="px-10px pt-20px toggle-item mb-20px">
              <el-progress :text-inside="true" :stroke-width="13" :percentage="item.complete"></el-progress>
            </div>
            <!-- <div v-if="item.type == '上传中'" style="margin-top: 37px" class="px-10px">
              <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
            </div> -->

            <template v-else class="h-100">
              <img slot='item' :src="item.cover_pic">
              <videoPalyer v-if="item.type == 'video'"
                slot='toggle-item'
                :video_options="getVideoOptions(item.resource_url)"
                :key='item.id' />

              <div slot='toggle-body'>
                <label :class="['el-upload-list__item-status-label']">
                  <i class="iconfont iconqueding d-block"></i>
                </label>
                <a class="delete-icon" @click="deleteRsource(item,index)">
                  <i class="iconfont fc-white icondelect hove-c"></i>
                </a>
              </div>
            </template>
          </videoToggleItem>
        </div>

      </videoToggle>
    </scroll>
  </div>
</template>

<script>
import { searchImgAi } from '../js/search_img_ai.js'
import videoToggle from '@/components/video_toggle/src/video_toggle'
import videoToggleItem from '@/components/video_toggle/src/video_toggle_item'
export default {
  props: {},
  mixins: [searchImgAi],
  data() {
    return {
      complete: 0, // 文件上传进度
      img_video_list: [],
      my_image_index: null,
      page: 1,
      video_options: {
        width: '332',
        height: '190',
        autoplay: false,
        src: ''
      }
    }
  },
  computed: {},
  watch: {},
  methods: {
    // 原文图片加载
    scrollXDown() {
      console.log('scrollXDown -> scrollXDown')
      this.page++
      this.getMyFileList()
    },
    getFileUrl(url, file) {
      var fileType = file.type
      var type = fileType.split('/')[0]
      var obj = {
        type: type,
        url: url
      }
      this.uploadMyFile(obj, file)
    },
    uploadMyFile(obj, file) {
      // this.complete = 0
      var formData = new FormData()
      formData.append('type', obj.type)
      formData.append('resource', file)
      formData.append('duration', '123')

      this.img_video_list.unshift({
        type: '上传中',
        complete:0
      })

      this.$axios
        .post('/intelligent-creation/upload-user-resource', formData, {
          onUploadProgress: progressEvent => {
            this.img_video_list[0].complete= ((progressEvent.loaded / progressEvent.total) * 100) | 0
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
    addItem(item, index, type) {
      // console.log('addItem -> item', item)
      if (item.imgError || item.type == '上传中') {
        return
      }
      // 选择用户素材
      // if (type == 2) {
      this.my_image_index = index
      this.$emit('selectImg', 'local_index', this.img_video_list[index])
      // }
    },
    getVideoOptions(src) {
      let video_options = {
        width: '110',
        height: '62',
        autoplay: false,
        src: src
      }

      return video_options
    }
  },
  components: { videoToggle, videoToggleItem },
  created() {},
  mounted() {
    this.getMyFileList()
  }
}
</script>

<style scoped lang="scss">
#videoWrap {
  ::v-deep .video-js.vjs-fluid {
    padding-top: 56.25% !important;
    .vjs-big-play-button {
      width: 30px !important;
      height: 30px !important;
      line-height: 28px;
      font-size: 2em;
    }
  }
}
::v-deep {
  .toggle-item {
    width: 110px;
    height: 62px;
    overflow: hidden;
    background: #e5e5e5;
    position: relative;
    img {
      width: 100%;
      height: 100%;
      object-fit: contain;
    }
    .video-js {
      .vjs-big-play-button {
        width: 30px !important;
        height: 30px !important;
        line-height: 28px;
        font-size: 2em;
      }
    }
  }
}
.box {
  min-width: 110px;
  min-height: 62px;
  max-width: 110px;
  max-height: 62px;
  overflow: hidden;
  background-color: #e5e5e5;
  text-align: center;
  position: relative;
  transition: all 0.3s;
  border-radius: 3px;

  &:hover {
    border: 2px solid $c;

    .el-upload-list__item-status-label {
      display: block;
    }
  }

  &.active {
    border: 2px solid $c;

    .el-upload-list__item-status-label {
      display: block;
    }
  }
  .el-upload-list__item-status-label {
    position: absolute;
    right: -17px;
    top: -7px;
    width: 46px;
    height: 26px;
    background: $c;
    text-align: center;
    transform: rotate(45deg);
    box-shadow: 0 1px 1px #ccc;

    &.active {
      display: block;
    }

    i {
      color: #fff;
      font-size: 12px;
      margin-top: 12px;
      transform: rotate(-45deg);
    }
  }

  img {
    height: 100%;
  }
}

.list {
  overflow: auto;
  height: 320px;
  margin-bottom: 0 !important;
  .toggle-wrap {
    display: flex;
    flex-wrap: wrap;
    .toggle-content {
      width: 110px;
      height: 82px;
      margin-right: 15px;
      overflow: hidden;
      margin-bottom: 20px;
      text-align: center;
      position: relative;
      &:hover {
        .icondelect {
          display: block;
        }
      }
      .icondelect {
        position: absolute;
        top: 6px;
        left: 6px;
        display: none;
        color: #c51a1a;
      }
      .title {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      &.active {
        .toggle-item {
          border: 1px solid $c;
        }
      }
    }
  }
}
</style>
