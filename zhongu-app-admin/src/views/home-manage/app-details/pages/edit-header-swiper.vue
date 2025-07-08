<!--
 * @Author: your name
 * @Date: 2021-10-22 14:21:10
 * @LastEditTime: 2021-11-19 16:48:55
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/app-details/pages/index.vue
-->
<template>
  <div class="header-swiper">

    <div class="conteniner">

      <div class="edit-box banner-edit-wrap">
        <BaseTitle class="base-title" title="Banner内容配置" />
        <span> Banner {{ baseHeaderBanner.length }}/{{ MAX_LENGTH }}</span>

        <div v-for="(item,index) in baseHeaderBanner" :key="index" class="data-list">
          <svg-icon
            v-if="baseHeaderBanner.length!==1"
            icon-class="delete"
            class="del-icon"
            @click="deleteBannerData(index)"
          />
          <div class="wrap">
            <div class="banner-title">
              <span class="name">Banner背景图</span>
              <span class="size">尺寸： {{ item.bannerSize.width }}*{{ item.bannerSize.height }}px</span>
            </div>

            <div class="btn-card">
              <div v-if="item.bannerFileName" class="group-info-wrap">
                <div class="banner-name">{{ item.bannerFileName }}</div>
                <el-button class="delete-btn" type="text" @click="deleteBanner('banner',index)">删除</el-button>
              </div>

              <UpImg v-else :size="item.bannerSize" class="btn" @getFileDetails="getFileDetails(arguments, index, 'banner')" />
            </div>
          </div>

          <div class="wrap">

            <div class="banner-title">
              <span class="name">Banner合成图</span>
              <span class="size">尺寸： {{ item.subBannerSize.width }}*{{ item.subBannerSize.height }}px</span>
            </div>

            <div class="btn-card">

              <div v-if="item.subBannerFileName" class="group-info-wrap">
                <div class="banner-name">{{ item.subBannerFileName }}</div>
                <el-button class="delete-btn" type="text" @click="deleteBanner('subBanner',index)">删除</el-button>
              </div>

              <UpImg v-else class="btn" :size="item.subBannerSize" @getFileDetails="getFileDetails(arguments, index,'subBanner')" />
            </div>

          </div>

          <div class="wrap">

            <div class="banner-title">
              <span class="name">Banner跳转页面选择</span>
              <span class="size">选择单品或分类</span>
            </div>

            <div class="btn-card">

              <div v-if="item.group.name || item.calssData.name" class="group-info-wrap">
                <GroupInfo :group="item.group" :calss-data="item.calssData" />
                <el-button class="delete-btn" type="text" @click="deleteSingle(index)">删除</el-button>
              </div>

              <div v-else class="com-btn btn" @click="selectCom(index)">选择单品</div>
            </div>
          </div>

        </div>

      </div>

      <div class="btn-info" @click="addBannerList()">+ 增加Banner</div>

      <div class="edit-btn-wrap">
        <el-button type="danger" style="width: 100px" size="mini" @click="reloadData">取消</el-button>
        <!-- <el-button type="primary" style="width: 100px" size="mini" @click="save">保存</el-button> -->
      </div>

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { baseHeaderBanner } from '../../base-com-data'
import ComBtn from '../../components/select-com-btn.vue'
import GroupInfo from '../../components/group-info.vue'
import UpImg from '../../components/upimg.vue'
import ImgMerge from '@/utils/draw-imgs'
import { dataURLtoFile } from '@/utils/file.js'

