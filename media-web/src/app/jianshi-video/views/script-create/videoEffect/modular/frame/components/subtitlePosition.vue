<!--
 * @Author: your name
 * @Date: 2021-07-30 10:13:54
 * @LastEditTime: 2021-09-26 16:50:19
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoEffect/modular/frame/components/subtitlePosition.vue
-->
<template>
  <div class="host">
    <el-form-item label="字幕位置:" label-width="90px" class="subtitle-position">
      <div>
        <div class="subtitle">
          <img :src="defaultImg" alt="" class="defaultImg">
          <span class="subtitle-title">当前字幕距底部距离为{{ subtitle.margin_bottom * 100 }}%</span>
          <a class="edit-title" @click="editSubtitle()">修改字幕位置</a>
        </div>
      </div>
    </el-form-item>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="450px">
      <SubtitleDialog :subtitle="subtitle.margin_bottom" @subtitleFun="subtitleFun" />
    </base-dialog>
  </div>
</template>

<script>
import SubtitleDialog from '../model/subtitleModels'
export default {
  components: {
    SubtitleDialog
  },
  props: {

  },
  data() {
    return {
      dialogVisible: false,
      defaultImg: require('@/assets/images/createVideo/default_video.jpg')
    }
  },
  computed: {
    // 字幕位置
    subtitle() {
      return this.$store.state.jianshi.effectData.subtitle
    }
  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    // 更新字幕位置数据
    subtitleFun(data) {
      this.dialogVisible = false
    },
    // 修改字幕位置
    editSubtitle() {
      this.dialogVisible = true
    }
  }
}
</script>

<style scoped lang="scss">
.subtitle {
  display: flex;
  align-items: center;

  .subtitle-title {
    font-size: 14px;
    color: #999;
    margin-left: 30px;
    min-width: 200px;
  }

  .edit-title {
    font-size: 14px;
    color: #5675e8;
    margin-left: 60px;
    min-width: 100px;
  }
}

.defaultImg {
  width: 45px;
  height: 80px;
}
</style>
