<!--
 * @Author: zzx
 * @Date: 2020-07-06 11:20:28
 * @LastEditTime: 2020-11-04 18:11:54
 * @FilePath: /zhijian_web/src/components/show_img_m.vue
-->
<template>
  <div>
    <model :id="'showVideoM'"
      @close="close">
      <div slot="body"
        class="d-flex justify-content-center videom-video-wrap">
        <img :src="imgUrl">
      </div>
    </model>
  </div>
</template>

<script>
export default {
  name: 'showIMgM',
  props: ['img_url'],
  data() {
    return {}
  },
  components: {},

  computed: {
    imgUrl() {
      var str = new RegExp('http')
      var isHttp = str.test(this.img_url)
      if (isHttp) {
        return this.img_url
      } else {
        return require(`@/assets/${this.img_url}`)
      }
    }
  },

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
  background: url(/img/default.b629be3a.png) #e5e5e5 no-repeat top left;
  background-size: 100% 100%;
  img {
    object-fit: fill;
  }
  #videoWrap {
    width: 100%;
    height: 100%;
  }
}
</style>
