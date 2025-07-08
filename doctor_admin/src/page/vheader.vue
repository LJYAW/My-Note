<!--
 * @Author: your name
 * @Date: 2020-08-28 18:23:33
 * @LastEditTime: 2021-02-09 17:16:40
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/vheader.vue
-->
<template>
  <div class="header-wrap">
    <div>
      <img src="../assets/images/logo.png" alt="">
      <span>闪创后台管理</span>
    </div>
    <div>
      <a @click="signOut()" class="signOut">退出登录</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'vheader',
  data() {
    return {}
  },
  components: {},
  computed: {},
  watch: {},
  methods: {
    signOut() {
      localStorage.removeItem('user_info')
      this.$router.push('/')
    }
  }
}
</script>

<style lang="scss" scoped>
.header-wrap {
  width: 100%;
  height: 60px;
  background: #ffffff;
  padding: 0 30px;
  box-sizing: border-box;
  line-height: 60px;
  box-shadow: 0px 2px 6px 0px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 100);
  z-index: 100;
  display: flex;
  justify-content: space-between;
  .signOut {
    color: #3287e4;
    font-size: 14px;
  }
  img {
    width: 76px;
    height: 39px;
  }
  span {
    color: rgba(16, 16, 16, 100);
    font-size: 14px;
    margin-left: 10px;
  }
}
</style>
