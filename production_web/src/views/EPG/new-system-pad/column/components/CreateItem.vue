<!--
 * @Author: your name
 * @Date: 2021-01-15 19:35:12
 * @LastEditTime: 2021-12-23 18:01:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work_order_web/src/views/order_mgt/components/CreateItem.vue
-->
<template>
  <div>
    <el-form ref="ruleForm" v-loading="loading" :model="form" label-width="100px" :rules="rules">
      <el-form-item label="频道名称" prop="channel_id">
        <el-select
          v-model="form.channel_id"
          placeholder="请选择"
          filterable
          clearable
          style="width:100%"
        >
          <el-option
            v-for="item in channelData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="栏目名称" prop="item_name">
        <el-input v-model.trim="form.item_name" placeholder="请输入栏目名称" maxlength="50" style="width:100%" />
      </el-form-item>
      <el-form-item label="栏目分类" required>
        <base-btn @click.native="openClassModal"><i class="iconfont el-icon-edit" />编辑分类</base-btn>
        <div v-if="firstCheckList.length" class="column-table">
          <div class="column-content">
            <p class="type">一级分类</p>
            <p class="flex-1">{{ firstCheckName.join("、") }}</p>
          </div>
          <div v-if="secondCheckList.length" class="column-content column-last">
            <p class="type">二级分类</p>
            <p class="flex-1">{{ secondCheckName.join("、") }}</p>
          </div>
        </div>

        <ColumnClassify
          ref="category"
          :show-cate-dialog="showCateDialog"
          @setClassData="setClassData"
          @close="close"
        />
      </el-form-item>
      <el-form-item label="" required>
        <el-radio v-model="form.country" label="中国">中国</el-radio>
        <el-radio v-model="form.country" label="其他">其他</el-radio>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import ColumnClassify from './ColumnClassify'
import { mapGetters } from 'vuex'
import rules from './validate'
import isEmpty from 'lodash/camelCase'

export default {
  // provide() {
  //   return {
  //     channel: this
  //   }
  // },

  components: {
    ColumnClassify
  },
  props: {
    defaultData: {
      type: Object,
      default() {
        return {
          channelId: null,
          itemName: '',
          firstCheckList: [],
          secondCheckList: []
        }
      }
    }
  },
  data() {
    return {
      form: {
        channel_id: '',
        country: '中国',
        item_name: ''
      },
      loading: true,
      rules: rules,
      showCateDialog: false,
      firstCheckList: [],
      secondCheckList: []

    }
  },
  computed: {
    ...mapGetters(['channelData']),
    firstCheckName() {
      var arr = []
      this.firstCheckList.forEach((item) => {
        arr.push(item.first_category_name)
      })
      return arr
    },
    firstCheckId() {
      var arr = []
      this.firstCheckList.forEach((item) => {
        arr.push(item.first_category_id)
      })
      return arr
    },
    secondCheckName() {
      var arr = []
      this.secondCheckList.forEach((item) => {
        arr.push(item.second_category_name)
      })
      return arr
    },
    secondCheckId() {
      var arr = []
      this.secondCheckList.forEach((item) => {
        arr.push(item.second_category_id)
      })
      return arr
    }

  },
  watch: {
    defaultData: {
      handler(val) {
        if (val) {
          this.setDefalutData(val)
        }
      },
      deep: true,
      immediate: true
    }
  },
  async created() {
    if (!this.channelData) {
      await this.$store.dispatch('channel/getChannelData')
    }
    this.loading = false
  },
  mounted() {
    this.setDefalutData()
  },
  methods: {
    openClassModal() {
      this.showCateDialog = true
      this.setDefalutData(this.defaultData)
    },
    setClassData() {
      this.firstCheckList = this.$refs.category.firstCheckList
      this.secondCheckList = this.$refs.category.secondCheckList
      this.close()
    },
    close() {
      this.showCateDialog = false
      // this.firstCheckList = this.$refs.category.firstCheckList
      // this.secondCheckList = this.$refs.category.secondCheckList
    },
    setDefalutData(val) {
      const obj = val
      if (obj && !obj.itemName) return
      if (isEmpty(obj)) {
        this.form.channel_id = obj.channelId
        this.form.item_name = obj.itemName
        this.$nextTick(() => {
          this.$refs.category.setDefalutData(obj.firstCheckList, obj.secondCheckList)
          this.firstCheckList = this.$refs.category.firstCheckList
          this.secondCheckList = this.$refs.category.secondCheckList
          // this.close()
        })
      }
    },
    checkClass() {
      if (this.firstCheckList.length < 1) {
        this.$message({
          message: '请选择一级分类',
          type: 'warning'
        })
        return false
      }
      if (this.secondCheckList.length < 1) {
        this.$message({
          message: '请选择二级分类',
          type: 'warning'
        })
        return false
      }
      return true
    },
    checkFrom(callBack) {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid && this.checkClass()) {
          if (typeof callBack === 'function') {
            callBack()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm() {
      this.$refs['ruleForm'].resetFields()
    }
  }
}
</script>

<style scoped lang="scss">
.dialog-footer{
  text-align: center;
}
.el-form{
  .column-table{
    margin-top: 20px;
    .column-content {
      display: flex;
      width: 100%;
      border-top: 1px solid #eee;
      border-right: 1px solid #eee;
      border-bottom: 1px solid #eee;

      p {
        border-left: 1px solid #eee;
        padding: 0 20px;

      }
      .type{
        width: 120px;
      }
      .flex-1{
        flex: 1;
      }
      &.column-last {
        border-top: none;
      }
    }
  }
}
</style>
