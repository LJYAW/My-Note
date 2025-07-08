<!--
 * @Author: your name
 * @Date: 2021-08-04 10:46:12
 * @LastEditTime: 2021-09-23 15:51:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/video-detail/index.vue
-->
<template>
  <div class="videos-detail">
    <title-bar title="视频详情" />
    <!-- <video class="video" controls :src="videoData.video_url" crossorigin="anonymous" /> -->
    <video-player
      v-if="!videoLoading"
      id="videoDetail"
      ref="videoPlayer"
      cross-origin="anonymous"
      :options="playerOptions"
      class="video"
    />
    <!-- 相关信息 -->
    <div class="title">视频信息</div>
    <div class="video-cont">
      <div class="cont-item">
        <span class="cont-title">视频封面</span>
        <base-image :src="videoData.cover_url" />
        <span class="cut-img" @click.self="canvasImgSrc">选取当前帧作为封面</span>
      </div>
      <div class="cont-item name">
        <span class="cont-title">视频名称</span>
        <span class="cont">{{ videoData.titles }}</span>
        <i class="el-icon-edit-outline edit" @click="openDialog('视频标题', 'EditTitle')" />
      </div>
      <div class="cont-item">
        <span class="cont-title">视频时长</span>
        <span class="cont">{{ videoData.video_duration | timesToHMS }}</span>
      </div>
      <div class="cont-item">
        <span class="cont-title">视频大小</span>
        <span class="cont">{{ videoData.video_size | initSize }}</span>
      </div>
      <div class="cont-item">
        <span class="cont-title">视频属性</span>
        <span class="cont">{{ videoData.vca_status | vcaStatus }}</span>
      </div>
      <div class="cont-item">
        <span class="cont-title">视频来源</span>
        <span class="cont">{{ videoData.source }}</span>
      </div>
      <div class="cont-item">
        <span class="cont-title">上传时间</span>
        <span class="cont">{{ videoData.create_time }}</span>
      </div>
      <div class="cont-item">
        <span class="cont-title">视频目录</span>
        <span class="cont">{{ videoData.dir_name }}</span>
        <i class="el-icon-edit-outline edit" @click="openDialog('视频目录', 'EditProject')" />
      </div>
    </div>
    <!-- 标签 -->
    <div class="title">
      标签信息<i class="el-icon-edit-outline edit" @click="openDialog('视频标签', 'EditTags')" />
    </div>
    <div class="tags-wrap">
      <div v-for="tag in tags" :key="tag" class="smart-tag tag">
        {{ tag }}
      </div>
      <div v-for="tag in labels" :key="tag" class="tag">
        {{ tag }}
      </div>
    </div>

    <div class="btn-wrap">
      <el-button
        v-if="videoData.vca_status===2"
        type="primary"
        size="small"
        @click="openDialog('视频分析', 'Vca')"
      >开始分析</el-button>
      <el-button type="danger" size="small" @click="confirmDelete()">删除</el-button>
      <el-button type="info" size="small" @click="goback()">取消</el-button>
      <el-button type="primary" size="small" @click="uploadImg()">保存</el-button>
    </div>

    <BaseDialog :show.sync="dialogFlag" width="294px" :title="dialogTit">
      <base-image class="bg" :src="videoData.cover_url" />
      <component :is="editComponent" :video-data.sync="videoData" @modify="changeData" @analysis="analysis" />
    </BaseDialog>
    <!-- <remote-js /> -->
  </div>
</template>

<script>
import * as canvas from '@/utils/canvas.js'
import BaiduSDK from '@/utils/sdk.js'
import EditTitle from './component/editTitles.vue'
import EditTags from './component/editTags.vue'
import EditProject from './component/editProject.vue'
import Vca from './component/vca.vue'

