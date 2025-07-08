<!--
 * @Author: your name
 * @Date: 2021-07-27 17:13:05
 * @LastEditTime: 2021-09-16 14:12:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/select/index.vue
-->
<template>
  <div class="screen-bar">
    <el-button v-if="!batch" type="info" size="mini" class="batch-btn" @click="handlebatch">批量管理</el-button>
    <div v-else>
      <el-button type="danger" size="small" @click="handleDelete">移除</el-button>
      <el-button type="info" size="small" @click="handlCancel">取消</el-button>
    </div>
    <div class="videoSum">共有{{ count }}个内容</div>
  </div>

</template>

<script>

export default {
  components: {
  },
  props: {
    count: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      batch: false
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
    // 批量管理
    handlebatch() {
      this.$bus.$emit('batchFlag', true)
      this.batch = true
    },
    // 取消
    handlCancel() {
      this.$bus.$emit('batchFlag', false)
      this.batch = false
      this.$store.commit('videoStatus/BATCH_VIDEO_CHANGE', [])
    },
    // 全部删除
    handleDelete() {
      const pArr = []
      const arr = this.$store.state.videoStatus.batchVideo
      if (!arr.length) {
        this.$message.warning('请选择要删除的视频')
        return
      }
      for (let i = 0; i < arr.length; i++) {
        const p = this.delete(arr[i])
        pArr.push(p)
      }
      Promise.all(pArr).then(() => {
        // this.page = 1
        this.$emit('onChange')
        this.$message.success('删除成功')
        this.handlCancel()
      })
    },
    async delete(id) {
      const { err, res } = await this.$deleteRun(`/favorites/${id}`)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
    }
  }
}
</script>

<style scoped lang="scss">
.screen-bar {
  display: flex;
  align-items: center;
  margin: 20px 0;
  position: relative;

  .select-bar {
    display: flex;
    justify-content: flex-start;
    width: 240px;
    overflow: hidden;
    border-radius: 4px;

    ::v-deep.select-item {
      position: relative;
      z-index: 1;

      &::after {
        content: "";
        position: absolute;
        right: 0;
        top: 8px;
        height: 15px;
        width: 1px;
        background: #d8d8d8;
      }

      &:last-child {

        &::after {
          display: none;
        }
      }

    }
  }

  .videoSum {
    font-size: 12px;
    color: #bababa;
    position: absolute;
    right: 0px;
  }

  ::v-deep .batch-btn {
    opacity: .8;

    span {
      opacity: .8;
    }
  }
}
</style>
