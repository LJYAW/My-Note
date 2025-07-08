<!--
 * @Author: wxh
 * @Date: 2020-11-10 19:14:58
 * @LastEditTime: 2020-11-19 18:03:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/assignment/assign_detail.vue
-->
<template>
  <div class="assign-detail">
    <!-- 详情背景图 -->
    <div class="bannerl-img">
      <slot name="detail-bg">
        <img :src="assignInfo.banner_url" alt="" srcset="">
      </slot>
    </div>

    <div class="assign-detail-con">
      <!-- 任务细节公共部分 -->
      <div class="assign-d-top">
        <h5 class="assign-d-title">{{assignInfo.title}}</h5>
        <p class="assign-d-time">任务时间：{{assignInfo.start_date}}～{{assignInfo.end_date}}</p>
        <!-- <p class="assign-d-text">{{assignInfo.title}}</p> -->
      </div>
      <p v-html="assignInfo.intro"></p>

      <!-- 任务附件 -->
      <div class="assign-d-download" v-if="assignInfo.attach_file_name">
        <h6>任务附件</h6>
        <div style="display:flex;">
          <span v-if="assignInfo.attach_file_name">{{assignInfo.attach_file_name}}</span>
          <a :href="assignInfo.attach_file_url">下载附件</a>
        </div>
      </div>

      <!-- 展开 收起 -->
      <slot name="assign-unfold"></slot>
    </div>

    <!-- 任务视频 -->
    <slot name="assign-video"></slot>

    <!-- 领取任务 按钮 -->
    <slot name="assign-get-btn"></slot>
  </div>
</template>

<script>
import demandList from './assign-demand-list'
import { imgError } from '../js/imgError'

export default {
  mixins: [imgError],
  props: {},
  components: { demandList },
  inject: ['assignDetail', 'detail'],
  data() {
    return {}
  },
  computed: {
    type() {
      return this.$route.query.type
    },
    assignInfo() {
      return this.assignDetail.assignInfo
    },
    isUnfold() {
      // console.log(this.isUnfold)
      return this.detail.isUnfold == undefined ? true : this.detail.isUnfold
    }
  },
  watch: {},
  methods: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.assign-detail {
  display: flex;
  flex-direction: column;
  overflow: hidden;
  .bannerl-img {
    background-color: #f7f7f7;
    height: 300px;
    margin-bottom: 30px;
    img {
      width: 100%;
      height: 100%;
      display: block;
      object-fit: contain;
    }
  }
  h6 {
    font-size: 18px;
    color: #101010;
    font-weight: normal;
  }
  .assign-detail-bg {
    // width: 1000px;
    height: 300px;
    margin-bottom: 30px;
    background-color: #e8e8e8;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .assign-detail-con {
    position: relative;
    color: #171717;
    line-height: 1.5;
    pre {
      white-space: pre-wrap;
      word-wrap: break-word;
    }
    .assign-d-top {
      margin-bottom: 15px;
      .assign-d-title {
        line-height: 36px;
        color: #101010;
        font-size: 24px;
        font-weight: normal;
      }
      .assign-d-time {
        margin-top: 16px;
        color: #999;
        font-size: 12px;
        height: 18px;
        line-height: 18px;
      }
      .assign-d-text {
        margin-top: 16px;
        line-height: 25px;
        font-size: 14px;
      }
    }

    .assign-d-download {
      margin-top: 32px;
      font-size: 0;
      > div {
        margin-top: 12px;
        span {
          margin-right: 40px;
          font-size: 14px;
          line-height: 24px;
        }
        a {
          display: inline-block;
          color: #2a79df;
          line-height: 24px;
          font-size: 14px;
          text-decoration: underline;
        }
      }
    }
    .assign-d-flag {
      display: flex;
      position: absolute;
      right: 0;
      bottom: -24px;
      color: #2a79df;
      cursor: pointer;
      img.arrow_down {
        transform: rotate(180deg);
      }
      span {
        font-size: 14px;
        line-height: 24px;
      }
    }
  }
  .assign-d-video-con {
    margin: 33px 0;
    .assign-d-video-title {
      display: flex;
      align-items: center;
      h6 {
        margin-right: 30px;
      }
      a {
        color: #2a79df;
        text-decoration: underline;
      }
    }
    .assign-d-video-wrap {
      max-width: 100%;
      margin: 18px auto 52px;
      .assign-d-video-ul {
        display: flex;
        flex-wrap: wrap;
        margin: 18px auto 52px;
        li {
          flex: 1;
          max-width: 232px;
          margin-right: 24px;
          margin-bottom: 24px;
          &:nth-child(4n) {
            margin-right: 0;
          }
          .assign-d-video {
            width: 232px;
            height: 140px;
            background-color: #e8e8e8;
          }
          .assign-d-video-status {
            width: 232px;
            // height: 20px;
            margin-top: 8px;
            display: flex;
            flex-direction: column;
            // justify-content: space-between;
            // align-items: center;
            span:first-child {
              line-height: 16px;
              height: 16px;
              font-size: 14px;
              color: #101010;
            }
            span:last-child {
              font-size: 12px;
              color: #999;
              margin-top: 10px;
            }
          }
        }
      }
    }
  }
}
</style>
