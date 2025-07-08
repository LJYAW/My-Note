<!--
 * @Author: your name
 * @Date: 2021-05-27 16:22:08
 * @LastEditTime: 2021-05-28 11:58:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/videoAdmin/model/editMaterial.vue
-->
<!--  -->
<template>
  <div class="edit-mater">
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
      <el-form-item label="视频名称" prop="videoname">
        <el-input v-model="ruleForm.videoname" />
      </el-form-item>
      <el-form-item label="开播时间" prop="time">
        <el-input v-model="ruleForm.time" />
      </el-form-item>
      <el-form-item label="集/期数" prop="num">
        <el-input v-model="ruleForm.num" />
      </el-form-item>
      <el-form-item label="视频介绍" prop="introduce">
        <el-input v-model="ruleForm.introduce" />
      </el-form-item>
      <el-form-item label="封面图" prop="coverimg">
        <el-select v-model="ruleForm.coverimg" placeholder="请选择页面" style="width:100%;margin-bottom:15px">
          <el-option label="区域一" value="shanghai" />
          <el-option label="区域二" value="beijing" />
        </el-select>
        <div class="copy">
          请输入copy图片进入
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">完成</el-button>
        <el-button @click="cancel('ruleForm')">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）
// 例如：import 《组件名称》 from '《组件路径》';

export default {
// import引入的组件需要注入到对象中才能使用
  components: {},
  props: {
    show: {
      type: Boolean,
      default: false
    }
  },
  data() {
    // 这里存放数据
    return {
      ruleForm: {
        videoname: '',
        introduce: '',
        time: '',
        num: '',
        coverimg: '',
        desc: ''
      },
      rules: {
        videoname: [
          { required: true, message: '请输入视频名称', trigger: 'blur' }
        ],
        time: [
          { required: true, message: '请输入开播时间', trigger: 'blur' }
        ]
      }
    }
  },
  // 监听属性 类似于data概念
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {

  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {

  },
  beforeCreate() {}, // 生命周期 - 创建之前
  beforeMount() {}, // 生命周期 - 挂载之前
  beforeUpdate() {}, // 生命周期 - 更新之前
  updated() {}, // 生命周期 - 更新之后
  beforeDestroy() {}, // 生命周期 - 销毁之前
  destroyed() {}, // 生命周期 - 销毁完成
  activated() {},
  // 方法集合
  methods: {
    cancel() {
      this.$emit('close')
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  } // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>
<style lang='scss' scoped>
//@import url(); 引入公共css类
.copy{
  width: 100%;
  height: 100px;
  border: 1px solid #ddd;
  line-height: 86px;
  padding: 10px;
  box-sizing: border-box;
  font-size: 12px;
}
</style>
