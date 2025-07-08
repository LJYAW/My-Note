<!--
 * @Author: zzx
 * @Date: 2020-10-27 14:53:26
 * @LastEditTime: 2020-12-28 15:50:59
 * @FilePath: /zhijian_web/src/views/intellect_create/components/img_wrap.vue
-->
<template>
  <div
    @click="selectImg"
    :class="['box mr-7px d-flex justify-content-center source-img-wrap',
    {'active': active},{'imgError': img_error}]">
    <img :src="img_url" @error="imgError()" />

    <label class="el-upload-list__item-status-label">
      <i class="iconfont iconqueding d-block"></i>
    </label>
  </div>
</template>

<script>
export default {
  name: 'VImg',
  props: {
    img_url: {
      type: String,
      default: ''
    },
    active: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      img_error: false
    }
  },
  computed: {},
  watch: {},
  methods: {
    selectImg() {
      this.$emit('selectImg')
    },
    imgError(item) {
      this.img_error = true
      let img = event.srcElement
      let img_error_url = require('@/assets/images/img_error.svg')
      img.src = img_error_url
      img.onerror = null // 防止闪图
      this.$emit('imgError')
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.box {
  min-width: 110px;
  min-height: 62px;
  max-width: 110px;
  max-height: 62px;
  overflow: hidden;
  background-color: #e5e5e5;
  border: 2px solid transparent;
  text-align: center;
  position: relative;
  transition: all 0.3s;
  border-radius: 3px;

  &:hover {
    border: 2px solid $c;

    .el-upload-list__item-status-label {
      display: block;
    }
  }

  .delete-icon {
    position: absolute;
    top: 6px;
    left: 6px;
  }

  &.active {
    border: 2px solid $c;

    .el-upload-list__item-status-label {
      display: block;
    }
  }

  .el-upload-list__item-status-label {
    position: absolute;
    right: -17px;
    top: -7px;
    width: 46px;
    height: 26px;
    background: $c;
    text-align: center;
    transform: rotate(45deg);
    box-shadow: 0 1px 1px #ccc;

    &.active {
      display: block;
    }

    i {
      color: #fff;
      font-size: 12px;
      margin-top: 12px;
      transform: rotate(-45deg);
    }
  }

  img {
    height: 100%;
  }
}

.list {
  overflow: hidden;
  margin-bottom: 25px;
}

.source-wrap {
  margin-bottom: 36px;
}

.active {
  color: $c;
}

.el-input-group__append {
  background-color: $c;
  height: 35px;
  border: none;
  border-radius: 0 3px 3px 0;

  .el-button {
    height: 34px;
    padding: 0 22px;
    text-align: center;
  }
}

.el-input-group {
  .el-input__inner {
    border-radius: 3px 0 0 3px;
  }
}

.source-img-wrap {
  &.imgError {
    img {
      width: 60px;
      height: 60px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }
  }

  img {
    height: 100%;
    object-fit: contain;
  }
}
</style>
