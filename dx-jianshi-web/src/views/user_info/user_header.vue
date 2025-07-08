<!--
 * @Author: zzx
 * @Date: 2020-07-22 17:57:30
 * @LastEditTime: 2021-07-27 19:57:09
 * @FilePath: /weijian_web/src/views/user_info/user_header.vue
-->
<template>
  <div class="user-header">
    <div class="content">
      <div>
        <div class="avatar-wrap">
          <div class="avatar-img">
            <img :src="user_detail.avatar_url" alt="" @error="imgError()" v-if="user_detail && user_detail.avatar_url != null" />
            <img src="@/assets/images/user_info/touxiang.png" v-else />
          </div>
          <div class="user-des" v-if="user_detail">
            <div class="nickname">
              {{ user_detail.nickname }}

              <div v-if="user_detail.is_vip" class="ml-10px">
                <img src="@/assets/images/user_info/vip_type_1.png" v-if="user_detail.vip_detail.name == '月卡'" width="80" height="24" />
                <img src="@/assets/images/user_info/vip_type_2.png" v-if="user_detail.vip_detail.name == '年卡'" width="80" height="24" />
              </div>
            </div>

            <div class="vip-time" v-if="user_detail.is_vip">
              <span>到期时间：{{ user_detail.vip_detail.vip_expired_at.slice(0, 10) }}</span>
              <a class="renew-btn"></a>
              <router-link to="/vip_page" class="goto-vip">立即续费</router-link>
            </div>

            <p class="mt-20px fc-999 fz-12px mb-40px" v-else>您还未购买VIP</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {}
  },
  computed: {
    user_detail() {
      return this.$store.state.user_details
    }
  },
  watch: {},
  methods: {
    imgError() {
      let img = event.srcElement
      let img_error_url = require('@/assets/images/user_info/touxiang.png')
      img.src = img_error_url
      img.onerror = null // 防止闪图
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss"></style>
