<!--
 * @Author: your name
 * @Date: 2021-07-26 18:30:10
 * @LastEditTime: 2021-09-28 11:19:10
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/materialList/addMaterial/index.vue
-->
<template>
  <div class="add-material-wrap">
    <el-button class="add-btn" @click.native="addMaterail">+ 点击添加素材段落</el-button>
    <p class="duration-wrap">
      当前视频段落总计时长&nbsp;
      <span>{{ estimate_duration ? estimate_duration.toFixed(0) : '0' }}s</span>
      <span v-if="maxSize != '810' && estimate_duration.toFixed(0) > maxTime">（视频已超长）</span>
    </p>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  components: {

  },
  props: {

  },
  data() {
    return {

    }
  },
  computed: {
    ...mapState('jianshi', ['maxSize', 'maxTime', 'img_list', 'text_list', 'estimate_duration', 'subTitle_list'])
  },
  watch: {
    img_list: {
      handler(val, oldVal) {
        if (val && val.length) this.setStateEstimateDuration(val)
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    // 回显视频总时长
    setStateEstimateDuration(val) {
      let duration = 0
      val.forEach(item => {
        duration += item.duration
      })
      this.$store.commit('jianshi/SET_ESTIMATE_DURATION', duration / 1000)
    },
    addMaterail() {
      // 判断文本素材和图片素材length值是否相同，不同就只添加图片
      if (this.img_list.length === this.text_list.length) {
        const text_list = JSON.parse(JSON.stringify(this.text_list))
        text_list.push({ text: '' })
        this.$store.commit('jianshi/SET_TEXT_LIST', text_list)

        const subTitle = JSON.parse(JSON.stringify(this.subTitle_list))
        subTitle.push({ title: '', show: false })
        this.$store.commit('jianshi/SET_SUBTITLEDATA', subTitle)
      }

      const img_list = JSON.parse(JSON.stringify(this.img_list))
      img_list.push({
        duration: 0,
        id: null,
        resource_url: '',
        type: ''
      })
      this.$store.commit('jianshi/SET_IMG_LIST', img_list)
    }
  }
}
</script>

<style scoped lang="scss">
.add-material-wrap {
  display: flex;

  .add-btn {
    flex: 1;
    height: 40px;
    border: 1px solid #5675e8;
    border-radius: 4px;
    color: #5675e8;
    margin-right: 20px;
  }

  .duration-wrap {
    width: 342px;
    color: #000;
    opacity: .2;
    display: flex;
    align-items: center;
    justify-content: center;

    span {
      font-weight: 500;
    }
  }
}
</style>
