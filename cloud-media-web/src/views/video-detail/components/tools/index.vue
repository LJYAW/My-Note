<!--
 * @Author: your name
 * @Date: 2021-07-29 14:58:57
 * @LastEditTime: 2021-09-01 10:12:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/video-detail/components/tools/index.vue
-->
<template>
  <div class="tools-wrap">
    <div class="tools-tab">
      <div
        v-for="(item,index) in toolsData"
        :key="index"
        :class="['tools',index==activeToolIndex?'active':'']"
        @click="toolChange(index)"
      >
        {{ item.name }}
      </div>
    </div>

    <components
      :is="activeTool.cont.length?activeTool.component:'Normal'"
      :cont="activeTool.cont"
    />

  </div>
</template>

<script>
import Person from './person.vue'
import Object from './object.vue'
import SmartTag from './smartTag.vue'
import Subtitle from './subtitle.vue'
import Voice from './voice.vue'
import Normal from './normal.vue'

export default {
  components: {
    Person,
    Object,
    SmartTag,
    Subtitle,
    Voice,
    Normal
  },
  props: {

  },
  data() {
    return {
      toolsData: [
        { name: '人物识别', component: 'Person', cont: [] },
        { name: '物体识别', component: 'Object', cont: [] },
        { name: '字幕文本', component: 'Subtitle', cont: [] },
        { name: '语音文本', component: 'Voice', cont: [] },
        { name: '关键词', component: 'SmartTag', cont: [] }

      ],
      activeToolIndex: 0
    }
  },
  computed: {
    activeTool() {
      return this.toolsData[this.activeToolIndex]
    },
    videoData() {
      return this.$store.state.videoStatus.videoData
    }
  },

  watch: {
    videoData: {
      handler: function(data) {
        this.toolsData[0].cont = data.vca.person || []
        this.toolsData[2].cont = data.vca.ocr || []
        this.toolsData[3].cont = data.vca.asr || []
        this.toolsData[4].cont = data.vca.words || []
        this.toolsData[1].cont = data.vca.object || []
      },
      immediate: true,
      deep: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    toolChange(index) {
      this.activeToolIndex = index
    }
  }
}
</script>

<style scoped lang="scss">
.tools-tab {
  display: flex;
  align-items: center;
  margin-bottom: 20px;

  .tools {
    margin-right: 10px;
    padding: 5px 10px;
    background: rgba(239,239,239,.8);
    border-radius: 4px;
    font-size: 12px;
    color: #404040;
    cursor: pointer;

    &.active {
      background: #5675e8;
      color: #fff;
    }
  }

}
</style>
