<!--
 * @Author: your name
 * @Date: 2021-04-16 09:54:25
 * @LastEditTime: 2021-05-10 11:37:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/components/table/selfTable.vue
-->

<template>
  <div class="table">

    <div class="item">
      <div class="item-header">品牌</div>
      <div class="item-content">
        <div class="edit" @click="edit('品牌', 'brandEditDialog')">编辑</div>
        <el-popover
          v-for="(items, i) in list.brandMap"
          :key="i"
          placement="bottom-start"
          width="10"
          trigger="hover"
        >
          <div class="btn" @click="addKeyword(items, i)">添加特殊提报</div>
          <div slot="reference">{{ items }}</div>
        </el-popover>
      </div>
    </div>

    <div class="item">
      <div class="item-header">企业Logo</div>
      <div class="item-content">
        <el-popover
          v-for="(items, i) in list.logoMap"
          :key="i"
          placement="bottom-start"
          width="10"
          trigger="hover"
        >
          <div class="btn" @click="addImg(items, i)">添加特殊提报</div>
          <div
            slot="reference"
            style="color: #2971c1"
            @click="goImgDialog(items)"
          >{{ items }}</div>
        </el-popover>
      </div>
    </div>

    <div class="item">
      <div class="item-header">人物名称</div>
      <div class="item-content">
        <div class="edit" @click="edit('人物', 'personageEditDialog')">编辑</div>
        <el-popover
          v-for="(items, i) in list.personageMap"
          :key="i"
          placement="bottom-start"
          width="10"
          trigger="hover"
        >
          <div class="btn" @click="addKeyword(items, i)">添加特殊提报</div>
          <div slot="reference">{{ items }}</div>
        </el-popover>
      </div>
    </div>

    <div class="item">
      <div class="item-header">人物头像</div>
      <div class="item-content">
        <el-popover
          v-for="(items, i) in list.avatarMap"
          :key="i"
          placement="bottom-start"
          width="10"
          trigger="hover"
        >
          <div class="btn" @click="addImg(items, i)">添加特殊提报</div>
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

    <BaseDialog :title="dialogTit" :show.sync="dialogVisible" width="900px" class="dialog">
      <compontents
        :is="dialogCompomtentsName"
        :dialog-table-data="dialogTableData"
        :dialog-table-data-personage="dialogTableDataPersonage"
        @addList="addList(...arguments)"
        @deleteList="deleteList(...arguments)"
        @submit="submit"
        @goImgDialog="goImgDialog"
      />
    </BaseDialog>
  </div>
</template>

<script>
import brandEditDialog from '../model/brandEditDialog'
import personageEditDialog from '../model/personageEditDialog'

import { checkTableData } from '../../js/utils'
import { get } from '@/axios/httpBd.js'

export default {
  components: { brandEditDialog, personageEditDialog },
  props: {
    list: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      dialogTit: '',
      dialogVisible: false,
      dialogCompomtentsName: '',
      logoMap: [],
      avatarMap: [],
      dialogImgTit: '图片库',
      dialogImgVisible: false,
      dialogImgArray: []
    }
  },
  computed: {
    dialogTableData() {
      return checkTableData(this.list.brandMap, this.list.logoMap)
    },
    dialogTableDataPersonage() {
      return checkTableData(this.list.personageMap, this.list.avatarMap)
    },
    keywordList() {
      return this.$store.state.monitor.reportList
    }
  },
  watch: {
    dialogTableData: {
      handler(val) {},
      deep: true
    },
    dialogTableDataPersonage: {
      handler(val) {},
      deep: true
    }
  },
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
    addImg(item, i) {
      !this.keywordList.images.includes(item) ? this.keywordList.images.push(item) : this.$message.error('请勿重复添加')
    },
    addKeyword(item, i) {
      !this.keywordList.keyword.includes(item) ? this.keywordList.keyword.push(item) : this.$message.error('请勿重复添加')
    },
    edit(title, name) {
      this.dialogTit = title
      this.dialogCompomtentsName = name
      this.dialogVisible = true
    },
    addList(name, type) {
      this.$emit('addCompetitor', name, type)
    },
    deleteList(index, type) {
      type === 'brand' ? this.dialogTableData.splice(index, 1) : this.dialogTableDataPersonage.splice(index, 1)
    },
    submit() {
      this.dialogVisible = false
      const list = {
        brandMap: [],
        logoMap: [],
        personageMap: [],
        avatarMap: []
      }
      this.dialogTableData.forEach((item) => {
        list.brandMap.push(item.personage)
        item.status ? list.logoMap.push(item.personage) : ''
      })
      this.dialogTableDataPersonage.forEach((item) => {
        list.personageMap.push(item.personage)
        item.status ? list.avatarMap.push(item.personage) : ''
      })
      this.$emit('changeKeyword', list)
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
