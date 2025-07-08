<!--
 * @Author: your name
 * @Date: 2021-08-31 15:09:45
 * @LastEditTime: 2021-09-17 16:26:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/material-tab/AdditionalMaterial/index.vue
-->
<template>
  <div class="additional-material">
    <!-- tab -->
    <div class="tabs-wrap">
      <div
        v-for="item in tabs"
        :key="item"
        :class="[activeName==item&&'active','tabs-item']"
        @click="activeName=item"
      >{{ item }}素材</div>
    </div>
    <!-- 素材列表 -->
    <ul v-infinite-scroll="load" class="infinite-list transition-wrap">
      <div v-for="item in materialData" :key="item.id" class="infinite-list-item">
        <component :is="componentName" :material-data="item" @delete="deleteMaterial" />
      </div>
    </ul>
  </div>
</template>

<script>
import MaterialVideo from './MaterialVideo.vue'
import MaterialImg from './MaterialImg.vue'
import setQueryParams from '@/utils/setQueryParams.js'
export default {
  components: {
    MaterialVideo,
    MaterialImg
  },
  props: {

  },
  data() {
    return {
      tabs: ['片头', '片尾', '角标', '模板'],
      activeName: '片头',
      media_type: 'video',
      materialData: [],
      componentName: 'MaterialVideo',
      page: 1,
      limit: 20,
      count: 0
    }
  },
  computed: {
  },
  watch: {
    activeName(val) {
      this.componentName = (val === '片头' || val === '片尾') ? 'MaterialVideo' : 'MaterialImg'
      this.media_type = (val === '片头' || val === '片尾') ? 'video' : 'pic'
      // this.$store.commit('dialog/SET_DIALOG_TITLE', this.tabs[val])
      this.page = 1
      this.materialData = []
      this.getInitData()
    }
  },
  created() {
    this.getInitData()
    // this.$store.commit('dialog/SET_DIALOG_TITLE', this.tabs[0])
  },
  mounted() {

  },
  methods: {
    load() {
      if (this.materialData.length < this.count) {
        this.page++
        this.getInitData()
      }
    },
    async getInitData() {
      const query = {
        source: '附加素材',
        sub_type__in: this.activeName === '模板' ? '视频背景|字幕背景' : this.activeName,
        media_type: this.media_type
      }
      const params = {
        page: this.page,
        limit: this.limit,
        query: setQueryParams(query)
      }
      const { err, res } = await this.$get('/materials', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.count = res.count
      this.materialData = this.materialData.concat(res.data)
    },
    async deleteMaterial(id) {
      const { err, data } = await this.$deleteRun(`/materials/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.page = 1
        this.materialData = []
        this.getInitData()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.additional-material {

  .tabs-wrap {
    display: flex;
    margin-bottom: 20px;

    .tabs-item {
      padding: 9px 10px;
      background: rgba(239,239,239,.8);
      border-radius: 4px;
      color: #404040;
      margin-right: 10px;
      font-size: 12px;
      line-height: 12px;
      cursor: pointer;
      transition: all .3s cubic-bezier(.645,.045,.355,1);

      &.active {
        background: #5675e8;
        color: #fff;
      }
    }
  }

  .transition-wrap {
    display: grid;
    grid-template-columns: repeat(4, 25%);  //同上
    grid-row-gap: 30px;
    margin: 0 -12px;
    max-height: calc(100vh - 200px);
    overflow-y: auto;
  }
}
</style>
