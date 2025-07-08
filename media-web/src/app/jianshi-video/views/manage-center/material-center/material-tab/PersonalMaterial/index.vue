<!--
 * @Author: your name
 * @Date: 2021-08-31 15:08:58
 * @LastEditTime: 2021-10-11 16:49:35
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/material-tab/PersonalMaterial/index.vue
-->
<template>
  <div class="personal-material">
    <ul v-infinite-scroll="load" class="infinite-list transition-wrap">
      <base-card
        v-for="item in materialData"
        :key="item.id"
        :show-mark="false"
        class="video-item infinite-list-item"
      >
        <div slot="img">
          <VideoCard v-if="item.cover_url" :cover-url="item.cover_url" :video-url="item.file_url" />
          <div v-else class="image-slot">
            <base-image :src="item.file_url" />
          </div>
          <div class="btn-wrap">
            <div>
              <p v-if="item.cover_url">视频时长 {{ item.duration|timesToHMS }}</p>
            </div>
            <svg-icon icon-class="delete" @click="deleteMaterial(item.uuid)" />
          </div>
        </div>
      </base-card>
    </ul>
  </div>
</template>

<script>
import VideoCard from '@/app/jianshi-video/components/VideoCard'
import setQueryParams from '@/utils/setQueryParams.js'
export default {
  components: {
    VideoCard
  },
  props: {

  },
  data() {
    return {
      materialData: [],
      page: 1,
      limit: 20,
      count: 0
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    this.getInitData()
  },
  mounted() {

  },
  methods: {
    load() {
      if (this.materialData.length < this.count) {
        this.page++
        this.getInitData()
      }
    },
    async getInitData() {
      const query = {
        source: '个人素材',
        sub_type: '通用'
      }
      const params = {
        page: this.page,
        limit: this.limit,
        query: setQueryParams(query)
      }
      const { err, res } = await this.$get('/materials', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.count = res.count
      this.materialData = this.materialData.concat(res.data)
    },
    async deleteMaterial(id) {
      const { err, data } = await this.$deleteRun(`/materials/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
        this.page = 1
        this.materialData = []
        this.getInitData()
      }
    }
  }
}
</script>

<style scoped lang="scss">
.personal-material {

  .transition-wrap {
    display: grid;
    grid-template-columns: repeat(4, 25%);  //同上
    grid-row-gap: 30px;
    margin: 0 -12px;
    max-height: calc(100vh - 230px);
    overflow-y: auto;

    .video-item {
      margin: 0 12px;

      ::v-deep .image {
        border-radius: 4px;
        height: auto;
        position: relative;
        background: none;

        .btn-wrap {
          position: absolute;
          width: 100%;
          padding: 9px;
          bottom: 0;
          z-index: 9;
          color: #fff;
          font-size: 12px;
          line-height: 12px;
          background: rgba(0,0,0,.4);
          display: flex;
          justify-content: space-between;
        }

        .image-slot {
          padding-bottom: 56.25%;
          height: 0;
          background: #eee;
          position: relative;

          .el-image {
            position: static;

            img {
              position: absolute;
              width: 100%;
              height: 100%;
              top: 0;
              // object-fit: cover;
            }
          }
        }

      }
    }
  }

  .laoding-text {
    text-align: center;
  }
}

</style>
