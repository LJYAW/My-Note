<!--
 * @Author: your name
 * @Date: 2021-08-06 14:08:03
 * @LastEditTime: 2021-09-01 17:30:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/video-detail/component/editTItle.vue
-->
<template>
  <div>
    <el-input v-model="title" placeholder="请输入视频名称" size="normal" clearable />

    <el-button type="primary" size="small" @click="save">确定</el-button>

  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    videoData: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      title: this.videoData.titles
    }
  },
  computed: {

  },
  watch: {
    videoData(newVal) {
      this.title = newVal.titles
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    save() {
      if (!this.title) {
        this.$message.error('视频名称不能为空')
        return
      }
      this.$emit('modify', { titles: this.title })
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep.el-input {
  margin-top: 30px;
  margin-bottom: 20px;

  .el-input__suffix {
    line-height: 30px;
    display: flex;
    align-items: center;
  }

  .el-input__inner {
    border-color: transparent;
    background: #f7f8f9;
    font-size: 14px;
    box-sizing: border-box;
    height: 30px;

    &::placeholder {
      color: rgba(64,64,64,.2);
    }

    &:focus {
      border-color: #5675e8;
    }
  }

}

.el-button {
  width: 100%;
}
</style>
