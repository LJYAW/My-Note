<!--
 * @Author: your name
 * @Date: 2021-10-25 18:29:09
 * @LastEditTime: 2021-12-07 15:41:58
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/components/ProductForm.vue
-->
<template>
  <div class="new-product-wrap">
    <div class="product-form">
      <el-form ref="ruleForm" :model="form" :rules="rules" :inline="true">

        <el-form-item prop="name" label="产品名称">
          <el-input v-model="form.name" :maxlength="20" placeholder="产品名称最多20个字符" />
        </el-form-item>

        <div class="flex-wrap">
          <el-form-item prop="category_id" label="产品分类">
            <el-select v-model="form.category_id">
              <!-- <el-option label="未分类" :value="0" /> -->
              <el-option
                v-for="item in classData"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="产品编号">
            <el-input v-model="form.number" placeholder="请输入产品编号" />
          </el-form-item>
        </div>

        <div class="flex-wrap">
          <el-form-item prop="inventory" label="产品库存">
            <el-input-number v-model="form.inventory" :min="1" :controls="false" :max="999999" />
          </el-form-item>
          <el-form-item prop="price" label="产品价格">
            <el-input-number v-model="form.price" :min="1" :controls="false" :max="999999" />
          </el-form-item>
          <el-form-item prop="type" label="售卖模式">
            <el-select v-model="form.type">
              <el-option
                v-for="item in saleData"
                :key="item"
                :label="item"
                :value="item"
              />
            </el-select>
          </el-form-item>
          <el-form-item class="date-item">
            <el-radio-group v-model="shelfType">
              <el-radio label="立即上架">立即上架</el-radio>
              <el-radio label="定时上架">定时上架</el-radio>
            </el-radio-group>
            <el-date-picker
              ref="datePicker"
              v-model="form.on_at"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              :clearable="false"
              :picker-options="pickerOptions"
              placeholder="选择日期时间"
            />
          </el-form-item>
        </div>

        <div v-if="form.type=='竞拍'" class="flex-wrap">
          <el-form-item prop="max_price" label="产品封顶价格">
            <el-input-number v-model="form.max_price" :min="1" :controls="false" :max="999999" />
          </el-form-item>
          <el-form-item prop="end_at" label="产品售卖时长">
            <el-date-picker
              v-model="form.end_at"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="endPickerOptions"
              placeholder="选择日期时间"
            />
          </el-form-item>
        </div>

        <div v-if="form.type=='盲盒'">
          <div class="flex-wrap blind-wrap">
            <div v-for="(item,index) in form.blind_box" :key="index" class="blind-box">
              <div class="blind-item">
                <el-form-item label="盲盒单品名称" :prop="'blind_box.'+index+'.name'" :rules="blindRules.name">
                  <el-input v-model="item.name" />
                </el-form-item>
                <el-form-item label="盲盒单品描述" :prop="'blind_box.'+index+'.des'" :rules="blindRules.des">
                  <el-input v-model="item.des" />
                </el-form-item>
              </div>
              <el-form-item label="盲盒单品图" :prop="'blind_box.'+index+'.img'" :rules="blindRules.img">
                <p class="tips">尺寸需为140X212px png格式</p>
                <div class="blind-image">
                  <p>{{ item.blindFileName }}</p>
                  <base-upload file-type="image/png" @getFileUrl="getBlindSrc(arguments,index)" />
                </div>
              </el-form-item>
            </div>
          </div>
          <base-btn class="add-btn" @click="addBlind">+ 增加盲盒单品</base-btn>
        </div>

        <div class="flex-wrap">

          <div class="desc-item">
            <el-form-item label="产品简介" prop="intro">
              <el-input v-model="form.intro" type="textarea" :maxlength="16" placeholder="最多16个英文字符" />
            </el-form-item>
            <el-form-item label="产品描述" prop="des">
              <el-input v-model="form.des" type="textarea" :maxlength="1000" placeholder="最多1000个英文字符" />
            </el-form-item>
          </div>
        </div>

        <el-form-item label="产品主图" prop="main_img">
          <div v-loading="mainImgLoading" class="image-wrap">
            <div v-if="form.main_img">
              <el-image style="width: 340px; height: 300px" :preview-src-list="[BASE_IMG_SRC + form.main_img]" :src="BASE_IMG_SRC + form.main_img" fit="contain" />
              <!-- <el-button type="text" @click="shouImgCut([750,850],'main_img')">点击更换图片</el-button> -->
            </div>
            <!-- <div v-else @click="shouImgCut([750,850],'main_img')">
                <svg-icon icon-class="file" class="upload-icon" />
                <p class="btn">点击上传图片</p>
              </div> -->
            <base-upload style="margin-bottom: 50px" button-name="" class="btn-wrap" @getFileUrl="getDetailDataBanner">
              <svg-icon icon-class="file" class="upload-icon" />
              <p class="btn-style">点击上传图片</p>
            </base-upload>
            <p class="image-size">固定尺寸 750X850px</p>
          </div>
        </el-form-item>

        <el-form-item label="产品详情" prop="detail">
          <div v-loading="detailImgLoading" class="detail-wrap">
            <p class="tips">固定尺寸 678X750px</p>
            <div v-for="(item,index) in form.detail_img" :key="item" class="detail-item">
              <!-- <base-image :src="item" /> -->
              <el-image :preview-src-list="[BASE_IMG_SRC + item]" :src="BASE_IMG_SRC + item" fit="contain" />
              <svg-icon icon-class="delete" class="el-icon-delete" @click="deleteImage(index)" />
            </div>
            <!-- <base-upload button-name="" class="btn-wrap" @getFileUrl="getDetailData">
              <svg-icon icon-class="file" class="upload-icon" />
              <p class="btn-style">增加图片</p>
            </base-upload> -->

            <base-upload style="margin-bottom: 50px" button-name="" class="btn-wrap" @getFileUrl="getDetailDataImgList">
              <svg-icon icon-class="file" class="upload-icon" style="margin-bottom: 10px" />
              <p class="btn-style">增加产品详情图片</p>
            </base-upload>

            <p class="image-size">固定尺寸 750X850px</p>

            <!-- <div class="btn-wrap" @click="shouImgCut([678,750],'detail_img', true)">
              <svg-icon icon-class="file" class="upload-icon" />
              <p class="btn-style">增加图片</p>
              <el-button type="text" />
            </div> -->

          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="btn-wrap">
      <base-btn type="info">清空数据</base-btn>
      <base-btn @click="save('preview')">预览</base-btn>
      <base-btn @click="save('submit')">确认保存</base-btn>
      <base-btn @click="save('toStore')">放入仓库</base-btn>
      <base-btn>返回</base-btn>
    </div>
    <base-dialog
      :show.sync="dialogVisible"
      width="450px"
      title=""
    >
      <PreviewDialog :preview-data="form" />
    </base-dialog>

    <base-dialog
      :show.sync="dialogVisibleImgCutter"
      :width="boxWidth + 'px'"
      title=""
    >
      <ImgCutter
        v-if="dialogVisibleImgCutter"
        ref="imgCutterModal"
        label="请选择图片"
        file-type="png"
        :cross-origin="true"
        cross-origin-header="*"
        :rate="rate"
        :do-not-display-copyright="true"
        tool-bgc="none"
        :is-modal="false"
        :show-choose-btn="true"
        :lock-scroll="true"
        :box-width="boxWidth"
        :box-height="boxHeight"
        :cut-width="cutWidth"
        :cut-height="cutHeight"
        :size-change="true"
        :move-able="true"
        :img-move="true"
        :original-graph="false"
        watermark-text=""
        :small-to-upload="true"
        :save-cut-position="true"
        :scale-able="true"
        :preview-mode="false"
        :tool-box-overflow="true"
        @cutDown="cutDown"
      />
    </base-dialog>

  </div>
