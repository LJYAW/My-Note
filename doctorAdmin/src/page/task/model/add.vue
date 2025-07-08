<template>
  <div class="add_task">
    <el-form :model="ruleForm" ref="ruleForm" label-width="120px" class="demo-ruleForm">
      <el-form-item label="任务类型:" prop="type" class="error_before">
        <el-select v-model="ruleForm.type" placeholder="请选择任务类型" style="width:100%">
          <el-option label="视频任务" value="video"></el-option>
          <el-option label="音频任务" value="audio"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务名称:" prop="title" class="error_before">
        <el-input v-model="ruleForm.title" :disabled="this.type=='deatils'"></el-input>
      </el-form-item>
      <!-- <el-form-item label="任务说明:" prop="intro"> -->
      <div class="d-flex">
        <span style="min-width: 105px;margin-left: 15px"><span style="color: red">*</span> 任务说明：</span>
        <mavon-editor :defaultHtml='this.ruleForm.intro' ref="mavonEditor"></mavon-editor>
      </div>
      <!-- </el-form-item> -->
      <el-form-item label="任务时间" class="mt-20px error_before" prop="date">

        <el-date-picker
          v-model="ruleForm.date"
          type="daterange"
          range-separator="至"
          :picker-options="pickerOptions"
          value-format="yyyy-MM-dd"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :disabled="this.type=='deatils'">
        </el-date-picker>

      </el-form-item>
      <el-form-item label="封面海报:" prop="cover_pic" class="error_before">
        <upload :button_name="'上传图片'"
          @getFileUrl="getimgUrl" :size="'480x260'"
          :disabled="this.type=='deatils'" />
        <div class="cover_Img">
          <img :src="fileImg.cover_pic" alt="" v-if="fileImg.cover_pic">
        </div>
      </el-form-item>
      <el-form-item label="任务banner:" prop="banner" class="error_before" v-if="ruleForm.type=='video'">
        <upload :button_name="'上传图片'"
          @getFileUrl="getbannerUrl" :size="'1000x300'" :disabled="this.type=='deatils'" />
        <div class="bannerImg">
          <img :src="fileImg.banner" alt="" v-if="fileImg.banner">
        </div>
      </el-form-item>
      <el-form-item label="任务附件:" prop="attach_file" v-if="ruleForm.type=='video'">
        <upload :button_name="'上传附件'"
          @getFileUrl="getFileUrl" :file_type="file_type" :disabled="this.type=='deatils'" />
        <div>{{fileImg.name}}</div>
      </el-form-item>
      <el-form-item label="指定企业:" prop="enterprise">
        <div>
          <div class="tags_value">
            <p v-for="(tags_item,tem_index) in tags_item" :key="tem_index">
              {{tags_item.company_name}}
              <i class="el-icon-close" @click="tagsClose(tags_item,tem_index)"></i>
            </p>
          </div>
          <div class="tag_list">
            <div class="d-flex">
              <el-input v-model="keywords" placeholder="请输入企业名称" style="width:300px" :disabled="this.type=='deatils'"></el-input>
              <a class="add_tags" @click="add_tags()">搜索</a>
            </div>
            <div class="d-flex flex-wrap">
              <p v-for="(item,index) in tags" :key="index" class="ml-10px" @click="tagsTick(item)">
                <span :class="[tags_item.some(data => data.invitation_code == item.invitation_code) ? 'isSelect': '']">{{item.company_name}}</span>
              </p>
            </div>
          </div>
        </div>
      </el-form-item>
      <el-form-item class="btns" v-if="this.type!='deatils'">
        <el-button type="primary" @click="submitForm('ruleForm')" round :disabled="submit_btn_loading">确定</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import mavonEditor from '@/components/mavon_editor/index'

