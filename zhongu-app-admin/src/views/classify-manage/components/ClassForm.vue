<!--
 * @Author: your name
 * @Date: 2021-10-27 15:14:22
 * @LastEditTime: 2021-11-25 16:36:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/classify-manage/components/class-form.vue
-->
<template>
  <div class="new-class">
    <div v-loading="loading" class="new-class-form">
      <el-form ref="ruleForm" :model="form" :rules="rules" :inline="true">

        <el-form-item prop="name" label="分类名称">
          <el-input v-model.trim="form.name" :maxlength="10" placeholder="分类名称最多十个字" clearable />
        </el-form-item>

        <el-form-item prop="intro" label="分类简介">
          <el-input v-model.trim="form.intro" :maxlength="12" placeholder="分类简介最多12个字符" clearable />
        </el-form-item>

        <el-form-item v-for="(item,index) in imgData" :key="item.prop" :prop="item.prop" :label="item.label">
          <div class="image-wrap">
            <div v-if="form[item.prop]">
              <base-upload button-name="" @getFileUrl="getFileData(arguments,index)">
                <base-image :src="form[item.prop]" fit="contain" />
                <p class="btn">点击更换图片</p>
              </base-upload>
            </div>
            <div v-else>
              <base-upload button-name="" class="uplaod-btn" @getFileUrl="getFileData(arguments,index)">
                <svg-icon icon-class="file" class="upload-icon" />
                <p class="btn">点击上传图片</p>
              </base-upload>
            </div>
            <p class="image-size">固定尺寸 {{ item.size }}px  png格式</p>
          </div>
        </el-form-item>

        <el-form-item label="分类描述" prop="des">
          <div class="image-wrap textarea-wrap">
            <el-input v-model.trim="form.des" type="textarea" :maxlength="30" placeholder="分类描述最多30个字符" />
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="btn-wrap">
      <base-btn type="info" class="clear-btn" @click="clear">清空数据</base-btn>
      <base-btn :disabled="disabled" @click="confirm">确认保存</base-btn>
      <base-btn @click="routeTo">返回</base-btn>
    </div>
  </div>
</template>

<script>
import rules from '../js/rule'
import { Upload } from '@/utils/upload'
import checkImg from '@/utils/img-size'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        name: '',
        intro: '',
        small_img: '',
        main_img: '',
        banner_img: '',
        des: ''
      },
      rules: rules,
      imgData: [{
        prop: 'small_img',
        label: '分类主形象',
        size: '110X130'
      }, {
        prop: 'main_img',
        label: '分类主图',
        size: '330X330'
      }, {
        prop: 'banner_img',
        label: '分类banner',
        size: '678X300'
      }],
      loading: false,
      disabled: false
    }
  },
  computed: {
  },
  watch: {

  },
  created() {
    if (this.$route.query.id) {
      this.getClassData()
    }
  },
  mounted() {

  },
  methods: {
    // 编辑回显数据
    async getClassData() {
      this.loading = true
      const params = {
        ids: this.$route.query.id
      }
      const { err, res } = await this.$get('/admin/categories', params)
      this.loading = false
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form = JSON.parse(JSON.stringify(res.data.list[0]))
    },
    async getFileData(params, index) {
      const imgData = await checkImg(params[0])
      const ruleSize = this.imgData[index].size.split('X')
      if (imgData.w !== Number(ruleSize[0]) || imgData.h !== Number(ruleSize[1])) {
        this.$message.error(`请上传尺寸为${this.imgData[index].size}的图片`)
        return
      }
      const { err, res } = await Upload(params[1])
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form[this.imgData[index].prop] = res.data.file_path
    },
    // 清空数据
    clear() {
      this.$refs['ruleForm'].resetFields()
    },
    routeTo() {
      this.$router.push({
        path: '/classify-manage'
      })
    },
    // 确认新建
    confirm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.submit()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    async submit() {
      this.disabled = true
      const { err, res } = !this.$route.query.id
        ? await this.$post('/admin/categories', this.form)
        : await this.$put(`/admin/categories/${this.$route.query.id}`, this.form)
      this.disabled = false
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '保存成功'
        })
        this.routeTo()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.new-class {
  display : flex;
  ::v-deep .new-class-form {
    flex : 1;
    .el-form-item {
      margin-right : 2%;
      width : 48%;
      .el-form-item__label {
        display : block;
        text-align : left;
      }
      .el-form-item__content {
        width : 100%;
        line-height : 0;
        input {
          background : #F8F8F8;
          border-radius : 3px;
          border : none;
        }
      }
      .image-wrap {
        padding : 31px 0 25px 0;
        background : #F8F8F8;
        border-radius : 4px;
        text-align : center;
        .el-image {
          height : 98px;
          margin-bottom : 7px;
        }
        .upload-icon {
          display : inline-block;
          width : 39px;
          height : 32px;
          margin-bottom : 15px;
          margin-top : 30px;
        }
        .btn {
          font-size : 11px;
          line-height : 15px;
          color : #404040;
          margin-bottom : 3px;
        }
        .uplaod-btn .btn {
          font-size : 21px;
          line-height : 21px;
          margin-bottom : 26px;
        }
        .image-size {
          opacity : .4;
          font-size : 11px;
          line-height : 11px;
        }
      }
      .textarea-wrap {
        padding : 20px 10px;
        textarea {
          background : none;
          border : none;
          resize : none;
          height : 151px;
          overflow-y : auto;
        }
      }
    }
  }
  .btn-wrap {
    display : flex;
    flex-direction : column;
    margin-top : 40px;
    .clear-btn {
      color : #404040;
    }
    button {
      margin : 0;
      margin-bottom : 10px;
    }
  }
}

</style>
