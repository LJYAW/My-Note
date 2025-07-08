<!--
 * @Author: your name
 * @Date: 2021-04-13 11:28:23
 * @LastEditTime: 2021-11-05 12:01:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/components/base/Image.vue
-->
<template>
  <el-image :src="imgSrc" :fit="fit" @error="error">
    <div slot="error" class="image-slot">
      <svg-icon icon-class="img-error" @click="changeRange('minus')" />
    </div>
  </el-image>
</template>

<script>
export default {
  name: 'BaseImage',
  components: {

  },
  props: {
    src: {
      type: String,
      default() {
        return ''
      }

    },
    fit: {
      type: String,
      default() {
        return 'cover'
      }
    }
  },
  data() {
    return {

    }
  },
  computed: {
    imgSrc() {
      if (this.src && this.src.indexOf('http') !== -1) {
        return this.src
      }
      return this.$store.state.user.fileUrl + '/' + this.src
    }
  },
  methods: {
    error() {
      this.$emit('error')
    }
  }
}
</script>

<style scoped lang="scss">
.el-image {
  // margin-right: 3px;
  height: 100%;
  align-items: center;
  display: flex;
  justify-content: center;

  svg {
    font-size: 28px;
  }
}
</style>