export default {
  name: 'addAssins',
  props: {
    layerid: {
      type: String,
      default: ''
    },
    _this: '',
    data: {
      type: Object
    },
    type: {
      type: String
    }
  },
  data() {
    return {
      keywords: '',
      tags_item: [],
      tags: [],
      submit_btn_loading: false,
      file_type: 'all',
      fileImg: {
        cover_pic: '',
        banner: '',
        attach_file: '',
        name: ''
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      invitation_codes: [],
      isA: false,
      ruleForm: {
        title: '',
        intro: '',
        date: ['', ''],
        cover_pic: null,
        banner: null,
        attach_file: null,
        type: 'video'
      }
    }
  },
  components: {
    mavonEditor
  },
  computed: {},
  watch: {},
  methods: {
    // 设置默认选中的企业
    setDeauflutTags() {
      console.log('🚀 ~ file: add.vue ~ line 139 ~ setDeauflutTags ~ this.type', this.type)

      if (this.type != 'edit' && this.type != 'deatils') return
      this.data.invitation_codes.forEach(code => {
        let item = this.tags.find(item => code == item.invitation_code)
        this.tags_item.push(item)
      })
    },
    // 指定企业数据
    getcompany_name() {
      this.tags = []
      let params = {
        company_name: this.keywords
      }
      this.$get('/admin/task/company-user-list', {
        params: params
      }).then(
        res => {
          if (res.data.code == '0000') {
            this.tags = res.data.data
            this.setDeauflutTags()
          }
        },
        err => {}
      )
    },
    tagsTick(item) {
      let obj = {
        company_name: item.company_name,
        invitation_code: item.invitation_code
      }
      let index = this.tags_item.findIndex(data => data.invitation_code == item.invitation_code)
      if (index > -1) {
        this.tags_item.splice(index, 1)
      } else {
        this.tags_item.push(obj)
      }
    },
    // 搜索指定企业
    add_tags() {
      this.getcompany_name()
    },
    // 删除指定企业
    tagsClose(item, index) {
      this.tags_item.splice(index, 1)
    },
    // 更新
    update() {
      if (this.data) {
        this.ruleForm.title = this.data.title
        this.ruleForm.intro = this.data.intro
        this.ruleForm.date = [this.data.start_date, this.data.end_date]
        this.fileImg.cover_pic = this.data.cover_pic_url
        this.fileImg.banner = this.data.banner_url
        this.fileImg.name = this.data.attach_file_name
        this.ruleForm.type = this.data.type
      }
    },
    getFileUrl(url, file) {
      this.ruleForm.attach_file = file
      this.fileImg.name = file.name
    },
    getimgUrl(url, file) {
      this.ruleForm.cover_pic = file
      this.fileImg.cover_pic = url
    },
    getbannerUrl(url, file) {
      this.ruleForm.banner = file
      this.fileImg.banner = url
    },
    checkoutFrom() {
      let rules = {
        title: value => {
          if (!value) return `请填写任务标题`
        },
        intro: value => {
          if (!value) return `请填写任务说明`
        },
        date: arr => {
          if (!arr[0]) return `请选择日期`
        },
        cover_pic: value => {
          if (!value) return `请上传封面图片`
        },
        banner: value => {
          if (!value) return `请上传Banner`
        }
      }
      if (this.type == 'edit') {
        delete rules.cover_pic
        delete rules.banner
      }
      if (this.ruleForm.type == 'audio') {
        delete rules.banner
      }
      let is_right = true
      for (const key in this.ruleForm) {
        let value = this.ruleForm[key]

        if (rules[key] && rules[key](value)) {
          this.$message({
            message: rules[key](value),
            type: 'warning'
          })
          is_right = false
          break
        }
      }

      return is_right
    },
    postTark(formData) {
      this.submit_btn_loading = true
      this.$post('/admin/task/task', formData)
        .then(
          res => {
            if (res.data.code == '0000') {
              this.$message({
                message: '提交成功',
                type: 'success'
              })
              this._this.getList()
              this.$layer.close(this.layerid)
            } else {
              this.$message({
                message: res.data.msg,
                type: 'warning'
              })
            }
          },
          err => {}
        )
        .finally(res => {
          this.submit_btn_loading = false
        })
    },
    submitForm(ruleForm) {
      this.ruleForm.intro = this.$refs.mavonEditor.editorData
      if (!this.checkoutFrom()) return

      var formData = new FormData()
      formData.append('type', this.ruleForm.type)
      formData.append('title', this.ruleForm.title)
      formData.append('intro', this.ruleForm.intro)
      formData.append('start_date', this.ruleForm.date[0])
      formData.append('end_date', this.ruleForm.date[1])
      formData.append('cover_pic', this.ruleForm.cover_pic)
      this.tags_item.forEach(item => {
        formData.append('invitation_codes[]', item.invitation_code)
      })
      if (this.ruleForm.type == 'video') {
        if (this.ruleForm.attach_file) {
          formData.append('attach_file', this.ruleForm.attach_file)
        }
        if (this.ruleForm.banner) {
          formData.append('banner', this.ruleForm.banner)
        }
      }

      // 修改
      if (this.type == 'edit') {
        if (!this.ruleForm.attach_file) formData.delete('attach_file')
        if (!this.ruleForm.banner) formData.delete('banner')
        if (!this.ruleForm.cover_pic) formData.delete('cover_pic')
        formData.append('task_id', this.data.task_id)
      }

      this.postTark(formData)
    }
  },
  created() {
    this.getcompany_name()
    if (this.type != 'add') {
      this.update()
    }
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.add_task {
  width: 100%;
  padding: 20px 26px;
  .demo-ruleForm {
    .tags_value {
      margin-top: 10px;
      display: flex;
      flex-wrap: wrap;
      i {
        &:hover {
          color: #c51b19;
        }
      }
      p {
        width: auto;
        cursor: pointer;
        height: 30px;
        margin-right: 10px;
        background: #f5f5f5;
        margin-top: 10px;
        line-height: 20px;
        padding: 5px;
        box-sizing: border-box;
        border-radius: 2px;
      }
    }
    .tag_list {
      padding: 10px;
      background: #f5f5f5;
      max-height: 300px;
      overflow: auto;
      margin-top: 15px;
      p {
        cursor: pointer;
      }
      .isSelect {
        background: #2a79df;
        color: #fff;
        padding: 5px;
        border-radius: 2px;
      }
      .add_tags {
        font-size: 14px;
        min-width: 80px;
        color: #c51b19;
        margin-left: 10px;
        cursor: pointer;
      }
    }
    .error_before {
      /deep/ .el-form-item__label::before {
        content: '*';
        color: red;
        margin-right: 4px;
      }
    }
    .cover_Img {
      width: 350px;
      height: 190px;
      border: 1px solid #dddddd;
      img {
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }
    .bannerImg {
      width: 650px;
      height: 195px;
      border: 1px solid #dddddd;
      img {
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }
    .btns {
      text-align: center;
      /deep/ .el-form-item__content {
        margin-left: 0 !important;
      }
    }
    /deep/ .el-form-item__label {
      text-align: center;
      color: #333333;
    }
    /deep/ .el-textarea__inner {
      height: 200px;
    }
    .enclosure {
      font-size: 12px;
      color: #3485e2;
    }
  }
}
</style>
