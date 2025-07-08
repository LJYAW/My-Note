<!--
 * @Author: your name
 * @Date: 2021-08-30 17:33:45
 * @LastEditTime: 2021-09-27 14:30:06
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/audioSubtitle/index.vue
-->
<template>
  <div class="audio-subtitle">
    <ToolsHeader name="语音创作">
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
          @blur.stop="setTitle"
        />
        <p v-else>{{ formTitle }}</p>
        <svg-icon icon-class="edit" class="edit" @click="showInput = !showInput" />

      </div>
      <div slot="btn">
        <base-btn @click="createVideo">生成视频</base-btn>
      </div>

    </ToolsHeader>

    <div class="audio-subtitle">
      <contentEdit />
    </div>

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
import contentEdit from './contentEdit/index.vue'
import ToolsHeader from '@/components/ToolsHeader'
import { mapState } from 'vuex'
import { cheackFun, createVideoData } from './js/createVideo'
export default {
  name: 'AudioSubtitle',
  components: {
    contentEdit,
    ToolsHeader
  },
  props: {

  },
  data() {
    return {
      dialogVisible: false,
      formTitle: '',
      showInput: true,
      timeLineLoading: false
    }
  },
  computed: {
    ...mapState('jianshi', ['phoneticTitle'])
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    cancel() {
      this.dialogVisible = false
    },
    setTitle() {
      this.showInput = false
      this.$store.commit('jianshi/set_phonetictitle', this.formTitle)
    },
    createVideo() {
      this.dialogVisible = true
    },
    async confirm() {
      if (!cheackFun()) {
        this.$message({
          message: '请选择素材！',
          type: 'warning'
        })
        return
      }
      const params = createVideoData()
      const { err, res } = await this.$post('https://tjzm.tmsx.net/v1/audio-creation/create-audio-creation-task', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.timeLineLoading = false
      this.$router.push({
        path: '/manage-center/creative-center',
        query: {
          tab: '语音创作'
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
.title-wrap {
  display: flex;
  justify-content: center;
  align-items: center;

  .subtitletitel {
    margin-right: 10px;
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

.audio-subtitle {
  width: 100%;
  margin-top: 15px;
  padding: 30px;
  box-sizing: border-box;
  border-radius: 4px;
  background: #fff;
  margin-bottom: 20px;

  .title-wrap {
    display: flex;
    align-items: center;

    .edit {
      margin-right: 10px;
    }

    .subtitletitel {
      width: 300px;
    }
  }
}
</style>
