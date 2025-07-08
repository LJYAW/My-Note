<!--  -->
<template>
  <div class="slider-main" :style="{width: width}" @mousedown.stop="mousedown($event)">
    <a @click.stop="reduce()">
      <i class="iconfont icon-reduce"></i>
    </a>
    <input
      id="processRange"
      :min="min"
      :max="max"
      type="range"
      v-model="size"
      :step="step"
      @change="getRange()"
      :style="{background: '-webkit-linear-gradient(top, red, red) 0% 0% / '+ size*100/max +'% 100% no-repeat',width:width+'px'}"
    />

    <span class="slider-number">{{size}}</span>
    <a @click.stop="add()">
      <i class="iconfont icon-solid_add"></i>
    </a>
  </div>
</template>

<script>
export default {
  name: 'slider',
  props: {
    // 最大值
    max: {
      type: Number,
      default: 10
    },
    // 最小值
    min: {
      type: Number,
      default: 0
    },
    // 当前位置
    rate: {
      type: Number,
      default: 4
    },
    // 移动的距离
    step: {
      type: Number,
      default: 1
    },
    // 组件宽度
    width: {
      type: String,
      default: '100px'
    }
  },
  data() {
    return {
      size: this.rate
    }
  },

  computed: {},

  created() {},
  watch: {
    size() {
      this.$emit('getRange', this.size)
    }
  },
  methods: {
    mousedown(e) {
      e.stopPropagation() //  阻止事件冒泡
    },
    add() {
      if (this.size < this.max) {
        this.size++
      }
    },
    reduce() {
      if (this.size > this.min) {
        this.size--
      }
    },
    getRange() {
      this.$emit('getRange', this.size)
    }
  }
}
</script>
<style lang='scss' scoped>
.slider-main {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .slider-number {
    width: 20px;
    margin: 0 3px;
    cursor: pointer;
  }
  .reduce-btn,
  .add-btn {
    background: rgb(206, 81, 36);
    font-size: 14px;
    min-width: 15px;
    min-height: 15px;
    line-height: 14px;
    border-radius: 50%;
    text-align: center;
    display: block;
    color: #fff;
    cursor: pointer;
  }

  input[type='range'] {
    -webkit-appearance: none;
    /*去除默认样式*/
    background-color: orangered;
    -webkit-appearance: none;
    height: 5px;
    padding: 0;
    border: none;
    /*input的长度为80%，margin-left的长度为10%*/
  }
  input[type='range']::-webkit-slider-thumb {
    -webkit-appearance: none;
    /*去除默认样式*/
    cursor: default;
    top: 0;
    height: 15px;
    width: 15px;
    transform: translateY(-4px);
    background: #fff;
    border-radius: 50%;
    border: 1px solid #000;
  }
  input[type='range']::-webkit-slider-runnable-track {
    /*轨道*/
    height: 5px;
    background: rgba(0, 0, 0, 0.2);
    border-radius: 10px;
  }
}
</style>