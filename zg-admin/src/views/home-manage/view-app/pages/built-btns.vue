<!--
 * @Author: your name
 * @Date: 2021-10-20 18:02:59
 * @LastEditTime: 2021-10-26 14:35:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/view-app-edit/components/built-button.vue
-->
<template>
  <div class="built-button-wrap">
    <div class="card">
      <div class="btn">
        <div class="btn-icon-1 icon">
          <img src="@/assets/edit-app-imgs/hammer.png">
        </div>

        <div class="btn-des">
          <span>限量竞拍</span>
          <span>每日上新</span>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="btn">
        <div class="btn-icon-2 icon">
          <img src="@/assets/edit-app-imgs/hammer.png">
        </div>

        <div class="btn-des">
          <span>抽盲盒</span>
          <span>无限送好礼</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BuiltBtns',
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

  }
}
</script>

<style scoped lang="scss">
.built-button-wrap {
  margin-top: 24px;
  display: flex;
  margin: 0 -5px;
  margin-top: 33px;

  .card {
     width: 50%;
     margin: 0 5px;
     border-radius: 14px;
  }
  .btn {
    background-color: #fff;
    width: 100%;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 8px 11px;

    .btn-icon-1 {
      width: 40px;
      height: 40px;
      background: linear-gradient(135deg, #FFD298 0%, #FFA430 100%);
      box-shadow: 0px 8px 11px -4px rgba(255,180,85,1);
      border-radius: 5px;
    }

    .btn-icon-2 {
      width: 40px;
      height: 40px;
      background: linear-gradient(135deg, #FFBBFF 0%, #FF4BFF 100%);
      box-shadow: 0px 8px 11px -4px rgba(255,117,255,1);
      border-radius: 5px;
    }

    .icon {
      display: flex;
      align-items: center;
      justify-content: center;
      img {
        width: 18px;
        height: 18px;
      }
    }

    .btn-des {
      display: flex;
      flex-direction: column;
      margin-left: 8px;

      :nth-child(1) {
        font-weight: 800;
        font-size: 12px;
      }
      :nth-child(2) {
        font-weight: 400;
        font-size: 12px;
        color: #ABBDD2;
      }
    }
  }
}
</style>
