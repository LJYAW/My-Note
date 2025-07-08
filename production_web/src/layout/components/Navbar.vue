<template>
  <div class="navbar">
    <hamburger class="hamburger-container" @toggleClick="toggleSideBar" />
    <p class="title">{{ settings.loginTitle }} /</p>
    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" />
    <div class="right-menu">
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="">
          <span class="nickname">{{ userInfo.nickname }}</span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="logout">
            <span style="display:block;">退出登录</span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="resetDialog">
            <span style="display:block;">修改密码</span>
          </el-dropdown-item>

        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <ResetPassword :show="showDialog" @close="close" @submit="logout" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Hamburger from '@/components/Hamburger'
import Breadcrumb from '@/components/Breadcrumb'
import ResetPassword from './ResetPassword'
export default {
  components: {
    Hamburger,
    Breadcrumb,
    ResetPassword
  },
  data() {
    return {
      showDialog: false
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'userInfo'
    ]),
    settings() {
      return this.$store.state.settings
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    resetDialog() {
      this.showDialog = true
    },
    close() {
      this.showDialog = false
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  align-items: center;
  margin-bottom: 20px;

  .hamburger-container {
    line-height: 50px;
    height: 100%;
    cursor: pointer;
    display: inline-block;
    vertical-align: top;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }
  .title{
    line-height: 50px;
    display: inline-block;
    vertical-align: top;
    font-size: 16px;
  }

  .right-menu {
    height: 100%;
    line-height: 50px;
    margin-right: 20px;
    display: flex;
    margin-left: auto;
    .nickname {
      font-size: 16px;
      margin-right: 10px;
    }
    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      .avatar-wrapper {
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          font-size: 12px;
        }
      }
    }
  }
}
</style>
