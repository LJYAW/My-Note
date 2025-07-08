<!--
 * @Author: your name
 * @Date: 2021-09-22 19:04:39
 * @LastEditTime: 2021-10-12 16:03:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/subtitleHeader/index.vue
-->
<template>
  <div>
    <Theader name="字幕创作">
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
          @blur="setTitle"
        />
        <p v-else>{{ title }}</p>
        <svg-icon icon-class="edit" @click="setShowInput" />
      </div>
      <div slot="btn">
        <base-btn @click="exportSubtitle">导出字幕</base-btn>
        <base-btn @click="generateVideo">生成视频</base-btn>
      </div>

    </Theader>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="388px" title="">
      <div class="confirm-dialog">
        <p>确认生成视频？提交后将无法修改！</p>
        <div class="btn-wrap">
          <base-btn @click="confirm">确定</base-btn>
          <base-btn type="info" @click="cancel">取消</base-btn>
        </div>
      </div>

    </base-dialog>
  </div>
</template>

<script>
import Theader from '@/components/ToolsHeader'
import { downloadJs } from '../../../utils/download'
import setVideoParams from '../js/generate-video'
import { PutVideos } from '@/app/jianshi-video/api/videos/index.js'
import { mapState } from 'vuex'
export default {
  components: {
    Theader
  },
  props: {

  },
  data() {
    return {
      formTitle: '',
      showInput: false,
      dialogVisible: false
    }
  },
  computed: {
    ...mapState('jianshi', ['subTitleSingle', 'title'])
  },
  watch: {
    subTitleSingle: {
      handler(val) {
        this.formTitle = val.titles
        this.$store.commit('jianshi/SET_TITLE', this.formTitle)
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    setTitle() {
      this.showInput = false
      this.$store.commit('jianshi/SET_TITLE', this.formTitle)
    },
    setShowInput() {
      this.showInput = !this.showInput
    },
    // 导出字幕
    exportSubtitle() {
      const url = process.env.VUE_APP_BASE_API_JIANSHI + '/videos/' + this.subTitleSingle.id + '?types=' + 1
      downloadJs(url, this.formTitle, setVideoParams())
    },
    generateVideo() {
      this.dialogVisible = true
    },
    async confirm() {
      const { err, data } = await PutVideos(setVideoParams())
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.dialogVisible = false
        this.$message({
          type: 'success',
          message: '提交成功'
        })
        this.$store.dispatch('settings/changeSetting', { showHeader: true })
        this.$router.push({
          path: '/manage-center/creative-center',
          query: {
            tab: '字幕创作'
          }
        })
      }
    },
    cancel() {
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped lang="scss">
.title-wrap {
  display: flex;
  align-items: center;

  svg {
    margin-left: 13px;
  }

  ::v-deep input {
    width: 500px;
    padding-right: 45px;
  }
}

.confirm-dialog {

  p {
    font-size: 16px;
    margin-bottom: 70px;
    color: #1a1a1a;
    text-align: center;
  }

  .btn-wrap {
    display: flex;
    justify-content: space-between;
    padding: 0 80px;
  }
}
</style>
