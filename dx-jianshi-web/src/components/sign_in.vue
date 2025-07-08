<!--
 * @Author: your name
 * @Date: 2020-12-04 17:53:52
 * @LastEditTime: 2020-12-16 18:26:05
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/qiandao.vue
-->
<template>
  <div>
    <model :id="'signIn'" @close="close">
      <div slot="title">
        <div class="d-flex justify-content-between sign-title">
          <div class="d-flex flex-1 pl-80px pt-40px">
            <span class="fz-20px text">我的能量</span>
            <em class="fz-32px ml-16px">{{ userDetail.energy + userDetail.temp_energy }}</em>
            <hr />
          </div>
          <div class="d-flex flex-1 fz-16px pl-80px pt-40px">
            <span class="fz-20px text">我的VIP时长</span>
            <em class="fz-32px ml-16px mr-8px">{{ getVipDays() }}</em>
            <span class="pt-3px">天</span>
          </div>
        </div>
      </div>
      <div slot="body" class="d-flex justify-content-center pb-50px px-60px pt-35px flex-column">
        <div class="receive d-flex w-100 mb-40px">
          <div class="d-flex flex-1 receive-text align-items-center">
            <div class="pl-40px">
              <p class="fz-16px mb-8px">每日签到可领取{{ getTempEnergy() }}点能量</p>
              <p class="fz-14px only-today">签到能量点仅限当日使用</p>
            </div>
          </div>
          <div :class="['d-flex receive-btn align-items-center justify-content-center', userDetail.is_sign_in && 'isSign']" @click="signIn">
            <el-button :disabled="userDetail.is_sign_in" class="fz-24px">{{ userDetail.is_sign_in ? '已签到领取' : '签到领取' }}</el-button>
          </div>
        </div>
        <ul class="d-flex flex-column">
          <li v-for="(item, index) in signList" :key="index" class="d-flex justify-content-between py-20px">
            <div class="d-flex align-items-center">
              <div class="power-number d-flex justify-content-center align-items-center">
                <vsvg icon="iconnengliangicon" class="mr-3px" />
                {{ item.number }}
              </div>
              <div class="d-flex flex-column pl-24px">
                <p class="fz-16px mb-10px">{{ item.title }}</p>
                <p class="fz-12px">
                  <span v-if="index == '1'">{{ shareNumber }}</span>
                  {{ item.text }}
                </p>
              </div>
            </div>
            <div class="d-flex align-items-center">
              <el-button size="small" class="over" v-if="index == '0'">已赠送</el-button>
              <el-button size="small" v-else @click.native="getEmergy" :class="index == 1 && shareNumber >= 5 && 'over'">
                {{ index == 1 && shareNumber >= 5 ? '已完成' : '去完成' }}
              </el-button>
            </div>
          </li>
        </ul>
        <p class="fz-12px mt-20px">PS:每生成一条视频消耗10点能量</p>
      </div>
    </model>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      signList: [
        {
          number: 100,
          title: '新用户赠送100点能量',
          text: '赠送能量可永久使用'
        },
        {
          number: 2,
          title: '分享视频获取2点能量',
          text: '/5'
        },
        {
          number: 10,
          title: '视频播放量每增加1000',
          text: '每个视频单独统计'
        }
      ],
      shareNumber: this.$store.state.user_details.shared_num
    }
  },
  computed: {
    userDetail() {
      return this.$store.state.user_details
    }
  },
  watch: {},
  methods: {
    close() {
      this.$emit('Modalclose')
    },
    getVipDays() {
      if (this.userDetail.vip_detail) {
        var endTime = this.userDetail.vip_detail.vip_expired_at
        var _endTime = Date.parse(endTime)
        var startTime = Date.now() //获取到今天的纪元时间(到1970-1-1 零点的毫秒数)
        var res = _endTime - startTime //毫秒数
        var times = this.mschange(res)
        return times
      }
      return 0
    },
    mschange(num) {
      var num1 = parseInt(num / 1000)
      var day = parseInt(num1 / 60 / 60 / 24) //天
      return day
    },
    signIn() {
      if (this.userDetail.is_sign_in) return

      this.$post('/user/daily-sign-in', {}).then(res => {
        if (res.data.code == '0000') {
          this.$store.dispatch('setUserDetails')
        }
        this.$alertMsg(res.data.msg)
      })
    },
    getEmergy() {
      this.close()
      this.$router.push({
        path: '/user-info/ai-created'
      })
    },
    getTempEnergy() {
      if (!this.userDetail.vip_detail) {
        return 10
      }
      switch (this.userDetail.vip_detail.name) {
        case '年卡':
          return 30
        case '月卡':
          return 20
      }
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
#signIn {
  color: #333;
  /deep/.modal-wrap {
    max-width: 850px;
    .modal-content {
      border-radius: 10px;
      overflow: hidden;
      .modal-header {
        position: relative;
        height: 100px;
        color: #fff;
        & > div {
          width: 100%;
          .sign-title {
            background: url('../assets/images/qiandao_banner.png');
            background-size: 100% 100%;
            background-repeat: no-repeat;
            position: absolute;
            width: 100%;
            left: 0;
            height: 100px;
            top: 0;
            & > div {
              position: relative;
            }
            em {
              margin-top: -7px;
            }
            .text {
              position: relative;
              &::after {
                display: block;
                content: '';
                width: 0;
                height: 0;
                border-right: 7px solid transparent;
                border-left: 7px solid transparent;
                border-bottom: 6px solid #fff;
                position: absolute;
                top: 38px;
                left: 35px;
              }
            }
            hr {
              opacity: 0.17;
              height: 48px;
              position: absolute;
              right: 0;
              top: 26px;
            }
          }
        }
        .iconclose {
          position: absolute;
          color: #fff;
          top: 24px;
          right: 21px;
          &:hover {
            color: #fff !important;
          }
        }
      }
      .modal-body {
        .receive {
          height: 110px;
          border-radius: 16px;
          overflow: hidden;
          .receive-text {
            background-color: #fbece7;
            color: $c;
            .only-today {
              opacity: 0.51;
            }
          }
          .receive-btn {
            width: 180px;
            background: linear-gradient(90deg, #ff7f64 -17%, $c 119%);
            color: #fff;
            border-top-left-radius: 6px;
            position: relative;
            cursor: pointer;
            button {
              background: none;
              color: #fff;
              border: none;
              outline: none;
            }
            &.isSign {
              background: #dfdfdf;
            }
            &::before {
              display: block;
              content: '';
              width: 28px;
              height: 28px;
              border-radius: 100%;
              position: absolute;
              background: #fff;
              left: -14px;
              top: -14px;
            }
            &::after {
              display: block;
              content: '';
              width: 28px;
              height: 28px;
              border-radius: 100%;
              position: absolute;
              background: #fff;
              left: -14px;
              bottom: -14px;
            }
          }
        }
        li {
          border-bottom: 1px solid #dedede;
          color: #333;
          .power-number {
            width: 62px;
            height: 62px;
            background: linear-gradient(90deg, #ff7f64 -17%, $c 119%);
            border-radius: 100%;
            color: #fff;
          }
          button {
            background: $c;
            color: #fff;
          }
          .over {
            background: #999999;
          }
        }
      }
    }
  }
}
</style>
