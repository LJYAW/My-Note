<!--
 * @Author: your name
 * @Date: 2021-08-04 10:45:52
 * @LastEditTime: 2021-10-11 19:10:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/add-video/index.vue
-->
<template>
  <div class="add-videos">
    <!-- <div class="title-bar-list">
      <title-bar title="上传视频" />
      <title-bar title="批量上传" />
    </div> -->

    <el-tabs v-model="activeName" @tab-click="handleClick">
      <!-- <el-tab-pane label="上传视频" name="upload" /> -->
      <el-tab-pane label="批量上传" name="batchUpload" />
    </el-tabs>

    <BatchUpload v-if="activeName === 'batchUpload'" />

    <div v-if="activeName === 'upload'">

      <div class="upload-btn" @click="open">
        <svg-icon icon-class="upload" />
        <div class="upload-tips">点击上传视频</div>
      </div>

      <title-bar title="任务管理" />

      <div class="delete">
        <!-- <span>{}个视频任务 共1834M</span> -->
        <el-button type="danger" size="small" @click="deleteAll">全部删除</el-button>
      </div>

      <div
        v-if="uploaderArr.length"
        class="video-upload"
      >
        <div
          v-for="(item,index) in uploaderArr"
          :key="index"
          class="video-upload-item"
        >
          <div class="video-item">
            <img :src="item.params.poster" alt="">
            <div v-show="item._progress<100" class="video-msg">
              <el-progress :text-inside="true" :stroke-width="26" :percentage="parseInt(item._progress)" />
              <span @click="deleteItem(index)">取消上传</span>

              <div class="video-desc">
                <span>{{ item.params.project }}</span>
                <span>{{ item.params.size |initSize }}</span>
              </div>
            </div>
          </div>
          <div class="title">{{ item.params.title }}</div>
        </div>
      </div>

      <Normal v-else />

      <BaseDialog :show.sync="uploadDialog" width="588px" title="上传视频">
        <Upload v-show="!file" @getFile="getFile" />
        <video v-show="file" id="videoUpload" class="video" :src="fileUrl" controls />
        <!-- 视频标题 -->
        <div ref="upload-input1" class="upload-input title">
          <div class="input-tit">视频标题</div>
          <input v-model="title" type="text" class="input" @focus="inputFocus('upload-input1')" @blur="inputBlur('upload-input1')">
        </div>
        <!-- 当前帧截图 -->
        <div class="flex">
          <div class="poster">
            <base-image :src="poster_img" />
            <span class="cutImg" @click="canvasImgSrc">选择当前帧作为封面</span>
          </div>
          <!-- 视频所在目录 -->
          <div class="select-box">
            <div ref="upload-input2" class="upload-input">
              <div class="input-tit">视频目录</div>
              <el-select
                v-model="project"
                value-key="name"
                placeholder=""
                @focus="inputFocus('upload-input2')"
                @blur="inputBlur('upload-input2')"
                @change="dirChange"
              >
                <el-option
                  v-for="item in option"
                  :key="item.id"
                  :label="item.name"
                  :value="item"
                />
              </el-select>
            </div>
            <!-- 标签 -->
            <div ref="upload-input3" class="upload-input upload-input-tag">
              <div class="input-tit">视频标签</div>
              <el-tooltip class="item" effect="dark" content="请输入便于搜索识别的关键词" placement="top">

                <input
                  v-model="tags"
                  type="text"
                  class="input"
                  @keyup.enter="addTag"
                  @focus="inputFocus('upload-input3')"
                  @blur="inputBlur('upload-input3')"
                >
              </el-tooltip>
              <el-button type="primary" size="default" @click="addTag">增加

              </el-button>

            </div>
            <el-tag
              v-for="str in label"
              :key="str"
              closable
              @close="removeTag(str)"
            >
              {{ str }}
            </el-tag>
            <div class="space">
              剩余储存空间：{{ surplusSize|initSize }}
            </div>
          </div>
        </div>

        <el-button
          class="uploadBtn"
          type="primary"
          size="default"
          :loading="loading"
          @click="upload"
        >开始上传</el-button>
      </BaseDialog>

    </div>
    <!-- <remote-js /> -->
  </div>
</template>

<script>
import Upload from '@/components/Upload'
import BaiduSDK from '@/utils/sdk.js'
import * as canvas from '@/utils/canvas.js'
import BatchUpload from './batchUpload.vue'

