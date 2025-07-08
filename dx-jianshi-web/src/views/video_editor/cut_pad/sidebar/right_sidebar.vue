<template>
  <div class="right-sidebar-wrap">
    <ul class="track-list">
      <li class="item mt-27px">
        <i class="iconfont iconstaff"></i>
      </li>
      <li class="item pt-27px">
        <i class="iconfont  iconshipin2"></i>
      </li>
      <li class="item pt-40px">
      </li>
      <li class="item pt-40px">
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'trackContent',
  data() {
    return {}
  }
}
</script>

<style lang="scss">
.right-sidebar-wrap {
  display: flex;
  flex-direction: column;
  background: white;
  z-index: 9;
  box-shadow: 6px 0px 10px -6px rgba(0, 0, 0, 0.3);

  .track-list {
    width: 0px;
    height: 100px;
    opacity: 0;
    text-align: center;
    // display: none;
    .item {
      margin-bottom: 20px;
      color: #999;
      .iconfont {
        font-size: 20px;
      }
    }
  }
}
</style>