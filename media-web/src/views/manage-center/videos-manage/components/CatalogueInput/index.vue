<!--
 * @Author: your name
 * @Date: 2021-09-14 15:21:54
 * @LastEditTime: 2021-09-16 11:22:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/components/CatalogueInput/index.vue
-->
<template>
  <div class="catalogue-input">
    <div class="upload-input">
      <div class="input-tit">视频目录</div>
      <el-select
        v-model="project"
        value-key="name"
        placeholder=""
        @change="dirChange"
      >
        <el-option
          v-for="item in list"
          :key="item.id"
          :label="item.name"
          :value="item"
        />
      </el-select>
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
      dir_id: '', // 目录id
      project: '',
      list: []
    }
  },
  computed: {

  },
  watch: {
    dir_id: {
      handler(val, oldVal) {
        this.$emit('getDirId', val)
      },
      deep: true,
      immediate: true
    }
  },
  created() {
    this.$store.state.user.dirInfo.forEach(item => {
      const obj = {}
      obj.name = item.dir_name
      obj.id = item.dir_id
      this.list.push(obj)
    })

    const defalutItem = this.list.find(item => item.name === '通用视频')
    this.project = defalutItem
    this.dir_id = defalutItem.id
  },
  mounted() {

  },
  methods: {
    dirChange(e) {
      this.dir_id = e.id
    }
  }
}
</script>

<style scoped lang="scss">

.upload-input {
  width: 100%;
  height: 30px;
  display: flex;
  background: #f2f2f2;
  border-radius: 4px;
  align-items: center;
  margin-bottom: 10px;
  box-sizing: border-box;

  &.upload-input-tag {
    width: 66%;
    overflow: hidden;

    .input {
      width: 70px;
    }

    .tip-img {
      width: 10px;
    }
  }

  .input-tit {
    flex-shrink: 0;
    width: 58px;
    font-size: 12px;
    color: #404040;
    margin: 9px;
    padding-right: 9px;
    box-sizing: border-box;
    border-right: 1px solid #d8d8d8;
  }

  .input {
    flex: 1;
    font-size: 12px;
    color: #404040;
  }

  .el-select {
    flex: 1;

    ::v-deep.el-input__inner {
      height: 28px;
      font-size: 12px;
      background: #f2f2f2;
      border: 0;
      padding-left: 0;
    }

    ::v-deep.el-input__icon {
      line-height: 30px;
    }
  }
}
</style>
