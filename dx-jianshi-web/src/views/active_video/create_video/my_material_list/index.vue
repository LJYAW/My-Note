<!--
 * @Author: zzx
 * @Date: 2020-10-19 12:05:56
 * @LastEditTime: 2020-12-01 11:18:57
 * @FilePath: /zhijian_web/src/views/active_video/create_video/my_material_list/index.vue
-->
<template>
  <div class="my-materilal-list">
    <el-input v-model="search" class="search" placeholder="输入关键词搜索素材" suffix-icon="iconfont iconsearch fz-16px"></el-input>
    <scroll class="search-list" ref="scroll" style="min-height: 400px" @scrollDown="scrollDown">
      <div
        v-for="(item,index) in img_video_list"
        :key="index"
        :class="['item',{'is_select': select_item.id == item.id}]"
        @click="select(item)">
        <img v-if="item.type == 'image'" :src="item.resource_url" />
        <videoCut
          v-if="item.type == 'video'"
          :key="item.resource_url"
          :video_options="{width: '176',height: '96',src: item.resource_url}" />
        <a class="delete-icon" @click="deleteRsource(item,index)">
          <i class="iconfont fc-white iconshanchuicon hove-c"></i>
        </a>
      </div>
    </scroll>
    <div class="upload-btn">
      <upload
        button_name="上传本地素材"
        file_type="image/png, image/jpeg, image/gif, image/jpg, video/mp4"
        class="ml-10px"
        @getFileUrl="getFileUrl" />
      <el-button size="mini" round type="primary" @click="submit">确认</el-button>
    </div>
  </div>
</template>

<script>
import { checkVideoDetails } from '@/utils/check_video_size.js'
export default {
  name: 'MyMaterIalList',
  props: {
    layerid: {
      // 自动注入的layerid
      type: String,
      default: ''
    }
  },
  data() {
    return {
      img_video_list: [],
      select_item: {
        id: null
      },
      page: 1,
      search: ''
    }
  },
  computed: {},
  watch: {},
  methods: {
    // 删除
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
    // 滚动
    scrollDown() {
      this.page++
      this.getMyFileList()
    },
    select(item) {
      this.select_item = item
    },
    getMyFileList() {
      var params = {
        page: this.page,
        limit: 10
      }
      this.$get('/interactive-video/material-list', params).then(res => {
        if (res.data.code == '0000') {
          this.img_video_list = res.data.data.list
        }
      })
    },
    async getFileUrl(url, file) {
      var fileType = file.type
      let videoDetails = await checkVideoDetails(file)
      var type = fileType.split('/')[0]
      if (videoDetails.ratio == '16:9') {
        var obj = {
          type: type,
          ratio: videoDetails.ratio,
          duration: videoDetails.duration,
          url: url
        }
        this.uploadMyFile(obj, file)
      } else {
        this.$alertMsg('目前只支持上传16:9的视频')
      }
    },
    uploadMyFile(obj, file) {
      this.complete = 0
      var formData = new FormData()
      formData.append('material', file)
      formData.append('type', 'video')
      formData.append('duration', obj.duration * 1000)
      formData.append('ratio', obj.ratio)

      this.img_video_list.unshift({
        type: '上传中'
      })

      this.$axios
        .post('/interactive-video/upload-material', formData, {
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
    submit() {
      this.$bus.$emit('setVideoDetails', this.select_item)
      this.$layer.close(this.layerid)
    }
  },
  components: {},
  created() {
    this.getMyFileList()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.my-materilal-list {
  position: relative;
  width: 100%;
  height: 100%;
  .search {
    width: 300px;
    margin: 20px 0 0px 30px;
    /deep/ .el-input__inner::-webkit-input-placeholder {
      color: #999999 !important;
      font-weight: bold;
      font-size: 12px;
    }
    /deep/ .el-input__inner {
      height: 30px;
    }
    /deep/ .el-input__suffix {
      top: -4px;
    }
  }
  .search-list {
    display: flex;
    align-content: flex-start;
    flex-wrap: wrap;
    height: calc(100% - 67px);
    padding: 20px;
    overflow: hidden;
    overflow-y: auto;

    .item {
      min-width: 180px;
      min-height: 100px;
      max-width: 180px;
      max-height: 100px;
      overflow: hidden;
      background-color: #e5e5e5;
      border: 2px solid transparent;
      text-align: center;
      position: relative;
      transition: all 0.3s;
      border-radius: 3px;
      margin-bottom: 20px;
      margin: 0 0 10px 8px;
      &:hover {
        border: 2px solid #215fd1;
      }
      &.is_select {
        border: 2px solid #215fd1;
      }
      .delete-icon {
        position: absolute;
        top: 6px;
        left: 6px;
        .hove-c:hover {
          color: #c51a1a !important;
          transition: color 0.27s linear;
        }
      }
      img {
        width: 100%;
        height: 100%;
      }
    }
  }
  .upload-btn {
    position: fixed;
    bottom: 0;
    padding: 20px;
    margin-top: auto;
    width: 100%;
    border-top: 1px solid #ececec;
    display: flex;
    justify-content: flex-end;
    .el-button--primary {
      background-color: #215fd1;
      border-color: #215fd1;
      width: 90px;
      margin-left: 20px;
    }
  }
}
</style>
