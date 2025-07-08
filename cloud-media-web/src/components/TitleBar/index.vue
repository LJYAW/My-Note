<!--
 * @Author: your name
 * @Date: 2021-08-03 17:28:09
 * @LastEditTime: 2021-08-03 17:54:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/Title/index.vue
-->
<template>
  <div class="title-bar">
    <span>{{ title }}</span>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    title: {
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
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.title-bar {
  color: #404040;
  font-size: 18px;

  span {
    display: inline-block;
    padding-bottom: 10px;
    border-bottom: 2px solid #5675e8;
  }
}
</style>
