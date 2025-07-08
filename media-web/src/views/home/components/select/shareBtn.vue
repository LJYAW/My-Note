<!--
 * @Author: your name
 * @Date: 2021-07-28 17:00:45
 * @LastEditTime: 2021-08-23 14:41:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/select/shareBtn.vue
-->
<template>
  <div class="share-wrap">
    <div v-if="!isShare" class="shareBtn" @click="handleShare">分享</div>
    <div v-else class="share-choose">
      <div class="finish isShare" @click="finishShare">完成</div>
      <div class="cancel shareBtn" @click="handleCancel">取消</div>
    </div>
    <shareWrap v-show="isSuccess" :share-id="shareId" @close="closeModel" />
  </div>
</template>

<script>
import shareWrap from '../../model/share.vue'
export default {
  components: {
    shareWrap
  },
  props: {

  },
  data() {
    return {
      isShare: false,
      isSuccess: false,
      shareId: 0
    }
  },
  computed: {

  },
  watch: {
    shareFlag: {
      handler: function(val, oldVal) {
        console.log(val)
      }
    }
  },
  created() {
    this.$bus.$on('params', (e) => {
      this.initVal(e)
    })
  },
  mounted() {

  },
  methods: {
    initVal(e) {
      this.isShare = e.batchFlag
    },
    handleShare() {
      this.$bus.$emit('batchFlag', true)
      this.isShare = true
    },
    handleCancel() {
      this.$bus.$emit('batchFlag', false)
      this.isShare = false
      const arr = this.$store.state.videoStatus.batchVideo
      arr.splice(0)
      this.$store.commit('videoStatus/BATCH_VIDEO_CHANGE', arr)
    },
    async finishShare() {
      var str = this.$store.state.videoStatus.batchVideo.join(',')
      if (!this.$store.state.videoStatus.batchVideo.length) {
        this.$message.warning('请选择要分享的视频')
        return
      }
      const { res, err } = await this.$post('/videos/share/', { video_uuids: str })
      if (err) {
        this.$message.warning(err.msg)
        return
      }
      this.shareId = res.data.id
      this.isSuccess = true
    },
    closeModel() {
      this.handleCancel()
      this.isSuccess = false
    }
  }
}
</script>

<style scoped lang="scss">
.shareBtn {
  flex-shrink: 0;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
  text-align: center;
  cursor: pointer;
  background: rgba(239,239,239,.8);
  font-size: 12px;
  color: #404040;

  &:hover {
    opacity: .5;
    color: #5675e8;
  }
}

.isShare {
  flex-shrink: 0;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
  text-align: center;
  cursor: pointer;
  font-size: 12px;
  background: #5675e8;
  color: #fff;
}

.share-choose {
  display: flex;
}

.finish,
.cancel {
  border-radius: 4px;
}

.cancel {
  margin-left: 10px;
}
</style>
