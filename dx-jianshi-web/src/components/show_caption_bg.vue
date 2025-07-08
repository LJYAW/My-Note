<!--
 * @Author: zzx
 * @Date: 2020-07-06 11:20:28
 * @LastEditTime: 2020-12-22 11:48:11
 * @FilePath: /zhijian_web/src/components/show_caption_bg.vue
-->
<template>
  <div>
    <model :id="'showCaptionBg'" @close="close">
      <div slot="title">
        <hander-title @showTypeHandler="showTypeHandler" :typeArr="['简视推荐', '自定义上传']"></hander-title>
      </div>

      <div slot="body" class="d-flex flex-column">
        <!-- 简视推荐 -->
        <div v-if="showType == '简视推荐'" class="mb-50px caption_bg">
          <div class="d-flex flex-wrap">
            <div v-for="(item, index) in caption_bg.built_in" :key="index" @click="setCaptionId(item)" :class="['img-wrap', { active: active_id == item.id }]">
              <img :src="item.image_url" />
              <p class="sub-title">{{ item.name }}</p>
            </div>
          </div>
        </div>

        <div v-if="showType == '自定义上传'" class="mb-50px caption_bg">
          <div class="d-flex align-items-cente r">
            <upload button_name="上传图片" class="ml-10px" @getFileUrl="getFileUrl" />
          </div>

          <div class="d-flex flex-wrap  custom">
            <div
              v-for="(item, index) in caption_bg.custom"
              :key="index"
              @mouseover="showIcondelectIndex = index"
              @click="setCaptionId(item, true)"
              :class="['img-wrap', { active: active_id == item.id }]"
            >
              <img :src="item.image_url" />
              <p class="sub-title">{{ item.name }}</p>
              <i @click="deleteRsource(item)" class="iconfont icondelect cp"></i>
            </div>
          </div>
        </div>
        <p class="mb-20px fz-12px fc-999">注：视频模版用于遮挡视频底部信息并显示字幕，建议制作仅遮挡底部的1920x1080的png图</p>

        <div class="text-center mb-30px">
          <el-button type="primary" size="mini" round @click="setActiveId()">确定</el-button>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
import handerTitle from '@/components/modal_handel_title'

export default {
  name: 'showCaptionBg',
  props: ['caption_bg'],
  data() {
    return {
      showType: '简视推荐',
      active_id: this.caption_bg.built_in[0].id,
      set_active_item: null
    }
  },
  components: {
    handerTitle
  },

  computed: {},

  watch: {},

  methods: {
    showTypeHandler(type) {
      this.showType = type
    },
    setActiveId() {
      if (this.set_active_item) {
        this.$emit('setCaptionId', this.set_active_item)
      }
      this.$store.commit('modalHidden', 'showCaptionBg')
    },
    setCaptionId(item, custom = false) {
      console.log('setCaptionId -> custom', custom)
      this.active_id = item.id
      this.set_active_item = {
        item: item,
        custom: custom
      }
    },
    close() {
      this.$emit('Modalclose')
    },
    getFileUrl(url, file) {
      console.log('getFileUrl -> getFileUrl', file)
      var fileType = file.type
      var formData = new FormData()

      formData.append('resource', file)
      formData.append('title', file.name)

      this.$post('/intelligent-creation/upload-caption-bg', formData).then(res => {
        console.log(res)
        this.$emit('updataCaptionBgImg')
      })
    },
    // 删除本地素材
    deleteRsource(item) {
      this.$deleteRun('/intelligent-creation/delete-caption-bg', { data: { id: item.id } })
        .then(res => {
          if (res.data.code == '0000') {
            this.$alertMsg('删除成功')
          } else {
            this.$alertMsg(res.data.msg)
          }
        })
        .then(res => {
          this.$emit('updataCaptionBgImg')
        })
    }
  },

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss">
.handel-title {
  display: flex;
  span {
    background: $c;
    display: block;
    display: block;
    border-radius: 3px;
    height: 32px;
    width: 100px;
    text-align: center;
    line-height: 32px;
    cursor: pointer;
    background: #e6e6e6;
    transition: all 0.3s;
    &.is-active {
      background: #c51b18;
      color: #fff;
    }
  }
}
.videom-video-wrap {
  width: 640px;
  height: 390px;
  margin: 0px auto;
  margin-bottom: 50px;
  #videoWrap {
    width: 100%;
    height: 100%;
  }
}

.caption_bg {
  width: 100%;
  height: 350px;
  overflow: hidden;
  overflow-y: auto;
  .custom {
    height: 120px;
    // overflow: hidden;
    // overflow-x: auto;
  }
  .img-wrap {
    width: 110px;
    height: 62px;
    min-width: 110px;
    min-height: 62px;
    margin-right: 15px;
    margin-top: 20px;
    margin-bottom: 40px;
    position: relative;
    background-color: #ccc;
    background-image: url('../assets/images/caption_bg/default.png');
    background-size: cover;
    text-align: center;
    &.active {
      border: 1px solid $c;
    }
    img {
      height: 100%;
      width: 100%;
      object-fit: cover;
    }
  }
  .sub-title {
    position: absolute;
    bottom: -23px;
    width: 100%;
    text-align: center;
  }
  .custom {
    .img-wrap {
      .icondelect {
        position: absolute;
        top: 5px;
        right: 5px;
        display: none;
        color: $c;
      }
      &:hover {
        .icondelect {
          display: block;
        }
      }
    }
  }
}
</style>
