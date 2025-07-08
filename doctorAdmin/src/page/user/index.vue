<!--
 * @Author: your name
 * @Date: 2020-12-07 15:17:54
 * @LastEditTime: 2020-12-22 12:05:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /medical_cms_web/src/page/user/index.vue
-->
<template>
  <div class="user_wrap">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="企业用户" name="enterprise">
        <enterprise />
      </el-tab-pane>
      <el-tab-pane label="个人用户" name="persopn">
        <persopn />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import enterprise from './user_tab/enterprise'
import persopn from './user_tab/personal'
export default {
  name: '',
  data() {
    return {
      activeName: ''
    }
  },
  components: {
    enterprise,
    persopn
  },
  computed: {},
  watch: {},
  methods: {
    handleClick() {},
    getTab() {
      if (this.$route.query.activeName) {
        this.activeName = this.$route.query.activeName
      } else {
        this.activeName = 'enterprise'
      }
    }
  },
  created() {},
  mounted() {
    this.getTab()
  }
}
</script>

<style lang="scss">
.user_wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background: #ffffff;
  padding: 15px;
  box-sizing: border-box;
  overflow: auto;
  .el-tabs__nav-wrap::after {
    background-color: transparent;
  }
  .paginationBox {
    text-align: right;
    margin-top: 20px;
  }
  .searchBox {
    position: relative;
    display: flex;
    /deep/ .el-input {
      height: 36px;
    }
    .status {
      width: 100% !important;
    }
    .user_count {
      justify-content: flex-end;
      font-size: 14px;
      height: 100%;
      line-height: 36px;
    }
    .search_img {
      width: 56px;
      height: 36px;
      border-radius: 3px;
      background-color: rgba(43, 121, 223, 100);
      text-align: center;
      line-height: 36px;
      img {
        width: 24px;
        height: 24px;
      }
    }
  }
  .tableBox {
    margin-top: 30px;
  }
}
</style>
