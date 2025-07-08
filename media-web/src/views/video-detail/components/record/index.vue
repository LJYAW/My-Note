<!--
 * @Author: your name
 * @Date: 2021-08-03 10:18:52
 * @LastEditTime: 2021-08-03 10:27:53
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/video-detail/components/record/index.vue
-->
<template>
  <div class="record-line-wrap">
    <div class="record-line">
      <div
        v-for="v in recordData"
        :key="v.time"
        class="record-item"
      >
        <div class="time">00:00:{{ v.time }}</div>
        <div class="content">{{ v.content }}</div>
      </div>
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
      recordData: [
        { time: 10, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 12, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 14, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 15, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 16, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 18, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 20, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 22, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 26, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 28, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' },
        { time: 50, content: '目前从事技术管理工作。我想谈谈我对于中国梦的理解。和绝大多数普通人一样，我成长中的不一样时期有着不一样的梦，小的时候想变为机器猫，能够变出吃不完的零食和数不尽的玩具，初高中的时候，在教师和家长的耳提面命下，我的梦是考上一所好的大学。' }
      ]
    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.record-line-wrap {
  max-height: 75vh;
  height: 811px;
  overflow: scroll;
}

.record-line {
  height: 100%;

  .record-item {
    padding-left: 20px;
    padding-bottom: 17px;
    position: relative;
    cursor: pointer;

    &::before {
      content: "";
      position: absolute;
      left: 0px;
      top: 5px;
      width: 5px;
      height: 5px;
      background: #404040;
      border-radius: 50%;
      z-index: 1;
    }

    &::after {
      content: "";
      position: absolute;
      left: 2px;
      top: 5px;
      width: 1px;
      height: 100%;
      background: #d8d8d8;
      opacity: .8;
    }

    &:last-child {

      &::after {
        display: none;
      }
    }

    .time {
      font-size: 12px;
      color: #404040;
      margin-bottom: 5px;
      opacity: .6;
    }

    .content {
      font-size: 14px;
      color: #404040;
    }
  }
}
</style>