export default {
  components: {
    // ComBtn,
    GroupInfo,
    UpImg
  },
  props: {

  },
  data() {
    return {
      mageImg: '',
      baseHeaderBanner: [],
      MAX_LENGTH: 5,
      activeIndex: -1
    }
  },
  computed: {
    ...mapState('bdapp', ['appData', 'appDataCopy'])
  },
  watch: {
    // 更新 store 数据
    baseHeaderBanner: {
      handler(obj) {
        const data = this.appData
        data.HeaderBanner = this.baseHeaderBanner
        this.$store.commit('bdapp/SET_ZHONGGU_APP_DATA', data)
      },
      deep: true
    }
  },
  created() {
    this.baseHeaderBanner = this.$lodash.cloneDeep(this.appData.HeaderBanner)
    console.log('🚀 ~ file: edit-header-swiper.vue ~ line 135 ~ created ~ this.appData', this.appData)
  },
  beforeDestroy() {
    this.$bus.$off('getActiveClassData')
  },
  mounted() {
    this.$bus.$on('getActiveClassData', (data) => {
      this.setBaseData(data)
    })
  },
  methods: {
    setBaseData(data) {
      const { activeClassData, checkboxGroup } = data
      console.log('🚀 ~ f', activeClassData, checkboxGroup)
      if ((activeClassData.id === -1 || activeClassData.name === '未分类') && !checkboxGroup.id) {
        this.$message.error('不能选择' + activeClassData.name)
        return
      }
      this.baseHeaderBanner[this.activeIndex]['calssData'] = activeClassData
      this.baseHeaderBanner[this.activeIndex]['group'] = checkboxGroup
    },
    deleteBanner(key, index) {
      if (key === 'banner') {
        this.baseHeaderBanner[index]['bannerFileName'] = ''
        this.baseHeaderBanner[index]['bannerSrc'] = ''
        this.baseHeaderBanner[index]['bannerUrl'] = ''
      }
      if (key === 'subBanner') {
        this.baseHeaderBanner[index]['subBannerFileName'] = ''
        this.baseHeaderBanner[index]['subBannerSrc'] = ''
        this.baseHeaderBanner[index]['subBannerUrl'] = ''
      }
    },
    deleteBannerData(index) {
      this.baseHeaderBanner.splice(index, 1)
    },
    // 添加
    addBannerList() {
      if (this.baseHeaderBanner.length > this.MAX_LENGTH - 1) {
        this.$message.error(`最多添加${this.MAX_LENGTH}条`)
        return
      }
      const data = baseHeaderBanner()
      this.baseHeaderBanner.push(data)
    },
    // 获取图片信息
    async getFileDetails(arg, index, key) {
      const { fileName, filePath, fileUrl } = arg[0]

      if (key === 'banner') {
        this.baseHeaderBanner[index]['bannerFileName'] = fileName
        this.baseHeaderBanner[index]['bannerSrc'] = filePath
        this.baseHeaderBanner[index]['bannerUrl'] = fileUrl
      }
      if (key === 'subBanner') {
        this.baseHeaderBanner[index]['subBannerFileName'] = fileName
        this.baseHeaderBanner[index]['subBannerSrc'] = filePath
        this.baseHeaderBanner[index]['subBannerUrl'] = fileUrl
      }

      const imgArr = [this.baseHeaderBanner[index]['bannerUrl'], this.baseHeaderBanner[index]['subBannerUrl']]

      if (imgArr[0] && imgArr[1]) {
        const base64 = await this.imgMerge(imgArr)
        const file = dataURLtoFile(base64, 'homeHeaderBannerfile')

        const fromData = new FormData()
        fromData.append('photo', file)

        const { err, res } = await this.$post('/admin/util/upload-img', fromData)
        if (err) {
          this.$message.error(err.msg)
        } else {
          const { data } = res
          this.baseHeaderBanner[index]['imgMergeUrl'] = data.file_path
          this.baseHeaderBanner[index]['imgMergeFileName'] = 'homeHeaderBannerfile'
        }
      }
    },
    // 合并图片
    async imgMerge(arr) {
      console.log('🚀 ~ file: edit-header-swiper.vue ~ line 183 ~ imgMerge ~ arr', arr)
      return new Promise((resolve, reject) => {
        const imgMerge = new ImgMerge([
          {
            url: arr[0],
            width: 678,
            height: 252,
            x: 0,
            y: 148
          },
          {
            url: arr[1],
            width: 300,
            height: 400,
            x: 308,
            y: 0
          }
        ])

        imgMerge.then(img => {
          resolve(img)
        })
      })
    },
    // 弹起 单品分类弹框
    selectCom(index) {
      this.activeIndex = index
      this.$store.commit('bdapp/SHOW_COM_DIALOG', true)
    },
    // 删除
    deleteSingle(index) {
      this.baseHeaderBanner[index].group = {}
      this.baseHeaderBanner[index].calssData = {}
    },
    // 重置数据
    reloadData() {
      this.$confirm('确认恢复到之前数据吗', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.baseHeaderBanner = this.$lodash.cloneDeep(this.appDataCopy.HeaderBanner)
      })
    },
    save() {
    }
  }
}
</script>

<style scoped lang="scss">
.header-swiper {
  height : 100%;
}
.conteniner {
  height : 100%;
  overflow : hidden;
  padding : 20px;
  display: flex;
  flex-direction: column;

  .btn-card {
    min-width : 200px;
  }

  .edit-box {
    flex: 1;
    max-height : calc(100%);
    // overflow: hidden;
    overflow-y : auto;
  }

  .data-list {
    width : calc(100% - 6px);
    display : flex;
    flex-direction : column;
    border : 1px solid rgb(224, 223, 223);
    margin : 0;
    margin-top : 10px;
    margin-bottom : 20px;
    position : relative;
    .del-icon {
      position : absolute;
      right : -6px;
      top : -6px;
      cursor : pointer;
    }
    .wrap {
      margin-bottom : 10px;
    }

    .banner-title {
      margin-left : 10px;
      display : flex;
      justify-content : space-between;
      margin : 10px;
      .size {
        color : rgb(136, 134, 134);
      }
    }
  }
  .edit-btn-wrap {
    display : flex;
    justify-content : flex-end;
    margin-bottom : 20px;
    margin-top : 20px;
  }
}

</style>
