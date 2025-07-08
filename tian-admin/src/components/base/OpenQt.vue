<!--
 * @Author: your name
 * @Date: 2021-01-23 13:34:54
 * @LastEditTime: 2021-02-26 11:02:44
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/components/base/OpenQt.vue
-->
<template>
  <el-button type="text" :loading="loading" @click="openQTweb()">{{ btnName }}</el-button>
</template>

<script>
import { getToken } from '@/utils/auth' // get token from cookie

export default {
  name: 'BaseOpenQt',
  components: {},
  props: {
    taskId: {
      type: Number,
      default: null
    },
    btnName: {
      type: String,
      default: '开始任务'
    }
  },
  data() {
    return {
      loading: false
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    openQTweb() {
      /* eslint-disable */
      const token = getToken();
      const id = this.taskId;
      this.loading = true
      
      let url = '/production/start-task'

      if (this.btnName == '查看详情') {
        url = '/production/view-task'
      }

      this.$post(url, { task_id: id })
        .then((res) => {
          const { data } = res;

          if (data.status) {
            try {

              var webChannel = new QWebChannel(
                qt.webChannelTransport, // 这里的webChannel是全局的变量，可以在其它位置访问
                function (channel) {
                  window.TaskEditor = channel.objects.TaskEditor;
                  window.TaskEditor.onEditTask(id, token);
                }
              );
              // setTimeout(() => {
              //   if (window.TaskEditor) {
              //     window.TaskEditor.onEditTask(id, token);
              //   }
              // })
            } catch (error) {
              alert(error.message); // 接住抛出的自定义错误
            }
          } else {
            this.$message({
                message: data.msg,
                type: "error",
            })
          }
            this.loading = false
        })
        .catch((res) => {
          this.loading = false
          this.$message({
            message: res.msg,
            type: "error",
          })
        })
    },
  },
};
</script>

<style scoped lang="scss">
</style>
