<!--
 * @Author: your name
 * @Date: 2021-02-26 11:02:09
 * @LastEditTime: 2021-03-02 18:36:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/components/base/EpgOpenQt.vue
-->
<template>
  <base-btn :loading="loading" size="big" @click="editSystem()">{{ btnName }}</base-btn>
</template>

<script>
import { getToken } from '@/utils/auth' // get token from cookie

export default {
  name: 'BaseEpgOpenQt',
  components: {},
  props: {
    // taskId: {
    //   type: Number,
    //   default: null
    // },
    btnName: {
      type: String,
      default: '进入编辑系统'
    }
  },
  data() {
    return {
      loading: false,
      taskId: null
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
      
      try {
        var webChannel = new QWebChannel(
            qt.webChannelTransport, // 这里的webChannel是全局的变量，可以在其它位置访问
            function (channel) {
                window.TaskEditor = channel.objects.TaskEditor;
                window.TaskEditor.onEditEPGTask(id, token);
            }
        );
      } catch (error) {
        alert(error.message); // 接住抛出的自定义错误
      }
    },
    editSystem(){
      this.loading = true
      this.$get('/epg-task/get-task').then((res) => {
        if(res.data.id){
            this.taskId=res.data.id
            this.loading = false
            this.openQTweb()
        }
      }).catch((error) => {
        this.loading = false
        this.$message.error(error.msg)
      })
    }
  },
};
</script>

<style scoped lang="scss">
</style>
