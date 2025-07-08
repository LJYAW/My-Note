<!--
 * @Author: your name
 * @Date: 2020-12-08 14:20:21
 * @LastEditTime: 2020-12-22 11:57:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/share_video.vue
-->
<template>
  <div>
    <model :id="'shareVideoM'"
      @close="close">
      <div slot="body"
        class="d-flex flex-column">
        <div class='d-flex justify-content-center align-items-center flex-column'>
          <vue-qr :text="qr_code" :size="200"></vue-qr>
          <p>请使用微信扫一扫进行分享</p>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
import vueQr from 'vue-qr'
export default {
  props: ['qr_code'],
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {
    close() {
      this.$emit('Modalclose')
    }
  },
  components: { vueQr },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
#shareVideoM {
  /deep/.modal-wrap {
    max-width: 300px;
    img {
      width: 250px;
    }
    p {
      color: #999;
      margin-bottom: 80px;
    }
  }
}
</style>
