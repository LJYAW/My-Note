<!--
 * @Author: your name
 * @Date: 2021-01-28 11:11:25
 * @LastEditTime: 2021-02-10 11:52:30
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/task/model/adoc_table.vue
-->
<template>
  <div class="doctorTable">
    <el-form ref="form" :model="form" label-width="60px"
      :header-cell-style="theadStyle">
      <el-form-item label="标题">
        <el-input v-model="form.title"></el-input>
      </el-form-item>
      <el-form-item label="正文">
        <el-input v-model="form.question"></el-input>
      </el-form-item>
      <el-form-item label="科室">
        <tags ref="tags" :tags_item="keys_item" :tags="tags" @tasList="tasList" :type="'single'" v-if="!departmentLoading" />
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="add">添加</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import remarks from './remarks'
import tags from '../components/search_tags'
export default {
  name: '',
  props: {
    _this: '',
    layerid: {
      type: String,
      default: ''
    },
    lydata: {
      type: Object,
      default: () => {
        return {}
      }
    },
    status: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      departmentLoading: false,
      keys_item: [],
      tags: [],
      form: {
        title: '',
        question: '',
        department: ''
      },
      theadStyle: {
        background: '#E2EEFD',
        color: '#101010',
        textAlign: 'left',
        padding: '5px 0',
        fontWeight: 'bolder',
        fontSize: '12px'
      }
    }
  },
  components: {
    tags
  },
  computed: {},
  watch: {},
  methods: {
    tasList(val) {
      if (val.length == 0) {
        this.keys_item = []
        this.form.department = ''
      } else {
        this.keys_item = val
        this.form.department = val[0].name
      }
    },
    // 获取科室名称
    getdepartmentData() {
      this.departmentLoading = true
      this.$get('/admin/task/departments-list')
        .then(
          res => {
            if (res.data.code == '0000') {
              this.tags = res.data.data
            }
          },
          err => {}
        )
        .finally(res => {
          this.departmentLoading = false
        })
    },
    add() {
      let arr = []
      arr.push(this.form)
      this.lydata._this.subData(arr, 'append')
      this.$layer.close(this.layerid)
    }
  },
  created() {
    this.getdepartmentData()
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.doctorTable {
  width: 100%;
  padding: 20px;
  ::v-deep.el-form-item__label {
    text-align: center;
  }
  .btns {
    text-align: center;
    ::v-deep.el-form-item__content {
      margin-left: 0 !important;
    }
  }
}
</style>