import Normal from '@/components/Normal'
export default {
  components: {
    Upload,
    BatchUpload,
    Normal
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
  props: {

  },
  data() {
    return {
      uploadDialog: false,
      dir_id: 0, // 目录id
      file: null, // 文件
      fileUrl: '', // 文件链接
      poster_img: '', // 视频封面
      title: '', // 视频标题
      project: '', // 选中目录
      tags: '', // 标签
      label: [],
      // 上传参数
      sdkParams: {},
      // 上传队列
      uploaderArr: [],
      loading: false,
      // 剩余空间
      surplusSize: 0,
      activeName: 'batchUpload'
    }
  },
  computed: {
    option() {
      const arr = []
      this.$store.state.user.dirInfo.forEach(item => {
        const obj = {}
        obj.name = item.dir_name
        obj.id = item.dir_id
        arr.push(obj)
      })
      return arr
    }
  },
  watch: {
    uploadDialog: {
      handler: function(val) {
        !val && this.resetParams()
      }
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    handleClick(tab, event) {
      this.activeName = tab.name
    },
    async getSurplusSize() {
      // 获取剩余空间
      const { res, err } = await this.$get('/companies/')
      this.surplusSize = res.data.t_store_size - res.data.store_size
    },
    dirChange(e) {
      this.dir_id = e.id
    },
    addTag() {
      if (!this.tags.trim()) {
        this.$message.error('不能添加空标签')
        return
      }
      if (this.label.includes(this.tags)) {
        this.$message.error('不能添加重复标签')
        return
      }
      this.label.push(this.tags)
      this.tags = ''
    },
    removeTag(e) {
      this.label = this.label.filter(item => item !== e)
    },
    getFile(file) {
      // event.target.files[0].name 去掉扩展名是title
      // event.target.files[0].size 视频大小单位（字节）
      const { size, name } = file
      this.title = this.getFileName(name)
      this.file = file
      this.size = size
      this.fileUrl = URL.createObjectURL(file)
    },
    // 视频开始上传
    async uploadVideo(e) {
      const obj = {
        size: this.size,
        title: this.title,
        dir_id: this.dir_id,
        label: this.label,
        poster: e.body.location,
        posterKey: e.body.key,
        video_width: this.video_width,
        video_height: this.video_height
      }
      await this.getStsToken('video')
      // 合并参数
      this.sdkParams = Object.assign(this.sdkParams, obj)
      const uploader = await new BaiduSDK(this.sdkParams)
      console.log('🚀 ~ file: index.vue ~ line 273 ~ uploadVideo ~ uploader', uploader)

      this.uploaderArr.push(uploader)
      // 切片上传
      uploader.startPartUpload(this.file, this.submit)
      this.resetParams()
    },
    // 截图
    canvasImgSrc() {
      if (!this.fileUrl) {
        this.$message.error('请先上传视频')
        return
      }
      var img_url = canvas.canvasImgSrc('videoUpload')
      this.poster_img = img_url
      const dom = document.getElementById('videoUpload')
      this.video_width = dom.videoWidth * 1
      this.video_height = dom.videoHeight * 1
    },
    // 封面图上传
    async uploadImg() {
      const file = canvas.dataURLtoFile(this.poster_img)
      await this.getStsToken('pic')
      const uploader = new BaiduSDK(this.sdkParams)
      // 先上传封面图再开始上传视频
      uploader.startPartUpload(file, this.uploadVideo)
    },

    async upload() {
      const { title, project, label, poster_img } = this
      if (!title || !project || !poster_img) {
        this.$message.error('请完善视频信息')
        return
      }

      if (this.surplusSize < this.size) {
        this.$message.error('剩余空间不足')
        return
      }
      this.loading = true
      this.uploadImg()
    },
    // 提交
    async submit(res, obj) {
      var audio = new Audio(res.body.location)
      // 元数据已加载
      audio.addEventListener('loadedmetadata', async(e) => {
        const duration = parseInt(audio.duration * 1000)
        const params = {
          cover_bos_key: obj.posterKey,
          titles: obj.title,
          video_bos_key: res.body.key,
          video_size: obj.size,
          labels: obj.label.join(','),
          dir_id: obj.dir_id,
          video_duration: duration,
          video_height: obj.video_height,
          video_width: obj.video_width
        }
        this.$post('/videos/', params, { DirId: obj.dir_id }).then(response => {
          const { err, res } = response
          if (err) {
            this.$message({
              message: err.msg,
              type: 'error'
            })
            return
          }
        })
      })
    },

    // 获取STS权限
    async getStsToken(type) {
      const params = {
        media_type: type
      }
      const { err, res } = await this.$get('/util/bos-sts', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.sdkParams = res.data
    },
    open() {
      this.getSurplusSize()
      this.uploadDialog = true
    },
    inputFocus(el) {
      this.$refs[el].style = `border: 1px solid #5675E8`
    },
    inputBlur(el) {
      this.$refs[el].style = `border: 0px solid #5675E8`
    },
    deleteItem(index) {
      this.uploaderArr[index].abortUpload()
      this.uploaderArr.splice(index, 1)
    },
    deleteAll() {
      this.uploaderArr.forEach(upload => {
        upload.abortUpload()
      })
      this.uploaderArr = []
    },
    resetParams() {
      this.loading = false
      this.title = ''
      this.file = null
      this.size = 0
      this.project = ''
      this.uploadDialog = false
      this.poster_img = ''
      this.label = []
    },
    getFileName(data) {
      return data.substring(0, data.indexOf('.'))
    }
  }
}
</script>

<style scoped lang="scss">
.add-videos {
  width: 100%;
}
// 上传视频相关
.upload-btn {
  width: 367px;
  height: 205px;
  background: #f2f2f2;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 20px;
  margin-bottom: 30px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  svg {
    font-size: 50px;
    color: #fff;
  }

  .upload-tips {
    color: #404040;
    width: 100%;
    text-align: center;
    margin-top: 10px;
  }
}

.video {
  width: 548px;
  height: 308px;
  margin: 0 auto;
  background: #f2f2f2;
  border-radius: 4px;
}

.title {
  margin-top: 20px;
}

.poster {
  width: 160px;
  height: 120px;
  display: flex;
  flex-direction: column;
  background: #f2f2f2;
  border-radius: 4px 4px 0px 0px;
  overflow: hidden;

  .cutImg {
    height: 30px;
    background: #5675e8;
    border-radius: 0px 0px 4px 4px;
    font-size: 12px;
    color: #fff;
    line-height: 30px;
    text-align: center;
    cursor: pointer;
  }

  .poster-img {
    flex-shrink: 0;
    width: 100%;
  }
}

.upload-input {
  width: 100%;
  height: 30px;
  display: flex;
  background: #f2f2f2;
  border-radius: 4px;
  align-items: center;
  margin-bottom: 10px;
  box-sizing: border-box;

  &.upload-input-tag {
    width: 66%;
    overflow: hidden;

    .input {
      width: 70px;
    }

    .tip-img {
      width: 10px;
    }
  }

  .input-tit {
    flex-shrink: 0;
    width: 58px;
    font-size: 12px;
    color: #404040;
    margin: 9px;
    padding-right: 9px;
    box-sizing: border-box;
    border-right: 1px solid #d8d8d8;
  }

  .input {
    flex: 1;
    font-size: 12px;
    color: #404040;
  }

  .el-select {
    flex: 1;

    ::v-deep.el-input__inner {
      height: 28px;
      font-size: 12px;
      background: #f2f2f2;
      border: 0;
      padding-left: 0;
    }

    ::v-deep.el-input__icon {
      line-height: 30px;
    }
  }
}

.flex {
  display: flex;
  justify-content: space-between;
}

.select-box {
  width: 60%;
}

.uploadBtn {
  width: 100%;
  margin-top: 20px;
}

// 任务管理

.delete {
  margin-top: 20px;
  text-align: right;

  span {
    font-size: 12px;
    color: #bababa;
    margin-right: 10px;
  }
}

.video-upload {
  clear: both;
  width: 100%;
  display: flex;
  margin-top: 20px;

  .video-upload-item {
    flex-shrink: 0;
    width: 366px;
    height: 192px;
    max-width: 23.4%;
    margin-right: 24px;
    margin-bottom: 30px;

    &:nth-child(4n+4) {
      margin-right: 0;
    }

    .video-item {
      position: relative;
      width: 100%;
      height: 100%;
      border-radius: 4px;
      overflow: hidden;

      img {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

    }

    .video-msg {
      position: absolute;
      width: 100%;
      height: 100%;
      z-index: 1;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      background: rgba(0,0,0,.6);

      .el-progress {
        width: 90%;

        ::v-deep.el-progress-bar__outer {
          background: #000;
          opacity: .6;
        }
      }

      span {
        font-size: 14px;
        color: #fff;
        cursor: pointer;
      }

      .video-desc {
        position: absolute;
        display: flex;
        width: 90%;
        justify-content: space-between;
        bottom: 10px
      }
    }

    .title {
      font-size: 14px;
      font-weight: 600;
      color: #404040;
      line-height: 18px;
      margin-top: 10px;
      word-break: break-all;//只对英文起作用，以字母作为换行依据
      word-wrap: break-word; //只对英文起作用，以单词作为换行依据
      white-space: pre-wrap; //只对中文起作用，强制换行
    }

  }

}

.space {
  text-align: right;
  margin-top: 20px;
}

::v-deep.el-tag {
  margin-right: 5px;
  margin-bottom: 5px;
  background: #e5eaff;
  border-color: #e5eaff;
  color: #404040;

  .el-tag__close {
    color: #404040;

    &:hover {
      background: #e5eaff;
    }
  }
}

.title-bar-list {
  display: flex;

  .title-bar {
    margin-right: 20px;

  }
}

::v-deep .el-tabs__nav-wrap {

  .el-tabs__item {
    font-size: 18px;
  }

  &::after {
    display: none;
  }
}
</style>
