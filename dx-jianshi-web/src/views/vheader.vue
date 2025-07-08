<template>
  <div class="header-wrap" ref="header">
    <div class="header d-flex align-content-center">
      <div class="content container d-flex justify-content-between flex-1">
        <div class="left d-flex">
          <router-link to="/" class="mt-15px">
            <img src="../assets/images/dx_logo.jpg" />
          </router-link>
          <!-- <p class="ml-40px">短视频智能创作系统</p> -->
          <ul class="mini-nav mt-10px ml-40px">
            <li v-for="(nav, index) in nav_list" :key="index">
              <router-link :to="nav.path">{{ nav.name }}</router-link>
            </li>
          </ul>
        </div>

        <!-- 未登录 -->
        <div v-if="logged_in" class="d-flex">
          <div class="right d-flex align-items-center">
            <el-button id="loginBtn" type="text" round size="mini" @click="login" style="width: 80px">登录</el-button>
          </div>
        </div>
        <!-- 已登录 -->
        <div v-else class="d-flex">
          <div class="d-flex ml-auto" v-if="0">
            <router-link to="" class="qiandao vip-btn d-flex align-items-center ml-auto mr-23px align-self-center cp" @click.native="signIn">
              <vsvg icon="iconqiandao" />
              <span class="fz-12px ml-4px mt-2px">签到</span>
            </router-link>

            <router-link to="/vip_page" class="vip-btn d-flex align-items-center mr-20px align-self-center cp">
              <vsvg icon="iconhuiyuanlogo" />
              <span class="fz-12px ml-4px mt-2px">会员</span>
            </router-link>
          </div>

          <div class="user-info d-flex align-items-center">
            <div class="my-info">
              <router-link to="/user-info/ai-created" class="hove-c fz-16px h-100 d-flex align-items-center touxiang">
                <img v-if="user_details && user_details.avatar_url" :src="user_details.avatar_url" alt="" @error="imgError()" />
                <img v-else src="../assets/images/user_info/touxiang.png" alt="" />
              </router-link>

              <div class="drap-down" id="drap-down">
                <div class="p-20px d-flex flex-column">
                  <div class="d-flex user-avatar">
                    <a class="hove-c fz-16px h-100 d-flex align-items-center touxiang">
                      <img v-if="user_details && user_details.avatar_url" :src="user_details.avatar_url" alt="" @error="imgError()" />
                      <img v-else src="../assets/images/user_info/touxiang.png" alt="" />
                    </a>

                    <div class="ml-16px pt-5px">
                      <div class="d-flex">
                        <p class="fz-18px fc-333 mb-5px">{{ user_details.nickname }}</p>
                        <div v-if="user_details.is_vip" class="ml-5px is_vip">
                          <img src="@/assets/images/user_info/vip_type_1.png" v-if="user_details.vip_detail.name == '月卡'" width="80" height="24" />
                          <img src="@/assets/images/user_info/vip_type_2.png" v-if="user_details.vip_detail.name == '年卡'" width="80" height="24" />
                        </div>
                      </div>

                      <p class="fz-12px fc-999" v-if="user_details.is_vip">{{ user_details.vip_detail.name }}到期时间：{{ user_details.vip_detail.vip_expired_at.slice(0, 10) }}</p>
                      <p class="fz-12px fc-999" v-else>您还未购买VIP</p>
                    </div>
                  </div>
                  <div class="user-detail d-flex align-items-center justify-content-between">
                    <p>
                      账户能量点：
                      <span>{{ user_details.energy + user_details.temp_energy }}</span>
                    </p>
                    <!-- <el-button round size="mini" @click.native="showPayModal">立即充值</el-button> -->
                  </div>
                  <div class="weijian-vip d-flex" v-if="!user_details.is_vip">
                    <router-link to="/vip_page" class="fz-14px">
                      <span class="fz-16px">
                        简视
                        <i class="ml-6px">VIP</i>
                        <em class="ml-12px mr-8px">|</em>
                      </span>
                      <span>更多功能与优惠，开通VIP ></span>
                    </router-link>
                  </div>

                  <div class="d-flex link-wrap justify-content-between fz-14px">
                    <router-link to="/user-info/ai-created" class="box d-flex flex-column text-center">
                      <img src="../assets/images/user_info/vip/my_videos.svg" width="30" height="30" />
                      <span>我的作品</span>
                    </router-link>
                    <router-link to="/user-info/my-library" class="box d-flex flex-column text-center">
                      <img src="../assets/images/user_info/vip/my_sucai.svg" width="30" height="30" />
                      <span>我的素材库</span>
                    </router-link>
                    <router-link to="/user-info/purchase-records" class="box d-flex flex-column text-center">
                      <img src="../assets/images/user_info/vip/goumai.svg" width="30" height="30" />
                      <span>购买记录</span>
                    </router-link>
                    <!-- <router-link to="/user-info/release-record"
                    class="box d-flex flex-column text-center">
                    <img src="../assets/images/user_info/vip/fabu.svg" alt="">
                    发布记录</router-link> -->
                  </div>

                  <div class="d-flex mt-20px fz-16px log-out justify-content-center">
                    <!-- <router-link to="/user-info/account-info">账号信息</router-link> -->
                    <a @click="logout()">退出登录</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- </div> -->
        <!-- <div v-if="!user_info && !user_details"
          class="right d-flex align-items-center">
          <div>
            <a class="hove-c"
              @click="login">登录</a>
          </div>
        </div> -->
      </div>
    </div>

    <loginM @loginIsDone="loginIsDone" />
    <signM />
    <changePassword />
    <shortMesage @loginIsDone="loginIsDone" />
    <!-- <payModel /> -->
    <signIn v-if="modal_show_name == 'signIn'" @Modalclose="Modalclose" />
  </div>
