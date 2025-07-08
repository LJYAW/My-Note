<!--
 * @Author: zzx
 * @Date: 2020-10-15 11:45:22
 * @LastEditTime: 2020-11-13 10:58:42
 * @FilePath: /zhijian_web/src/views/active_video/my_videos/index.vue
-->
<template>
  <div class="my-videos container">
    <img src="@/assets/images/banner.png" alt />

    <ul class="header-bar">
      <li>
        <span>项目排序：</span>
        <el-select v-model="form.time" size="mini">
          <el-option
            v-for="item in time_sort_list"
            :key="item.type"
            :label="item.name"
            :value="item.type"></el-option>
        </el-select>
      </li>
      <li>
        <span>视频类型：</span>
        <el-select v-model="form.time" size="mini">
          <el-option
            v-for="item in time_sort_list"
            :key="item.type"
            :label="item.name"
            :value="item.type"></el-option>
        </el-select>
      </li>
      <li>
        <span>项目状态：</span>
        <el-select v-model="form.time" size="mini">
          <el-option
            v-for="item in time_sort_list"
            :key="item.type"
            :label="item.name"
            :value="item.type"></el-option>
        </el-select>
      </li>
      <li>
        <el-input v-model="key_word" size="mini" class="serarch-input" placeholder="输入关键字搜索项目"></el-input>
      </li>
    </ul>

    <div class="card-list">
      <div class="card-wrap">
        <div class="add-video-card card" @click="goToCreatedActiveVideo">
          <i class="iconfont iconadd"></i>
        </div>
        <p class="card-name">新建项目</p>
      </div>

      <div class="card-wrap" v-for="(item,i) in video_list" :key="i">
        <div class="video-card card">
          <i class="iconfont icontietuku"></i>
          <ul class="btn-list" v-if="item.play_url">
            <li>
              <a @click="preview(item)">
                <i class="iconfont iconbofang"></i>
                <span>预览</span>
              </a>
            </li>
            <li>
              <i class="iconfont iconbianji"></i>
              <span>编辑</span>
            </li>
            <li>
              <el-popover placement="bottom" width="100" :ref="'popover'+i">
                <div slot="reference">
                  <i class="iconfont iconcaidan-"></i>
                  <span>更多</span>
                </div>
                <ul class="moreList">
                  <li @click="shares(item,i)">分享</li>
                  <li>重命名</li>
                  <li>创建副本</li>
                  <li>删除</li>
                </ul>
              </el-popover>
            </li>
          </ul>
          <div v-else>生成中...</div>
        </div>
        <p class="card-name">{{item.title}}</p>
      </div>
    </div>
  </div>
</template>

<script>
import addProject from '../models/add_project'
import share from '../models/share'
import $iframe from '../models/layer'
export default {
  name: 'MyVideos',
  props: {},
  data() {
    return {
      key_word: '',
      page: 1,
      limit: 20,
      form: {
        time: 1
      },
      time_sort_list: [
        {
          type: 1,
          name: '按创建时间排序'
        },
        {
          type: 2,
          name: '按生产时间排序'
        }
      ],
      video_list: []
    }
  },
  computed: {},
  watch: {},
  methods: {
    goToCreatedActiveVideo() {
      $iframe({
        content: addProject,
        title: '新建项目',
        width: '500px',
        height: '408px'
      })
    },
    shares(item, i) {
      let popover = 'popover' + i
      this.$refs[popover][0].doClose()
      $iframe({
        content: share,
        title: '分享',
        width: '500px',
        height: '408px'
      })
    },
    getActiveVideoList() {
      this.$get('/interactive-video/list', { page: this.page, limit: this.limit }).then(res => {
        if (res.data.code == '0000') {
          this.video_list = res.data.data.list
        }
      })
    },
    preview(item) {
      this.$actions.openWin(item.play_url)
    }
  },
  components: {
    addProject,
    share
  },
  created() {
    this.getActiveVideoList()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
