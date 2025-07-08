<!-- 进度条 -->
<template>
  <div>
    <div style="position: relative;width:570px;height:360px;"
         class='bg-white d-flex flex-column align-items-center justify-content-center'
         v-if='isShow '>
      <img src="@/assets/img/logo.png" />
      <div style="position:absolute;top:0">
        <div class='d-flex flex-column align-items-center justify-content-center'
             style="background:rgba(0,0,0,0.5);width:570px;height:360px;">
          <div class='fc-white fz-14px'>正在为您剪辑视频中，请稍后...</div>
          <div class='progressBar mt-20px mb-20px'>
            <div class='over'
                 :style="'width:'+progress+'%; transition: width 1s;'">
              <div :style="'width:'+translateMoveX+'%; transition: width 1s;'"
                   :class="{'tracClass' : translateMoveX != 100 }">
              </div>
            </div>
          </div>
          <div class='fc-white fz-14px'>{{progress}}%</div>
        </div>
      </div>
    </div>

  </div>

</template>

<script>
import { setTimeout, setInterval, clearInterval } from 'timers'

/**
 *
 * @param done
 * 当 done 为 1 的时候 进度条完成
 */
export default {
  props: {
    done: {
      type: Number,
      default: 0
    },
    progress: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      num: 0,
      translateMoveX: 100,
      barValue: '',
      isShow: true,
      timer: null
    }
  },

  computed: {},

  created() { },

  mounted() {
    // this.getStatus()
    // this.timer = setInterval(this.getInterval, 1000)
  },
  methods: {
    // getInterval() {
    //   console.log('运行中')
    //   if (this.translateMoveX == 100) {
    //     this.translateMoveX = 0
    //   } else {
    //     this.translateMoveX = 100
    //   }
    //   if (this.done == 0) {
    //     // this.getStatus()
    //     if (this.num < 85) {
    //       this.num = this.num + Math.ceil(Math.random() * 10)
    //     } else {
    //       if (this.num < 99) {
    //         this.num = this.num + 1
    //       }
    //     }
    //   } else if (this.done == 1) {
    //     if (this.num < 80) {
    //       this.num = this.num + Math.ceil(Math.random() * 20)
    //     } else {
    //       this.num = 100
    //       if (this.num == 100) {
    //         setTimeout(() => {
    //           this.$emit('changVideoUrl', this.barValue)
    //           this.isShow = false
    //         }, 1500)
    //       }
    //     }
    //   }
    // },
  },
  destroyed() {
    clearInterval(this.timer)
    this.timer = null
  }
}
</script>
<style lang='scss' scoped>
.progressBar {
  width: 503px;
  height: 25px;
  // background: #F0F0F0;
  border-radius: 100px;
  border: 1px solid rgba(75, 75, 75, 1);
  .over {
    height: 23px;
    border-radius: 100px;
    // background-color: $c;
    background: -webkit-linear-gradient(left, yellow, $c);
    background: -o-linear-gradient(right, yellow, $c);
    background: -moz-linear-gradient(right, yellow, $c);
    background: linear-gradient(to right, rgba(255, 44, 32, 0.1), $c);
    div {
      height: 23px;
      border-radius: 100px;
      background: rgba(255, 255, 255, 0.1);
    }
    .tracClass {
      height: 23px;
      border-radius: 100px;
      background: rgba(255, 255, 255, 0);
    }
  }
}
</style>