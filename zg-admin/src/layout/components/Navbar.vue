<template>
  <div class="navbar">
    <!-- <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" /> -->
    <p class="navbar-title">中古后台操作系统</p>
    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <!-- <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar"> -->
          <p>{{ userInfo.nickname }}</p>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <!-- <router-link to="/">
            <el-dropdown-item>
              Home
            </el-dropdown-item>
          </router-link>
          <a target="_blank" href="https://github.com/PanJiaChen/vue-admin-template/">
            <el-dropdown-item>Github</el-dropdown-item>
          </a>
          <a target="_blank" href="https://panjiachen.github.io/vue-element-admin-site/#/">
            <el-dropdown-item>Docs</el-dropdown-item>
          </a> -->
          <el-dropdown-item @click.native="logout">
            <span style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
// import Hamburger from '@/components/Hamburger'

export default {
  components: {
    Breadcrumb
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'userInfo'
    ])
  },
  created() {

  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login`)
      // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height : 70px;
  z-index : 1;
  overflow : hidden;
  position : relative;
  background : #FFFFFF;
  box-shadow : 0px 4px 8px 0px rgba(64, 64, 64, .1);
  // box-shadow : 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height : 66px;
    height : 100%;
    float : left;
    cursor : pointer;
    transition : background .3s;
    -webkit-tap-highlight-color : transparent;

    &:hover {
      background : rgba(0, 0, 0, .025);
    }
  }
  .navbar-title {
    line-height : 70px;
    float : left;
    padding-left : 200px;
  }
  .breadcrumb-container {
    float : left;
  }

  .right-menu {
    float : right;
    height : 100%;
    line-height : 70px;

    &:focus {
      outline : none;
    }

    .right-menu-item {
      display : inline-block;
      padding : 0 8px;
      height : 100%;
      font-size : 18px;
      color : #5A5E66;
      vertical-align : text-bottom;

      &.hover-effect {
        cursor : pointer;
        transition : background .3s;

        &:hover {
          background : rgba(0, 0, 0, .025);
        }
      }
    }

    .avatar-container {
      margin-right : 30px;

      .avatar-wrapper {
        position : relative;

        .user-avatar {
          cursor : pointer;
          width : 40px;
          height : 40px;
          border-radius : 10px;
        }

        .el-icon-caret-bottom {
          cursor : pointer;
          position : absolute;
          right : -20px;
          top : 29px;
          font-size : 12px;
        }
      }
    }
  }
}

</style>
