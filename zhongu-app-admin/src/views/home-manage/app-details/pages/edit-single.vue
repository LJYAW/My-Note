<!--
 * @Author: your name
 * @Date: 2021-10-22 14:21:10
 * @LastEditTime: 2021-11-25 15:19:44
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/app-details/pages/index.vue
-->
<template>
  <div class="header-swiper">

    <div class="conteniner">

      <div class="edit-box banner-edit-wrap">
        <BaseTitle class="base-title" title="Banner内容配置" />

        <div class="banner-title-wrap">
          <div class="banner-title">
            <span class="name">Banner背景图</span>
            <span class="size">尺寸： {{ baseSingle.bannerSize.width }}*{{ baseSingle.bannerSize.height }}px</span>
          </div>

          <div class="banner-title">
            <span class="name">Banner跳转页面选择</span>
            <span class="size">选择单品或分类</span>
          </div>
        </div>

        <div v-for="(item,index) in baseSingle.banner" :key="index" class="data-list">
          <svg-icon
            icon-class="delete"
            class="del-icon"
            @click="deleteBanner(index)"
          />
          <div class="btn-card">

            <div v-if="item.bannerFileName" class="group-info-wrap">
              <div class="banner-name">{{ item.bannerFileName }}</div>
              <el-button class="delete-btn" type="text" @click="deleteSingleBanner(index)">删除</el-button>
            </div>

            <UpImg v-else :size="baseSingle.bannerSize" class="btn" @getFileDetails="getFileDetails(arguments, index)" />
          </div>

          <div class="btn-card">
            <div v-if="item.calssData && item.calssData.name" class="group-info-wrap">
              <GroupInfo :group="item.group" :calss-data="item.calssData" />
              <el-button class="delete-btn" type="text" @click="deleteSingleGroup('banner',index)">删除</el-button>
            </div>

            <div v-else class="com-btn btn" @click="selectCom('banner',index)">选择单品</div>
          </div>

        </div>

        <div class="btn-info" @click="addBannerList()">+ 增加Banner</div>

        <div class="edit-btn-wrap">
          <el-button type="danger" style="width: 100px" size="mini" @click="reloadData">取消</el-button>
          <!-- <el-button type="primary" style="width: 100px" size="mini" @click="save">保存</el-button> -->
        </div>

      </div>

      <div class="edit-box">
        <BaseTitle class="base-title" title="单品配置" />

        <div v-for="(item,j) in baseSingle.commodity" :key="j" class="data-list">
          <div class="btn-card">

            <div v-if="item.group && item.group.name" class="group-info-wrap">
              <GroupInfo :group="item.group" :calss-data="item.calssData" />
              <el-button class="delete-btn" type="text" @click="deleteSingleGroup('commodity',j)">删除</el-button>
            </div>

            <div v-else class="com-btn btn" @click="selectCom('commodity',j)">选择单品</div>
          </div>
        </div>

        <div class="btn-info" @click="addCommodity()">+ 增加单品</div>

        <div class="edit-btn-wrap">
          <el-button type="danger" style="width: 100px" size="mini" @click="reloadData">取消</el-button>
          <!-- <el-button type="primary" style="width: 100px" size="mini" @click="save">保存</el-button> -->
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { baseSingle } from '../../base-com-data'
import GroupInfo from '../../components/group-info.vue'
import UpImg from '../../components/upimg.vue'

