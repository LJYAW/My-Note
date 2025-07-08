<!--
 * @Author: wxh
 * @Date: 2020-11-13 14:24:27
 * @LastEditTime: 2020-11-20 11:49:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/assignment/assign_detail/detail_my.vue
-->
<template>
  <div>
    <assign-detail>

      <!-- 收起、展开 -->
      <div slot="assign-unfold" class="assign-d-flag" @click="isUnfold = !isUnfold">
        <img :class="{'arrow_down':!isUnfold}" src="@/assets/images/arrow_up.svg" alt="">
        <span>{{isUnfold ? '收起' : '展开'}}</span>
      </div>

      <!-- 任务视频 -->
      <div slot="assign-video" class="assign-d-video-con">
        <div class="assign-d-video-title">
          <h6>任务视频</h6>

          <!-- <a href="javascript:;" @click="uploadModel">上传视频</a> -->

          <!-- 上传视频按钮 -->
          <upload
            v-if="assignInfo.status !== '已结束'"
            class="assign-upload"
            button_name="上传视频"
            file_type="video/mp4"
            style="margin-top:10px;"
            @getFileUrl="getFileUrl" />

        </div>
        <div class="assign-d-video-wrap">
          <ul class="assign-d-video-ul" v-if="assignVideoList.length">
            <li v-for="(list,index) in assignVideoList" :key="'assign-d-video'+index">
              <div class="assign-d-video">
                <video
                  controls="controls"
                  style="width:100%;height:100%"
                  :src="list.video_url"
                  :poster="list.cover_pic_url"></video>
              </div>
              <div class="assign-d-video-status">
                <span class="text-ellipsis">{{list.title}}</span>
                <span>{{list.status}} <span>{{list.mark}}</span></span>

              </div>
            </li>
          </ul>
          <p v-else>暂无上传视频</p>
        </div>
        <!-- 分页 -->
        <el-pagination
          :hide-on-single-page="assignVideoList.length <= limit"
          background
          style="margin:20px auto;text-align:center;"
          :page-size="limit"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          layout="prev, pager, next"
          :total="total">
        </el-pagination>
      </div>
    </assign-detail>

    <!-- 任务视频上传弹框 -->
    <uploadVideoM
      v-if="modalName == 'uploadVideoAssign'" :uploadVideo="uploadVideo" />
  </div>
</template>

<script>
import AssignDetail from './../components/assign_detail.vue'
import uploadVideoM from './../modal/uploadVideo'
export default {
  provide() {
    return {
      detail: this //展开收起使用
    }
  },
  inject: ['assignDetail'],
  props: {},
  data() {
    return {
      getVideoListUrl: 'task/task-video-list',
      page: 1,
      limit: 8,
      currentPage: 1,
      total: 0,
      isUnfold: true,
      assignVideoList: [],
      modalName: '',
      uploadVideo: {}
    }
  },
  computed: {
    assignInfo() {
      return this.assignDetail.assignInfo
    },
    ct_id() {
      return this.assignDetail.assignInfo.ct_id
    }
  },
  watch: {},
  methods: {
    getFileUrl(url, file) {
      console.log('getFileUrl -> url, file', url, file)
      return new Promise((reslove, reject) => {
        this.uploadVideo = {
          url: url,
          file: file
        }
        reslove()
      }).then(() => {
        this.modalName = 'uploadVideoAssign'
        this.$nextTick(() => {
          this.$store.commit('modalShow', 'uploadVideoAssign')
        })
      })
    },
    getAssignVideoList() {
      // this.ct_id = this.assignInfo.ct_id
      this.$get(this.getVideoListUrl, { params: { ct_id: this.ct_id, page: this.page, limit: this.limit } }).then(res => {
        if (res.data.code == '0000') {
          const listData = res.data.data
          this.total = listData.total
          this.currentPage = Number(listData.page)
          this.assignVideoList = listData.list
        }
      })
    },
    async uploadModel() {
      await this.getFileUrl()
    },
    handleSizeChange() {},
    handleCurrentChange(val) {
      this.page = val
      this.getAssignVideoList()
    }
  },
  components: { AssignDetail, uploadVideoM },
  created() {},
  mounted() {
    this.getAssignVideoList()
  }
}
</script>

<style scoped lang="scss">
</style>
