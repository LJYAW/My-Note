<!--
 * @Author: your name
 * @Date: 2021-05-27 18:28:11
 * @LastEditTime: 2021-05-28 10:17:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/task_mgt/model/addAnalyzerDialog.vue
-->
<template>
  <div>
    <el-input v-model="person" placeholder="请输入您的姓名" />
    <div class="btn-wrap">
      <base-btn @click="submit">确定</base-btn>
      <base-btn type="info" @click="cancel">取消</base-btn>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      person: ''
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
    close() {
      this.$emit('close')
    },
    cancel() {
      this.close()
    },
    submit() {
      this.$emit('updateData')
      this.close()
    }
  }
}
</script>

<style scoped lang="scss">
.btn-wrap{
    margin-top: 40px;
    text-align: center;
}
</style>