</template>

<script>
import checkImg from '@/utils/img-size'
import { rules, blindRules } from '../js/rule'
import PreviewDialog from './PreviewDialog.vue'
import { Upload } from '@/utils/upload'
import ImgCutter from 'vue-img-cutter'

export default {
  components: {
    PreviewDialog,
    ImgCutter
  },
  props: {

  },
  data() {
    return {
      boxWidth: 0,
      boxHeight: 0,
      dialogVisibleImgCutter: false,
      cutWidth: '',
      cutHeight: '',
      rate: '',
      activeDataKey: '',
      activeIndex: null,
      form: {
        name: null,
        category_id: -1,
        id: null,
        number: null,
        inventory: undefined,
        price: undefined,
        type: null,
        on_at: null,
        max_price: undefined,
        end_at: null,
        blind_box: [{
          name: '',
          des: '',
          img: '',
          blindFileName: ''
        }], // 盲盒单品
        detail_img: [], // 详情图片
        main_img: null,
        intro: null,
        des: null
      },
      mainImgLoading: false,
      detailImgLoading: false,
      shelfType: '立即上架',
      saleData: ['单品', '盲盒', '竞拍'],
      classData: [], // 分类
      rules: rules,
      blindRules: blindRules,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now()
        }
      },
      endPickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now()
        }
      },
      dialogVisible: false
    }
  },
  computed: {
    BASE_IMG_SRC() {
      return this.$store.state.user.fileUrl + '/'
    }
  },
  watch: {

  },
  created() {
    this.form.on_at = new Date(+new Date() + 8 * 3600 * 1000).toJSON().substr(0, 19).replace('T', ' ')
    this.getClassData()
    if (this.$route.query.id) {
      this.getGoodsDetail()
    }
  },
  mounted() {

  },
  methods: {
    async getDetailDataBanner(url, file) {
      this.mainImgLoading = true
      const { err, res } = await Upload(file)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form.main_img = res.data.file_path
      this.mainImgLoading = false
    },
    async getDetailDataImgList(url, file) {
      this.detailImgLoading = true
      const { err, res } = await Upload(file)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form.detail_img.push(res.data.file_path)
      this.detailImgLoading = false
    },
    async cutDown(data) {
      console.log('🚀 ~ file: ProductForm.vue ~ line 287 ~ cutDown ~ data', data)
      const { file } = data
      const { err, res } = await Upload(file)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      if (!this.activeIndex) {
        this.form[this.activeDataKey] = res.data.file_path
      } else {
        this.form[this.activeDataKey].push(res.data.file_path)
      }

      this.activeDataKey = ''
      this.activeIndex = null
      this.dialogVisibleImgCutter = false
    },
    shouImgCut(size, key, index) {
      const [width, height] = size
      this.boxWidth = width + 400
      this.boxHeight = height + 100
      this.cutHeight = height
      this.rate = `${width}:${height}`
      this.cutWidth = width
      this.activeDataKey = key
      this.activeIndex = index
      this.dialogVisibleImgCutter = true
    },
    async getGoodsDetail() {
      const { err, res } = await this.$get('/admin/goods', { ids: this.$route.query.id })
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form = JSON.parse(JSON.stringify(res.data.list[0]))
      const arr = [{
        name: '',
        des: '',
        img: '',
        blindFileName: ''
      }]
      this.form.blind_box = this.form.type === '盲盒' ? this.form.blind_box : arr
      this.shelfType = this.form.status === '已下架' ? '立即上架' : '定时上架'
    },
    async getClassData() {
      const params = {
        page: 1,
        limit: 1000
      }
      const { err, res } = await this.$get('/admin/categories', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.classData = res.data.list
    },
    // 上传产品主图
    async getFileData(url, file) {
      const imgData = await checkImg(url)
      if (imgData.w !== 750 || imgData.h !== 850) {
        this.$message.error('请上传尺寸为750X850的图片')
        return
      }
      const { err, res } = await Upload(file)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form.main_img = res.data.file_path
    },
    // 上传盲盒单品
    async getBlindSrc(params, index) {
      const imgData = await checkImg(params[0])
      if (imgData.w !== 140 || imgData.h !== 212) {
        this.$message.error('请上传尺寸为140X212的图片')
        return
      }
      const { err, res } = await Upload(params[1])
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form.blind_box[index].blindFileName = params[1].name
      this.form.blind_box[index].img = res.data.file_path
    },
    // 新增盲盒单品
    addBlind() {
      this.form.blind_box.push({
        name: '',
        des: '',
        img: '',
        blindFileName: '' })
    },
    // 上传产品详情
    async getDetailData(url, file) {
      if (this.form.detail_img.length >= 10) {
        this.$message.error('产品详情图不能超过10张')
        return
      }
      const imgData = await checkImg(url)
      if (imgData.w !== 678 || imgData.h !== 750) {
        this.$message.error('请上传尺寸为678X750的图片')
        return
      }
      const { err, res } = await Upload(file)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.form.detail_img.push(res.data.file_path)
    },
    // 删除产品详情图片
    deleteImage(index) {
      this.form.detail_img.splice(index, 1)
    },
    // 保存或者预览
    save(type) {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          if (this.shelfType === '定时上架' && !this.form.on_at) {
            this.$message.error('请选择上架日期')
            return
          }
          if (this.form.type === '盲盒' && !this.form.blind_box.length) {
            this.$message.error('请上传盲盒单品')
            return
          }
          if (!this.form.detail_img.length) {
            this.$message.error('请上传产品详情图')
            return
          }
          if (this.form.type === '竞拍' && this.form.price > this.form.max_price) {
            this.$message.error('产品价格不能高于产品封顶价格')
            return
          }
          type === 'preview' ? this.dialogVisible = true : this.submit(type)
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    async submit(type) {
      const obj = JSON.parse(JSON.stringify(this.form))
      obj.on_at = this.shelfType === '立即上架' ? '立即上架' : obj.on_at
      obj.blind_box = obj.type === '盲盒' ? obj.blind_box : null
      if (type === 'toStore') {
        obj.on_at = '放入仓库'
      }
      const { err, res } = !this.$route.query.id
        ? await this.$post('/admin/goods', obj)
        : await this.$put(`/admin/goods/${this.$route.query.id}`, obj)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '保存成功'
        })
        this.routeGo()
      }
    },
    routeGo() {
      this.$router.push({
        path: '/product-manage'
      })
    }
  }
}
</script>

