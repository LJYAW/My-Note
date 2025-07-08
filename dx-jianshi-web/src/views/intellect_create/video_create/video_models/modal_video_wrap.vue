<!--
 * @Author: your name
 * @Date: 2020-12-23 15:50:24
 * @LastEditTime: 2021-01-04 14:19:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/modal_video_wrap.vue
-->
<template>
  <div>
    <model :id="'showVideoWrap'"
      @close="close">
      <div slot="title" class='d-flex align-items-center'>
        <upload :button_name="button_name" :file_type="upload_type" @getFileUrl="getFileUrl" />
        <p class="fz-12px fc-999 ml-20px" style="line-height: 1">注：建议上传50px高度的透明背景png图</p>
      </div>

      <div slot="body"
        class="d-flex pb-50px videom-video-wrap flex-column">
        <el-input v-model="keyword" placeholder="输入关键词进行搜索" @keyup.enter.native="searchData">
          <i slot="suffix" class="el-input__icon el-icon-search" @click='searchData'></i>
        </el-input>

        <scroll class="search-list" ref="scroll" @scrollDown="scrollDown">

          <div v-if='modal_data_type=="video_logo"' class='toggle-wrap'>
            <div v-for='item in data_list' :key='item.id'>
              <div slot='progress' class="px-10px pt-20px toggle-item mb-20px" v-if="item.status == '上传中'">
                <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
              </div>
              <div v-else class='toggle-content' @click="setActiveId(item)" :class='[{"active" : active_id==item.id}]'>
                <div class='toggle-item'>
                  <img :src="item.resource_url" alt="">
                </div>
                <i @click="deleteResource(item)" class="iconfont icondelect cp"></i>
                <p class='mt-3px fz-12px title'>{{item.title}}</p>
              </div>
            </div>
          </div>
          <video-toggle v-else>
            <video-toggle-item v-for='(item,index) in data_list' :key='index'
              :class='[{"active" : active_id==item.id}]'
              @click.native="setActiveId(item)">
              <div
                slot='progress'
                v-if="item.status == '上传中'"
                class="px-10px pt-20px toggle-item mb-20px">
                <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
              </div>
              <template v-else>
                <img slot='item' :src="item.cover_pic">
                <videoPalyer
                  slot='toggle-item'
                  :key='item.id'
                  :fluid="false"
                  :video_options="getVideoOptions(item.resource_url)" />

                <div slot='toggle-body'>
                  <i @click="deleteResource(item)" class="iconfont icondelect cp"></i>
                  <p class='mt-3px fz-12px title'>{{item.title}}</p>
                </div>
              </template>

            </video-toggle-item>
          </video-toggle>
        </scroll>

        <div class='text-center mt-30px'>
          <el-button type='primary' round size='mini' @click='setVideoId'>确定</el-button>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
import videoToggle from '@/components/video_toggle/src/video_toggle'
import videoToggleItem from '@/components/video_toggle/src/video_toggle_item'
export default {
  props: ['modal_data_type', 'modal_avtive_id'],
  data() {
    return {
      PAGE: 1,
      LIMIT: 15,
      LAST_PAGE: 0,
      keyword: '',
      button_name: '上传角标',
      upload_type: 'image/png, image/jpeg, image/gif, image/jpg',
      active_id: '',
      complete: 0,
      active_item: {},
      data_list: [],
      loading_status: false
    }
  },
  computed: {},
  watch: {},
  methods: {
    // 获取数据
    async getDataList() {
      this.loading_status = true
      let params = {
        tag: this.modal_data_type,
        title: this.keyword,
        page: this.PAGE,
        limit: this.LIMIT
      }
      let data = await this.$parent.getMyFileList(params)
      this.LAST_PAGE = data.last_page
      this.data_list = data.list[this.modal_data_type] || []
      this.loading_status = true
    },
    // 设置选中ID
    setActiveId(item) {
      this.active_id = item.id
      this.active_item = item
    },
    close() {
      this.$emit('Modalclose')
    },
    //加载下一页
    async scrollDown() {
      this.PAGE++
      if (this.PAGE <= this.LAST_PAGE) {
        this.loading_status = true
        let params = {
          tag: this.modal_data_type,
          title: this.keyword,
          page: this.PAGE,
          limit: this.LIMIT
        }
        let arr = await this.$parent.getMyFileList(params)
        this.loading_status = false
        arr = arr.list[this.modal_data_type]
        this.data_list = this.data_list.concat(arr)
      }
    },
    //筛选数据
    searchData() {
      this.PAGE = 1
      this.getDataList()
    },
    setBtnName() {
      switch (this.modal_data_type) {
        case 'video_begin':
          this.button_name = '上传片头'
          this.upload_type = 'video/mp4'
          return
        case 'video_end':
          this.button_name = '上传片尾'
          this.upload_type = 'video/mp4'
          return
      }
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
      this.complete = 0
      var formData = new FormData()
      formData.append('type', obj.type)
      formData.append('title', file.name.split('.')[0])
      formData.append('resource', file)
      formData.append('tag', this.modal_data_type)
      if (obj.duration) {
        formData.append('duration', obj.duration)
      }
      this.data_list.unshift({
        status: '上传中'
      })
      this.$axios
        .post('/intelligent-creation/upload-user-resource', formData, {
          onUploadProgress: progressEvent => {
            this.complete = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          }
        })
        .then(res => {
          if (res.data.code === '0000') {
            this.$parent.getAllDataList([this.modal_data_type])
            this.getDataList()
          } else {
            this.$alertMsg('上传失败，请重新上传')
          }
        })
        .catch(function(err) {
          console.error(err)
        })
    },
    getVideoOptions(src) {
      return {
        width: '110',
        height: '62',
        autoplay: false,
        src: src
      }
    },
    // 删除素材
    deleteResource(item) {
      this.$deleteRun('/intelligent-creation/delete-user-resource', {
        data: { id: item.id }
      }).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg('删除成功')
          this.$parent.getAllDataList([this.modal_data_type])
          this.getDataList()
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    setVideoId() {
      this.$emit('setActiveTtem', this.active_item)
      this.close()
    }
  },
  components: {
    videoToggle,
    videoToggleItem
  },
  created() {
    this.active_id = this.modal_avtive_id
    this.getDataList()
    this.setBtnName()
  }
}
</script>

<style lang="scss">
#showVideoWrap {
  .btn-style {
    font-size: 14px;
    color: #c51b18;
    border-bottom: 1px solid #c51b18;
  }
  .el-input {
    width: 330px !important;
    input {
      font-size: 12px;
      height: 30px;
      line-height: 30px;
      border: 1px solid rgba(219, 219, 219, 100);
    }
    .el-input__icon {
      line-height: 30px;
    }
  }
  .search-list {
    height: 290px;
    max-height: 290px;
    overflow: hidden;
    overflow-y: auto;
    margin-top: 20px;
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
          top: 5px;
          right: 5px;
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
      .toggle-item {
        width: 110px;
        height: 62px;
        overflow: hidden;
        background: #e5e5e5;

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
  }
}
</style>
