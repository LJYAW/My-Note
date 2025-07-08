<!--
 * @Author: your name
 * @Date: 2021-07-28 10:45:02
 * @LastEditTime: 2021-08-11 15:00:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/select/tabItem.vue
-->
<template>
  <div class="tab-item">
    {{ val.name }}
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    val: {
      type: Object,
      default: () => {
        return {}
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
  created() {

  },
  mounted() {

  },
  methods: {

  }
}
</script>

<style scoped lang="scss">
.tab-item {
  flex-shrink: 0;
  //   width: 100%;
  height: 30px;
  line-height: 30px;
  padding: 0 10px;
  text-align: center;
  cursor: pointer;
  background: rgba(239,239,239,.8);
  font-size: 12px;
  color: #404040;

  &:hover {
    opacity: .5;
    color: #5675e8;
  }
}
</style>
