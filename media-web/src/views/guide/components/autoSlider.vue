<template>
  <div class="content" :style="infos.contentStyle">
    <div class="inner">
      <div class="box list_flex align_items" :style="infos.boxStyle">
        <div class="info">
          <img class="ico" :src="infos['ico']">
          <div class="title">{{ infos.title }}</div>
          <div class="desc">{{ infos.desc }}</div>
          <div class="tabs">
            <div
              v-for="(item, index) in infos.tabs"
              :key="index"
              :class="index === activeindex?'active':''"
              class="item"
              @click="itemclick(index)"
            >
              {{ item.title }}
              <div class="progressbar">
                <div class="bar" :style="{ width:barwidth }" />
              </div>
            </div>
          </div>
        </div>
        <div class="imgbox">
          <img v-if="infos.tabs" :class="barwidth !== '0%'?'in':''" :src="infos.tabs[activeindex]['pic']">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    infos: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      activeindex: 0,
      barwidth: '0%',
      timer: {
        duration: 4000,
        nts: 0,
        timeout: null
      }
    }
  },
  computed: {
  },
  watch: {
    infos: {
      handler(newName, oldName) {
        this.run()
      },
      immediate: true
    }
  },
  beforeDestroy() {
    if (this.timer.timeout) {
      clearTimeout(this.timer.timeout)
    }
  },
  created() {
  },
  mounted() {
  },
  methods: {
    itemclick(index) {
      if (index !== this.activeindex) {
        this.next(index)
      }
    },
    reset() {
      this.barwidth = '0%'
      this.timer.nts = 0
      this.run()
    },
    next(index) {
      if (index === undefined) {
        this.activeindex++
        if (this.activeindex > this.infos.tabs.length - 1) {
          this.activeindex = 0
        }
        this.reset()
      } else {
        this.activeindex = index
        this.reset()
      }
    },
    run() {
      const step = Math.ceil(this.timer.duration / 60)
      if (this.timer.timeout) {
        clearTimeout(this.timer.timeout)
      }
      this.timer.timeout = setTimeout(() => {
        if (this.timer.nts < this.timer.duration) {
          this.timer.nts += Math.ceil(this.timer.duration / step)
          this.run()
        } else {
          clearTimeout(this.timer.timeout)
          this.next()
        }
        let per = Math.floor(this.timer.nts / this.timer.duration * 100)
        per = Math.min(per, 100)
        this.barwidth = per + '%'
      }, 60)
    }
  }
}
</script>
<style scoped lang="scss">
@import "../css/index.scss";

@keyframes opacityAnima {

  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}

.content {
  padding: 100px 0;
  background-color: #fff;

  .box {
    justify-content: space-between;
  }

  .imgbox {
    width: 710px;

    .in {
      animation: opacityAnima 2s;
      animation-iteration-count: 1;
    }
  }

  .info {

    .ico {
      width: 40px;
      height: 40px;
    }

    .title {
      font-weight: bold;
      font-size: 30px;
      margin-top: 5px;
    }

    .desc {
      font-size: 18px;
      margin-top: 30px;
    }

    .tabs {
      margin-top: 10px;
      display: flex;
      flex-wrap: wrap;
      width: 340px;

      .item {
        height: 42px;
        line-height: 42px;
        background-color: rgba(239, 239, 239, .8);
        width: 100px;
        text-align: center;
        margin-right: 4px;
        margin-top: 20px;
        cursor: pointer;
        position: relative;

        &.active {
          background: #fff;
          box-shadow: 0px 4px 8px 0px rgba(64, 64, 64, .1);

          .progressbar {
            display: block;
          }
        }

        .progressbar {
          display: none;
          position: absolute;
          width: 100%;
          bottom: 0;
          height: 2px;
          background: #eef2fe;

          .bar {
            position: absolute;
            width: 0%;
            height: 100%;
            left: 0;
            top: 0;
            background: #5675e8;
          }
        }
      }
    }
  }
}
</style>
