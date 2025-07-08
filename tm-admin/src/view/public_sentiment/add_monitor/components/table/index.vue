<!--
 * @Author: your name
 * @Date: 2021-04-12 14:11:16
 * @LastEditTime: 2021-04-27 16:58:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vue-beautiful-template/src/view/public_sentiment/add_monitor/table/index.vue
-->
<template>
  <div class="table">

    <div class="item">
      <div class="item-header">关键字</div>
      <div class="item-content">
        <el-popover
          v-for="(items, j) in list.keyword"
          :key="j"
          placement="bottom-start"
          width="10"
          trigger="hover"
        >
          <div class="btn" @click="deleteKeyword(j)">删除特殊提报</div>
          <div slot="reference">{{ items }}</div>
        </el-popover>
      </div>
    </div>

    <div class="item">
      <div class="item-header">图片</div>
      <div class="item-content">
        <el-popover
          v-for="(items, j) in list.images"
          :key="j"
          placement="bottom-start"
          width="10"
          trigger="hover"
        >
          <div class="btn" @click="deleteImg(j)">删除特殊提报</div>
          <div
            slot="reference"
            style="color: #2971c1"
            @click="goImgDialog(items)"
          >{{ items }}</div>
        </el-popover>
      </div>
    </div>

    <BaseDialog
      ref="dialog"
      :title="dialogImgTit + '的图片库'"
      :show.sync="dialogImgVisible"
      width="900px"
      class="dialog"
    >
      <div class="img-wrap d-flex flex-wrap">
        <div
          v-for="(item, i) in dialogImgArray"
          :key="i"
          class="img-item d-flex align-items-center justify-content-center"
        >
          <el-image :src="item" fit="scale-down" :lazy="true" />
        </div>
      </div>
    </BaseDialog>
  </div>
</template>

<script>
import { get, post, put, deleteRun } from '@/axios/httpBd.js'

export default {
  components: {},
  props: {},
  data() {
    return {
      dialogImgTit: '图片库',
      dialogImgVisible: false,
      dialogImgArray: []
    }
  },
  computed: {
    list() {
      return this.$store.state.monitor.reportList
    }
  },
  watch: {},
  created() {},
  mounted() {},
  methods: {
    async goImgDialog(name) {
      this.dialogImgTit = name
      this.dialogImgVisible = true
      const params = {
        brief: name
      }
      this.dialogImgArray = []
      const resLogo = await get('/image/api/v1/logo', params)
      this.dialogImgArray = this.dialogImgArray.concat(resLogo.res.data)
      const resFace = await get('/image/api/v1/face', params)
      this.dialogImgArray = this.dialogImgArray.concat(resFace.res.data)
    },
    deleteKeyword(index) {
      this.list.keyword.splice(index, 1)
    },
    deleteImg(index) {
      this.list.images.splice(index, 1)
    }
  }
}
</script>

<style scoped lang="scss">
.table {
  width: 100%;
  display: flex;
  .item {
    flex: 1;
    .item-header {
      background: #ebebeb;
      width: 100%;
      height: 45px;
      line-height: 45px;
      text-align: center;
    }
    .item-content {
      display: flex;
      justify-content: flex-start;
      align-content: flex-start;
      flex-wrap: wrap;
      border: 1px solid #bbbbbb;
      box-sizing: border-box;
      padding: 8px 16px;
      min-height: 282px;
      max-height: 282px;
      overflow: hidden;
      overflow-y: auto;
      span {
        flex-shrink: 0;
        display: block;
        color: rgba(16, 16, 16, 100);
        font-size: 14px;
        line-height: 20px;
        // height: 20px;
        margin-right: 14px;
        cursor: pointer;
      }

      .el-image {
        max-width: 110px;
      }
    }
  }
}
.btn {
  text-align: center;
  cursor: pointer;
}
.edit {
  display: block;
  width: 100%;
  color: rgba(41, 113, 193, 100);
  font-size: 12px;
  cursor: pointer;
  margin-bottom: 11px;
}
.img-item {
  position: relative;
  margin-right: 10px;
  background: #e3e3e3;
  margin-top: 10px;
}
</style>
