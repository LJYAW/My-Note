<!--
 * @Author: your name
 * @Date: 2021-08-23 11:04:23
 * @LastEditTime: 2021-10-13 15:12:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/account-info/index.vue
-->
<template>
  <div class="account-info">
    <!-- 企业信息 -->
    <div class="company-info-wrap">
      <div class="company-name">
        <p class="msg">{{ mobile }}
          <span
            v-if="companyInfo.classes===2&&hasVideoLibrary&&companyInfo.apply_status==0"
            class="upgrade"
            @click="upgrade"
          >升级为企业用户</span>

          <span
            v-if="companyInfo.apply_status==1"
            class="upgrade"
          >
            您的企业账号开通信息已提交，请等待审核
          </span>
        </p>
        <p class="name">{{ companyInfo.names }}</p>
      </div>
      <div class="company-msg">
        <!-- <div class="msg-wrap">
          <p><span class="msg">企业账号管理员</span>{{ companyInfo.admin_name||'无' }}</p>
          <p><span class="msg label">管理员邮箱</span>{{ companyInfo.admin_email }}</p>
        </div> -->
        <p><span class="msg">剩余能量点</span>{{ companyInfo.energy }}<router-link to="/manage-center/pay" class="cz">立即充值</router-link></p>
        <p v-if="hasVideoLibrary"><span v-if="hasVideoLibrary" class="msg">账号到期时间</span>{{ companyInfo.expire_time*1000|timesToYMD }}</p>
      </div>
    </div>
    <!-- 账户信息 -->
    <div v-if="hasVideoLibrary" class="account-info-wrap">
      <p class="sub-title">账户信息</p>
      <div class="info-wrap">
        <div class="info-item">
          <p class="info-title">已使用存储空间</p>
          <div class="progress">
            <span class="num">{{ bytesToSize(companyInfo.store_size).split(' ')[0] }}</span>{{ bytesToSize(companyInfo.store_size).split(' ')[1] }} <span class="space"> <router-link to="/manage-center/pay?id=2">空间购买</router-link> </span>
            <base-progress :percentage="getPercentage(companyInfo.store_size, companyInfo.t_store_size)" class="num-progress" />
          </div>
          <p class="msg info-msg">总计可使用空间：{{ bytesToSize(companyInfo.t_store_size) }}</p>
        </div>
        <!-- <div class="info-item">
          <p class="info-title">已使用视频创作时长</p>
          <div class="progress">
            <span class="num">0</span>小时
            <base-progress :percentage="50" class="num-progress" />
          </div>
          <p class="msg info-msg">总计可使用创作时长：小时</p>
        </div> -->
        <!-- <div class="info-item">
          <p class="info-title">已创建账号数</p>
          <div class="progress">
            <span class="num">{{ companyInfo.subuser_count }}</span>个
            <base-progress :percentage="getPercentage(companyInfo.subuser_count,companyInfo.t_subuser_count)" class="num-progress" />
          </div>
          <p class="msg info-msg">总计可使用账户数：{{ companyInfo.t_subuser_count }}个</p>
        </div> -->
        <!-- <div class="info-item">
          <p class="info-title">已分析视频时长</p>
          <div class="progress">
            <span class="num">{{ timeToHour(companyInfo.analyzed_duration) }}</span>小时
            <base-progress
              :percentage="getPercentage(companyInfo.analyzed_duration,companyInfo.time_size+companyInfo.analyzed_duration)"
              class="num-progress"
            />
          </div>
          <p class="msg info-msg">剩余可使用解析时长：{{ timeToHour(companyInfo.time_size) }}小时</p>
        </div> -->
      </div>
    </div>
    <!-- 账户统计 -->
    <div v-if="hasVideoLibrary" class="account-statistics-wrap">
      <p class="sub-title">账户统计</p>
      <div class="info-wrap">
        <div class="info-item">
          <p class="info-title">视频上传量</p>
          <div class="progress">
            <span class="num">{{ companyInfo.upload_video_count }}</span>条
          </div>
        </div>
        <div class="info-item">
          <p class="info-title">视频下载量</p>
          <div class="progress">
            <span class="num">{{ companyInfo.download_video_count }}</span>次
          </div>
        </div>
        <div class="info-item">
          <p class="info-title">视频分析量</p>
          <div class="progress">
            <span class="num">{{ companyInfo.analyses_video_count }}</span>次
          </div>
        </div>
        <div class="info-item">
          <p class="info-title">视频剪辑量</p>
          <div class="progress">
            <span class="num">{{ companyInfo.clips_video_count }}</span>次
          </div>
        </div>
        <div class="info-item">
          <p class="info-title">视频分享量</p>
          <div class="progress">
            <span class="num">{{ companyInfo.share_video_count }}</span>次
          </div>
        </div>
        <!-- <div class="info-item">
          <p class="info-title">视频删除量</p>
          <div class="progress">
            <span class="num">{{ companyInfo.share_video_count }}</span>次
          </div>
        </div> -->
      </div>
    </div>

    <base-dialog
      :show.sync="dialogVisible"
      width="588px"
      title="企业用户认证"
    >
      <div class="company">
        <svg-icon icon-class="企业" />
      </div>

      <div class="input-wrap">
        <div ref="upload-input1" class="upload-input">
          <div class="input-tit">企业名称</div>
          <input
            v-model="companyData.name"
            type="text"
            class="input"
            placeholder="请输入企业名称"
            @focus="inputFocus('upload-input1')"
            @blur="inputBlur('upload-input1')"
          >
        </div>
        <div ref="upload-input2" class="upload-input">
          <div class="input-tit">机构代码</div>
          <input
            v-model="companyData.code"
            type="text"
            class="input"
            placeholder="请输入组织结构代码"
            @focus="inputFocus('upload-input2')"
            @blur="inputBlur('upload-input2')"
          >
        </div>
        <div ref="upload-input3" class="upload-input">
          <div class="input-tit">联系人</div>
          <input
            v-model="companyData.user"
            type="text"
            class="input"
            placeholder="请输入联系人姓名"
            @focus="inputFocus('upload-input3')"
            @blur="inputBlur('upload-input3')"
          >
        </div>
        <div ref="upload-input4" class="upload-input">
          <div class="input-tit">联系电话</div>
          <input
            v-model="companyData.mobile"
            type="text"
            class="input"
            maxlength="11"
            placeholder="请输入联系人电话"
            @focus="inputFocus('upload-input4')"
            @blur="inputBlur('upload-input4')"
          >
        </div>
      </div>
      <div class="sub-wrap">
        <el-button class="sub-btn" type="primary" size="small" @click="sub">提交申请</el-button>
      </div>
    </base-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import setMobile from '@/utils/mobile.js'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      dialogVisible: false,
      companyData: {
        name: '',
        code: '',
        user: '',
        mobile: ''
      }
    }
  },
  computed: {
    ...mapGetters(['companyInfo', 'userInfo', 'roles']),
    mobile() {
      return setMobile(this.userInfo.mobile)
    },
    hasVideoLibrary() {
      return this.roles.some(item => item === '视频管家')
    }
  },
  watch: {

  },
  created() {
    this.$store.dispatch('company/getCompanyInfo')
  },
  mounted() {

  },
  methods: {
    async sub() {
      console.log(this.companyData)
      const { name, code, user, mobile } = this.companyData
      if (!name || !code || !user || !mobile) {
        this.$message.warning('请填写完整企业信息')
        return
      }
      const params = {
        names: name,
        admin_name: user,
        admin_phone: mobile,
        yyzz: code,
        apply_status: 1
      }
      const { err, res } = await this.$put(`/companies?${this.companyInfo.id}`, params)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '申请成功，请等待审核'
        })
        this.$store.dispatch('company/getCompanyInfo')
        this.dialogVisible = false
      }
    },
    upgrade() {
      this.dialogVisible = true
    },
    inputFocus(el) {
      this.$refs[el].style = `border: 1px solid #5675E8`
    },
    inputBlur(el) {
      this.$refs[el].style = `border: 0px solid #5675E8`
    },

    bytesToSize(size) {
      const bytes = Math.abs(size)
      if (bytes === 0) return '0 B'

      var k = 1024

      var sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']

      var i = Math.floor(Math.log(bytes) / Math.log(k))

      var num = bytes / Math.pow(k, i)
      return num.toFixed(2) + ' ' + sizes[i]
    },
    timeToHour(ms) {
      const hour = ms / (60 * 60 * 1000)
      return Math.floor(hour * 100) / 100
    },
    getPercentage(val1, sum) {
      let num = 0
      sum !== 0 && (num = (Math.abs(val1) / Math.abs(sum)) * 100)
      return Math.min(num, 100)
    }
  }
}
</script>

