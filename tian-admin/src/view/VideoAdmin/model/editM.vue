<!--
 * @Author: your name
 * @Date: 2021-05-27 16:22:08
 * @LastEditTime: 2021-06-08 16:59:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/videoAdmin/model/editMaterial.vue
-->
<!--  -->
<template>
  <div class="edit-mater">
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
      <el-form-item label="视频名称" prop="videotitle">
        <el-input v-model="ruleForm.videotitle" />
      </el-form-item>
      <el-form-item label="开播时间" prop="playtime">
        <el-date-picker
          v-model="ruleForm.playtime"
          type="date"
          placeholder="选择开播时间"
          value-format="yyyy-MM-dd"
          style="width:100%;"
        />
      </el-form-item>
      <el-form-item label="集/期数" prop="nums">
        <el-input v-model="ruleForm.nums" />
      </el-form-item>
      <el-form-item label="视频介绍" prop="descs">
        <el-input v-model="ruleForm.descs" type="textarea" class="descs" />
      </el-form-item>
      <el-form-item label="封面图" prop="videopic">
        <BaseUpload
          key="presenters"
          button_name="上传图片"
          class="ml-10px BaseUploadImg"
          @getFileUrl="getFileUrl"
        />
        <img :src="ruleForm.videopic" alt="" class="cover-img">
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
    videoData: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data() {
    // 这里存放数据
    return {
      ruleForm: {
        videotitle: '',
        descs: '',
        playtime: '',
        nums: '',
        videopic: ''
      },
      rules: {
        videotitle: [
          { required: true, message: '请输入视频名称', trigger: 'blur' }
        ],
        playtime: [
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
    this.initdata()
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
    getFileUrl(url, file) {
      this.uploadImg(file)
    },
    async uploadImg(file) {
      const formData = new FormData()
      formData.append('file', file)
      const res = await this.$post('/common/uploadfile', formData)
      const { data, err } = res
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.ruleForm.videopic = data.data.imgurl
    },
    initdata() {
      Object.keys(this.ruleForm).forEach(item => {
        this.ruleForm[item] = this.videoData[item]
      })
    },
    cancel() {
      this.$emit('close')
    },
    async postData() {
      const res = await this.$put(`/videos/${this.videoData.Id}`, this.ruleForm)
      const { err } = res
      err ? this.$message({ type: 'error', message: err.msg }) : this.$message({ type: 'success', message: '编辑成功' })
      this.$parent.$parent.$parent.getList()
      this.$emit('close')
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.postData()
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
.edit-mater{
  height: 500px;
  overflow-y: scroll;
  .cover-img{
    width: auto;
    height: 100px;
    object-fit: contain;
    margin-top: 10px;
  }
  .el-date-editor{
    width: 100%  !important;
  }
  ::v-deep .descs{
    height: 150px;
    .el-textarea__inner{
      height: 100%;
      line-height: 24px;
    }
  }
}
</style>
