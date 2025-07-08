<!--
 * @Author: your name
 * @Date: 2021-04-15 19:05:48
 * @LastEditTime: 2021-05-10 10:46:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/components/model/beandDialog.vue
-->
<template>
  <div>
    <div class="d-flex align-items-center justify-content-between">
      <div>
        <el-select
          v-model="brandDialogData.dialogSelectVal"
          filterable
          placeholder="关键词搜索&下拉选择公司"
          clearable
          @change="selectChange"
        >
          <el-option
            v-for="item in brandDialogData.dialogOption"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
        <el-input
          v-model="brandDialogData.dialogSearch"
          placeholder="搜索品牌名称"
          suffix-icon="el-icon-search"
          @input="searchChange"
        />
      </div>
    </div>

    <div class="d-flex align-items-center justify-content-between" style="margin-top: 20px">
      <div>
        <el-input
          v-model="brandDialogData.dialogCompany"
          placeholder="添加公司名称"
        />
        <el-input
          v-model="brandDialogData.dialigBrand"
          placeholder="添加品牌名称"
        />
      </div>
      <div class="add-btn" @click="addItem">添加新一条</div>
    </div>

    <el-table :data="brandDialogData.dialogTableData" style="width: 100%">
      <el-table-column prop="company" label="公司" />
      <el-table-column prop="contents" label="品牌" />
      <el-table-column fixed="right" label="操作" style="position: relative">
        <template slot-scope="scope">
          <!-- <el-upload class="upload-demo" action="customize" multiple :show-file-list="false" :http-request="uploadImg" :limit="1">
            <el-button type="text" size="small" v-if="!scope.row.logo" @click="getBrief(scope)">上传图片</el-button>
          </el-upload> -->
          <el-button type="text" size="small" @click="goImgDialog(scope)">图片库</el-button>
          <el-popconfirm
            title="确定删除吗？"
            @confirm="deleteItem(scope)"
          >
            <el-button slot="reference" type="text" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page.sync="currentPage"
      hide-on-single-page
      :page-size="10"
      layout="prev, pager, next, jumper"
      :total="count"
      @current-change="currentChange"
    />

  </div>
</template>

<script>
import { validatorFn } from '@/utils/validate.js'
import rules from '../../js/rules'

export default {
  components: {},
  props: {},
  data() {
    return {
      // 监测实体dialog
      brandDialogData: {
        index: 0,
        dialogOption: [],
        dialogSelectVal: '',
        dialogSearch: '',
        dialogCompany: '',
        dialigBrand: '',
        dialogTableData: []
      },
      currentPage: 1,
      count: 0
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {
    this.initTopics()
  },
  methods: {
    async initTopics(params) {
      this.getSelect()
      const { res } = await this.$get('/api/topics', { ...params, query: `types:1,company__contains:${this.brandDialogData.dialogSelectVal},contents__contains:${this.brandDialogData.dialogSearch}` })
      this.brandDialogData.dialogTableData = res.data
      this.count = res.count
    },
    async getSelect() {
      const { res } = await this.$get('/api/topics', { limit: 10000, query: `types:1,company__contains:${this.brandDialogData.dialogSelectVal},contents__contains:${this.brandDialogData.dialogSearch}` })
      this.brandDialogData.dialogOption = Array.from(new Set(res.data.map(item => item.company)))
    },
    currentChange(val) {
      this.initTopics({ page: val })
    },
    selectChange(e) {
      this.initTopics()
    },
    searchChange(e) {
      this.initTopics()
    },
    goImgDialog(scope) {
      this.$emit('goImgDialog', scope.row.contents, 'brand')
    },
    // 添加
    addItem() {
      validatorFn(rules['addMointorBrand'], { name: this.brandDialogData.dialogCompany, brand: this.brandDialogData.dialigBrand }, () => {
        const parmas = {
          company: this.brandDialogData.dialogCompany,
          contents: this.brandDialogData.dialigBrand,
          types: 1
        }
        this.$post('/api/topics', parmas).then((data) => {
          console.log(data)
          const { err, res } = data
          if (err) {
            this.$message.error(err.msg)
          } else {
            this.brandDialogData.dialogCompany = ''
            this.brandDialogData.dialigBrand = ''
            this.$message.success('添加成功')
            this.initTopics()
          }
        })
      })
    },
    // 删除
    deleteItem(scope) {
      this.$deleteRun('/api/topics/' + scope.row.id).then((res) => {
        this.$message.success('删除成功')
        this.$emit('replaceBrand', scope.row.contents)
        this.initTopics()
      })
    }
  }
}
</script>

<style scoped lang="scss">
.dialog {
  ::v-deep.el-dialog {
    min-height: 50vh;
  }
  // .el-select {
  //   // width: 250px;
  // }
  .el-input {
    width: 300px;
  }
  .add-btn {
    color: rgba(41, 113, 193, 100);
    font-size: 14px;
    cursor: pointer;
  }
  .el-table {
    margin-top: 20px;
    img {
      width: 124px;
      height: 70px;
      object-fit: cover;
    }
  }
  .el-pagination {
    margin-top: 10px;
    ::v-deep.el-input{
      width: 50px!important;
    }
  }
}
</style>
