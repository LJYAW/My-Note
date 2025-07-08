<!--
 * @Author: your name
 * @Date: 2020-11-13 14:19:50
 * @LastEditTime: 2020-12-02 15:04:29
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/components/card_list/index.vue
-->
<template>
  <div class="card-list">
    <el-row :gutter="10">
      <el-col :span="6" v-for="(item, index) in list" :key="index" :style="{ marginBottom: '10px'}" class='item'>
        <el-card :body-style="{padding: '0px'}" v-if="item.type == '上传中'">
          <div class="img-wrap upload-img">
            <img src="../../assets/images/user_info/banner.png">
            <slot name="progress"></slot>
          </div>
          <div class="card-content">
            <div class="mb-58px">
              <p>正在上传中</p>
            </div>
          </div>
        </el-card>
        <el-card v-else :body-style="{padding: '0px'}" shadow="hover" @click.native="handerClick(item)">
          <div class="img-wrap">
            <slot name="img" :item="item" :index='index'></slot>
          </div>
          <div class="card-content">
            <div class="mb-18px">
              <slot name="title" :item="item"></slot>
            </div>
            <div class="des flex-column">
              <slot name="details" :item="item"></slot>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
export default {
  props: {
    list: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {}
  },
  computed: {},
  watch: {},
  methods: {
    handerClick(item) {
      this.$emit('handerClick', item)
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style lang="scss">
.card-list {
  .item {
    position: relative;
    transition: all 0.25s ease-in;
    .el-card {
      border: none;
    }
    &:hover {
      transform: translateY(-10px);
      .el-card {
        box-shadow: 0px 2px 20px 0px rgba(0, 0, 0, 0.16);
      }
      .icon-wrap {
        opacity: 1;
        .iconfont {
          transform: scale(1);
        }
      }
      .bofang {
        opacity: 1;
      }
    }
  }
  .img-wrap {
    height: 135px;
    background: #dedede;
    position: relative;
    .bofang {
      position: absolute;
      left: 0;
      top: 0;
      background: rgba(0, 0, 0, 0.2);
      opacity: 0;
      .iconfont {
        cursor: pointer;
      }
    }
    .iconyinfu {
      display: inline-block;
    }
    .iconbofang {
      position: absolute;
      right: 6px;
      bottom: 6px;
    }
    img {
      height: 100%;
      width: 100%;
      object-fit: contain;
    }
    span {
      padding: 4px 10px;
      background: #000000;
      border-radius: 13px;
      color: #fff;
      font-size: 12px;
      line-height: 18px;
      position: absolute;
      left: 6px;
      bottom: 6px;
      z-index: 9;
    }
  }
  .upload-img {
    img {
      object-fit: cover;
    }
    .el-progress {
      width: 90%;
      position: absolute;
      top: 45%;
      left: 5%;
    }
    .el-progress-bar__outer {
      height: 2px !important;
      background: rgba(0, 0, 0, 0.2);
      border-radius: 1px;
      overflow: visible;
      .el-progress-bar__inner {
        background: #ff9e40;
        .el-progress-bar__innerText {
          font-size: 14px;
          color: #ff9e40;
          margin-top: 13px;
        }
      }
    }
  }
  .icon-wrap {
    text-align: center;
    position: absolute;
    top: 8px;
    padding: 0 10px;
    opacity: 0;
    transition: all 0.3s;
    border-radius: 5px;
    z-index: 9;
    .icondelect {
      background-color: rgba($color: #fff, $alpha: 0.8);
    }
    .iconfont {
      background: $c;
      border-radius: 50%;
      width: 35px;
      height: 35px;
      line-height: 33px;
      display: block;
      text-indent: 2px;
    }
    .iconshanchu {
      font-size: 16px;
      line-height: 35px;
    }
  }
  .card-content {
    line-height: 20px;
    padding: 12px;
    font-size: 13px;
    font-weight: 400;
    text-align: left;
    color: #333333;
    display: flex;
    flex-direction: column;

    .title {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      overflow: hidden;
      height: 40px;
      word-break: break-all;
    }
    .des {
      margin-top: auto;
      display: flex;
      justify-content: space-between;
      font-size: 12px;
      color: #999;
    }
  }
}
</style>