export default {
  components: {
    UpImg,
    GroupInfo
  },
  props: {

  },
  data() {
    return {
      baseSingle: {},
      activeSingle: {
        key: '',
        index: -1
      }
    }
  },
  computed: {
    ...mapState('bdapp', ['appData', 'singleIndex', 'appDataCopy'])
  },
  watch: {
    singleIndex(val) {
      console.log('🚀 ~ file: edit-single.vue ~ line 121 ~ singleIndex ~ val', val)
      this.baseSingle = this.$lodash.cloneDeep(this.appData.Single[this.singleIndex])
    },
    // 更新 store 数据
    baseSingle: {
      handler(obj) {
        const data = this.appData
        data.Single[this.singleIndex] = obj
        this.$store.commit('bdapp/SET_ZHONGGU_APP_DATA', data)
      },
      deep: true
    }
  },
  created() {
    this.baseSingle = this.$lodash.cloneDeep(this.appData.Single[this.singleIndex])
  },
  beforeDestroy() {
    this.$bus.$off('getActiveClassData')
  },
  mounted() {
    this.$bus.$on('getActiveClassData', (data) => {
      console.log('🚀 ~ file: edit-single.vue ~ line 142 ~ this.$bus.$on ~ data', data)
      if (this.activeSingle.key !== 'banner' && !data.checkboxGroup.id) {
        this.$message.error('请选择单品')
        return
      }
      this.setBaseData(data)
    })
    this.$bus.$on('delSingle', (data) => {
      this.baseSingle = this.$lodash.cloneDeep(this.appData.Single[0])
      this.$forceUpdate()
    })
  },
  methods: {
    // 删除 banner
    deleteSingleBanner(index) {
      this.baseSingle.banner[index].bannerSrc = ''
      this.baseSingle.banner[index].bannerUrl = ''
      this.baseSingle.banner[index].bannerFileName = ''
    },
    deleteBanner(index) {
      this.baseSingle.banner.splice(index, 1)
    },
    // 删除 单品
    deleteSingleGroup(key, index) {
      this.$set(this.baseSingle[key][index], 'group', {})
      this.$set(this.baseSingle[key][index], 'calssData', {})
    },
    // 设置数据
    setBaseData(data) {
      const { activeClassData, checkboxGroup } = data
      const { key, index } = this.activeSingle
      // 未选择分类的时候
      console.log(this.baseSingle)

      // if (!this.baseSingle.calssData && !this.baseSingle.calssData.id) {
      this.baseSingle.calssData = activeClassData
      this.baseSingle.title = activeClassData.name
      this.baseSingle.subTitle = activeClassData.intro
      // }

      this.baseSingle[key][index]['group'] = checkboxGroup
      this.baseSingle[key][index]['calssData'] = activeClassData
    },
    // 获取图片信息
    getFileDetails(arg, index) {
      const { fileName, filePath, fileUrl } = arg[0]
      this.baseSingle.banner[index].bannerSrc = filePath
      this.baseSingle.banner[index].bannerUrl = fileUrl
      this.baseSingle.banner[index].bannerFileName = fileName
    },
    // 弹起 单品分类弹框
    selectCom(key, index) {
      this.activeSingle = { key: key, index: index }
      this.$store.commit('bdapp/SHOW_COM_DIALOG', true)
    },
    // 增加Banner
    addBannerList() {
      const MAX_SIZE = 5
      if (this.baseSingle.banner.length > MAX_SIZE) {
        this.$message.warning(`最多添加${MAX_SIZE}条`)
        return
      }
      const banner = JSON.parse(JSON.stringify(baseSingle().banner[0]))
      this.baseSingle.banner.push(banner)
    },
    // 增加单品
    addCommodity() {
      const MAX_SIZE = 8

      if (this.baseSingle.commodity.length > MAX_SIZE) {
        this.$message.warning(`最多添加${MAX_SIZE}条`)
        return
      }
      const comType = this.baseSingle.comType
      const comArr = JSON.parse(JSON.stringify(baseSingle(comType).commodity))
      for (let i = 0; i < comArr.length; i++) {
        const element = comArr[i]
        this.baseSingle.commodity.push(element)
      }
    },
    // 重置数据
    reloadData() {
      this.$confirm('确认恢复到之前数据吗', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.baseSingle = this.$lodash.cloneDeep(this.appDataCopy.Single[this.singleIndex])
      })
    },
    // 保存数据
    save() {

    }

  }
}
</script>

<style scoped lang="scss">
.conteniner {
  padding : 20px;
  height : 100%;
  max-height : 800px;
  overflow : hidden;
  overflow-y : auto;
  .banner-edit-wrap {
    .data-list {
      border : 1px solid #E0DFDF;
      margin-bottom : 10px;
      position : relative;
      .del-icon {
        position : absolute;
        right : -6px;
        top : -6px;
        cursor : pointer;
      }
    }
  }
}

</style>
