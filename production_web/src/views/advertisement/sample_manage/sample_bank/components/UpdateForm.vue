<!--
 * @Author: your name
 * @Date: 2021-05-11 15:54:14
 * @LastEditTime: 2021-10-20 17:19:21
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/advertisement/sample_manage/sample_bank/components/UpdateForm.vue
-->
<template>
  <div>
    <el-form ref="ruleForm" :inline="true" :rules="rules" :model="paramsForm" label-width="90px" label-position="left">
      <el-form-item label="标题" prop="title">
        <el-autocomplete
          v-model="paramsForm.title"
          :fetch-suggestions="querySearchAsync"
          placeholder="请输入标题名称"
          type="textarea"
        />
      </el-form-item>
      <p class="subtitle">基础信息</p>
      <el-form-item label="商品名称">
        <el-select
          v-model="paramsForm.product_id"
          filterable
          clearable
          remote
          :remote-method="(val)=>filterData(val,0)"
          @clear="(val)=>filterData('',0)"
        >
          <el-option v-for="item in productData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="品牌名称">
        <el-select
          v-model="paramsForm.brand_id"
          filterable
          clearable
          remote
          :remote-method="(val)=>filterData(val,1)"
          @clear="(val)=>filterData('',1)"
        >
          <el-option v-for="item in brandData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="广告主">
        <el-select
          v-model="paramsForm.ader_id"
          filterable
          clearable
          remote
          :remote-method="(val)=>filterData(val,2)"
          @clear="(val)=>filterData('',2)"
        >
          <el-option v-for="item in aderData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <p class="subtitle">行业分类</p>
      <el-form-item label="行业大类">
        <!-- @change="changeIndustryId" -->
        <el-select v-model="paramsForm.industry_id" filterable clearable @change="changeIndustryId">
          <el-option v-for="item in firstIndustry" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="行业中类">
        <!-- @change="changeSecIndustryId" -->
        <el-select v-model="paramsForm.sec_industry_id" filterable clearable @change="changeSecIndustryId">
          <el-option v-for="item in secondIndustry" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="行业小类">
        <el-select v-model="paramsForm.thr_industry_id" filterable clearable>
          <el-option v-for="item in thirdIndustry" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <p class="subtitle">广告分类</p>
      <el-form-item label="一级分类" prop="tags_id">
        <!-- @change="changeTagsId" -->
        <el-select v-model="paramsForm.tags_id" filterable clearable @change="changeTagsId">
          <el-option v-for="item in firstAd" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="二级分类">
        <!-- @change="changeSecTagsId" -->
        <el-select v-model="paramsForm.sec_tags_id" filterable clearable @change="changeSecTagsId">
          <el-option v-for="item in secondAd" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="三级分类">
        <el-select v-model="paramsForm.thr_tags_id" filterable clearable>
          <el-option v-for="item in thirdAd" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <div class="key-image-wrap">
        <p class="subtitle">关键图</p>
        <base-btn :disabled="cutDisabled" @click="cutVideoImage">截取</base-btn>
        <div class="key-image-content">
          <div v-for="(item,index) in keyImageData" :key="index" class="key-image-item">
            <base-image :src="item.url" />
            <i class="el-icon-delete-solid" @click="deleteImage(index)" />
          </div>
        </div>
      </div>
      <el-form-item label="广告代言人">
        <el-autocomplete
          v-model="paramsForm.spokesman"
          :fetch-suggestions="querySearchSpokesman"
          placeholder="请输入广告代言人"
          :maxlength="50"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="广告语">
        <el-autocomplete
          v-model="paramsForm.slogan"
          :fetch-suggestions="querySearchSlogan"
          placeholder="请输入广告语"
          :maxlength="50"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="主题分类">
        <el-autocomplete
          v-model="paramsForm.subject"
          :fetch-suggestions="querySearchSubject"
          placeholder="请输入主题分类"
          :maxlength="50"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="广告系列">
        <el-autocomplete
          v-model="paramsForm.series"
          :fetch-suggestions="querySearchSeries"
          placeholder="请输入广告系列"
          :maxlength="50"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="制作机构">
        <el-autocomplete
          v-model="paramsForm.organization"
          :fetch-suggestions="querySearchOrganization"
          placeholder="请输入制作机构"
          :maxlength="50"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="语言">
        <el-select v-model="paramsForm.language">
          <el-option label="中文" value="中文" />
          <el-option label="英文" value="英文" />
          <el-option label="韩语" value="韩语" />
          <el-option label="日语" value="日语" />
        </el-select>
      </el-form-item>
    </el-form>
    <div class="btn-wrap">
      <base-btn :disabled="disabled" @click="confirm('ruleForm')">确定</base-btn>
      <base-btn type="info" @click="close">取消</base-btn>
    </div>
  </div>
