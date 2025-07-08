<!--
 * @Author: your name
 * @Date: 2021-08-10 10:14:27
 * @LastEditTime: 2021-09-01 17:29:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/video-detail/component/editProject.vue
-->
<template>
  <div>
    <el-select
      v-model="project"
      placeholder="请选择视频目录"
      filterable
      value-key="name"
    >
      <el-option
        v-for="item in option"
        :key="item.id"
        :label="item.name"
        :value="item"
      />
    </el-select>

    <el-button type="primary" size="small" class="btn" @click="save">确定</el-button>

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
      project: {
        id: this.videoData.dir_id,
        name: this.videoData.dir_name
      }
      // options: ['目录1'] // 拥有权限目录

    }
  },
  computed: {
    option() {
      const arr = []
      this.$store.state.user.dirInfo.forEach(item => {
        const obj = {}
        obj.name = item.dir_name
        obj.id = item.dir_id
        arr.push(obj)
      })
      return arr
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    save() {
      this.$emit('modify', { dir_id: this.project.id, dir_name: this.project.name })
    }
  }
}
</script>

<style scoped lang="scss">
.el-select {
  width: 100%;
  margin-top: 30px;

  ::v-deep.el-input__inner {
    height: 30px;
    font-size: 12px;
    background: #f8f8f8;
    border: 0;
  }

  ::v-deep.el-input__icon {
    line-height: 30px;
  }
}

.btn {
  margin-top: 35px;
  width: 100%;
}
</style>
