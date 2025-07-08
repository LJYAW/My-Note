<template>
  <div class="right-pad-wrap">

    <div class="toolbar">
      <ul class="mini-nav">
        <li @click="checkoutToolbar(7)"
            style="padding-right: 20px;
                   border-right: 1px solid #2e2e2e;"
            class="ml-auto">
          <a :class="{'action' : action_index == 7}">
            <i class="iconfont iconflie"></i>
            素材库</a>
        </li>
      </ul>
      <!-- <ul class="mini-nav">
        <li>
          <span class="fz-12px fc-666">AI剪辑助手</span>
        </li>
        <li @click="checkoutToolbar(0)">
          <a :class="{'action' : action_index == 0}">
            字幕</a>
        </li>
        <li @click="checkoutToolbar(1)">
          <a :class="{'action' : action_index == 1}">
            语音</a>
        </li>
        <li @click="checkoutToolbar(2)">
          <a :class="{'action' : action_index == 2}">
            图像</a>
        </li>
        <li @click="checkoutToolbar(3)"
            style="padding-right: 20px;
                   border-right: 1px solid #2e2e2e;">
          <a :class="{'action' : action_index == 3}">
            标记片段</a>
        </li>
      </ul> -->

      <ul class="mini-nav">
        <li>
          <span class="fz-12px fc-666">编辑工具</span>
        </li>
        <li @click="checkoutToolbar(4)">
          <a :class="{'action' : action_index == 4}">
            <i class="iconfont iconwenben"></i>
            文本</a>
        </li>
        <li @click="checkoutToolbar(5)">
          <a :class="{'action' : action_index == 5}">
            <i class="iconfont icontietuku"></i>
            贴图</a>
        </li>
        <li @click="checkoutToolbar(6)">
          <a :class="{'action' : action_index == 6}">
            <i class="iconfont iconjianji-"></i>
            贴片</a>
        </li>

      </ul>
    </div>

    <div class="right-pad-content">
      <textMain v-if="action_index == 4" />
      <imageMain v-if="action_index == 5" />
      <myVideoStore v-if="action_index == 7" />
    </div>

  </div>
</template>

<script>
import textMain from './text_main/index'
import myVideoStore from './my_video_store/index'
import imageMain from './image_main/index'

export default {
  props: [],
  data() {
    return {
      action_index: 7
    }
  },
  components: {
    textMain,
    myVideoStore,
    imageMain
  },

  computed: {},

  watch: {},

  methods: {
    checkoutToolbar(index) {
      this.action_index = index
    }
  },

  created() { },

  mounted() { }
}
</script>

<style scoped lang="scss">
.right-pad-wrap {
  overflow: hidden;
  height: 100%;
  display: flex;
  flex-direction: column;
  border-left: 1px solid #000;
  .right-pad-content {
    flex: 1;
    // margin-top: 20px;
    overflow: hidden;
    overflow-y: auto;
    background: #313131;
  }
  .mini-nav {
    height: 44px;
    li {
      a {
        color: #999;
      }
    }
  }
}
</style>
