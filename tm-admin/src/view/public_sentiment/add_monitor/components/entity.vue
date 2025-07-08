<!--
 * @Author: your name
 * @Date: 2021-04-15 20:15:45
 * @LastEditTime: 2021-05-10 11:36:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/components/entity.vue
-->
<template>
  <div class="d-flex add-item">
    <div class="lable">监测实体：</div>
    <div class="content">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane
          v-for="(item, i) in tab"
          :key="i"
          :label="item.lable"
          :name="item.name"
        />
      </el-tabs>
      <div class="dialog-btn">
        <span @click="showDialog('监测品牌列表', 'brandDialog')">监测品牌表</span>
        <span @click="showDialog('监测人物列表', 'personageDialog')">监测人物表</span>
      </div>
      <compontents
        :is="activeName"
        :list="entityList[activeName]"
        @showDialog="showDialog"
        @changeKeyword="changeKeyword"
        @addSlef="addSlef"
        @addCompetitor="addCompetitor"
      />
    </div>
    <!-- 弹窗 -->
    <BaseDialog
      :title="dialogTit"
      :show.sync="dialogVisible"
      width="900px"
      class="dialog"
    >
      <compontents
        :is="dialogCompomtentsName"
        @replaceBrand="replaceBrand"
        @replacePersonage="replacePersonage"
        @goImgDialog="goImgDialog(...arguments)"
      />
    </BaseDialog>

    <BaseDialog ref="dialog" :title="dialogImgTit + '的图片库'" :show.sync="dialogImgVisible" width="900px" class="dialog">
      <div class="addImg">
        <el-upload
          v-if="dialogImgType == 'brand'"
          class="upload-demo"
          action="customize"
          multiple
          :show-file-list="false"
          :http-request="uploadImg"
        >
          <el-button type="text" size="small" :disabled="uploadFlag">{{ uploadText }}</el-button>
        </el-upload>

        <div v-else>
          <el-input v-model="imgUrl" placeholder="请输入图片链接" size="normal" clearable />
          <el-button type="primary" size="default" :disabled="uploadFlag" @click="addImg(null)">{{ uploadText }}</el-button>
        </div>
      </div>

      <div class="img-wrap d-flex flex-wrap">
        <div
          v-for="(item, i) in dialogImgArray"
          :key="i"
          class="img-item d-flex align-items-center justify-content-center"
        >
          <i class="el-icon-close close" @click="delateImg(item)" />
          <el-image :src="item" fit="scale-down" :lazy="true" />
        </div>
      </div>
    </BaseDialog>
  </div>
</template>

<script>
import entityListSelf from './table/selfTable'
import entityListCompetitor from './table/competitorTable'
import professionalList from './table/keywordTable'
import brandDialog from './model/brandDialog'
import personageDialog from './model/personageDialog'

import { get, post, deleteRun } from '@/axios/httpBd.js'

