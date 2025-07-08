<!--
 * @Author: your name
 * @Date: 2020-11-11 17:03:11
 * @LastEditTime: 2020-12-04 19:48:43
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/index.vue
-->
<!--
 * @Author: zzx
 * @Date: 2020-07-22 17:50:57
 * @LastEditTime: 2020-07-22 19:10:13
 * @FilePath: /weijian_web/src/views/user_info/index.vue
--> 
<template>
  <div class="user-info-content">

    <div class="container d-flex">
      <div class="user-right-pad">
        <usderHeader />

        <rightMenu />
      </div>

      <div class="user-left-pad">

        <div class="user-pad flex-1">
          <router-view />
        </div>

      </div>
    </div>

  </div>
</template>

<script>
import usderHeader from './user_header'
import rightMenu from './right_menu'
export default {
  name: 'user',
  props: {},
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {},
  components: {
    usderHeader,
    rightMenu
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss">
@import './user_info.scss';
</style>