</template>

<script>
import loginM from './login_modal/login'
import signM from './login_modal/sign'
import changePassword from './login_modal/change_password'
import shortMesage from './login_modal/short_message'

import signIn from '@/components/sign_in.vue'
// import paySuccess from '@/components/pay/pay_success.vue'
export default {
  data() {
    return {
      adminAccont: ['13910110685', '18500311866', '18500311866', '18813002365'],
      modal_show_name: '',
      logged_in: true,
      nav_list: [
        // { name: '首页', path: '/' },
        { name: '智能创作', path: '/intellect-create' },
        { name: '素材库', path: '/material-library' }
      ]
    }
  },
  components: { loginM, signM, changePassword, shortMesage, signIn },
  computed: {
    user_details() {
      return this.$store.state.user_details
    },
    user_info() {
      return this.$store.state.user_info
    }
  },
  watch: {
    user_info(val) {
      this.checkout()
    }
  },
  methods: {
    loginIsDone() {
      this.logged_in = false
    },
    goToGuangDian() {
      window.open('https://cloud.weijian.video')
    },
    login() {
      this.$store.commit('modalShow', 'loginM')
    },
    logout() {
      this.logged_in = true
      this.$store.commit('LOGUOT')
      this.$router.push({
        path: '/'
      })
    },
    checkout() {
      if (!this.user_details) {
        // this.login()
      }
    },
    imgError() {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/user_info/touxiang.png')
      img.src = img_error_url
      img.onerror = null // 防止闪图
    },
    showPayModal() {
      this.$router.push({
        path: '/vip_page'
      })
    },
    signIn() {
      this.modal_show_name = 'signIn'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'signIn')
      })
    },
    Modalclose() {
      this.modal_show_name = ''
    }
  },
  created() {
    if (this.user_details && this.user_info) {
      this.logged_in = false
    }
  },
  mounted() {
    this.checkout()
    // this.getUserInfo()
  }
}
</script>

