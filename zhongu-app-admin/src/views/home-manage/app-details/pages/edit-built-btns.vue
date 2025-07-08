<!--
 * @Author: your name
 * @Date: 2021-10-22 17:16:08
 * @LastEditTime: 2021-10-26 14:40:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/app-details/pages/edit-class.vue
-->
<!--
 * @Author: your name
 * @Date: 2021-10-22 14:21:10
 * @LastEditTime: 2021-10-22 16:08:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/app-details/pages/index.vue
-->
<template>
  <div class="header-swiper">
    按钮组
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {

  },
  data() {
    return {

    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    addBannerList() {

    },
    deleteBanner() {

    },
    saveBanner() {

    }
  }
}
</script>

<style scoped lang="scss">
.conteniner {
  padding: 20px;

  .banner-list-wrap {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-gap: 20px 20px;
    margin-top: 30px;

    .content-wrap {
      height: 50px;
      background-color: #F8F8F8;
      border-radius: 4px;
    }

    .sub-title {
      margin-bottom: 20px;
      display: flex;

      .des {
        font-size: 12px;
        margin-left: auto;
        color: #404040;
      }
    }
  }
  .btns {
    display: flex;
    flex-direction: column;
    margin-top: 30px;

    .btn-w-100 {
      width: 100%;
    }

    .e-btn {
      margin-top: 20px;
      margin-left: auto;
    }

    .el-button {
      min-width: 100px;
    }
  }
}

</style>
