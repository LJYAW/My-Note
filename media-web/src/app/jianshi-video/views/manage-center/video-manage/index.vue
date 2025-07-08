<!--
 * @Author: your name
 * @Date: 2021-08-30 14:13:43
 * @LastEditTime: 2021-10-21 19:07:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/video-manage/index.vue
-->
<template>
  <div class="video-mannge">
    <div class="search-wrap">
      <el-input
        v-model="keyword"
        size="mini"
        @keyup.enter.native="searchData"
      >
        <i slot="suffix" class="el-input__icon el-icon-search" @click="searchData" />
      </el-input>
      <p v-if="!$store.state.user.roles.includes('视频管家')" class="tips">
        成品视频保存有效期为3天，请尽快下载 <span @click="gotoPage">获得更长保存时间</span>
      </p>
    </div>
    <div class="video-wrap">
      <div class="tabs-wrap">
        <div
          v-for="item in tabs"
          :key="item.title"
          :class="[activeName==item.title&&'active','tabs-item']"
          @click="activeName=item.title,componentName=item.componentName"
        >{{ item.title }}</div>
      </div>
      <component :is="componentName" ref="list" :keyword="keyword" />
    </div>

  </div>
</template>

<script>
import SubtitleVideo from './subtitle/index.vue'
import PhoneticVideo from './phonetic-create/index.vue'
import ScriptVideo from './script-create/index.vue'
import MaskVideo from './mask/index.vue'
import PPTCreate from './ppt/index.vue'
// import {mapState}
export default {
  components: {
    SubtitleVideo,
    PhoneticVideo,
    ScriptVideo,
    MaskVideo,
    PPTCreate
  },
  props: {

  },
  data() {
    return {
      tabs: [{
        componentName: 'ScriptVideo', title: '快速创作'
      },
      {
        componentName: 'PhoneticVideo', title: '语音创作'
      },
      {
        componentName: 'SubtitleVideo', title: '字幕创作'
      },
      {
        componentName: 'MaskVideo', title: '视频水印'
      }, {
        componentName: 'PPTCreate', title: 'PPT创作'
      }],
      activeName: '快速创作',
      keyword: '',
      componentName: 'ScriptVideo'
    }
  },
  computed: {

  },
  watch: {

  },
  created() {
    if (this.$route.query.tab) {
      this.activeName = this.$route.query.tab
      const obj = this.tabs.find(item => item.title === this.activeName)
      this.componentName = obj.componentName
    }
  },
  mounted() {

  },

  beforeDestroy() {
    this.$bus.$off('search', this.keyword)
  },
  methods: {
    searchData() {
      this.$refs.list.search()
    },
    gotoPage() {
      this.$router.push({
        path: '/guide/video-home'
      })
    }
  }
}
</script>

<style scoped lang="scss">
.video-mannge {
  width : 100%;

  .search-wrap {
    display : flex;
    align-items : center;

    ::v-deep .el-input {
      width : 365px;
    }

    .tips {
      font-weight : 400;
      line-height : 14px;
      margin-left : 24px;
      color : rgba(64,64,64,.4);

      span {
        color : #5783F3;
        margin-left : 10px;
        cursor : pointer;
      }
    }
  }

  ::v-deep .video-wrap {
    .tabs-wrap {
      display : flex;
      margin : 20px 0;

      .tabs-item {
        padding : 9px 10px;
        background : rgba(239,239,239,.8);
        border-radius : 4px;
        color : #404040;
        margin-right : 10px;
        font-size : 12px;
        line-height : 12px;
        cursor : pointer;
        transition : all .3s cubic-bezier(.645,.045,.355,1);

        &.active {
          background : #5675E8;
          color : #FFFFFF;
        }
      }
    }

    .empty-icon {
      width : 140px;
      height : 174px;
      margin : 100px auto;
      display : block;
    }

    .transition-wrap {
      display : grid;
      grid-template-columns : repeat(4, 25%);  //同上
      grid-row-gap : 30px;
      margin : 0 -12px;
      max-height : calc(100vh - 200px);
      overflow-y : auto;
      padding-bottom : 20px;

      .video-item {
        margin : 0 12px;

        .image {
          border-radius : 4px;
          height : auto;
          position : relative;

          .down-btn {
            position : absolute;
            top : 9px;
            right : 10px;
            z-index : 9;
            display : flex;
            align-items : center;

            svg {
              width : 24px;
              height : 24px;
              margin-right : 4px;
            }

            button {
              padding : 5px 15px;
            }
          }

          .btn-wrap {
            position : absolute;
            width : 100%;
            padding : 9px;
            bottom : 0;
            z-index : 9;
            color : #FFFFFF;
            font-size : 12px;
            line-height : 12px;
            background : rgba(0,0,0,.4);
            display : flex;
            justify-content : space-between;
          }
        }

        .desc {
          font-size : 14px;
          font-weight : 600;
          color : #404040;
          line-height : 18px;
          margin-bottom : 10px;
          white-space : nowrap;
          overflow : hidden;
          text-overflow : ellipsis;
        }

        .video-status {
          display : flex;
          justify-content : space-between;
          font-size : 12px;
          line-height : 12px;

          .date {
            color : #A3A3A3;
          }

          .build-success {
            color : #5675E8;
          }

          .build-fail {
            color : #FF6464;
          }

          .is-building {
            color : #33E766;
          }

          .to-build {
            color : #CB650D;
          }
        }
      }
    }
  }
}

</style>
