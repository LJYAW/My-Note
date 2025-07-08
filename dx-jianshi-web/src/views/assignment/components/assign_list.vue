<!--
 * @Author: your name
 * @Date: 2020-11-10 16:01:24
 * @LastEditTime: 2020-11-19 18:25:46
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/assign_platform/assign_list.vue
-->
<template>
  <div class="assign-list" @click="assignListClick">
    <el-card shadow="hover">
      <img :src="list.cover_pic_url" @error="imgError()" />
      <div class="assign-list-info">
        <div class="assign-list-title d-flex align-items-center">
          <p class="assign-title text-ellipsis ">{{list.title}}</p>
          <span v-if="list.status == '已结束'" style="width: 55px" class="fz-12px fc-6d ml-auto d-block">{{list.status}}</span>
        </div>
        <p class="assign-list-intro text-ellipsis-2">{{list.intro_content}}</p>
        <HR style="FILTER: progid:DXImageTransform.Microsoft.Glow(color=#987cb9,strength=10)" color=#eaeaea SIZE=1 />
        <div class="assign-time">
          <span>任务时间：</span>
          <span>{{list.start_date}}～{{list.end_date}}</span>
        </div>
      </div>
    </el-card>
  </div>

</template>

<script>
import { imgError } from '../js/imgError'
export default {
  name: 'assignList',
  mixins: [imgError],
  props: {
    list: {
      type: Object
    },
    click: {
      type: Function
    }
  },
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {
    assignListClick() {
      this.$emit('click')
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.assign-list {
  width: 480px;
  background-color: #fff;
  margin-bottom: 36px;
  cursor: pointer;
  /deep/ .el-card__body {
    padding: 0;
  }
  img {
    width: 480px;
    height: 270px;
    background-color: #e8e8e8;
  }
  .assign-list-info {
    padding: 3px 19px;
    font-size: 14px;
    color: #101010;
    .assign-list-title {
      margin-top: 3px;
      .assign-title {
        width: 100%;
        display: inline-block;
        height: 27px;
        line-height: 27px;
        font-size: 18px;
      }
      .assign-get {
      }
    }
    .assign-list-intro {
      margin: 7px 0 14px;
      height: 36px;
      line-height: 18px;
      color: rgba(153, 153, 153, 100);
      font-size: 12px;
      text-align: justify;
      &:after {
        display: inline-block;
        content: '';
        overflow: hidden;
        height: 0;
      }
    }
    hr {
      color: rgba(234, 234, 234, 100);
    }
    .assign-time {
      margin: 10px 0;
      span:first-child {
        font-size: 12px;
      }
      span:last-child {
        font-size: 12px;
        color: #999;
      }
    }
  }
}
</style>
