<!--
 * @Author: your name
 * @Date: 2021-08-30 11:08:43
 * @LastEditTime: 2021-08-30 11:38:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/ImageSkeleton/index.vue
-->
<template>
  <el-skeleton
    style="width: 100%;height: 100%"
    :loading="loading"
    animated
  >
    <template slot="template">
      <el-skeleton-item
        variant="image"
        style="width: 100%; height: 0;padding-bottom: 55%"
      />
    </template>
    <template>
      <el-card :body-style="{ padding: '0px'}">
        <img
          :src="imgSrc"
          class="image"
        >
      </el-card>
    </template>
  </el-skeleton>
</template>

<script>
export default {
  components: {

  },
  props: {
    imgSrc: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      loading: true
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.$nextTick(() => {
      setTimeout(() => {
        this.loading = false
      }, 500)
    })
  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">

::v-deep.el-skeleton__image svg {
  position: absolute;
  top: 40%;
}
</style>
