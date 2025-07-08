<!--
 * @Author: your name
 * @Date: 2021-05-11 16:50:53
 * @LastEditTime: 2021-05-14 11:04:13
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/sample_queue/components/ContinueAdd.vue
-->
<template>
  <base-dialog
    :show="show"
    title="出现重复样本，请确定是否继续添加！重复样本见下："
    width="800px"
    class="to-add-dialog"
    :close-on-click-modal="false"
    @close="close"
  >
    <div v-loading="loading" class="sample-content">
      <div v-for="item in sampleData" :key="item.id" class="sample-wrap">
        <div class="sample-wrap-left">
          <video-player
            ref="videoPlayer"
            :key="item.id"
            :options="getPlayOption(item)"
            :playsinline="true"
          />
        </div>

        <div class="sample-wrap-right">
          <p>样本 ID：{{ item.id }}</p>
          <p>标题 ：{{ item.title }}</p>
          <p>分类 {{ item.tags }}</p>
          <p>品牌：{{ item.brand }}</p>
          <p>样本来源 ：{{ item.channel_name }} {{ item.updated_at }}</p>
        </div>
      </div>
    </div>

    <div class="btn-wrap">
      <base-btn @click="confirm">确定添加</base-btn>
      <base-btn type="info" @click="cancel">取消添加</base-btn>
    </div>
  </base-dialog>
</template>

<script>
export default {
  components: {

  },
  props: {
    show: {
      type: Boolean,
      default() {
        return false
      }
    },
    modelData: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      playerOptions: {
        height: '360',
        autoplay: true,
        muted: false,
        language: 'en',
        fluid: true,
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [
          {
            type: 'video/mp4',
            // mp4
            src: ''
          }
        ],
        poster:
          'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg'
      },
      sampleData: [],
      loading: true
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getSampleData()
  },
  mounted() {

  },
  methods: {
    close() {
      this.$emit('close')
    },
    confirm() {
      const params = {
        data_id: this.modelData.id,
        action: 'go-on',
        seq: {
          title: this.modelData.title,
          brand: this.modelData.brand,
          tags: this.modelData.tags
        }
      }
      this.$post('/ad-sample/queue-modify', params).then((res) => {
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        this.close()
        this.$emit('update')
      }).catch((error) => {
        this.$message({
          type: 'warning',
          message: error.msg
        })
      })
    },
    cancel() {
      this.close()
    },
    getSampleData() {
      this.loading = true
      const params = {
        sample_ids: this.modelData.sample_ids,
        page: 1,
        limit: 30,
        show_delete: 1
      }
      this.$get('/ad-sample/search', params).then((res) => {
        this.sampleData = res.data.list
      }).catch((error) => {
        this.$message({
          type: 'error',
          message: error.msg
        })
      }).finally(() => {
        this.loading = false
      })
    },
    getPlayOption(item) {
      return {
        height: '360',
        autoplay: false,
        muted: false,
        language: 'en',
        fluid: true,
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [
          {
            type: 'video/mp4',
            // mp4
            src: item.mp4_url
          }
        ],
        poster:
          'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg'
      }
    }
  }
}
</script>

<style scoped lang="scss">
.to-add-dialog{
    display: flex;
    flex-direction: column;
    ::v-deep .el-dialog{
        display: flex;
        flex: 1;
        flex-direction: column;
        height: 0;
        margin-bottom: 10vh;
        .el-dialog__body{
            display: flex;
            flex: 1;
            height: 0;
            flex-direction: column;
            .sample-content{
                display: flex;
                flex-direction: column;
                flex: 1;
                height: 0;
                overflow-y: auto;
            }
        }
    }
}
.sample-wrap{
    display: flex;
    margin-bottom: 20px;
    .sample-wrap-left{
        // flex: 1;
        width: 410px;
    }
    .sample-wrap-right{
        flex: 1;
        margin-left: 20px;
        margin-right: 10px;
        padding-top: 10px;
        p{
            color: #333;
            margin-bottom: 11px;
        }
    }
}
.btn-wrap{
    display: flex;
    margin-top: 30px;
    justify-content: center;
}
</style>