<style scoped lang="scss">
.account-info {
  color: #404040;

  .msg {
    opacity: .6;
  }

  .sub-title {
    padding-left: 10px;
    border-left: 2px solid #5675e8;
    font-size: 14px;
    line-height: 14px;
    margin-bottom: 10px;
  }

  .info-wrap {
    display: grid;
    grid-template-columns: repeat(4, 25%);  //同上
    margin: 0 -12px;

    .info-item {
      margin-right: 24px;
      background: #f7f8f9;
      border-radius: 4px;
      padding: 20px;
      margin: 0 12px;
      margin-bottom: 20px;

      .info-title {
        margin-bottom: 10px;
        font-size: 14px;
        line-height: 14px;
      }

      .progress {
        font-size: 12px;
        margin-bottom: 10px;

        .num {
          font-size: 28px;
          line-height: 28px;
          color: #5675e8;
          margin-right: 4px;
        }

        .space {
          font-size: 14px;
          line-height: 28px;
          color: #5675e8;
          margin-left: 10px;
          cursor: pointer;
        }

        .num-progress {
          margin-top: 10px;

          ::v-deep .el-progress-bar__outer {
            height: 6px!important;
            border-radius: 1px;

            .el-progress-bar__inner {
              border-radius: 1px;
            }
          }
        }
      }

      .info-msg {
        font-size: 10px;
      }
    }
  }

  .company-info-wrap {
    display: flex;
    justify-content: space-between;

    .company-name {
      padding: 23px 30px;
      background: #f7f8f9;
      margin-right: 24px;
      flex: 1;

      p {
        font-size: 12px;
        line-height: 12px;
      }

      .name {
        font-size: 32px;
        line-height: 32px;
        margin-top: 10px;
      }

      .upgrade {
        margin-left: 10px;
        font-size: 12px;
        color: #5675e8;
        cursor: pointer;
      }
    }

    .company-msg {
      padding: 15px 20px;
      background: #f7f8f9;
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      p {
        display: flex;
        align-items: center;
        margin-right: 60px;
      }

      .msg {
        font-size: 14px;
        line-height: 14px;
        display: inline-block;
        width: 100px;
        margin-top: 10px;
        margin-bottom: 10px;
        margin-right: 20px;

        &.label {
          width: auto;
        }
      }

      .cz {
        margin-left: 10px;
        cursor: pointer;
        font-size: 14px;
        color: #5675e8;
      }

      .msg-wrap {
        display: flex;
      }
    }
  }

  .account-info-wrap {
    margin: 30px 0 10px 0;

  }
}

.company {
  font-size: 70px;
  text-align: center;
  margin-bottom: 45px;
}

.input-wrap {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  align-content: center;

  .upload-input {
    width: 40%;
    height: 30px;
    display: flex;
    background: #f2f2f2;
    border-radius: 4px;
    align-items: center;
    margin-bottom: 10px;
    margin-right: 5%;
    box-sizing: border-box;

    &.upload-input-tag {
      width: 66%;
      overflow: hidden;

      .input {
        width: 70px;
      }

      .tip-img {
        width: 10px;
      }
    }

    .input-tit {
      flex-shrink: 0;
      width: 58px;
      font-size: 12px;
      color: #404040;
      margin: 9px;
      padding-right: 9px;
      box-sizing: border-box;
      border-right: 1px solid #d8d8d8;
    }

    .input {
      flex: 1;
      font-size: 12px;
      color: #404040;
    }

  }
}

.sub-wrap {
  margin-top: 20px;
  text-align: center;

  .sub-btn {
    width: 30%;
    margin: 0 auto;
  }
}
</style>
