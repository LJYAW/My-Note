<!--
 * @Author: your name
 * @Date: 2021-09-09 10:28:14
 * @LastEditTime: 2021-09-28 11:27:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/views/subtitle-create/videoOption/index.vue
-->
<template>
  <div class="video-option">
    <div class="video-option-title">视频选项</div>
    <div class="setting-box">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="视频比例">
          <el-radio-group v-model="form.videoScale">
            <el-radio
              v-for="item in video_proportion"
              :key="item.type"
              class=""
              :label="item.type"
            >
              <svg-icon :icon-class="form.videoScale==item.type?`${item.icon}-active`:item.icon" />
              <span class="video-name">{{ item.name }}{{ item.type }}</span>
            </el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

export default {
  components: {

  },
  props: {

  },
  data() {
    return {
      form: {
        videoScale: '16:9'
      },
      // 视频比例数据
      video_proportion: [{
        type: '16:9',
        name: '横版',
        icon: 'hengping',
        val: 1
      },
      {
        type: '9:16',
        name: '竖版',
        icon: 'shuping',
        val: 2
      }
      ]
    }
  },
  computed: {
  },
  watch: {
    'form.videoScale': {
      handler(newName, oldName) {
        console.log('🚀 ~ file: index.vue ~ line 65 ~ handler ~ newName', newName)
        if (newName) this.$store.commit('jianshi/set_phoneticVideoSale', newName)
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
  }
}
</script>

<style scoped lang="scss">
.video-option {
  padding: 20px 20px 0 20px;
  box-sizing: border-box;
  background: #fff;
  min-width: 1220px;

  .video-option-title {
    font-size: 18px;
    font-weight: 600;
    color: #404040;
  }

  .setting-box {
    margin-top: 20px;

    .video-name {
      margin-left: 5px;
    }

    .el-form-item {
      margin-bottom: 0;

      ::v-deep .el-form-item__label {
        text-align: left;
      }

      .el-radio {
        min-width: 100px;
      }
    }

    .el-form-item__label {
      font-size: 14px;
      padding-right: 0;
      color: rgba(69, 69, 69, 1);
      font-weight: 400;
      text-align: left;
    }
  }
}
</style>
