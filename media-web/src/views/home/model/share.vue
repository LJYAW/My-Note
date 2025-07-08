<!--
 * @Author: your name
 * @Date: 2021-07-28 19:00:18
 * @LastEditTime: 2021-08-17 15:05:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/model/share.vue
-->
<template>
  <div class="share-success" @click.self="close">
    <div class="success-box">
      <div class="success-header">分享成功</div>
      <div class="success-body">
        <div class="title">通过连接分享</div>
        <div class="success-link">
          <!-- <input v-model="shareId" type="text"> -->
          <div class="input">{{ link }}</div>
          <div
            v-clipboard:copy="link"
            v-clipboard:success="onCopy"
            v-clipboard:error="onError"
            class="copy-btn"
          >复制链接</div>
        </div>
        <div class="desc">该连接仅24小时内有效</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    shareId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {

    }
  },
  computed: {
    link() {
      return window.location.protocol + '//' + window.location.host + '/share-video?id=' + this.shareId
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    close() {
      this.$bus.$emit('shareFlag', false)
      this.$parent.isShare = false
      this.$emit('close')
    },
    onCopy(e) {
      this.close()
      this.$message.success('内容已复制到剪切板！')
    },
    // 复制失败时的回调函数
    onError(e) {
      this.close()
      this.$message.error('抱歉，复制失败！')
    }
  }
}
</script>

<style scoped lang="scss">
.share-success {
  position: fixed;
  top: 0;
  left: 0;
  background: rgba(0,0,0,.5);
  width: 100vw;
  height: 100vh;
  z-index: 2021;
  display: flex;
  justify-content: center;
  align-items: center;

  .success-box {
    width: 500px;
    height: 236px;
    background: #fff;

    .success-header {
      height: 100px;
      background: linear-gradient(135deg, #7d99ff 0%, #5675e8 100%);
      font-size: 42px;
      font-weight: 600;
      color: #fff;
      line-height: 42px;
      line-height: 100px;
      text-align: center;
    }

    .success-body {
      padding: 20px;

      .title {
        font-size: 14px;
        font-weight: 400;
        color: #404040;
        margin-bottom: 10px;
      }

      .success-link {
        height: 30px;
        display: flex;

        .input {
          flex: 1;
          text-indent: 12px;
          height: 100%;
          line-height: 30px;
          background: #f5f5f6;
          font-size: 12px;
          font-weight: 400;
          color: #7e7e7e;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        .copy-btn {
          width: 68px;
          height: 30px;
          background: #5675e8;
          border-radius: 4px;
          line-height: 30px;
          font-size: 12px;
          font-weight: 600;
          text-align: center;
          color: #fff;
          cursor: pointer;
        }
      }

      .desc {
        margin-top: 20px;
        font-size: 12px;
        text-align: center;
        color: #404040;
      }
    }
  }
}
</style>
