<!--
 * @Author: your name
 * @Date: 2021-10-21 16:57:32
 * @LastEditTime: 2021-10-25 16:51:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/quick-script-create/index.vue
-->
<template>
  <div class="quick-create">
    <Theader name="快速创作" />
    <div class="main-wrap">
      <el-radio-group v-model="type">
        <el-radio label="normal">自由创作</el-radio>
        <el-radio label="url">
          文章链接快速创作
          <el-input
            v-if="type=='url'"
            v-model="materialUrl"
            clearable
            size="mini"
            placeholder="直接粘贴资讯链接：目前支持百家号及微信公众号的文章链接"
          />
        </el-radio>
        <el-radio label="ppt">
          PPT导入快速创作
          <base-upload
            v-if="type=='ppt'"
            button-name="导入PPT文件"
            :file-type="fileType"
            @getFileUrl="getFileUrl"
          />
          <el-progress v-if="type=='ppt'&&percentage" :percentage="percentage" />
          <p v-if="type=='ppt'" class="tips">ppt自动转成图片 ，备注将提取为文本作为字幕</p>
        </el-radio>
      </el-radio-group>
    </div>
    <div class="btn-wrap">
      <base-btn class="next-btn" :disabled="type=='ppt'&&disabled" @click="next">下一步</base-btn>
    </div>
  </div>
</template>

<script>
import Theader from '@/components/ToolsHeader'
import * as check from '../script-create/videoMaterial/js/check'
import axios from 'axios'
export default {
  components: {
    Theader
  },
  props: {

  },
  data() {
    return {
      type: 'normal',
      materialUrl: '',
      fileType: '.ppt,.pptx',
      percentage: 0,
      file: null,
      fileName: null,
      disabled: false
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    getFileUrl(url, file) {
      this.fileName = file.name
      this.uploadFile(file)
    },
    async uploadFile(file) {
      this.percentage = 0
      this.disabled = true
      const formData = new FormData()
      formData.append('file', file)
      this.$axios
        .post('/common/uploadfile', formData, {
          onUploadProgress: progressEvent => {
            this.percentage = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          }
        })
        .then(res => {
          if (res.data.code === '200') {
            this.disabled = false
            this.file = res.data.data.imgurl
          } else {
            this.$message.error('上传失败，请重新上传')
          }
        })
        .catch(err => {
          this.$message.error('上传失败，请重新上传')
          console.error(err)
        })
    },
    // 下一步
    next() {
      switch (this.type) {
        case 'normal':
          this.setStoreUrl()
          this.routerGo(this.type)
          break
        case 'url':
          this.setStoreUrl(this.materialUrl)
          this.checkUrl()
          break
        case 'ppt':
          this.setStoreUrl()
          this.checkFile()
          break
      }
    },
    // 跳转快速创作
    routerGo(type) {
      this.$router.push({
        path: '/jianshi-video/script-create',
        query: {
          type: type
        }
      })
    },
    // 跳转创作中心
    routeToPPt() {
      this.$router.push({
        path: '/manage-center/creative-center',
        query: {
          tab: 'PPT创作'
        }
      })
    },
    // 校验文章链接
    checkUrl() {
      const errMsg = check.checkoutIsURL(this.materialUrl)
      if (errMsg) {
        this.$message.error(errMsg)
        return
      }
      this.routerGo(this.type)
    },
    // 校验PPT文件
    checkFile() {
      if (!this.file) {
        this.$message.error('请上传ppt文件')
        return
      }
      this.transPPt()
      this.$alert('正在将ppt转换成图片素材，预计需要几分钟时间，详情可在我的作品中查看。', '转换中...', {
        confirmButtonText: '我的作品',
        callback: action => {
          this.routeToPPt()
        }
      })
    },
    setStoreUrl(url = '') {
      this.$store.commit('jianshi/SET_MATERIAL_URL', this.materialUrl)
    },
    transPPt() {
      const formData = new FormData()
      formData.append('file', this.file)
      formData.append('filename', this.fileName)
      formData.append('resize', '1280x720')
      return new Promise((resolve, reject) => {
        axios.post('/transppt/do', formData, { timeout: 10 * 60 * 1000 }).then((res) => {
          if (res.data.code === '200') {
            resolve(res.data)
          } else {
            this.$message.error(res.data.msg)
            reject(res.data.msg)
          }
        }).catch((err) => {
          this.$message.error(err.msg)
          reject(err)
        })
      })
    }
  }
}
</script>

<style scoped lang="scss">
.main-wrap {
  flex : 1;
  margin : 20px 10%;
  padding : 40px 40px 100px 40px;
  height : 100%;
  display : flex;
  background : #FFFFFF;

  .el-radio {
    display : block;
    margin-bottom : 30px;
    height: 40px;
    line-height: 40px;

    .el-input {
      width : 500px;
      margin-left : 20px;
    }

    .upload {
      display : inline-block;
      margin-left : 20px;
    }

    .el-progress {
      display : inline-block;
      width : 300px;
      margin-left : 20px;

    }
    .tips{
      color : rgba(64,64,64,.4);
      margin-left : 25px;
    }
  }
}

.btn-wrap {
  padding-right : 10%;

  .next-btn {
    float : right;
  }
}

</style>