</template>

<script>
import { dataURLtoFile } from '@/utils/canvas.js'
export default {
  components: {

  },
  props: {
    modelData: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      categories: ['公益广告', '商业广告', '购物广告', '植入广告', '赞助广告'],
      paramsForm: {
        tags: '',
        title: '',
        brand_id: null,
        product_id: null,
        tags_id: null,
        sec_tags_id: null,
        thr_tags_id: null,
        industry_id: null,
        sec_industry_id: null,
        thr_industry_id: null,
        ader_id: null,
        spokesman: '',
        subject: '',
        slogan: '',
        series: '',
        organization: '',
        language: '中文'
      },
      rules: {
        title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
        tags_id: [{ required: true, message: '请选择一级广告分类', trigger: 'change' }]
      },
      // 基础信息
      baseMessage: [{
        key: 'productData',
        label: '商品'
      }, {
        key: 'brandData',
        label: '品牌'
      }, {
        key: 'aderData',
        label: '广告主'
      }],
      // 分类
      cateGoryData: [{
        key: 'firstIndustry',
        label: '行业',
        level: '一级'
      }, {
        key: 'firstAd',
        label: '广告',
        level: '一级'
      }],
      productData: [],
      brandData: [],
      aderData: [],
      firstIndustry: [],
      secondIndustry: [],
      thirdIndustry: [],
      firstAd: [],
      secondAd: [],
      thirdAd: [],
      keyImageData: [],
      disabled: false,
      cutDisabled: false
    }
  },
  computed: {

  },
  watch: {

  },
  async created() {
    this.cateGoryData.forEach((item) => {
      this.getCategoryData(item)
    })
    Object.keys(this.paramsForm).forEach((item) => {
      this.paramsForm[item] = this.modelData[item]
    })
    this.baseMessage.forEach((item, index) => {
      const obj = JSON.parse(JSON.stringify(item))
      switch (index) {
        case 0:
          obj.id = this.paramsForm.product_id
          break
        case 1:
          obj.id = this.paramsForm.brand_id
          break
        case 2:
          obj.id = this.paramsForm.ader_id
          break
      }
      this.getBaseMessageData(obj)
    })
    this.keyImageData = this.modelData.img_list || []
    await this.getIndustryData()
    await this.getSecIndustryData()
    await this.getTagsData()
    await this.getSecTagsData()
  },
  mounted() {
    this.$bus.$on('setImageData', url => {
      this.keyImageData.push({ url: url, path: '' })
      this.cutDisabled = false
    })
  },
  methods: {
    // 标题联想功能
    async querySearchAsync(queryString, cb) {
      const titleList = await this.getTitleList(queryString)
      const titleArr = []
      titleList.forEach(item => {
        titleArr.push({
          id: item.id,
          value: item.title
        })
      })
      cb(titleArr)
    },
    // 获取标题列表
    getTitleList(val) {
      const params = {
        channel_id: this.modelData.channel_id,
        keyword: val
      }
      return new Promise((resolve, reject) => {
        this.$get('/epg-ad/epg-ad-search', params).then((res) => {
          resolve(res.data.list)
        }).catch((error) => {
          this.$message(error.msg)
        })
      })
    },
    confirm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid && !this.checkImg()) {
          this.disabled = true
          this.changeImg()
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    checkImg() {
      let errMsg = ''
      if (!this.keyImageData.length) {
        errMsg = '请截取关键图'
      }
      errMsg && this.$message.error(errMsg)
      return errMsg
    },
    deleteImage(index) {
      this.keyImageData.splice(index, 1)
    },
    // 上传截图
    async changeImg() {
      try {
        for (let i = 0; i < this.keyImageData.length; i++) {
          if (!this.keyImageData[i].path) {
            const imgName = new Date().getTime() + '.jpg'
            const imgFile = await dataURLtoFile(this.keyImageData[i].url, imgName)
            const formData = new FormData()
            formData.append('photo', imgFile)
            await this.uploadImg(formData, i)
          }
        }
        this.submit()
      } catch (err) {
        this.disabled = false
        alert(err)
      }
    },
    uploadImg(formData, index) {
      return new Promise((resolve, reject) => {
        this.$post('/ad-sample/upload-img', formData).then((res) => {
          this.keyImageData[index].path = res.data.file_path
          resolve()
        }).catch((err) => {
          reject(err)
        })
      })
    },
    getImgList() {
      const arr = []
      this.keyImageData.forEach((item) => {
        arr.push(item.path)
      })
      return arr
    },
    submit() {
      const params = {
        data_id: this.modelData.id,
        action: 'update',
        seq: {
          brand: this.getBrandName(),
          img_list: this.getImgList(),
          ...this.paramsForm
        }
      }
      this.$post('/ad-sample/modify', params).then((res) => {
        this.$message({
          type: 'success',
          message: '更新成功'
        })
        this.$emit('update')
        this.close()
      }).catch((error) => {
        this.$message({
          type: 'warning',
          message: error.msg
        })
      })
    },
    getBrandName() {
      const obj = this.brandData.find((item) => item.id === this.paramsForm.brand_id)
      return obj ? obj.name : ''
    },
    close() {
      this.$emit('close')
    },
    // 截取视频封面图
    cutVideoImage() {
      this.cutDisabled = true
      if (this.keyImageData.length >= 10) {
        this.cutDisabled = false
        this.$message({
          type: 'warning',
          message: '截取关键图不能超过10个'
        })
        return
      }
      this.$bus.$emit('cutVideoImage')
    },
    filterData(query, index) {
      const obj = this.baseMessage[index]
      obj.name = query
      this.getBaseMessageData(obj)
    },
    // 获取基本信息模块下拉列表数据
    async getBaseMessageData(obj) {
      const params = {
        type: obj.label,
        page: 1,
        limit: 20,
        ids: obj.id || null,
        name: obj.name || ''
      }
      this.$get('/ad-sample/ad-additional/list', params).then((res) => {
        this.$data[obj.key] = res.data.list
      }).catch((err) => {
        this.$message.error(err.msg)
      })
    },
    // 获取分类数据
    async getCategoryData(obj) {
      const params = {
        type: obj.label,
        level: obj.level,
        pid: obj.pid || null,
        page: 1,
        limit: 5000
      }
      this.$get('/ad-sample/ad-type/list', params).then((res) => {
        this.$data[obj.key] = res.data.list
      }).catch((err) => {
        this.$message.error(err.msg)
      })
    },
    // 改变一级行业分类选项
    changeIndustryId(id) {
      this.paramsForm.sec_industry_id = null
      this.paramsForm.thr_industry_id = null
      this.getIndustryData()
    },
    getIndustryData() {
      if (!this.paramsForm.industry_id) {
        this.secondIndustry = []
        this.thirdIndustry = []
        return
      }
      const obj = {
        level: '二级',
        type: '行业',
        key: 'secondIndustry',
        pid: this.paramsForm.industry_id
      }
      this.getCategoryData(obj)
    },
    // 改变二级行业分类选项
    changeSecIndustryId() {
      this.paramsForm.thr_industry_id = null
      this.getSecIndustryData()
    },
    getSecIndustryData() {
      if (!this.paramsForm.sec_industry_id) {
        this.thirdIndustry = []
        return
      }
      const obj = {
        level: '三级',
        type: '行业',
        key: 'thirdIndustry',
        pid: this.paramsForm.sec_industry_id
      }
      this.getCategoryData(obj)
    },
    // 改变一级广告分类选项
    changeTagsId(id) {
      const tagObj = this.firstAd.find((item) => item.id === Number(id))
      this.paramsForm.tags = tagObj ? tagObj.name : ''
      this.paramsForm.sec_tags_id = null
      this.paramsForm.thr_tags_id = null
      this.getTagsData()
    },
    getTagsData() {
      if (!this.paramsForm.tags_id) {
        this.secondAd = []
        this.thirdAd = []
        return
      }
      const obj = {
        level: '二级',
        type: '广告',
        key: 'secondAd',
        pid: this.paramsForm.tags_id
      }
      this.getCategoryData(obj)
    },
    // 改变二级广告分类选项
    changeSecTagsId() {
      this.paramsForm.thr_tags_id = null
      this.getSecTagsData()
    },
    getSecTagsData() {
      if (!this.paramsForm.sec_tags_id) {
        this.thirdAd = []
        return
      }
      const obj = {
        level: '三级',
        type: '广告',
        key: 'thirdAd',
        pid: this.paramsForm.sec_tags_id
      }
      this.getCategoryData(obj)
    },
    getSampleData(params) {
      params.page = 1
      params.limit = 5000
      return new Promise((resolve, reject) => {
        this.$get('/ad-sample/search', params)
          .then(res => {
            resolve(res.data.list)
          })
          .catch(error => {
            this.$message(error.msg)
          })
      })
    },
    getSampleShowArr(arr, type) {
      const sampleArr = []
      arr.forEach(item => {
        if (item[type]) {
          sampleArr.push({
            id: item.id,
            value: item[type]
          })
        }
      })
      return sampleArr
    },
    // 广告代言人联想
    async querySearchSpokesman(queryString, cb) {
      const params = {
        spokesman: queryString
      }
      const sampleList = await this.getSampleData(params)
      cb(this.getSampleShowArr(sampleList, 'spokesman'))
    },
    // 广告语联想
    async querySearchSlogan(queryString, cb) {
      const params = {
        slogan: queryString
      }
      const sampleList = await this.getSampleData(params)
      cb(this.getSampleShowArr(sampleList, 'slogan'))
    },
    // 主题分类联想
    async querySearchSubject(queryString, cb) {
      const params = {
        subject: queryString
      }
      const sampleList = await this.getSampleData(params)
      cb(this.getSampleShowArr(sampleList, 'subject'))
    },
    // 广告系列联想
    async querySearchSeries(queryString, cb) {
      const params = {
        series: queryString
      }
      const sampleList = await this.getSampleData(params)
      cb(this.getSampleShowArr(sampleList, 'series'))
    },
    async querySearchOrganization(queryString, cb) {
      const params = {
        organization: queryString
      }
      const sampleList = await this.getSampleData(params)
      cb(this.getSampleShowArr(sampleList, 'organization'))
    }
  }
}
</script>

<style scoped lang="scss">
.el-select{
  width: 300px;
}
.subtitle{
  color: #333;
  margin-bottom: 20px;
  font-weight: 600;
  display: inline-block;
  margin-right: 20px;
}
.key-image-wrap{
  margin-bottom: 20px;
  .key-image-content{
    .key-image-item{
      position: relative;
      width: 100px;
      height: 64px;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      background: #eee;
      margin-right: 10px;
      .el-icon-delete-solid{
        position: absolute;
        right: 5px;
        top: 5px;
        color: red;
        cursor: pointer;
      }
    }
  }
}
</style>
