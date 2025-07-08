<!--
 * @Author: your name
 * @Date: 2021-10-20 12:09:32
 * @LastEditTime: 2021-11-05 17:07:37
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/index.vue
-->
<template>
  <div v-loading="loading">
    <div class="btns">
      <el-button :disabled="btnLoading" style="width: 100px" type="primary" size="mini" @click="save">保存</el-button>
    </div>
    <div v-if="!loading" class="home-app-manage-wrap ">

      <ComSelect />

      <ViewApp />

      <AppDetails />
    </div>
  </div>
</template>

<script>
import ComSelect from './com-select/index.vue'
import ViewApp from './view-app/index.vue'
import AppDetails from './app-details/index.vue'
import myvuex from './store/index'
import { mapState } from 'vuex'
import { checkoutAppData } from './base-com-data'

export default {
  name: 'HomeAppManage',
  components: {
    ComSelect,
    ViewApp,
    AppDetails
  },
  props: {

  },
  data() {
    return {
      btnLoading: false,
      loading: true
    }
  },
  computed: {

  },
  watch: {

  },
  async created() {
    this.loading = true
    const data = await this.$store.dispatch('bdapp/GetZhongguAppData')
    this.$store.commit('bdapp/SET_ZHONGGU_APP_DATA_COPY', this.$lodash.cloneDeep(data))
    this.$store.commit('bdapp/SET_ACTIVE_COM', 'HeaderBanner')
    this.loading = false
  },
  beforeCreate() {
    this.$store.registerModule('bdapp', myvuex)
  },
  beforeDestroy() {
    this.$store.unregisterModule('bdapp')
  },
  mounted() {
  },
  methods: {
    async save() {
      const appData = this.$store.state.bdapp.appData
      console.log('🚀 ~ file: index.vue ~ line 73 ~ save ~ appData', appData)

      let errMsg = ''
      for (const key in appData) {
        errMsg = checkoutAppData(appData, key)
        if (errMsg) {
          this.$message({
            type: 'warning',
            message: errMsg
          })
          break
        }
      }
      if (errMsg) {
        return
      }

      this.$confirm('此操作将立即更新线上数据，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        this.btnLoading = true
        const res = await this.$store.dispatch('bdapp/PostZhongguAppData')
        if (res.err) {
          this.$message({
            type: 'warning',
            message: '更新失败!'
          })
        } else {
          this.$message({
            type: 'success',
            message: '更新成功!'
          })
          const data = this.$store.state.bdapp.appData
          this.$store.commit('bdapp/SET_ZHONGGU_APP_DATA_COPY', this.$lodash.cloneDeep(data))
        }
        this.btnLoading = false
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消更新'
        })
      })
    }
  }
}
</script>

<style lang="scss">
@import './css/index.scss';

.home-app-manage-wrap {
  display : flex;
  justify-content : space-around;
  max-height : calc(100vh - 120px);

  .view-app-edit-wrap {
    margin : 0 24px;
  }
  .com-select-wrap {
    min-width : 300px;
  }

  .app-details-wrap {
    flex : 1;
    overflow : hidden;
  }
}

::v-deep .swiper-pagination {
  text-align : left;
  margin-left : 15px;

  .swiper-pagination-bullet-active {
    background : #FFAB40;
  }
}
.btns {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}
</style>
