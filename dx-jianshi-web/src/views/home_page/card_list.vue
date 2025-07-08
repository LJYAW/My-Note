<!--
 * @Author: your name
 * @Date: 2020-11-23 16:30:30
 * @LastEditTime: 2020-11-24 15:01:04
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/home_page/card_list.vue
-->
<template>
  <div class="card-box">
    <div class="container">
      <div class="card-wrap d-flex justify-content-center align-content-center">
        <el-card shadow="hover"
          v-for="(item,index) in card_list"
          :key="index"
          class="flex-1 mx-32px">
          <div class="card text-center">
            <img :src="item.icon"
              class="mt-35px"
              width="60"
              height="60" />

            <p class="title fz-24px mt-25px mb-30px">{{item.title}}</p>
            <p class="des fc-999 text-left fz-14px"
              style="line-height: 2;">{{item.des}}</p>
          </div>
        </el-card>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  props: {},
  data() {
    return {
      card_list: [
        {
          icon: require('../../assets/images/homepage/page_1.png'),
          title: '海量视频资源',
          des: '汇聚全国上百家电视台，上千档栏目，上万集影视剧的正版授权资源包罗万象，应有尽有。',
        },
        {
          icon: require('../../assets/images/homepage/page_2.png'),
          title: 'AI视频帧搜索',
          des: '独创专利技术视频帧搜索，在海量视频中通过关键词、人脸识别、物体品牌识别，快速搜索到视频帧。',
        },
        {
          icon: require('../../assets/images/homepage/page_2.png'),
          title: '一键智能创作',
          des: '零基础上手，智能提取关键词匹配素材，一键智能短视频生成。',
        },
      ],
    }
  },
  computed: {},
  watch: {},
  methods: {},
  components: {},
  created() {},
  mounted() {},
}
</script>

<style scoped lang="scss">
.card-box {
  background: #ffffff;
  width: 100%;
  height: 260px;
  .card-wrap {
    position: relative;
    top: -130px;
    z-index: 300;
    width: 90%;
    height: 336px;
    margin: 0px auto;
  }
}
</style>
