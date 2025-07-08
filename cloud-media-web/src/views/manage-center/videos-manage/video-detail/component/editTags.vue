<!--
 * @Author: your name
 * @Date: 2021-08-06 15:07:38
 * @LastEditTime: 2021-09-01 17:29:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/videos-manage/video-detail/component/editTags.vue
-->
<template>
  <div>
    <div
      class="input-box"
    >
      <el-input
        v-model="tag"
        placeholder=""
        class=""
        @keyup.enter.native="addTag"
      >
        <el-button slot="append" class="button" @click="addTag">新增</el-button>
      </el-input>
    </div>
    <el-tag
      v-for="str in label"
      :key="str"
      closable
      @close="removeTag(str)"
    >
      {{ str }}
    </el-tag>

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
      tag: '',
      labels: this.videoData.labels
    }
  },
  computed: {
    label() {
      return this.labels.split(',').filter(function(s) {
        return s && s.trim()
      })
    }
  },
  watch: {
    videoData(newVal) {
      this.labels = newVal.labels
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    addTag() {
      if (!this.tag.trim()) {
        this.$message.error('不能添加空标签')
        return
      }
      if (this.label.includes(this.tag)) {
        this.$message.error('不能添加重复标签')
        return
      }
      const arr = this.label
      arr.push(this.tag)
      this.tag = ''
      this.labels = arr.join(',')
    },
    removeTag(e) {
      this.labels = this.labels.split(',').filter(item => item !== e).join(',')
    },
    save() {
      this.$emit('modify', { labels: this.labels })
    }
  }
}
</script>

<style scoped lang="scss">
.input-box {
  margin-top: 30px;
  margin-bottom: 20px;
}

::v-deep.el-tag {
  margin-right: 5px;
  margin-bottom: 5px;
  background: #e5eaff;
  border-color: #e5eaff;
  color: #404040;

  .el-tag__close {
    color: #404040;

    &:hover {
      background: #e5eaff;
    }
  }
}

::v-deep.el-input {

  .el-input__suffix {
    line-height: 32px;
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

  .el-input-group__append {
    border: 1px solid #5675e8;
    background: #5675e8;
  }

}

.btn {
  margin-top: 35px;
  width: 100%;
}
</style>
