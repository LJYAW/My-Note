<!--
 * @Author: your name
 * @Date: 2021-07-02 17:06:12
 * @LastEditTime: 2021-07-12 11:28:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/home/components/notice.vue
-->
<template>
  <div class="notes-notice">
    <div class="notes-box">
      <div class="title">备忘公告</div>
      <div class="btn" :class="!disabled?'activeClass':''" @click="edit()">{{ disabled?'编辑':'保存' }}</div>
    </div>
    <div class="notes-textarea">
      <el-input v-model="desc" type="textarea" :disabled="disabled" />
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      desc: '',
      disabled: true
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
    edit() {
      this.disabled = !this.disabled
    }
  }
}
</script>

<style scoped lang="scss">

</style>
