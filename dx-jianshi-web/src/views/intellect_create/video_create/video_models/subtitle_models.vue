<!--
 * @Author: your name
 * @Date: 2020-12-22 18:55:20
 * @LastEditTime: 2020-12-22 20:22:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/intellect_create/video_create/video_models/subtitle_models.vue
-->
<template>
  <div>
    <model :id="'subtitleModels'"
      @close="close" class="model-sm">
      <div slot="title">修改字幕显示位置</div>
      <div slot="body"
        class="d-flex flex-column subtitle_wrap">
        <div class="text-center">
          <div class="img-wrap">
            <img src="../../../../assets/images/default_video.jpg" alt="">
            <span class="font-position" :style="{bottom: bottom + '%'}">默认字幕显示位置示例</span>
          </div>
          <div class="mt-20px">
            <span class="fz-12px">当前字幕距底部距离为</span>
            <el-input v-model="bottom" @change="bottomChange" type="number" min="0" max="93" placeholder="" style="width:70px" class="ml-5px mr-5px" size="mini"></el-input>
            <span>%</span>
          </div>
          <div class="mt-10px">
            <el-button size="small" round type="primary" @click="determine()">确定</el-button>
          </div>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
export default {
  name: 'subtitleModels',
  props: [],
  data() {
    return {
      bottom: 30,
      maxBtm: 93,
      subtitle: ''
    }
  },
  components: {},

  computed: {},

  watch: {
    bottom() {
      this.bottom = Math.min(this.bottom, this.maxBtm)
    }
  },

  methods: {
    determine() {
      this.$store.commit('modalHidden', 'subtitleModels')
      this.$emit('subtitleFun', this.bottom)
    },
    bottomChange() {
      this.bottom = Math.min(this.bottom, this.maxBtm)
    },
    close() {
      this.$emit('subtitleClose')
    }
  },

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.subtitle_wrap {
  margin: 0 auto;
  padding-bottom: 20px;
  display: flex;
  .img-wrap {
    width: 193px;
    height: 343px;
    margin: 0 auto;
    position: relative;
    img {
    }
    .font-position {
      position: absolute;
      bottom: 5%;
      left: 14px;
      color: white;
      background: rgba(0, 0, 0, 0.6);
      padding: 3px 8px;
    }
  }
}
</style>