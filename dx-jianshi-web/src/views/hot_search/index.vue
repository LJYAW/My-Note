<template>
  <div class="hot-search-pad">
    <div class="using-tutorials mb-23px">
      <div class="p-30px" style="background: #fff;">
        <p class="fz-20px mb-20px">
          使用教程
        </p>
        <videoPalyer :video_options="video_options" />

        <p class="fz-12px mt-12px text-center">手把手教你如何使用微剪快速创作视频</p>
      </div>
    </div>

    <div v-if="0">
      <p class="fz-20px">
        <i class="iconfont iconrengongzhineng_sel fz-22px fc-ccc"></i>
        创作大脑
      </p>

      <div class="search-pad mt-13px">
        <div class="title d-flex justify-content-between">
          <p>今日热文</p>
          <a class="fz-12px fc-c" @click="refresh">
            <i class="iconfont iconrefresh" style="vertical-align: text-bottom;"></i>
            换一批
          </a>
        </div>
        <!-- <loading v-if="status_loading" /> -->
        <ul v-if="!status_loading" class="data-list">
          <li v-for="(item, index) in list" :key="index" class="d-flex">
            <div class="img-wrap">
              <i v-if="!item.cover" class="iconfont icontupian-tianchong fz-25px"></i>
              <img v-else :src="item.cover" />
            </div>
            <div class="flex-1 d-flex justify-content-between flex-column h-100">
              <a :href="item.link" target="_blank" class="fz-12px mb-10px text-ellipsis-2">{{ item.title }}</a>

              <div class="d-flex justify-content-between fz-12px">
                <span class="fc-999">热度：{{ item.hot_num }}</span>
                <a class="fc-c" @click="getHotLink(item)">获取内容</a>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
const LIMIT = 8

export default {
  data() {
    return {
      data_list: [],
      page: 0,
      limit: 8,
      status_loading: false,
      video_options: {
        width: '332',
        height: '190',
        autoplay: false,
        src: 'http://static-magic.weijian.video/storage/mnt/zhijian/system/one-minutes-do-it.mp4'
      }
    }
  },
  components: {},
  computed: {
    data_list_temp() {
      let arr = JSON.parse(JSON.stringify(this.data_list))
      let arr1 = arr.filter(item => {
        return item.cover
      })
      return arr1
    },
    list() {
      return this.data_list_temp.slice(this.page, this.page + LIMIT)
    }
  },
  watch: {},
  methods: {
    getHotLink(item) {
      if (item.link) {
        this.$store.commit('setHotLink', item.link)
      } else {
        this.$alertMsg('没有获取到链接')
      }
    },
    refresh() {
      this.page = LIMIT + this.page
      if (this.page > this.data_list_temp.length - 1) {
        this.page = 0
      }
    },
    getHOstAricles() {
      this.status_loading = true

      this.$get('/intelligent-creation/hot-articles').then(res => {
        if (res.data.code == '0000') {
          let list = res.data.data
          let obj = {}
          list.forEach(item => {
            obj = {
              title: item.title,
              cover: item.cover_image,
              link: item.article_url,
              hot_num: item.hot_value
            }
            this.data_list.push(obj)
          })
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.status_loading = false
      })
    }
  },
  created() {
    this.getHOstAricles()
  },
  mounted() {}
}
</script>

<style lang="scss" scoped>
.hot-search-pad {
  width: 350px;

  .search-pad {
    background-color: #fff;
    min-height: 400px;
    .title {
      padding-top: 15px;
      border-bottom: 1px solid #f2f2f2;
      margin: 12px 22px;

      p {
        padding-bottom: 12px;
        position: relative;
        &::after {
          display: block;
          content: ' ';
          width: 5px;
          height: 16px;
          background: $c;
          position: absolute;
          left: -22px;
          top: 0;
        }
      }
    }
  }

  .data-list {
    line-height: 1.4;
    padding: 17px 22px;

    li {
      margin-bottom: 26px;
      height: 68px;

      .img-wrap {
        width: 120px;
        background-color: rgba(219, 219, 219, 0.6);
        margin-right: 10px;
        position: relative;
        .icontupian-tianchong {
          width: 20px;
          height: 34px;
          position: absolute;
          left: 50%;
          top: 50%;
          transform: translate(-50%, -50%);
          color: #909da6;
        }
        img {
          height: 100%;
          width: 100%;
          object-fit: cover;
        }
      }
    }
  }
}
</style>
