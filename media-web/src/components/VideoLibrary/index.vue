<!--
 * @Author: your name
 * @Date: 2021-08-31 11:25:34
 * @LastEditTime: 2021-09-16 14:28:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/VideoLibrary/index.vue
-->
<template>
  <div class="video-library">
    <div class="search-wrap">
      <title-bar title="我的视频库" />
      <el-input
        v-model="searchWord"
        class="search"
        placeholder=""
        size="small"
        clearable
        suffix-icon="el-icon-search"
        @change="search"
      />
    </div>

    <div class="video-list">
      <base-card
        v-for="data in videoList"
        :key="data.id"
        class="item"
        :show-mark="false"
        @click.native="cardClick(data)"
      >
        <div slot="img" class="image-wrap">
          <base-image :src="data.cover_url" />
          <div class="duration">{{ data.video_duration|timesToHMS }}</div>
        </div>
        <div slot="des" class="video-msg">
          <div class="video-title">{{ data.titles }}</div>
        </div>
      </base-card>
    </div>
    <base-pag
      :total="total"
      :limit="limit"
      :page="page"
      layout="prev, pager, next"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import setQueryParams from '@/utils/setQueryParams'

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      videoList: [],
      page: 1,
      limit: 12,
      total: 0,
      searchWord: ''
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.initList()
  },
  mounted() {

  },
  methods: {
    search() {
      this.page = 1
      this.initList()
    },
    async initList() {
      const query = {
        titles__contains: this.searchWord
      }
      const params = {
        query: setQueryParams(query),
        page: this.page,
        limit: this.limit
      }
      const { err, res } = await this.$get('/videos', params)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.videoList = res.data
      this.total = res.count
    },
    handleCurrentChange(val) {
      this.page = val
      this.initList()
    },
    cardClick(item) {
      this.$emit('handelClick', item)
    }
  }
}
</script>

<style scoped lang="scss">
.search-wrap {
  display: flex;
  justify-content: space-between;
  width: 100%;

  .search {
    margin-left: 10%;
    width: 365px;
  }
}

.video-list {
  display: grid;
  grid-template-columns: repeat(4, 25%);  //同上
  grid-row-gap: 30px;
  margin: 0 -12px;
  margin-top: 20px;

  .item {
    transition: all 1s;
    margin: 0 12px;

    ::v-deep .image {
      position: relative;
      height: 0;
      padding-bottom: 55%;

      img {
        object-fit: fill;
      }
    }

    .duration {
      padding: 3px 4px;
      font-size: 12px;
      background: rgba(0,0,0,.4);
      color: #fff;
      border-radius: 4px;
      position: absolute;
      bottom: 10px;
      right: 6px;
      text-align: center;
    }

    .image-wrap {
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
    }
  }
}
</style>
