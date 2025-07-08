<!--
 * @Author: zll
 * @Date: 2020-12-07 15:56:20
 * @LastEditTime: 2020-12-14 15:38:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/ material_library/index.vue
-->
<template>
  <div class="contant_wrap container">
    <div class="item_list">
      <videos />

      <!-- <el-tabs v-model="activeName" @tab-click="handleClick"> -->
      <!-- <el-tab-pane label="电视台素材" name="tv">
          <tv />
        </el-tab-pane> -->
      <!-- <el-tab-pane label="视频素材" name="video">
          <videos />
        </el-tab-pane> -->
      <!-- <el-tab-pane label="图片素材" name="img">
          <imgs />
        </el-tab-pane>
        <el-tab-pane label="我的收藏夹" name="myself">
          <span slot="label">
            <img src="../../assets/images/material_library/favorite.png" alt="" class="labelimg">
            <span>我的收藏夹</span>
          </span>
          <myself />
        </el-tab-pane> -->
      <!-- </el-tabs> -->
    </div>
  </div>
</template>

<script>
// import tv from './material_tab/tv'
import videos from './material_tab/videos'
// import imgs from './material_tab/imgs'
// import myself from './material_tab/myself'
export default {
  props: {},
  data() {
    return {
      activeName: 'video'
    }
  },
  computed: {},
  watch: {},
  methods: {
    handleClick() {}
  },
  components: {
    // tv,
    videos
    // imgs,
    // myself
  },
  mounted() {},
  created() {}
}
</script>

<style lang='scss' scoped>
.contant_wrap {
  position: relative;
  width: 100%;
  .item_list {
    // width: 100%;
    // position: absolute;
    // top: -29px;
    // height: 57px;
    // background: #ffffff;
    // border-radius: 7px;
    // box-shadow: 0px 2px 7px 0px rgba(0, 0, 0, 0.16);
    // background: -webkit-linear-gradient(left, #fffdbb, #c2fff1); /* Safari 5.1 - 6.0 */
    // background: -o-linear-gradient(left, #fffdbb, #c2fff1); /* Opera 11.1 - 12.0 */
    // background: -moz-linear-gradient(left, #fffdbb, #c2fff1); /* Firefox 3.6 - 15 */
    // background: linear-gradient(to right, #fffdbb, #c2fff1, #fffdbb, #ffffff); /* 标准的语法（必须放在最后） */
    // opacity: 0.9;
    // filter: alpha(opacity=90);
    // filter: blur(1px);
    // .labelimg {
    //   width: 21px;
    //   height: 17px;
    //   margin-right: 10px;
    // }
    // /deep/ .el-tabs__nav {
    //   width: 100%;
    //   height: 100%;
    //   line-height: 57px;
    //   .el-tabs__item {
    //     text-align: center;
    //     padding: 0 125px;
    //     padding-left: 50px;
    //   }
    // }
    /deep/ .el-tabs__nav {
      line-height: 57px;
      margin-left: 30px;
    }
    /deep/ .el-tabs__nav-wrap::after {
      background-color: transparent;
    }
  }
}
</style>
