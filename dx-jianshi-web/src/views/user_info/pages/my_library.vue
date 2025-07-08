<!--
 * @Author: zzx
 * @Date: 2020-07-22 19:24:14
 * @LastEditTime: 2020-12-08 14:55:28
 * @FilePath: /weijian_web/src/views/user_info/pages/my_library.vue
-->
<template>
  <div class="my-library">
    <userNav :nav_list="nav_list" @getActiveId="getActiveId" :activeId="active_id" />
    <myLibraryVideo :activeId="active_id" v-if="active_id == 'video' || active_id == 'video_begin' || active_id == 'video_end'" />
    <myLibraryImage :activeId="active_id" v-if="active_id == 'video_logo' || active_id == 'image'" />
    <myLibraryMusic :activeId="active_id" v-if="active_id == 'music'" />
  </div>
</template>

<script>
import cardList from '@/components/card_list/index'
import userNav from '../components/nav'
import myLibraryVideo from '../pages/my_library/my_library_video'
import myLibraryImage from '../pages/my_library/my_library_image'
import myLibraryMusic from '../pages/my_library/my_library_music'
export default {
  props: {},
  data() {
    return {
      active_id: 'video',
      nav_list: [
        {
          name: '视频',
          activeId: 'video'
        },
        {
          name: '图片',
          activeId: 'image'
        },
        {
          name: '角标',
          activeId: 'video_logo'
        },
        {
          name: '音乐',
          activeId: 'music'
        },
        {
          name: '片头',
          activeId: 'video_begin'
        },
        {
          name: '片尾',
          activeId: 'video_end'
        }
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
    myLibraryVideo,
    myLibraryImage,
    myLibraryMusic
  },
  created() {},
  destroyed() {},
  mounted() {}
}
</script>

<style lang="scss">
.my-library {
  .upload-btn {
    display: inline-block;
    background: #feeded;
    border-radius: 21px;
    padding: 10px 24px;
    font-size: 16px;
    font-weight: 600;
    color: $c;
    font-family: PingFang SC, PingFang SC-Semibold;
    outline: none;
    border: none;
  }
  .content {
    position: relative;
    .no-data {
      position: absolute;
      top: 80px;
      left: 50%;
      transform: translateX(-50%);
      p {
        color: #999;
      }
    }
  }
}
</style>
