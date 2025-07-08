<!--
 * @Author: your name
 * @Date: 2020-09-24 11:29:25
 * @LastEditTime: 2020-12-02 17:08:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/show_video_m.vue
-->
<template>
  <div>
    <model :id="'showVideoM'"
      @close="close">
      <div slot="title"
        v-if="video_details.title">
        <span class="fz-16px">{{video_details.title}}</span>
      </div>
      <div slot="body"
        class="d-flex justify-content-center pb-50px videom-video-wrap">
        <videoPalyer v-if="video_options.src"
          :video_options="video_options" />
      </div>
    </model>
  </div>
</template>

<script>
export default {
  name: 'showVideoM',
  props: ['video_options', 'video_details'],
  data() {
    return {}
  },
  components: {},

  computed: {},

  watch: {},

  methods: {
    close() {
      this.$emit('Modalclose')
    }
  },

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.videom-video-wrap {
  width: 640px;
  height: 390px;
  margin: 0px auto;
  margin-bottom: 50px;
  #videoWrap {
    width: 100%;
    height: 100%;
    /deep/.video-js.vjs-fluid {
      padding-top: 56.25%;
    }
  }
}
</style>
