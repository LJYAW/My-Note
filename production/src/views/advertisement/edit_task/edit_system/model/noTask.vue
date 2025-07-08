<!--
 * @Author: your name
 * @Date: 2021-03-31 18:22:40
 * @LastEditTime: 2021-03-31 20:06:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/examine_task/model/noTask.vue
-->
<template>
  <base-dialog
    :show="show"
    :close-on-click-modal="false"
    :show-close="false"
    class="no-task-model"
    @close="close"
  >
    <p>暂时没有任务了！</p>
    <p>{{ second }}秒后回到编辑任务页面</p>
  </base-dialog>
</template>

<script>
export default {
  components: {

  },
  props: {
    show: {
      type: Boolean,
      default() {
        return false
      }
    }
  },
  data() {
    return {
      second: 3,
      timer: null
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.timer = setInterval(() => {
      this.second--
      if (this.second === 0) {
        this.close()
        this.$router.push({
          path: '/advertisement/edit-task'
        })
      }
    }, 1000)
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  mounted() {

  },
  methods: {
    close() {
      this.$emit('close')
    }
  }
}
</script>

<style scoped lang="scss">
.no-task-model{
    p{
        text-align: center;
        margin-bottom: 10px;
        color: #333;
    }
}
</style>
