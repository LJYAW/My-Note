<!--
 * @Author: your name
 * @Date: 2021-08-17 18:46:21
 * @LastEditTime: 2021-08-23 11:00:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/components/base/card.vue
-->
<template>
  <div class="base-card">

    <div class="image">
      <slot name="img" />

      <div v-if="showMark" class="mark">
        <slot name="mark" />
      </div>
    </div>

    <slot name="des" />
  </div>
</template>

<script>
export default {
  name: 'Card',
  components: {

  },
  props: {
    // 是否 hover 显示遮罩
    showMark: {
      type: Boolean,
      default: true
    },
    radius: {
      type: Number,
      default: 4
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
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.base-card {
  border-radius: 4px;
  // width: 342px;
  position: relative;

  .image {
    width: 100%;
    height: 192px;
    overflow: hidden;
    background-color: #232323;
    margin-bottom: 10px;
    cursor: pointer;
    transition: all .4s;

    img {
      border: none;
    }

    .mark {
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 192px;
      opacity: 0;
      transition: all .4s;
    }

    &:hover {
      // transform: translateY(-10px);
      // box-shadow: 0px 10px 20px 0px rgba(0, 0, 0, .2);

      .mark {
        transition: all .4s;
        background: rgba(0, 0, 0, .4);
        opacity: 1;
      }
    }
  }

  .text {
    font-size: 14px;
    color: #404040;
    font-weight: 600;
    overflow: hidden;
    text-overflow: ellipsis;
    -webkit-line-clamp: 2;
    box-orient: vertical;
    word-break: break-all;

  }
}
</style>
