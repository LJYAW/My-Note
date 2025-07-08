<!--
 * @Author: your name
 * @Date: 2020-12-22 11:39:08
 * @LastEditTime: 2020-12-22 11:48:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/modal_handel_title.vue
-->
<template>
  <div class="handel-title">
    <span
      v-for="(item,index) in typeArr"
      :key="index"
      :class="{'is-active': showType == item}"
      @click="showType = item">{{item}}</span>
  </div>
</template>

<script>
export default {
  props: {
    typeArr: {
      type: Array,
      default: []
    }
  },
  data() {
    return {
      showType: ''
    }
  },
  computed: {},
  watch: {
    showType: {
      handler(val) {
        this.$emit('showTypeHandler', val)
      },
      // 一进页面就执行
      immediate: true
    }
  },
  methods: {},
  components: {},
  created() {
    this.showType = this.typeArr[0]
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
.handel-title {
  display: flex;
  span {
    background: $c;
    display: block;
    display: block;
    border-radius: 3px;
    height: 32px;
    width: 100px;
    text-align: center;
    line-height: 32px;
    cursor: pointer;
    background: #e6e6e6;
    transition: all 0.3s;
    &.is-active {
      background: #c51b18;
      color: #fff;
    }
  }
}
</style>
