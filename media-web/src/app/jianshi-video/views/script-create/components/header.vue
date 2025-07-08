<!--
 * @Author: your name
 * @Date: 2021-09-22 21:26:57
 * @LastEditTime: 2021-10-19 15:52:18
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/script-create/components/header.vue
-->
<template>
  <div>
    <Theader name="快速创作">
      <div slot="tit" class="title-wrap">
        <el-input
          v-if="showInput"
          v-model="formTitle"
          size="mini"
          placeholder="请输入标题信息"
          maxlength="40"
          show-word-limit
          class="subtitletitel"
          type="text"
          @keyup.enter.native="setTitle"
          @blur="setTitle"
        />
        <p v-else>{{ title }}</p>
        <svg-icon icon-class="edit" @click="setShowInput" />
      </div>
      <div slot="btn">
        <base-btn @click="generateVideo">生成视频</base-btn>
      </div>

    </Theader>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="388px" title="">
      <div class="confirm-dialog">
        <p>确认生成视频？提交后将无法修改！</p>
        <span v-if="isUploading" class="tips">提交中，请勿关闭页面！</span>
        <div class="btn-wrap">
          <base-btn :disabled="isUploading" @click="confirm">确定</base-btn>
          <base-btn type="info" @click="cancel">取消</base-btn>
        </div>
      </div>

    </base-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import Theader from '@/components/ToolsHeader'
import setVideoParams from '../js/set-params-data'
import * as check from '../js/check-data'
import { batchUploadImg } from '../../../utils/batch-upload'
import { PostScriptCreate } from '@/app/jianshi-video/api/script_creation/index.js'
export default {
  components: {
    Theader
  },
  props: {

  },
  data() {
    return {
      formTitle: '',
      showInput: true,
      imgKeyList: [],
      isUploading: false,
      dialogVisible: false
    }
  },
  computed: {
    ...mapState('jianshi', ['title', 'img_list', 'max_size'])
  },
  watch: {
    title: {
      handler(val) {
        this.formTitle = val
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    setShowInput() {
      this.showInput = !this.showInput
    },
    generateVideo() {
      const err_msg = check.checkImgListError() // 检查imglist 是否有加载失败的图片
      const img_err_msg = check.checkData() // 检查生成数据 是否正确
      if (err_msg) {
        this.$message.error(err_msg)
        return
      }
      if (img_err_msg) {
        this.$message.error(img_err_msg)
        return
      }
      this.dialogVisible = true
    },
    async confirm() {
      this.isUploading = true
      const arr = []; const imgListArr = []
      this.img_list.forEach((item, index) => {
        if (item.source === '原文图库' || item.source === '原文素材') {
          item.index = index
          imgListArr.push(item)
        }
      })
      try {
        imgListArr.forEach(item => {
          arr.push({
            index: item.index,
            resource_url: item.resource_url
          })
        })
        this.imgKeyList = []
        await batchUploadImg(arr, (imgData, index) => {
          this.imgKeyList.push({
            key: imgData.body.key,
            index: index
          })
        })
      } catch {
        alert('上传图片发生错误')
      }
      this.isUploading = false
      this.submitData()
    },
    async submitData() {
      // setVideoParams(this.imgKeyList)
      const { err, res } = await PostScriptCreate(setVideoParams(this.imgKeyList))
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '提交成功'
        })
        this.$router.push({
          path: '/manage-center/creative-center',
          query: {
            tab: '快速创作'
          }
        })
      }
    },
    cancel() {
      this.dialogVisible = false
    },
    setTitle() {
      this.showInput = false
      this.$store.commit('jianshi/SET_TITLE', this.formTitle)
    }
  }
}
</script>

<style scoped lang="scss">
.title-wrap {
  display: flex;
  align-items: center;

  ::v-deep input {
    width: 500px;
  }

  svg {
    margin-left: 13px;
  }
}

.confirm-dialog {

  p {
    font-size: 16px;
    color: #1a1a1a;
    text-align: center;
    margin-bottom: 10px;

  }

  .tips {
    margin-bottom: 50px;
    text-align: center;
    display: block;

  }

  .btn-wrap {
    display: flex;
    justify-content: space-between;
    padding: 0 80px;
    margin-top: 30px;
  }
}
</style>
