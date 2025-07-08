<!--
 * @Author: your name
 * @Date: 2021-09-22 16:03:06
 * @LastEditTime: 2021-09-24 20:12:54
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/ToolsHeader/index.vue
-->
<template>
  <div class="tools-header">
    <div class="hander-content">
      <div class="tools-name">
        <span class="back-icon" @click="back"><i /></span>
        {{ name }}
      </div>
      <slot name="tit" />

      <div class="btn-wrap">
        <slot name="btn" />
      </div>
    </div>

    <HeaderUser />
  </div>
</template>

<script>
import HeaderUser from '@/components/HeaderUser/index'
export default {
  components: {
    HeaderUser
  },
  props: {
    name: {
      type: String,
      default: ''
    }
  },
  data() {
    return {

    }
  },
  computed: {

  },
  watch: {

  },
  destroyed() {
    this.$store.dispatch('settings/changeSetting', { showHeader: true })
  },
  created() {
    this.$store.dispatch('settings/changeSetting', { showHeader: false })
  },
  mounted() {

  },
  methods: {
    back() {
      this.$router.push({
        path: '/',
        query: {}
      })
    }
  }
}
</script>

<style scoped lang="scss">
.tools-header {
  width: 100%;
  height: 70px;
  background: #fff;
  padding: 0 10%;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 999;
  display: flex;
  justify-content: space-between;
  align-items: center;
  // box-shadow: 0px 2px 8px 0px rgb(0 0 0 / 10%);

  .hander-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex: 1;
  }

  .back-icon {
    margin-right: 20px;
    cursor: pointer;
    margin-left: 2px;

    i {
      border: solid #404040;
      opacity: .2;
      border-width: 0 2px 2px 0;
      display: inline-block;
      padding: 5px;
      transform: rotate(135deg);
      transform: rotate(135deg);

    }
  }

  .btn-wrap {

    margin-right: 18px;
  }

  .tools-name {
    font-size: 18px;
    font-weight: 400;
    color: #404040;
  }
}

</style>
