<!--
 * @Author: your name
 * @Date: 2021-07-27 14:33:02
 * @LastEditTime: 2021-09-07 17:13:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/seach.vue
-->
<template>
  <div class="search">

    <transition name="fade">
      <div v-if="historyShow" class="wrap" @click.stop="handlerBlur" />
    </transition>

    <div class="search-wrap">

      <div class="search-input">
        <input
          v-model.trim="searchWord"
          type="text"
          placeholder="请输入搜索关键字"
          @focus="handlerFocus()"
          @keyup.enter="search()"
        >
        <svg-icon icon-class="放大镜" class="svg" @click="search()" />
      </div>

      <div v-show="historyShow" class="recommend-search">
        <div v-show="historySearch.length" class="history-word">
          <div class="title">
            <span>搜索历史</span>
            <span class="deleteAll" @click="deleteHistory()">清空历史</span>
          </div>
          <div class="content">
            <span
              v-for="val in historySearch"
              :key="val"
              @click="tagClick({name:val})"
            >{{ val }}</span>
          </div>
        </div>

        <div v-show="pic_tops&&pic_tops.length" class="intelligence-recommend">
          <div class="title">智能推荐</div>
          <div class="content">
            <img
              v-for="i in pic_tops"
              :key="i.name"
              :src="i.pic_url"
              alt=""
              @click="tagClick(i)"
            >
          </div>
        </div>
      </div>
    </div>

    <div class="tag">
      <span
        v-for="i in word_tops"
        :key="i.name"
        @click="tagClick(i)"
      >{{ i.name }}
      </span>
    </div>

  </div>
</template>

<script>

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      historyShow: false,
      historySearch: [],
      searchWord: this.$store.state.videoStatus.searchWord,
      word_tops: [],
      pic_tops: [],
      searchType: 1
    }
  },
  computed: {

  },
  watch: {
    '$store.state.videoStatus.searchWord': {
      deep: true,
      immediate: true,
      handler(val, oldVal) {
        this.searchWord = val || ''
      }
    }
  },
  created() {
    this.getHistory()
    this.getTopSearch()
  },
  mounted() {

  },
  methods: {
    async getTopSearch() {
      const { err, res } = await this.$get('/search/top')
      const { pic_tops, word_tops } = res.data
      this.word_tops = word_tops && word_tops.splice(0, 5)

      this.pic_tops = pic_tops && pic_tops.splice(0, 10)
    },

    handlerFocus() {
      this.historyShow = true
    },
    handlerBlur() {
      this.historyShow = false
    },
    tagClick(i) {
      typeof (i) === 'string' ? this.searchWord = i : this.searchWord = i.name
      switch (i.stype) {
        case 'person':
          this.searchType = 4
          break
        case 'object':
          this.searchType = 3
          break
        default:
          this.searchType = 1
          break
      }
      this.search()
    },
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

      this.historyShow = false
      this.$bus.$emit('srearchOnkeyword', this.searchWord, this.searchType)
    },
    getHistory() {
      this.historySearch = JSON.parse(localStorage.getItem('historySearch')) || []
    },
    deleteHistory() {
      localStorage.removeItem('historySearch')
      this.getHistory()
    }
  }
}
</script>

<style scoped lang="scss">
.search {

  .wrap {
    position: fixed;
    top: 0;
    left: 0;
    background: rgba(0,0,0,.5);
    width: 100vw;
    height: 100vh;
    z-index: 100;
  }

  .search-wrap {
    width: 50%;
    margin: 0 auto;
    z-index: 200;
    border: 3px solid hsla(0,0%,100%,.5);
    background-clip: padding-box;
    border-radius: 4px;
    margin-bottom: 20px;

    .search-input {
      width: 100%;
      height: 54px;
      font-size: 14px;
      font-weight: 400;
      color: #404040;
      text-indent: 18px;
      background-color: #fff;
      padding: 0 20px;
      display: flex;
      justify-content: space-between;
      align-items: center;

      input {
        flex: 1;
        width: 100%;
        height: 100%;
      }

      .svg {
        font-size: 50px;
        cursor: pointer;
      }
    }

    .recommend-search {
      width: 100%;
      //   height: 500px;
      background: #fff;
      box-sizing: border-box;
      padding: 30px;

      .history-word {
        margin-bottom: 30px;
      }

      .title {
        font-size: 16px;
        font-weight: 400;
        color: #404040;
        margin-bottom: 20px;
        display: flex;
        justify-content: space-between;

        .deleteAll {
          font-size: 12px;
          font-weight: 400;
          color: #404040;
          line-height: 12px;
          cursor: pointer;
        }
      }

      .content {
        font-size: 14px;
        font-weight: 400;
        color: #404040;

        span {
          display: inline-block;
          height: 24px;
          line-height: 24px;
          padding: 0 10px;
          background: #d8d8d8;
          border-radius: 12px;
          opacity: .4;
          margin-right: 10px;
          cursor: pointer;
          margin-bottom: 10px;
        }

        img {
          width: 50px;
          height: 50px;
          background: #d8d8d8;
          border-radius: 4px;
          margin-right: 20px;
          cursor: pointer;
          object-fit: cover;
        }
      }
    }
  }

  .tag {
    font-size: 12px;
    font-weight: 400;
    color: #fff;
    margin-top: 20px;
    width: 50%;
    margin: 0 auto;

    span {
      cursor: pointer;
      margin-right: 20px;

      &:hover {
        color: #5675e8;
      }
    }
  }
}
</style>
