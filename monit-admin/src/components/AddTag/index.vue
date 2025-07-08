<!--
 * @Author: your name
 * @Date: 2021-07-05 16:51:28
 * @LastEditTime: 2021-07-15 18:12:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/components/AddTag/index.vue
-->
<template>
  <div class="add-tag">
    <p class="title">增加标签</p>

    <div class="form-wrap">
      <el-input v-model.trim="tagName" placeholder="请输入标签内容" maxlength="8" />
      <base-btn class="add-btn" @click="addTag">增加</base-btn>
    </div>

    <div class="tag-wrap">
      <div v-for="(item) in tagList" :key="item.names" class="tag-item">{{ item.names }}
        <i v-if="showClose" class="el-icon-error" @click="deleteTag(item.id)" />
      </div>
    </div>

    <div class="btn-wrap">
      <base-btn class="btn" @click="close">返回</base-btn>
      <!-- <base-btn v-if="showClose" class="btn" @click="clear">清空所有</base-btn> -->
      <base-btn v-if="!showClose" class="btn" @click="update">编辑</base-btn>
      <!-- <base-btn class="btn" @click="save">保存</base-btn> -->
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    tagList: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
      showClose: false,
      tagName: ''
      // tempTagList: []
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
    update() {
      this.showClose = true
    },
    close() {
      if (this.showClose) {
        this.showClose = false
        return
      }
      this.$emit('close')
    },
    deleteTag(id) {
      this.$emit('deleteTag', id)
    },
    clear() {
      this.$confirm('<h2>此操作将永久删除该文件, 是否继续?</h2><p>部分标签正在使用，删除后可能导致无法命中。</p>', '', {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.tempTagList = []
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消清空操作'
        })
      })
    },
    addTag() {
      const arr = this.tagList.filter(item => item.names === this.tagName)
      if (!this.tagName) {
        this.$message({
          type: 'warning',
          message: '请输入标签内容'
        })
        return
      }
      if (arr.length) {
        this.$message({
          type: 'warning',
          message: '添加的标签已存在'
        })
        return
      }
      this.$emit('addTag', this.tagName)
    }
  }
}
</script>

<style scoped lang="scss">
.add-tag {
  padding: 0 30px;

  .title {
    font-size: 48px;
    font-weight: 600;
    text-align: center;
    color: #000;
    margin-bottom: 50px;
  }

  .form-wrap {
    text-align: center;
    display: flex;
    justify-content: center;

    .el-input {
      width: 40%;
      height: 60px;

      ::v-deep input {
        height: 100%;
      }
    }

    .add-btn {
      padding: 17px 52px;
    }
  }

  .tag-wrap {
    margin-top: 65px;
    max-height: 300px;
    overflow-y: auto;

    .tag-item {
      display: inline-block;
      padding: 13px 46px;
      font-size: 14px;
      color: #000;
      background: #eaf1fe;
      border-radius: 4px;
      margin-right: 20px;
      margin-bottom: 20px;
      position: relative;

      .el-icon-error {
        font-size: 20px;
        position: absolute;
        top: -8px;
        right: -8px;
        z-index: 9;
        cursor: pointer;
      }
    }
  }

  .btn-wrap {
    text-align: center;
    margin-top: 55px;

    .btn {
      width: 150px;
      height: 40px;
      background: #9aacf1;
      border-radius: 4px;
      border: none;
      margin-right: 30px;
    }
  }
}

</style>
