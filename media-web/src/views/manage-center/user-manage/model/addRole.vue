<!--
 * @Author: your names
 * @Date: 2021-08-04 14:42:41
 * @LastEditTime: 2021-08-23 18:14:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/user-manage/model/add.vue
-->
<template>
  <div class="add-person">
    <div class="arrow-img">
      <svg-icon icon-class="person" class="icon-person" />
    </div>
    <el-form ref="ruleForm" :rules="rules" label-width="0" class="demo-ruleForm" :model="ruleForm" :inline="true">
      <div class="person-top">
        <div class="person-title">角色信息</div>
      </div>
      <el-form-item prop="names" class="item-list temp-item">
        <span class="title-top">角色名称</span>
        <el-input v-model="ruleForm.names" placeholder="请输入操作人员" />
      </el-form-item>
      <div class="person-top">
        <div class="person-title">功能授权</div>
      </div>
      <el-form-item prop="" class="fun-box">
        <JurisdictionCheck
          key="juanxian"
          ref="limitlist"
          title="权限选择"
          :check-data="JurisdictionData"
          :check-list="roleCheckData"
          default-val="查看视频"
        />
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="submitForm('ruleForm')">{{ title }}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import JurisdictionCheck from '../components/jurisdictionCheck'
export default {
  components: {
    JurisdictionCheck
  },
  props: {
    id: {
      type: Number,
      default: 0
    },
    title: {
      type: String,
      default: ''
    },
    itemData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    return {
      roleCheckData: [],
      JurisdictionData: [
        {
          label: '查看视频',
          val: 3
        },
        {
          label: '上传视频',
          val: 1
        },
        {
          label: '删除视频',
          val: 2
        },
        {
          label: '剪辑视频',
          val: 4
        },
        {
          label: '分析视频',
          val: 5
        },
        {
          label: '视频搜索',
          val: 6
        }
      ],
      ruleForm: {
        names: '',
        limitlist: ''
      },
      rules: {
        names: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    if (this.title === '修改角色') {
      this.initData()
    }
  },
  mounted() {

  },
  methods: {
    // 数据初始化
    async initData() {
      Object.keys(this.ruleForm).forEach(item => {
        this.ruleForm[item] = this.itemData[item]
      })
      const arr = await JSON.parse(JSON.stringify(this.ruleForm.limitlist.split(',')))
      this.roleCheckData = arr.map(k => k.split('|')[1])
    },
    // 设置数据
    setData() {
      const limitlist = this.$refs.limitlist.TempcheckList
      const arr = []
      this.JurisdictionData.map(item => {
        if (limitlist.indexOf(item.label) !== -1) {
          arr.push(item.val + '|' + item.label)
        }
      })
      this.ruleForm.limitlist = arr.join(',')
    },
    // 编辑提交
    editPOstData() {
      this.setData()
      this.$put(`/usergroup/${this.id}`, this.ruleForm).then(response => {
        const { err, res } = response
        if (err) {
          this.$message({
            message: err.msg,
            type: 'error'
          })
        } else {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.$parent.$parent.$parent.getInitData()
          this.$emit('close')
        }
      })
    },
    // 创建提交
    postData() {
      this.setData()
      this.$post('/usergroup/', this.ruleForm).then(response => {
        const { err, res } = response
        if (err) {
          this.$message({
            message: err.msg,
            type: 'error'
          })
        } else {
          this.$message({
            message: '创建成功',
            type: 'success'
          })
          this.$parent.$parent.$parent.getInitData()
          this.$emit('close')
        }
      })
    },
    // 提交数据
    submitForm(formnames) {
      this.$refs[formnames].validate((valid) => {
        if (valid) {
          if (this.id) {
            this.editPOstData()
          } else {
            this.postData()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style scoped lang="scss">
@import "./index.scss"

</style>
