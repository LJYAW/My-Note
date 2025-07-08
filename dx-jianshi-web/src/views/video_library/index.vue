<!--
 * @Author: your name
 * @Date: 2021-07-15 15:04:32
 * @LastEditTime: 2021-07-16 18:40:42
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-web/src/views/video_library/index.vue
-->
<template>
  <div class="video-library">
    <div class="top-title">我的视频库</div>
    <div class="video-list">
      <ul>
        <li v-for="(item, index) in videoList" :key="index">
          <div class="img-box">
            <img :src="item.cover_pic" alt="" />
            <div class="use">立即使用</div>
          </div>
          <div class="message-box">
            <div class="title">{{ item.title }}</div>
            <div class="duration">{{ item.duration_str }}</div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      videoList: []
    }
  },
  computed: {},
  watch: {},
  methods: {
    getList() {
      let params = {
        type: 'video',
        page: 1,
        limit: 5
      }
      this.$get('intelligent-creation/user-resource-list', { params }).then(res => {
        let { data } = res
        if (data.code === '0000') {
          this.videoList = data.data.list
        } else {
          this.$alertMsg(data.msg)
        }
      })
    }
  },
  components: {},
  created() {
    this.getList()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
@media screen and (max-width: 1440px) {
  .video-library {
    display: none;
  }
}
.video-library {
  width: 350px;
  height: 100vh;
  background: #fff;
  padding: 30px;
  box-sizing: border-box;

  .top-title {
    font-size: 18px;
    font-weight: 600;
    color: #454545;
  }
  .video-list {
    ul {
      li {
        margin-top: 20px;
        .img-box {
          position: relative;
          cursor: pointer;
          img {
            width: 100%;
            height: 163px;
            object-fit: contain;
            border-radius: 4px;
          }
          .use {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 93px;
            height: 31px;
            text-align: center;
            line-height: 31px;
            color: rgba(255, 255, 255, 0.8);
            background: rgba(0, 0, 0, 0.5);
            border-radius: 16px;
            font-size: 14px;
          }
        }
        .message-box {
          display: flex;
          justify-content: space-between;
          margin-top: 10px;
          .title {
            width: 165px;
            font-size: 14px;
            font-weight: 400;
            color: #000000;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
          }
          .duration {
            min-width: 50px;
            text-align: center;
            font-size: 14px;
            font-weight: 400;
            color: #8a8080;
          }
        }
      }
    }
  }
}
</style>
