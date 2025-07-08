<!--
 * @Author: your name
 * @Date: 2021-09-01 15:58:07
 * @LastEditTime: 2021-09-17 16:23:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/app/jianshi-video/material-center/material-tab/AdditionalMaterial/MaterialImg.vue
-->
<template>
  <base-card :show-mark="false" class="img-item">
    <div slot="img">
      <base-image :src="materialData.file_url" />
      <div class="btn-wrap">
        <svg-icon icon-class="delete-red" @click.native="deleteImg" />
      </div>
    </div>
  </base-card>
</template>

<script>
export default {
  components: {

  },
  props: {
    materialData: {
      type: Object,
      default() {
        return {}
      }
    }
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
    deleteImg() {
      this.$emit('delete', this.materialData.uuid)
    }
  }
}
</script>

<style scoped lang="scss">
.img-item {
  margin: 0 12px;

  ::v-deep .image {
    border-radius: 4px;
    height: 0;
    padding-bottom: 56.25%;
    position: relative;
    background: #f7f8f9;

    &:hover {
      box-shadow: 0px 5px 10px 0px rgba(0, 0, 0, .1);

      .btn-wrap {
        opacity: 1;
        transition: all .4s;
      }
    }

    .el-image {
      position: static;

      img {
        position: absolute;
        top: 0;
        width: 100%;
        height: 100%;
      }

      .image-slot {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
      }
    }

    .btn-wrap {
      position: absolute;
      top: 5px;
      right: 7px;
      opacity: 0;
      transition: all .4s;

      svg {
        width: 18px;
        height: 18px;
      }
    }

  }
}
</style>
