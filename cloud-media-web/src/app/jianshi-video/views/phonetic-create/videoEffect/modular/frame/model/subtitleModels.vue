<!--
 * @Author: your name
 * @Date: 2020-12-22 18:55:20
 * @LastEditTime: 2021-09-27 12:06:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/intellect_create/video_create/video_models/subtitle_models.vue
-->
<template>
  <div class="subtitle-wrap">
    <div class="subtitle-box">
      <div class="img-wrap">
        <img :src="defaultImg" alt="">
        <span class="font-position" :style="{bottom: bottom + '%'}">默认字幕显示位置示例</span>
      </div>
      <div class="position-box">
        <span>当前字幕距底部距离为</span>
        <el-input-number
          v-model="bottom"
          class="bottom"
          :min="0"
          :max="92"
          placeholder=""
          size="mini"
          @change="bottomChange"
        />
        <span>%</span>
      </div>
      <div class="btns">
        <el-button size="small" type="primary" @click="determine()">确定</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SubtitleModels',
  components: {},
  props: {
    subtitle: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      defaultImg: require('@/assets/images/createVideo/default_video.jpg'),
      bottom: 30,
      maxBtm: 93
    }
  },

  computed: {},

  watch: {
    bottom() {
      this.bottom = Math.min(this.bottom, this.maxBtm)
    }
  },

  created() {
    this.bottom = this.subtitle * 100
  },

  mounted() {},

  methods: {
    bottomChange() {
      this.bottom = Math.min(this.bottom, this.maxBtm)
    },
    determine() {
      this.$store.commit('jianshi/set_subtitlePosition', this.bottom / 100)
      this.$emit('subtitleFun')
    }
  }
}
</script>

<style scoped lang="scss">
.subtitle-wrap {
  height: 450px;
  width: 80%;
  margin: 0 auto;

  .subtitle-box {
    width: 250px;
    margin: 0 auto;

    .img-wrap {
      width: 193px;
      height: 343px;
      margin: 0 auto;
      position: relative;

      .font-position {
        position: absolute;
        bottom: 5%;
        left: 14px;
        color: white;
        background: rgba(0, 0, 0, .6);
        padding: 3px 8px;
        font-size: 14px;
      }
    }

    .position-box {
      display: flex;
      align-items: center;
      margin: 16px auto;

      span {
        color: #1a1a1a;
        font-size: 12px;
        font-weight: 500;
      }

      .bottom {
        width: 100px;
        height: 30px;
        margin: 0 5px 0 5px;

        ::v-deep input {
          height: 30px;
          border: 0;
          background: #f7f7f7;
        }
      }
    }

    .btns {
      margin-top: 20px;
      text-align: center;
    }
  }
}
</style>