<style scoped lang="scss">
.new-product-wrap {
  display : flex;
  ::v-deep .product-form {
    width : 80%;
    .el-form {
      display : flex;
      flex-direction : column;
      .flex-wrap {
        display : flex;
      }
      .add-btn {
        width : 100%;
        background : #FFFFFF;
        color : #5675E8;
        margin-bottom : 37px;
      }
      .el-form-item__label {
        display : block;
        text-align : left;
        line-height : 14px;
        margin-bottom : 15px;
      }
      .el-form-item {
        flex : 1;
        margin-right : 24px;
        .el-form-item__content {
          width : 100%;
          line-height : 0;
          input, .el-select, .el-input-number, .el-date-editor {
            width : 100%;
            background : #F8F8F8;
            border : none;
          }
          .image-wrap {
            width : 100%;
            padding : 32px 0 25px 0;
            background : #F8F8F8;
            border-radius : 4px;
            text-align : center;
            cursor : pointer;
            .el-image {
              height : 98px;
              margin-bottom : 8px;
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
          .detail-wrap {
            background : #F8F8F8;
            border-radius : 4px;
            padding : 30px 0;
            text-align : center;
            display : flex;
            flex-direction : column;
            align-items : center;
            .tips {
              width : 100%;
              text-align : right;
              opacity : .4;
              font-size : 11px;
              line-height : 11px;
              padding-right : 20px;
            }
            .detail-item {
              position : relative;
              width : fit-content;
              margin-bottom : 23px;
              .el-icon-delete {
                font-size : 30px;
                position : absolute;
                right : -10px;
                top : -10px;
                cursor : pointer;
              }
              .el-image {
                width : 28vw;
                height : 31vw;
                background : #D8D8D8;
                border-radius : 29px;
                margin : auto;
              }
            }
            .btn-wrap {
              display : flex;
              width : 100%;
              flex-direction : row;
              justify-content : center;
              align-items : center;
              margin-top : 30px;

              .btn-style {
                font-size : 21px;
                font-weight : 400;
                color : #404040;
                line-height : 21px;
              }
              .upload-icon {
                display : inline-block;
                width : 39px;
                height : 32px;
                margin-right : 11px;
              }
            }
          }
          .el-radio-group {
            vertical-align : top;
          }
        }
        &.date-item {
          .el-form-item__content {
            line-height : 29px;
            .el-radio {
              margin-right : 10px;
            }
          }
        }
      }
      .desc-item {
        display : flex;
        flex-direction : column;
        flex : 1;
        .el-form-item {
          margin-bottom : 23px;
          textarea {
            height : 71px;
            background : #F8F8F8;
            border-radius : 4px;
            resize : none;
            overflow-y : auto;
            border : none;
          }
          &:last-child textarea {
            height : 68px;
          }
        }
      }

      .blind-wrap {
        flex-direction : column;
        .blind-box {
          display : flex;
          flex : 1;
          .blind-item {
            display : flex;
            flex : 1;
            margin-right : 24px;
            .el-form-item:last-child {
              margin-right : 0;
            }
          }
          .blind-image {
            background : #F8F8F8;
            border-radius : 3px;
            text-align : center;
            line-height : 20px;
            padding : 5px 0;
            height : 40px;
            p, .btn-style {
              font-size : 11px;
              line-height : 11px;
              margin-top : 3px;
            }
          }
          .tips {
            position : absolute;
            right : 0;
            top : -25px;
            color : #BBBBBB;
          }
        }
      }
    }
  }
  .btn-wrap {
    display : flex;
    flex-direction : column;
    button {
      margin : 0;
      margin-bottom : 8px;
    }
    .el-button--info {
      color : #404040;
    }
  }
}
::v-deep .el-dialog {
  margin-top : 8vh!important;
  .el-dialog__header {
    padding : 0;
    .el-dialog__headerbtn {
      top : 0;
      right : -30px;
    }
  }
  .el-dialog__body {
    padding : 0;
    height : 86vh;
    overflow-y : auto;
    background : #F5F6F6;
  }
}

::v-deep .el-dialog .el-dialog__body {
  height : 100%
}

</style>