export default {
  components: {
    EditTitle,
    EditTags,
    EditProject,
    Vca
    // 'remote-js': {
    //   render(createElement) {
    //     return createElement(
    //       'script',
    //       {
    //         attrs: {
    //           type: 'text/javascript',
    //           src: 'https://code.bdstatic.com/npm/@baiducloud/sdk@1.0.0-rc.30/dist/baidubce-sdk.bundle.min.js'
    //         }
    //       }
    //     )
    //   }
    // }
  },
  props: {},
  data() {
    return {
      uuid: 0,
      videoData: {
        tags: '',
        labels: ''
      },
      dialogFlag: false,
      dialogTit: '',
      editComponent: '',

      sdkParams: {},

      videoLoading: true,
      playerOptions: {
        height: '360',
        autoplay: false,
        muted: false,
        language: 'en',
        fluid: true,
        sources: [
          {
            type: 'video/mp4',
            src: ''
          }
        ],
        poster: '',
        aspectRatio: '16:9'
      }
    }
  },
  computed: {
    tags() {
      return this.videoData.tags.split(',').filter(function(s) {
        return s && s.trim()
      })
    },
    labels() {
      return this.videoData.labels.split(',').filter(function(s) {
        return s && s.trim()
      })
    }
  },
  watch: {},
  created() {
    this.uuid = this.$route.query.uuid
    this.getDetail()
  },
  mounted() {
  },
  methods: {
    openDialog(t, c) {
      this.dialogTit = t
      this.editComponent = c
      this.dialogFlag = true
    },
    changeData(obj) {
      this.dialogFlag = false
      this.videoData = Object.assign(this.videoData, obj)
    },
    async analysis() {
      const params = [this.uuid]

      const { err, res } = await this.$post(`/videos/vca`, params, {
        DirId: this.videoData.dir_id
      })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('开始分析成功')
      this.dialogFlag = false
      this.getDetail()
    },
    goback() {
      history.go(-1)
    },
    canvasImgSrc() {
      const id = document.getElementById('videoDetail').querySelector('video').id
      if (!id) return
      var img_url = canvas.canvasImgSrc(id)
      this.videoData.cover_url = img_url
    },

    // 封面图上传
    async uploadImg() {
      if (this.videoData.cover_url.includes('http')) {
        this.setData()
      } else {
        const file = canvas.dataURLtoFile(this.videoData.cover_url)
        await this.getStsToken('pic')
        const uploader = new BaiduSDK(this.sdkParams)
        uploader.startPartUpload(file, this.setData)
      }
    },
    // 获取STS权限
    async getStsToken(type) {
      const params = {
        media_type: type
      }
      const {
        err,
        res
      } = await this.$get('/util/bos-sts', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.sdkParams = res.data
    },
    confirmDelete() {
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteVideo()
      }).catch(() => {
      })
    },
    async deleteVideo() {
      const {
        err,
        res
      } = await this.$deleteRun(`/videos/${this.uuid}`, {
        DirId: this.videoData.dir_id
      })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('删除成功')
      this.goback()
    },
    async setData(e) {
      if (e) {
        this.videoData.cover_bos_key = e.body.key
      }
      const params = this.videoData
      const {
        err,
        res
      } = await this.$put(`/videos/${this.uuid}`, params, {
        DirId: this.videoData.dir_id
      })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('修改成功')
      this.goback()
    },
    async getDetail() {
      this.videoLoading = true
      const {
        err,
        res
      } = await this.$get(`/videos/${this.uuid}`, {}, {
        DirId: this.$route.query.DirId
      })
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.videoLoading = false
      this.videoData = res.data
      this.playerOptions.sources[0].src = res.data.video_url
      // this.playerOptions.sources[0].src = 'https://bj.bcebos.com/v1/tm-test/test.mp4'
    }
  }
}

</script>

<style scoped lang="scss">
.videos-detail {
  padding-bottom: 47px;
}

.video {
  width: 623px;
  height: 351px;
  background: #eee;
  margin-top: 20px;
  border-radius: 4px;
  overflow: hidden;
}

.title {
  margin-top: 20px;
  margin-bottom: 16px;
  font-size: 18px;
  color: #404040;
}

.edit {
  color: #404040;
  line-height: 20px;
  margin-left: 10px;
  cursor: pointer;
}

.video-cont {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  width: 623px;

  .cont-item {
    display: flex;
    // align-items: center;
    margin-top: 10px;
    min-width: 50%;
    position: relative;

    &.name {
      width: 100%;
    }

    .cont-title {
      flex-shrink: 0;
      width: 56px;
      font-size: 14px;
      color: #a0a0a0;
      margin-right: 20px;
      word-break: keep-all;
    }

    .cont {
      font-size: 14px;
      line-height: 20px;
      color: #404040;
    }

    .cut-img {
      position: absolute;
      right: 12px;
      bottom: 0;
      width: 224px;
      height: 30px;
      background: rgba(0, 0, 0, .4);
      display: block;
      text-align: center;
      font-size: 14px;
      color: #fff;
      line-height: 30px;
      border-radius: 4px;
      cursor: pointer;
    }

    .el-image {
      width: 224px;
      height: 126px;
      background: #f2f2f2;
      border-radius: 4px;
    }
  }
}

.tags-wrap {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 30px;

  .tag {
    padding: 6px 8px;
    margin-right: 10px;
    margin-bottom: 10px;
    background: #e5eaff;
    border-radius: 4px;
    font-size: 12px;
    color: #404040;
    line-height: 12px;

    &.smart-tag {
      background: #efefef;
    }
  }
}

.bg {
  width: 100%;
  height: 126px;
  background: #f2f2f2;
  border-radius: 4px;
  margin: 0 auto;
}
</style>
