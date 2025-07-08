<template>
  <div class="navbar">
    <img src="@/assets/images/tvm.png" alt="" class="logo">
    <ul class="nav-wrap">
      <li v-for="item in permission_routes" :key="item.path">
        <div v-if="!item.hidden" class="nav-item">
          <router-link v-for="route in item.children" :key="route.path" :to="route.path" active-class="active-nav">{{ route.meta.title }}</router-link>
        </div>
      </li>
    </ul>
    <!-- <p class="title">{{ settings.loginTitle }} / </p> -->
    <!-- <Breadcrumb /> -->
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
          <!-- <el-dropdown-item @click.native="resetDialog">
            <span style="display:block;">修改密码</span>
          </el-dropdown-item> -->

        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- <ResetPassword :show="showDialog" @close="close" /> -->
  </div>
</template>

<script>
// import Breadcrumb from '@/components/Breadcrumb'
import { mapGetters } from 'vuex'
export default {
  components: {
    // Breadcrumb
  },
  data() {
    return {
      showDialog: false
    }
  },
  computed: {
    settings() {
      return this.$store.state.settings
    },
    ...mapGetters([
      'sidebar',
      'userInfo',
      'permission_routes'
    ])
  },
  created() {
    // if (this.userInfo.phone === 'ghydownload' && this.$route.name === 'homePage') {
    //   this.$router.push({
    //     path: '/down-Page'
    //   })
    // }
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
  padding-left: 30px;
  .logo{
    display: inline-block;
    width: 110px;
    margin-right: 27px;
  }
  .title{
    line-height: 50px;
    display: inline-block;
    vertical-align: top;
    font-size: 14px;
  }
  .nav-wrap{
    display: flex;
    li{
      list-style:none;
      font-size: 14px;
      .active-nav{
        color: #5675e8;
        font-weight: 600;
        margin-right: 20px;
      }
    }
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
