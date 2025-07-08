<!--
 * @Author: zzx
 * @Date: 2020-10-27 14:48:28
 * @LastEditTime: 2021-09-16 12:06:42
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/search_header.vue
-->
<template>
  <div class="search-header">
    <div class="search-input">
      <el-input
        v-model="search_key"
        @keyup.enter.native="searchKeyWord"
      >
        <i slot="suffix" class="el-input__icon el-icon-search" @click="searchKeyWord" />
      </el-input>
    </div>

    <div v-if="keyword.length > 0" class="keyword-wrap">
      <span style="min-width: 80px">推荐关键字：</span>
      <a
        v-for="(key,j) in keyword"
        :key="j"
        class="keyword-item"
        @click="keyWordClick(key.item)"
      >{{ key.item }}</a>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  components: {},
  props: {
    keywords: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
      search_key: ''
    }
  },
  computed: {
    ...mapState('jianshi', ['keyword', 'searchKey'])
  },
  watch: {},
  created() {
    this.search_key = this.searchKey
  },
  mounted() {},
  methods: {
    // 关键字搜索
    keyWordClick(text) {
      this.search_key = text
      this.searchKeyWord()
    },
    // 输入关键字搜索
    async searchKeyWord() {
      if (!this.search_key) {
        this.$message({
          type: 'warning',
          message: '请输入关键词'
        })
        return
      }
      // this.$emit('setComponentName', 'AiVideoList')
      this.$store.commit('jianshi/SET_SEARCH_KEY', this.search_key)
    }
  }
}
</script>

<style scoped lang="scss">
.search-header {
  position: absolute;
  right: 0;
  top: 0;

  .search-input {

    .el-input {
      width: 365px;
      height: 30px;
    }

    ::v-deep .el-input__inner {
      border-radius: 3px 0 0 3px;
      height: 30px;
    }

    .el-input__icon {
      line-height: 30px;
    }

    .el-button {
      background: #c51a1a;
      border-radius: 0 4px 4px 0;
    }
  }

  .keyword-wrap {
    font-size: 12px;
    margin-top: 10px;

    .keyword-item {
      margin-right: 10px;

      &:hover {
        color: #007aff;
      }
    }
  }
}
</style>