<style lang="scss">
.message_wrap {
  padding: 0 !important;
}
.vl-notify-mask {
  z-index: 1001 !important;
}
.vl-notify-alert.vl-notify-iframe {
  z-index: 1002 !important;
}
.header-wrap {
  position: relative;
  z-index: 1000;
  // width: 1000px;
  // min-width: 1032px;
  // width: max-content;

  .header {
    height: 60px;
    box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.1);
    background: #fff;
    .content {
      background: #fff;
      .left {
        img {
          height: 35px;
        }
      }
      .border {
        height: 20px;
        box-sizing: border-box;
        border-left: 1px solid rgba(30, 30, 30, 1);
        margin: 0 10px;
      }
    }
    .vip-btn {
      height: 24px;
      line-height: 24px;
      border: 1px solid rgba(198, 161, 96, 1);
      color: rgba(198, 161, 96, 1);
      border-radius: 20px;
      padding: 0 15px;
    }
    .qiandao {
      background: $c;
      color: #fff;
      border-radius: 19px;
      padding: 0 10px;
    }
  }

  .mini-nav {
    display: flex;
    justify-content: flex-start;
    border: none;
    li {
      padding-top: 15px;
      margin: 0 10px;
      a {
        color: #999;
        padding-bottom: 14px;
        &.action {
          color: white;
        }
        &.router-link-exact-active {
          border-bottom: 3px solid $c;
        }
      }
    }
  }
}
#loginM,
#signWrap,
#messageWrap,
#changePassword {
  .modal-body {
    .el-input__inner {
      padding-left: 60px;
      height: 46px;
    }
  }

  .el-input__prefix {
    width: 45px;
    height: 80%;
    top: 5px;
    border-right: 1px solid #ccc;
    .iconfont {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }
}
.user-info {
  position: relative;
  .my-info {
    height: 100%;
    width: 70px;
    min-height: 60px;
    img {
      height: 42px;
      margin: 0 auto;
      object-fit: contain;
    }
    .touxiang {
      img {
        border-radius: 100%;
      }
    }
    &:hover {
      .drap-down {
        opacity: 1;
        transform: scale(1);
        pointer-events: auto;
      }
    }
    .drap-down {
      padding: 5px 0;
      position: absolute;
      top: 60px;
      right: 0px;
      width: 380px;
      opacity: 0;
      // transform: scale(1);
      transform: scale(0.6);
      pointer-events: none;
      transform-origin: top right;
      background: white;
      box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.1);
      border: 1px solid rgba(255, 255, 255, 0);
      transition: all 0.2s;
      z-index: 1000;
      .is_vip {
        // position: absolute;
        // margin-top: 15px;
        margin-right: 10px;
        img {
          width: auto;
          height: 18px !important;
          object-fit: cover;
          border-radius: 0;
        }
      }
      .user-avatar {
        img {
          height: 50px;
        }
      }
      .user-detail {
        padding: 20px;
        background: #f7f7f7;
        margin-top: 20px;
        div {
          text-align: center;
        }
        .el-button {
          padding: 10px 24px;
          color: #fff;
          background: $c;
          border: none;
          height: 30px;
        }
      }
      li {
        height: 36px;
        line-height: 36px;
        padding-left: 16px;
        transition: all 0.3s;
        .iconfont {
          color: #797979;
          margin-right: 7px;
        }
        &:hover {
          background-color: #f0f0f0;
        }
        &:last-child {
          margin-top: 4px;
          border-top: 1px solid #eee;
        }
      }
      .user-status-crad {
        margin-bottom: 10px;
        margin: 27px -4px 15px -4px;
        .box {
          padding: 18px 19px;
          margin: 0 4px;
          font-size: 12px;
          background: linear-gradient(135deg, #fbf6ee, #f8e8cf);
          border-radius: 6px;
          color: #b3773d;
        }
      }
      .weijian-vip {
        padding: 18px 12px;
        background: linear-gradient(135deg, #fbf6ee, #fbf6ee);
        border-radius: 4px;
        color: #b3773d;
        span:first-child {
          font-weight: 500;
          i {
            font-weight: 600;
          }
          em {
            color: #dec9aa;
          }
        }
      }
      .link-wrap {
        margin-top: 22px;
        border-top: 1px solid rgba(244, 244, 244, 1);
        border-bottom: 1px solid rgba(244, 244, 244, 1);
        padding: 22px 20px;
        color: #b3773d;
        .iconfont {
          color: #8e8e8e;
          font-size: 20px;
          display: inline-block;
        }
      }
      .log-out {
        a:last-child {
          color: $c;
        }
      }
    }
    .drap-down.move-out {
      opacity: 0;
      transform: scale(0.6);
      transition: all 0.2s;
    }
  }
}
</style>
