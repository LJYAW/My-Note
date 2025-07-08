<!--
 * @Author: your name
 * @Date: 2021-07-29 18:25:18
 * @LastEditTime: 2021-08-11 14:22:26
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/video-edit/components/video-control/index.vue
-->
<template>
  <div class="video-control">

    <div class="play-control">
      <div v-if="playing" @click="controlHandel('play')">
        <i class="iconfont icon-ziyuan1" />
      </div>

      <div v-else @click="controlHandel('play')">
        <i class="iconfont icon-bofang" />
      </div>
    </div>

    <div class="time">
      <div class="cur-time">{{ currentTimeMs | msToSecond }}  </div>
      <span class="pre">/</span>
      <div class="durantion">{{ VIDEO_DURANTION_MS | msToSecond }}</div>
    </div>

    <div class="volume-control">
      <div v-if="muted" @click="controlHandel('muted')">
        <i class="iconfont icon-jingyin" />
      </div>

      <div v-else @click="controlHandel('muted')">
        <i class="iconfont icon-volume" />
      </div>
    </div>

  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'VideoControl',
  props: {
  },
  data() {
    return {

    }
  },
  computed: {
    ...mapState('video', ['playing', 'muted', 'VIDEO_DURANTION_MS', 'currentTimeMs'])
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    controlHandel(str) {
      this.$emit('controlHandel', str)
    }
  }
}
</script>

<style scoped lang="scss">
// https://www.iconfont.cn/manage/index?spm=a313x.7781069.1998910419.13&manage_type=myprojects&projectId=2073041&keyword=&project_type=&page=
@import "./icon/icon.css";

.video-control {
  display: flex;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: #404040;
  color: #fff;
  padding: 10px 25px;

  .iconfont {
    font-size: 20px;
  }

  .time {
    display: flex;
    font-size: 14px;
    margin-left: 20px;

    .pre {
      margin: 0 5px;
    }

    .durantion {
      color: #8c8c8c;
    }
  }

  .volume-control {
    margin-left: auto;

    .icon-volume,
    .icon-jingyin {
      font-size: 25px;
    }

  }
}
</style>
