<template>
  <div>
    <div class="form">
      <el-form :model="ruleForm"
               v-if="!status_loading"
               :rules="rules"
               ref="ruleForm"
               class="ruleForm">

        <el-form-item label="视频标题："
                      label-position="top"
                      prop="title">
          <el-input v-model="ruleForm.title"></el-input>
        </el-form-item>

        <el-form-item label="视频简介："
                      label-position="top"
                      prop="intro">
          <el-input type="textarea"
                    v-model="ruleForm.intro"
                    resize="none"
                    :autosize="{ minRows: 8, maxRows: 10 }"></el-input>
        </el-form-item>

        <el-form-item label="原创标签："
                      prop="tag_list"
                      label-position="top">
          <tagList @getTagList="getTagList" />
        </el-form-item>

        <el-form-item label="原创视频："
                      label-position="right">
          <el-checkbox v-model="ruleForm.is_original"></el-checkbox>
        </el-form-item>

        <el-form-item prop='category_id'
                      label="视频分类：">
          <el-select v-model="ruleForm.category_id"
                     style="width:150px"
                     class='mr-20px'
                     @change='setVideoLable'
                     placeholder="请选择">
            <el-option v-for="item in classList"
                       :key="item.category_id"
                       :label="item.name"
                       :value="item.category_id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="百家号账号："
                      prop='third_account_info_ids'
                      label-position="top">

          <el-checkbox-group v-model="ruleForm.third_account_info_ids">
            <el-checkbox v-for="(item,index) in bind_list"
                         :label="item.third_account_info_id"
                         :key="index">{{item.nickname}}</el-checkbox>
          </el-checkbox-group>

        </el-form-item>

      </el-form>
    </div>

  </div>
</template>

<script>
import tagList from '../../components/tag_list'
export default {
  props: {},
  data() {
    return {
      ruleForm: {
        title: '',
        tagText: '',
        intro: '',
        is_original: true,
        category_id: '',
        tag_list: [],
        third_account_info_ids: []
      },
      classList: [],
      bind_list: [],
      status_loading: true,
      passed: false, // 验证有没有通过
      rules: {
        title: [
          { required: true, message: '请输入视频标题', trigger: 'blur' },
          { min: 8, max: 40, message: '长度在 8 到 40 个字符', trigger: 'blur' }
        ],
        intro: [
          { required: true, message: '请输入视频简介', trigger: 'blur' },
          { min: 8, max: 200, message: '长度在 8 到 200 个字符', trigger: 'blur' }
        ],
        category_id: [{ required: true, message: '请选择视频分类', trigger: 'blur' }],
        third_account_info_ids: [{ type: 'array', required: true, message: '请至少选择一个发布账号', trigger: 'change' }],
        tag_list: [{ type: 'array', required: true, message: '请至少添加一个标签', trigger: 'change' }]
      }
    }
  },
  computed: {},
  watch: {},
  methods: {
    setVideoLable(item) {
      this.ruleForm.category_id = item
    },
    getTagList(list) {
      this.ruleForm.tag_list = list
    },
    getCategoryList() {
      this.status_loading = true
      this.$get('/short-video/category-list').then(res => {
        if (res.data.code == '0000') {
          this.classList = res.data.data
        }
        this.getBindList()
      })
    },
    getBindList() {
      this.$get('/user/bound-platform-list').then(res => {
        if (res.data.code == '0000') {
          this.bind_list = res.data.data
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.status_loading = false
      })
    },
    submitForm() {
      this.$refs.ruleForm.validate(valid => {
        if (valid) {
          this.passed = true
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    setThirdlist(item, index) {
      console.log('setThirdlist -> item', item, index)
    }
  },
  components: { tagList },
  created() {
    this.getCategoryList()
    this.ruleForm.title = this.$route.query.title
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.form {
  min-height: 578px;
  background: #fff;
  padding: 20px;
}
</style>
