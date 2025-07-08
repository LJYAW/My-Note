<!--
 * @Author: your name
 * @Date: 2021-07-29 15:10:52
 * @LastEditTime: 2021-09-02 11:02:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/video-detail/components/tools/person.vue
-->
<template>
  <div class="person-wrap">
    <div
      v-for="i in cont"
      :key="i.pic_url"
      class="person-item"
      @click="picToms(i.timestamp)"
    >
      <img :src="i.pic_url">
      <div class="name">{{ i.name }}</div>

    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    cont: {
      type: Array,
      default: () => []
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
    picToms(time) {
      console.log(time)
      this.$store.commit('videoStatus/TIME_LINE_CHANGE', time / 1000)
    }
  }
}
</script>

<style scoped lang="scss">
  .person-wrap {
    display: flex;
    flex-wrap: wrap;
    max-height: 70vh;
    // height: 800px;
    overflow: scroll;

    .person-item {
      flex-shrink: 0;
      width: 64px;
      // height: 64px;
      margin-right: 26px;
      margin-bottom: 20px;
      border-radius: 4px;
      cursor: pointer;

      .name {
        text-align: center;
        font-size: 12px;
      }

      img {
        width: 64px;
        height: 64px;
        object-fit: cover;
      }

      &:nth-child(5n+5) {
        margin-right: 0;
      }
    }
  }
</style>
