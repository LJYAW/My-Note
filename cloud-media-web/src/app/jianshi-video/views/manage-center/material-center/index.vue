<!--
 * @Author: your name
 * @Date: 2021-08-30 14:27:02
 * @LastEditTime: 2021-09-17 11:48:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/materail-center/index.vue
-->
<template>
  <div class="material-center">
    <!-- 素材中心 -->
    <div class="material-center-top">
      <el-tabs v-model="activeName" class="material-tab" @tab-click="handleClick">
        <el-tab-pane
          v-for="item in tabs"
          :key="item.key"
          :label="item.name"
          :name="item.key"
        />
      </el-tabs>
      <!-- <div v-if="activeName!='NetworkMaterial'">
        <base-btn size="mini" @click.native="upload">上传素材</base-btn>
      </div> -->
    </div>
    <component :is="activeName" ref="material" />
    <!-- <base-dialog :show.sync="dialogVisible" width="588px" :title="`上传${dialogTitle}`">
      <UploadMaterial @upload="getUploadFile" />
    </base-dialog> -->
  </div>
</template>

<script>
import PersonalMaterial from './material-tab/PersonalMaterial/index.vue'
import BgMusic from './material-tab/BgMusic'
import AdditionalMaterial from './material-tab/AdditionalMaterial'
import UploadMaterial from './model/UploadMaterial.vue'
import dialogVuex from './store/index'
import { mapState } from 'vuex'
export default {
  components: {
    PersonalMaterial,
    BgMusic,
    AdditionalMaterial,
    UploadMaterial
  },
  props: {

  },
  data() {
    return {
      activeName: 'PersonalMaterial',
      tabs: [{
        key: 'PersonalMaterial',
        name: '个人素材'
      }, {
        key: 'BgMusic',
        name: '背景音乐'
      }, {
        key: 'AdditionalMaterial',
        name: '附加素材'
      }],
      dialogVisible: false
    }
  },
  computed: {
    ...mapState('dialog', ['dialogTitle'])
  },
  watch: {

  },
  beforeCreate() {
    this.$store.registerModule('dialog', dialogVuex)
  },
  created() {
    this.$store.commit('dialog/SET_DIALOG_TITLE', this.tabs[0].name)
  },
  destroyed() {
    // resetState
    this.$store.commit('dialog/resetState')
    this.$store.unregisterModule('dialog')
  },
  mounted() {

  },
  methods: {
    upload() {
      this.dialogVisible = true
    },
    handleClick(tab, event) {
      this.$store.commit('dialog/SET_DIALOG_TITLE', tab.label)
    },
    getUploadFile(url) {
      console.log(url)
      this.dialogVisible = false
    }
  }
}
</script>

<style scoped lang="scss">
.material-center {
  flex: 1;
  display: flex;
  flex-direction: column;

  .material-center-top {
    display: flex;
    justify-content: space-between;

    ::v-deep .material-tab {

      .el-tabs__header {
        margin-bottom: 20px;
      }

      .el-tabs__nav-wrap::after {
        height: 0;
      }

      .el-tabs__item {
        font-size: 18px;
        font-weight: 400;
        color: #404040;
        opacity: .4;
        line-height: 18px;
        height: auto;
        padding: 0 10px 12px 10px;

        &:nth-child(2) {
          padding-left: 0;
        }

        &.is-active {
          opacity: 1;
        }
      }
    }
  }

  ::v-deep .el-dialog__body {
    padding-top: 15px;
  }
}
</style>