export default {
  components: {
    brandDialog,
    personageDialog,
    entityListSelf,
    entityListCompetitor,
    professionalList
  },
  props: {
    list: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      // tab
      tab: [
        { lable: '自身', name: 'entityListSelf' },
        { lable: '竞品', name: 'entityListCompetitor' }
        // { lable: "专业词", name: "professionalList" },
      ],
      activeName: 'entityListSelf',
      // table
      entityList: {
        // 自身
        entityListSelf: {
          brandMap: [],
          logoMap: [],
          personageMap: [],
          avatarMap: []
        },
        // 竞品
        entityListCompetitor: {
          brandMap: [],
          logoMap: [],
          personageMap: [],
          avatarMap: []
        },
        // 行业关键词
        professionalList: []
      },

      // dialog
      dialogTit: '',
      dialogVisible: false,
      dialogCompomtentsName: '',

      dialogImgTit: '图片库',
      dialogImgVisible: false,
      dialogImgType: '',
      dialogImgArray: [],

      imgUrl: '',
      uploadFlag: false,
      uploadText: '上传图片'
    }
  },
  computed: {},
  watch: {
    list() {
      this.entityList = this.list
    }
  },
  created() {},
  mounted() {},
  methods: {
    addSlef(name, type) {
      console.log(name, type)
      type === 'brand'
        ? this.entityList['entityListSelf'].brandMap.some((item) => item === name)
          ? this.$message.error('请勿重复添加')
          : this.entityList['entityListSelf'].brandMap.push(name)
        : this.entityList['entityListSelf'].personageMap.some((item) => item === name)
          ? this.$message.error('请勿重复添加')
          : this.entityList['entityListSelf'].personageMap.push(name)
    },
    addCompetitor(name, type) {
      type === 'brand'
        ? this.entityList['entityListCompetitor'].brandMap.some((item) => item === name)
          ? this.$message.error('请勿重复添加')
          : this.entityList['entityListCompetitor'].brandMap.push(name)
        : this.entityList['entityListCompetitor'].personageMap.some((item) => item === name)
          ? this.$message.error('请勿重复添加')
          : this.entityList['entityListCompetitor'].personageMap.push(name)
    },
    async goImgDialog(contents, type) {
      this.dialogImgTit = contents
      this.dialogImgVisible = true
      this.dialogImgType = type
      const params = {
        brief: contents
      }
      const url = type === 'brand' ? '/image/api/v1/logo' : '/image/api/v1/face'
      const { res } = await get(url, params)
      this.dialogImgArray = res.data
    },
    // 上传&添加图片
    async uploadImg(params) {
      console.log(params)
      const file = params.file
      const fileType = file.type
      const isImage = fileType.indexOf('image') !== -1

      if (!isImage) {
        this.$message.error('只能上传图片格式png、jpg、gif!')
        return
      }

      const form = new FormData()
      // 文件对象
      form.append('file', file)
      const { err, res } = await this.$post('/api/common/uploadfile', form)
      !err ? this.addImg(res.data.imgurl) : this.$message.error(err.msg)
    },
    addImg(url) {
      const params = {
        brief: this.dialogImgTit,
        image: url || this.imgUrl
      }
      this.uploadFlag = true
      this.uploadText = '上传中'
      const path = this.dialogImgType === 'brand' ? '/image/api/v1/logo' : '/image/api/v1/face'
      post(path, params).then((res) => {
        console.log(res)
        const { err } = res
        if (!err) {
          this.uploadFlag = false
          this.uploadText = '上传图片'
          this.$message.success('上传成功')
          this.dialogImgArray.push(params.image)
        } else {
          this.uploadFlag = false
          this.uploadText = '上传图片'
          this.$message.error('上传失败')
        }
      }).catch(() => {
        this.uploadFlag = false
        this.uploadText = '上传图片'
        this.$message.error('上传失败')
      })
    },
    delateImg(url) {
      const params = {
        brief: this.dialogImgTit,
        image: url
      }
      const path = this.dialogImgType === 'brand' ? '/image/api/v1/logo' : '/image/api/v1/face'
      deleteRun(path, params).then((res) => {
        this.$message.success('删除成功')

        this.dialogImgArray.splice(
          this.dialogImgArray.findIndex((item) => item === url),
          1
        )
      })
    },

    // 删除在品牌表里已删除的项
    replaceBrand(value) {
      this.entityList['entityListSelf'].brandMap = this.entityList['entityListSelf'].brandMap.map(item => {
        if (item !== value) {
          return item
        }
      })
      this.entityList['entityListSelf'].logoMap = this.entityList['entityListSelf'].logoMap.map(item => {
        if (item !== value) {
          return item
        }
      })
      this.entityList['entityListCompetitor'].brandMap = this.entityList['entityListSelf'].brandMap.map(item => {
        if (item !== value) {
          return item
        }
      })
      this.entityList['entityListCompetitor'].logoMap = this.entityList['entityListSelf'].logoMap.map(item => {
        if (item !== value) {
          return item
        }
      })
    },

    // 删除在人物表里已删除的项
    replacePersonage(value) {
      this.entityList['entityListSelf'].personageMap = this.entityList['entityListSelf'].brandMap.map(item => {
        if (item !== value) {
          return item
        }
      })
      this.entityList['entityListSelf'].avatarMap = this.entityList['entityListSelf'].logoMap.map(item => {
        if (item !== value) {
          return item
        }
      })
      this.entityList['entityListCompetitor'].personageMap = this.entityList['entityListSelf'].brandMap.map(item => {
        if (item !== value) {
          return item
        }
      })
      this.entityList['entityListCompetitor'].avatarMap = this.entityList['entityListSelf'].logoMap.map(item => {
        if (item !== value) {
          return item
        }
      })
    },
    // 展示dialog
    showDialog(title, name) {
      this.dialogTit = title
      this.dialogCompomtentsName = name
      this.dialogVisible = true
    },
    // 添加&删除 关键词
    changeKeyword(list) {
      this.entityList[this.activeName] = list
    }
  }
}
</script>

<style scoped lang="scss">
::v-deep .el-tabs__item.is-active {
  background: #ebebeb !important;
}

.img-item {
  position: relative;
  margin-right: 10px;
  background: #e3e3e3;
  margin-top: 10px;
  .close {
    position: absolute;
    top: 5px;
    right: 5px;
    z-index: 9;
  }
}
</style>
