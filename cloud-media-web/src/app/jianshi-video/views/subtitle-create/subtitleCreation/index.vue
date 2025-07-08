<!--
 * @Author: your name
 * @Date: 2021-08-30 17:33:45
 * @LastEditTime: 2021-09-23 15:02:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/create-center/audioSubtitle/index.vue
-->
<template>
  <div class="creation-subtitle">
    <contentEdit />
  </div>
</template>

<script>
import contentEdit from './contentEdit/index'
export default {
  name: 'SubTiltleCreate',
  components: {
    contentEdit
  },
  props: {

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
.creation-subtitle {
  width: 100%;
  margin-top: 15px;
  padding: 20px;
  box-sizing: border-box;
  border-radius: 4px;
  background: #fff;
  margin-bottom: 20px;
  min-width: 1220px;

  .title-box {

    .subtitletitel {

      ::v-deep input {
        padding-right: 85px;
        background: #f7f8f9;
        border-radius: 4px;
        border-color: transparent;
        padding-left: 20px;

        &::placeholder {
          color: #c2c4c7;
        }
      }

      ::v-deep .el-input__suffix {
        right: 20px;

        .el-input__count-inner {
          font-size: 14px;
          font-weight: 400;
          color: #5d5d5d;
          line-height: 14px;
          padding: 0;
          background: none;
        }
      }
    }

    .label {
      font-size: 18px;
      font-weight: 600;
      color: #404040;
      margin-bottom: 20px;
    }

    ::v-deep  .el-input__inner {
      border-color: transparent;
      background: rgba(247, 248, 249, 1);
      // color: rgba(194, 196, 199, 1);
      color: #404040;
      font-size: 14px;
      font-weight: 400;
    }
  }
}
</style>
