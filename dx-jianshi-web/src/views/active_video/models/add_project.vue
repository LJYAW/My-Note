<!--
 * @Author: your name
 * @Date: 2020-11-09 11:38:15
 * @LastEditTime: 2020-11-10 11:06:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/active_video/models/add_project.vue
-->
<template>
  <div class="add_project">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="form">
      <el-form-item label="项目名称:" prop="project_name">
        <el-input v-model="ruleForm.project_name" placeholder="项目名称18个字以内"></el-input>
      </el-form-item>
      <el-form-item label="视频比例:" prop="video_proportion">
        <el-radio-group v-model="ruleForm.video_proportion">
          <div class="proportion">
            <el-radio label="16:9">横屏16:9</el-radio>
            <div class="screen horizontal"></div>
          </div>
          <div class="proportion">
            <el-radio label="9:16">竖屏9:16</el-radio>
            <div class="screen vertical"></div>
          </div>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="btn">
        <el-button @click="submitForm('ruleForm')" type="primary" size="medium" round>确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'add_project',
  props: {
    layerid: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      ruleForm: {
        project_name: '测试项目标题',
        video_proportion: '16:9'
      },
      rules: {
        project_name: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 3, max: 18, message: '项目名称18个字以内', trigger: 'blur' }
        ]
      }
    }
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    submitForm(ruleForm) {
      this.$refs[ruleForm].validate(valid => {
        if (valid) {
          localStorage.setItem('active_video_title', JSON.stringify(this.ruleForm))
          this.$router.push({
            path: 'active_video/create_active_video'
          })
          this.$layer.close(this.layerid)
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
@import './index.scss';
.add_project {
  width: 100%;
  .form {
    margin-top: 20px;
    .proportion {
      display: flex;
      margin-bottom: 15px;
      margin-top: 15px;
      .vertical {
        width: 56px;
        height: 100px;
      }
      .horizontal {
        width: 100px;
        height: 56px;
      }
      .screen {
        background-color: #dbdbdb;
        border-radius: 3px;
      }
    }
  }
}
</style>
