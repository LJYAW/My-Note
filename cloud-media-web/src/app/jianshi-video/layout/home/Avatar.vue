<!--
 * @Author: your name
 * @Date: 2021-08-18 14:40:15
 * @LastEditTime: 2021-09-28 17:23:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/layout/home/Avatar.vue
-->
<template>
  <div class="avatar-wrap">
    <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
      <div class="user-info">
        <span class="nickname">{{ mobile }}</span>
        <i class="el-icon-caret-bottom" />
      </div>
      <el-dropdown-menu slot="dropdown">
        <router-link to="/jianshi-video/manage-center">
          <el-dropdown-item>进入管理系统</el-dropdown-item>
        </router-link>
        <el-dropdown-item @click.native="logout">
          <span style="display: block;">退出登录</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import vuex, { mapGetters } from 'vuex'
import setMobile from '@/utils/mobile.js'
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
    ...mapGetters(['userInfo']),
    mobile() {
      return setMobile(this.userInfo.mobile)
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  }
}
</script>

<style scoped lang="scss">
.avatar-wrap {
  margin-left: auto;

  .user-info {
    color: #404040;

    .nickname {
      margin-right: 20px;
      font-size: 14px;
    }
  }
}

.el-dropdown-menu {
  width: 128px;
  margin-top: 8px;
  padding: 10px;

  .el-dropdown-menu__item {
    padding: 9px 0 9px 16px;
    font-size: 12px;
    line-height: 12px;
    color: #404040;

    &:hover,
    &:focus {
      background: rgba(214,222,253,.4);
      border-radius: 4px;
      color: #404040;
    }
  }

  ::v-deep .popper__arrow {
    display: none;
  }
}
</style>
