<!--
 * @Author: your name
 * @Date: 2021-04-13 11:28:23
 * @LastEditTime: 2021-09-23 19:46:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/components/base/Image.vue
-->
<template>
  <el-image :src="src" @error="error">
    <div slot="error" class="image-slot">
      <!-- <i class="el-icon-picture-outline" /> -->
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
    }
  },
  data() {
    return {

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
  display: flex;
  height: 100%;
  align-items: center;
  justify-content: center;

  svg {
    font-size: 28px;
  }
}
</style>
