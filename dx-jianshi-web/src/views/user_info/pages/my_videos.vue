<!--
 * @Author: zzx
 * @Date: 2020-07-22 19:05:09
 * @LastEditTime: 2020-12-05 17:03:03
 * @FilePath: /weijian_web/src/views/user_info/pages/my_videos.vue
--> 
<template>
  <div>

    <userNav :nav_list="nav_list"
      @getActiveId="getActiveId"
      :activeId="active_id" />

    <myCreatedVideos :activeId="active_id" />
  </div>
</template>

<script>
import userNav from '../components/nav'
import myCreatedVideos from './my_videos/my_created_videos'
import myCutVideos from './my_videos/my_cut_videos'
export default {
  props: {},
  data() {
    return {
      active_id: '0',
      nav_list: [
        {
          name: '我创作的视频',
          activeId: '0'
        }
        // {
        //   name: '我剪辑的视频',
        //   activeId: '1'
        // }
      ]
    }
  },
  computed: {},
  watch: {},
  methods: {
    getActiveId(id) {
      this.active_id = id
    }
  },
  components: {
    userNav,
    myCreatedVideos,
    myCutVideos
  },
  created() {},
  mounted() {}
}
</script>

<style lang="scss" scoped>
</style>
