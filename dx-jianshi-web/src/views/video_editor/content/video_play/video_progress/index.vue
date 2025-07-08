<template>
  <div>
    <div class='progressBar d-flex align-items-center cp'
      id="progress"
      @click="getTime($event)"
      @mousedown="move($event)"
      @mouseup="moveOver($event)">
      <!-- <div
        :style="'position: relative;background:#C51A1A;height:3px;width:'+progress_width+'%;'">
        <div class='ball'>
        </div>
      </div> -->
      <div
        :style="'background:black;height:3px;width:'+(100 - progress_width)+'%;'">
      </div>
    </div>
  </div>
</template>

<script>
import { getMouseXY } from '@/utils/mouse.js'
export default {
  props: [],
  data() {
    return {
      video: null,
      progress_width: 0,
      progress: null,
      progressBall: null,
      moveIs: true
    }
  },
  components: {},

  computed: {
    currentTime() {
      return this.$store.state.currentTime
    }
  },

  watch: {
    currentTime(val) {
      if (this.moveIs) {
        this.progress_width = (val / this.video.duration) * 100
      }
    }
  },

  methods: {
    move(e) {
      const offsetWidth = this.progress.offsetWidth
      this.moveIs = false
      this.progress.onmousemove = (e) => {
        this.progress_width = (getMouseXY().x / offsetWidth) * 100
      }
    },
    moveOver(e) {
      this.moveIs = true
      document.onmouseup = () => {
        this.progress.onmousemove = null
      }
    },
    getTime(e) {
      const length = e.pageX - this.progress.offsetLeft
      const percent = length / this.progress.offsetWidth
      this.video.currentTime = percent * this.video.duration
    },
    getDocumentVideo() {
      this.video = document.getElementById('myVideo')
    }
  },

  created() {},

  mounted() {
    this.progress = document.getElementById('progress')
    this.getDocumentVideo()
  }
}
</script>

<style scoped lang="scss">
.progressBar {
  background: rgba(245, 245, 245, 1);
  position: relative;
  .ball {
    background: linear-gradient(180deg, rgba(255, 255, 255, 1) 0%, rgba(163, 163, 163, 1) 100%);
    position: absolute;
    right: -6px;
    top: -4px;
    height: 13px;
    width: 13px;
    border-radius: 100%;
    background-color: blue;
  }
}
</style>
