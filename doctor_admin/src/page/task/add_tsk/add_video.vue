<!--
 * @Author: your name
 * @Date: 2021-01-26 14:53:10
 * @LastEditTime: 2021-01-27 16:30:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/task/add_tsk/add_video.vue
-->
<template>
  <div>
    <el-form :model="ruleForm" ref="ruleForm" label-width="120px" class="demo-ruleForm" :rules="rules">
      <el-form-item label="任务说明:" prop="intro" class="introBox">
        <mavon-editor :defaultHtml='this.ruleForm.intro' ref="mavonEditor"></mavon-editor>
      </el-form-item>
      <el-form-item label="封面海报:" :prop="$route.query.type==='创建任务'? 'cover_pic' : ''">
        <upload :button_name="'上传图片'"
          @getFileUrl="getimgUrl(...arguments,'cover_pic')" :size="'480x260'"
          :key="'cover_pic'"
          :disabled="$route.query.type=='任务详情'" />
        <div class="cover_Img">
          <img :src="fileImg.cover_pic" alt="" v-if="fileImg.cover_pic">
        </div>
      </el-form-item>
      <el-form-item label="任务banner:" :prop="$route.query.type==='创建任务'? 'banner' : ''">
        <upload :button_name="'上传图片'"
          :key="'banner'"
          @getFileUrl="getimgUrl(...arguments,'banner')" :size="'1000x300'" />
        <div class="bannerImg">
          <img :src="fileImg.banner" alt="" v-if="fileImg.banner">
        </div>
      </el-form-item>
      <el-form-item label="任务附件:" prop="attach_file">
        <upload :button_name="'上传附件'"
          :key="'attach_file'"
          @getFileUrl="getimgUrl(...arguments,'attach_file')" :file_type="file_type" />
        <div>{{fileImg.name}}</div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import mavonEditor from '@/components/mavon_editor/index'
import rules from '../js/verification'
export default {
  name: 'addVideo',
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
      rules: rules,
      submit_btn_loading: false,
      file_type: 'all',
      fileImg: {
        cover_pic: '',
        banner: '',
        attach_file: '',
        name: ''
      },
      ruleForm: {
        cover_pic: null,
        banner: null,
        attach_file: null,
        intro: ''
      }
    }
  },
  components: {
    mavonEditor
  },
  computed: {},
  watch: {
    defaultData: {
      handler: function(newVal, oldVal) {
        this.fileImg = {
          cover_pic: newVal.cover_pic_url,
          banner: newVal.banner_url,
          name: newVal.attach_file_name
        }
        console.log(newVal)
        this.ruleForm.intro = newVal.intro
      },
      deep: true
    }
  },
  methods: {
    checkClass() {
      if (this.ruleForm.intro == '') {
        this.$message({
          message: '请填写任务说明',
          type: 'warning'
        })
        return false
      }
      return true
    },
    checkFrom(callBack) {
      this.ruleForm.intro = this.$refs.mavonEditor.editorData || ''
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
    getimgUrl(url, file, key) {
      this.ruleForm[key] = file
      if (key === 'attach_file') {
        this.fileImg.name = file.name
      } else {
        this.fileImg[key] = url
      }
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.introBox {
  ::v-deep.el-form-item__content {
    z-index: 0;
  }
}
</style>
