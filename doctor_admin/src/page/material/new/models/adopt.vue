<!--
 * @Author: zll
 * @Date: 2020-12-03 15:33:40
 * @LastEditTime: 2020-12-03 16:58:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /weijian_cms_web/src/page/material/new/models/adopt.vue
-->
<template>
  <div class="adopt_wrap">
    <video src="http://vd-drm.mtq.tvm.cn/video/DaTangLeiYinSi/2019/04/26/LiaoNingNorth_1500000_20190426_1556293984948_0.mp4" controls="controls"></video>
    <div class="btns">
      <el-button round size="small" @click="adopt()">审核通过</el-button>
      <el-button type="primary" round size="small" @click="no_adopt()">审核不通过</el-button>
    </div>
  </div>
</template>

<script>
import noAdopt from './no_adopted'

export default {
  name: '',
  props: {
    layerid: {
      type: String,
      default: ''
    }
  },
  data() {
    return {}
  },
  components: { noAdopt },
  computed: {},
  watch: {},
  methods: {
    adopt() {
      this.$layer.close(this.layerid)
    },
    no_adopt() {
      let obj = {
        content: noAdopt,
        title: '审核不通过',
        width: '600px',
        height: '500px',
        data: {}
      }
      this.$layerIfream(obj)
      this.$layer.close(this.layerid)
    }
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
.adopt_wrap {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
  video {
    width: 100%;
    height: 300px;
  }
  .btns {
    text-align: center;
    margin-top: 50px;
  }
}
</style>
