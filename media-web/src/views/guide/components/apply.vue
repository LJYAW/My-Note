<template>
  <div :class="bg" class="contentbox">
    <div class="inner">
      <div class="all_title">
        <div class="title"><span>企业级专属服务，个性化定制集成部署</span></div>
        <div class="desc">各功能可为企业Saas部署深度定制，助力企业高效管理媒资，输出价值内容。</div>
      </div>
      <div class="contactform">
        <div class="fdiv">
          <div class="label">公司名称</div>
          <input type="text">
        </div>
        <div class="list_flex justify_content">
          <div class="fdiv" style="width: 47%;">
            <div class="label">联系人</div>
            <input type="text">
          </div>
          <div class="fdiv" style="width: 47%;">
            <div class="label">联系电话</div>
            <input type="text">
          </div>
        </div>
        <div class="list_flex align_items" style="justify-content: center;">
          <div class="btn">立即提交</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    bg: {
      type: String,
      default() {
        return ''
      }
    }
  },
  data() {
    return {
    }
  },
  computed: {
  },
  watch: {

  },
  beforeDestroy() {

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
@import "../css/index.scss";

.contentbox {
  padding: 100px 0;

  &.bg {
    background-color: #333ed0;
    color: #fff;

    .all_title {
      color: rgba(255, 255, 255, 1);

      .desc {
        color: rgba(255, 255, 255, .7);
      }
    }
  }
}

.contactform {
  margin: 0 auto;
  max-width: 604px;
  padding-top: 30px;

  .fdiv {
    margin-top: 20px;

    .label {
      margin-bottom: 8px;
    }

    input {
      height: 34px;
      line-height: 34px;
      background: #f1f1f1;
      border-radius: 4px;
      border: 0;
      webkit-appearance: none;
      width: 100%;
      padding: 0 12px;
    }
  }
}
</style>
