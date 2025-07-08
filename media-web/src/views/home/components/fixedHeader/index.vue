<!--
 * @Author: your name
 * @Date: 2021-08-20 10:48:56
 * @LastEditTime: 2021-09-27 16:25:02
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/fixedHeader/index.vue
-->
<template>
  <div class="f-header">
    <div
      class="container"
      @keyup.enter="search()"
    >
      <SelectBar @onChange="onChange" @onSearchType="onSearchType" />

      <el-input v-model="searchWord" placeholder="">
        <el-button slot="append" class="button" @click="search()">
          <svg-icon icon-class="放大镜" class="svg" />
        </el-button>
      </el-input>

    </div>

  </div>
</template>

<script>
import SelectBar from '../select'

export default {
  components: {
    SelectBar
  },
  props: {

  },
  data() {
    return {
      searchWord: this.$store.state.videoStatus.searchWord,
      historySearch: []
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getHistory()
  },
  mounted() {

  },
  methods: {
    search() {
      // if (!this.searchWord) return
      this.setHistory()
    },

    setHistory() {
      if (this.searchWord) {
        const index = this.historySearch.findIndex(item => item === this.searchWord)
        index > -1 && this.historySearch.splice(index, 1)
        this.historySearch.unshift(this.searchWord)
        localStorage.setItem('historySearch', JSON.stringify(this.historySearch))
      }

      this.$bus.$emit('srearchOnkeyword', this.searchWord)
    },
    getHistory() {
      this.historySearch = JSON.parse(localStorage.getItem('historySearch')) || []
    },
    deleteHistory() {
      localStorage.removeItem('historySearch')
      this.getHistory()
    },

    onChange(obj) {
      this.$emit('onChange', obj)
    },

    onSearchType(index) {
      this.$emit('onSearchType', (index))
    }
  }
}
</script>

<style scoped lang="scss">
.f-header {
  position: fixed;
  animation: topScroll .5s ease 0s both;
  left: 0;
  width: 100%;
  height: 70px;
  z-index: 99;
  background: #fff;
  box-shadow: 0px 2px 8px 0px rgba(0, 0, 0, .1);

  .container {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  ::v-deep .el-select {
    background: #fff;
    font-weight: 600;

    .el-input__inner {
      background: #fff
    }
  }

  ::v-deep.select-bar {
    font-weight: 600;

    .select-item {

      &:after {
        display: none;
      }
    }

  }

  ::v-deep.tab-bar {
    font-weight: 600;

    &::after {
      content: "";
      position: absolute;
      left: 0;
      top: 8px;
      height: 15px;
      width: 1px;
      background: #d8d8d8;
    }

    .tab-item {
      font-weight: 600;

      background: #fff;

      &:first-child {
        padding-left: 20px;

        &:after {
          display: block;
          left: 0;
        }
      }

      &:after {
        display: none;
      }
    }
  }

  ::v-deep.share {
    font-weight: 600;

    position: relative;
    padding-left: 20px;

    &::after {
      content: "";
      position: absolute;
      left: 0;
      top: 8px;
      height: 15px;
      width: 1px;
      background: #d8d8d8;
    }

    .shareBtn {
      background: #fff;

    }
  }

  .el-input {
    width: 420px;

    ::v-deep.el-input__inner {
      height: 30px;
    }

    ::v-deep.el-input-group__append {
      width: 30px;
      background: #5675e8;
      border: 1px solid #5675e8;
      color: #fff;
      // padding: 0;

      svg {
        font-size: 22px;
      }
    }
  }

  ::v-deep.videoSum {
    display: none;
  }
}

@keyframes topScroll {

  0% {
    top: -70px;
  }

  100% {
    top: 0px;
  }
}
</style>
