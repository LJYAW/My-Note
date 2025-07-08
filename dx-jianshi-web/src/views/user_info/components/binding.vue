<!--
 * @Author: zll
 * @Date: 2020-12-14 14:14:28
 * @LastEditTime: 2020-12-14 14:26:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/components/binding.vue
-->
<template>
  <div>
    <model :id="'bingding'"
      @close="close">
      <div slot="body"
        class="d-flex flex-column">
        <div class='d-flex justify-content-center align-items-center flex-column'>
          <img src="../../../assets/images/user_info/bingding.jpeg" alt="" class="bingding">
          <p class="pb-20px">请使用微信扫一扫</p>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
export default {
  props: '',
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {
    close() {
      this.$emit('bingdingClose')
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
#bingding {
  /deep/.modal-wrap {
    width: 300px;
    height: 260px;
  }
  .bingding {
    object-fit: cover;
  }
}
</style>
