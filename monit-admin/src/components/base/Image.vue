<!--
 * @Author: your name
 * @Date: 2021-04-13 11:28:23
 * @LastEditTime: 2021-04-13 11:39:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/components/base/Image.vue
-->
<template>
  <el-image :src="src">
    <div slot="error" class="image-slot">
      <i class="el-icon-picture-outline" />
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
  }
}
</script>

<style scoped lang="scss">
.el-image{
    margin-right: 3px;
    background: #eee;
    display: flex;
    align-items: center;
    justify-content: center;
    i{
        font-size: 18px;
    }
}
</style>
