<!--
 * @Author: your name
 * @Date: 2021-01-26 14:53:10
 * @LastEditTime: 2021-02-10 12:20:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/task/add_tsk/add_video.vue
-->
<template>
  <div>
    <el-form :model="ruleForm" ref="ruleForm" label-width="120px" class="demo-ruleForm">
      <el-form-item label="预期收益:" prop="">
        <div class="d-flex">
          <el-input v-model.number.trim="ruleForm.earnings" maxlength="21" @input="changeInput" style="width:200px" :disabled="$route.query.type=='任务详情' || defaultData.status=='进行中'"></el-input>
          <span class=" ml-10px">元</span>
          <span class="ml-10px money-title">每条子任务预期收益金额</span>
        </div>
      </el-form-item>
      <el-form-item label="关键词:" class="error_before">
        <tags ref="tags" :tags_item="keys_item" :tags="tags" @tasList="tasList" v-if="!keyLoading" />
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import mavonEditor from '@/components/mavon_editor/index'
import tags from '../components/search_tags'
export default {
  name: 'addAudio',
  props: {
    defaultData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      tasg: [],
      keys_item: [],
      keyLoading: false,
      ruleForm: {
        earnings: ''
      }
    }
  },
  components: {
    mavonEditor,
    tags
  },
  computed: {},
  watch: {
    defaultData: {
      handler: function(newVal, oldVal) {
        this.keys_item = newVal.keywords
        this.ruleForm.earnings = newVal.earnings
      },
      deep: true
    }
  },
  methods: {
    // 限制只能输入 正整数
    changeInput() {
      var pattern = /^[1-9][0-9]*$/ // 正整数的正则表达式
      // 不符合正整数时
      if (!pattern.test(this.ruleForm.earnings)) {
        // input 框绑定的内容为空
        this.ruleForm.earnings = ''
      }
    },
    checkClass() {
      if (this.keys_item.length == 0) {
        this.$message({
          message: '请填写关键词',
          type: 'warning'
        })
        return false
      }
      return true
    },
    checkFrom(callBack) {
      this.$refs['ruleForm'].validate(valid => {
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
    tasList(val) {
      this.keys_item = val
    },
    getKeywords() {
      this.keyLoading = true
      this.$get('/admin/task/keywords-list')
        .then(
          res => {
            if (res.data.code == '0000') {
              this.tags = res.data.data
            }
          },
          err => {}
        )
        .finally(res => {
          this.keyLoading = false
        })
    }
  },
  created() {
    this.getKeywords()
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.money-title {
  font-size: 12px;
  color: #999;
}
</style>
